package com.indra.bbva.crudnorelations.controllers;

import com.indra.bbva.crudnorelations.entities.Countries;
import com.indra.bbva.crudnorelations.services.countries.ICountriesService;

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

@CrossOrigin(origins ="http://localhost:4200")
@RestController
public class CountriesController {

    @Autowired
    private ICountriesService countriesService;

    @GetMapping("/countries")
    public List<Countries> index(HttpServletRequest request){
        return countriesService.findAll();
    }
    @GetMapping("/relacion")
    public List<Countries> relacion(){
        return countriesService.findRelations();
    }
    @GetMapping("/countries/{id}")
    public ResponseEntity<?> mostrar(@PathVariable String id){
        Countries countries;
        Map<String,Object> response=new HashMap<>();
        try{
            countries=countriesService.findById(id);
        }catch (DataAccessException e){
            response.put("mensaje","Error al hacer la consulta en la base de datos");
            response.put("error",e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String,Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if (countries==null){
            response.put("mensaje","Id del cliente:".concat(id.concat("no existe en la base de datos")));
            return new ResponseEntity<Map<String,Object>>(response,HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Countries>(countries,HttpStatus.OK);
    }
    @PostMapping("/countries")
    public ResponseEntity<?> crear(@Valid @RequestBody Countries countries, BindingResult result){
        Countries c =null;

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
            c=countriesService.save(countries);
        }catch (DataAccessException e){
            response.put("mensaje","No se pudo insertar en la base de datos");
            response.put("error",e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
            new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("mensaje","El cliente fue creado satisfactoriamente");
        response.put("countries",c);
        return new ResponseEntity<Map<String,Object>>(response,HttpStatus.CREATED);
    }

    @DeleteMapping("/countries/{id}")
    public ResponseEntity<?> borrar(@PathVariable String id){
        Map<String, Object> response = new HashMap<>();
        try {
            countriesService.deleteById(id);
        }catch (DataAccessException e){
            response.put("mensaje","Error al hacer la consulta en la base de datos");
            response.put("error",e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String,Object>>(response,HttpStatus.NOT_FOUND);
        }
        response.put("mensaje","Country successfully removed!");
        return new ResponseEntity<Map<String,Object>>(response,HttpStatus.NOT_FOUND);
    }

    @PutMapping("/countries/{id}")
    public ResponseEntity<?> editar(@Valid @RequestBody Countries countries,BindingResult result,@PathVariable String id){
        Countries actualCountry=countriesService.findById(id);
        Countries updatedCountry=null;
        Map<String, Object> response = new HashMap<>();

        if (result.hasErrors()){
            List<String> errors=result.getFieldErrors()
                    .stream()
                    .map(err -> "El campo"+err.getField()+""+err.getDefaultMessage())
                    .collect(Collectors.toList());
            response.put("errors",errors);
            new ResponseEntity<Map<String,Object>>(response,HttpStatus.BAD_REQUEST);
        }
        if (actualCountry==null){
            response.put("mensaje","Error: problema al editar ,el ID".concat(id).concat("no existe en la base de datos"));
            return new ResponseEntity<Map<String,Object>>(response,HttpStatus.NOT_FOUND);
        }
        try{
            actualCountry.setCountry_id(countries.getCountry_id());
            actualCountry.setCountry_name(countries.getCountry_name());
           // actualCountry.setRegion_id(countries.getRegion_id());

            updatedCountry=countriesService.save(actualCountry);
        }catch (DataAccessException e){
            response.put("mensaje","Error al actualizar cliente en la base de datos");
            response.put("error",e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
        }
            response.put("mensaje","The country has been updated successfully");
            response.put("country",updatedCountry);
            return new ResponseEntity<Map<String, Object>>(response,HttpStatus.CREATED);
    }

}
