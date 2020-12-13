package com.indra.bbva.crudnorelations.controllers;


import com.indra.bbva.crudnorelations.entities.Regions;
import com.indra.bbva.crudnorelations.services.regions.IRegionsService;
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
public class RegionsController {

    @Autowired
    private IRegionsService regionsService;

    @GetMapping("/regions")
    public List<Regions> index(HttpServletRequest request){
        return regionsService.findAll();
    }
    @GetMapping("/regions/{id}")
    public ResponseEntity<?> mostrar(@PathVariable Long id){
        Regions regions;
        Map<String,Object> response=new HashMap<>();
        try{
            regions=regionsService.findById(id);
        }catch (DataAccessException e){
            response.put("mensaje","Error al hacer la consulta en la base de datos");
            response.put("error",e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String,Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if (regions==null){
            response.put("mensaje","Id del cliente:".concat(id.toString().concat("no existe en la base de datos")));
            return new ResponseEntity<Map<String,Object>>(response,HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Regions>(regions,HttpStatus.OK);
    }
    @PostMapping("/regions")
    public ResponseEntity<?> crear(@Valid @RequestBody Regions regions, BindingResult result){
        Regions c =null;

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
            c=regionsService.save(regions);
        }catch (DataAccessException e){
            response.put("mensaje","No se pudo insertar en la base de datos");
            response.put("error",e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
            new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("mensaje","El registro fue creado satisfactoriamente");
        response.put("Regions",c);
        return new ResponseEntity<Map<String,Object>>(response,HttpStatus.CREATED);
    }

    @DeleteMapping("/regions/{id}")
    public ResponseEntity<?> borrar(@PathVariable Long id){
        Map<String, Object> response = new HashMap<>();
        try {
            regionsService.deleteById(id);
        }catch (DataAccessException e){
            response.put("mensaje","Error al hacer la consulta en la base de datos");
            response.put("error",e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String,Object>>(response,HttpStatus.NOT_FOUND);
        }
        response.put("mensaje","Country successfully removed!");
        return new ResponseEntity<Map<String,Object>>(response,HttpStatus.NOT_FOUND);
    }

    @PutMapping("/Regions/{id}")
    public ResponseEntity<?> editar(@Valid @RequestBody Regions regions,BindingResult result,@PathVariable Long id){
        Regions actualRegion=regionsService.findById(id);
        Regions updatedRegion=null;
        Map<String, Object> response = new HashMap<>();

        if (result.hasErrors()){
            List<String> errors=result.getFieldErrors()
                    .stream()
                    .map(err -> "El campo"+err.getField()+""+err.getDefaultMessage())
                    .collect(Collectors.toList());
            response.put("errors",errors);
            new ResponseEntity<Map<String,Object>>(response,HttpStatus.BAD_REQUEST);
        }
        if (actualRegion==null){
            response.put("mensaje","Error: problema al editar ,el ID".concat(id.toString()).concat("no existe en la base de datos"));
            return new ResponseEntity<Map<String,Object>>(response,HttpStatus.NOT_FOUND);
        }
        try{
            actualRegion.setRegion_name(regions.getRegion_name());
            updatedRegion=regionsService.save(actualRegion);
        }catch (DataAccessException e){
            response.put("mensaje","Error al actualizar registro en la base de datos");
            response.put("error",e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("mensaje","The Region has been updated successfully");
        response.put("country",updatedRegion);
        return new ResponseEntity<Map<String, Object>>(response,HttpStatus.CREATED);
    }
}
