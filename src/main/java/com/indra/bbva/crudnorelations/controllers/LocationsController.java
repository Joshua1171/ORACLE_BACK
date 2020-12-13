package com.indra.bbva.crudnorelations.controllers;


import com.indra.bbva.crudnorelations.entities.Countries;
import com.indra.bbva.crudnorelations.entities.Locations;
import com.indra.bbva.crudnorelations.services.locations.ILocationsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class LocationsController {

    @Autowired
    private ILocationsService locationsService;

    @GetMapping("/locations")
    public List<Locations> index(HttpServletRequest request){
        return locationsService.findAll();
    }
    @GetMapping("/locations/countries")
    public List<Locations> relacion(HttpServletRequest request){
        return locationsService.findRelations();
    }
    @GetMapping("/locations/{id}")
    public ResponseEntity<?> mostrar(@PathVariable Long id){
        Locations locations;
        Map<String,Object> response=new HashMap<>();
        try{
            locations=locationsService.findById(id);
        }catch (DataAccessException e){
            response.put("mensaje","Error al hacer la consulta en la base de datos");
            response.put("error",e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String,Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if (locations==null){
            response.put("mensaje","El ID:".concat(id.toString().concat("no existe en la base de datos")));
            return new ResponseEntity<Map<String,Object>>(response,HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Locations>(locations,HttpStatus.OK);
    }
    @PostMapping("/locations")
    public ResponseEntity<?> crear(@Valid @RequestBody Locations locations, BindingResult result){
        Locations l =null;

        Map<String,Object> response = new HashMap<>();
        if (result.hasErrors()){

            /*
            *  List<String> errors = new ArrayList<>();
            for(FieldError err: result.getFieldErrors()) {
                errors.add("El campo '" + err.getField() +"' "+ err.getDefaultMessage());
            }
            * */
            List<String> errors=result.getFieldErrors()
                    .stream()
                    .map(err -> "El campo"+err.getField()+""+err.getDefaultMessage())
                    .collect(Collectors.toList());
            response.put("errors",errors);
            new ResponseEntity<Map<String,Object>>(response,HttpStatus.BAD_REQUEST);
        }
        try{
            l=locationsService.save(locations);
        }catch (DataAccessException e){
            response.put("mensaje","No se pudo insertar en la base de datos");
            response.put("error",e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
            new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("mensaje","El registro fue creado satisfactoriamente");
        response.put("Locations",l);
        return new ResponseEntity<Map<String,Object>>(response,HttpStatus.CREATED);
    }

    @DeleteMapping("/locations/{id}")
    public ResponseEntity<?> borrar(@PathVariable Long id){
        Map<String, Object> response = new HashMap<>();
        try {
            locationsService.deleteById(id);
        }catch (DataAccessException e){
            response.put("mensaje","Error al hacer la consulta en la base de datos");
            response.put("error",e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String,Object>>(response,HttpStatus.NOT_FOUND);
        }
        response.put("mensaje","Country successfully removed!");
        return new ResponseEntity<Map<String,Object>>(response,HttpStatus.NOT_FOUND);
    }

    @PutMapping("/locations/{id}")
    public ResponseEntity<?> editar(@Valid @RequestBody Locations locations,BindingResult result,@PathVariable Long id){
        Locations actualLocation=locationsService.findById(id);
        Locations updatedLocation=null;
        Map<String, Object> response = new HashMap<>();

        if (result.hasErrors()){
            List<String> errors=result.getFieldErrors()
                    .stream()
                    .map(err -> "El campo"+err.getField()+""+err.getDefaultMessage())
                    .collect(Collectors.toList());
            response.put("errors",errors);
            new ResponseEntity<Map<String,Object>>(response,HttpStatus.BAD_REQUEST);
        }
        if (actualLocation==null){
            response.put("mensaje","Error: problema al editar ,el ID".concat(id.toString()).concat("no existe en la base de datos"));
            return new ResponseEntity<Map<String,Object>>(response,HttpStatus.NOT_FOUND);
        }
        try{
            actualLocation.setStreet_address(locations.getStreet_address());
            actualLocation.setPostal_code(locations.getPostal_code());
            actualLocation.setCity(locations.getCity());
            actualLocation.setState_province(locations.getState_province());
            updatedLocation=locationsService.save(actualLocation);
        }catch (DataAccessException e){
            response.put("mensaje","Error al actualizar registro en la base de datos");
            response.put("error",e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("mensaje","The Region has been updated successfully");
        response.put("country",updatedLocation);
        return new ResponseEntity<Map<String, Object>>(response,HttpStatus.CREATED);
    }
}
