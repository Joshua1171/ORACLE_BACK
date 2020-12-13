package com.indra.bbva.crudnorelations.controllers;


import com.indra.bbva.crudnorelations.entities.Departments;
import com.indra.bbva.crudnorelations.services.departments.IDepartmentsService;
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
public class DepartmentsController {

    @Autowired
    private IDepartmentsService departmentsService;

    @GetMapping("/departments")
    public List<Departments> index(HttpServletRequest request){
        return departmentsService.findAll();
    }

    @GetMapping("/departments/{id}")
    public ResponseEntity<?> mostrar(@PathVariable Long id){
        Departments departments;
        Map<String,Object> response=new HashMap<>();
        try{
            departments=departmentsService.findById(id);
        }catch (DataAccessException e){
            response.put("mensaje","Error al hacer la consulta en la base de datos");
            response.put("error",e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String,Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if (departments==null){
            response.put("mensaje","Id del cliente:".concat(id.toString().concat("no existe en la base de datos")));
            return new ResponseEntity<Map<String,Object>>(response,HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Departments>(departments,HttpStatus.OK);
    }
    @PostMapping("/departments")
    public ResponseEntity<?> crear(@Valid @RequestBody Departments departments, BindingResult result){
        Departments d =null;

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
            d=departmentsService.save(departments);
        }catch (DataAccessException e){
            response.put("mensaje","No se pudo insertar en la base de datos");
            response.put("error",e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
            new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("mensaje","El cliente fue creado satisfactoriamente");
        response.put("Departments",d);
        return new ResponseEntity<Map<String,Object>>(response,HttpStatus.CREATED);
    }

    @DeleteMapping("/departments/{id}")
    public ResponseEntity<?> borrar(@PathVariable Long id){
        Map<String, Object> response = new HashMap<>();
        try {
            departmentsService.deleteById(id);
        }catch (DataAccessException e){
            response.put("mensaje","Error al hacer la consulta en la base de datos");
            response.put("error",e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String,Object>>(response,HttpStatus.NOT_FOUND);
        }
        response.put("mensaje","Country successfully removed!");
        return new ResponseEntity<Map<String,Object>>(response,HttpStatus.NOT_FOUND);
    }

    @PutMapping("/departments/{id}")
    public ResponseEntity<?> editar(@Valid @RequestBody Departments departments,BindingResult result,@PathVariable Long id){
        Departments actualDepartment=departmentsService.findById(id);
        Departments updatedDepartment=null;
        Map<String, Object> response = new HashMap<>();

        if (result.hasErrors()){
            List<String> errors=result.getFieldErrors()
                    .stream()
                    .map(err -> "El campo"+err.getField()+""+err.getDefaultMessage())
                    .collect(Collectors.toList());
            response.put("errors",errors);
            new ResponseEntity<Map<String,Object>>(response,HttpStatus.BAD_REQUEST);
        }
        if (actualDepartment==null){
            response.put("mensaje","Error: problema al editar ,el ID".concat(id.toString()).concat("no existe en la base de datos"));
            return new ResponseEntity<Map<String,Object>>(response,HttpStatus.NOT_FOUND);
        }
        try{
            actualDepartment.setDepartment_name(departments.getDepartment_name());
            actualDepartment.setManager_id(departments.getManager_id());
            actualDepartment.setLocation_id(departments.getLocation_id());
            updatedDepartment=departmentsService.save(actualDepartment);
        }catch (DataAccessException e){
            response.put("mensaje","Error al actualizar cliente en la base de datos");
            response.put("error",e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("mensaje","The country has been updated successfully");
        response.put("country",updatedDepartment);
        return new ResponseEntity<Map<String, Object>>(response,HttpStatus.CREATED);
    }

}
