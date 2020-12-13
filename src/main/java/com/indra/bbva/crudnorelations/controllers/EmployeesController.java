package com.indra.bbva.crudnorelations.controllers;


import com.indra.bbva.crudnorelations.entities.Employees;
import com.indra.bbva.crudnorelations.services.employees.IEmployeesServices;
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
public class EmployeesController {

    @Autowired
    private IEmployeesServices employeesService;

    @GetMapping("/employees")
    public List<Employees> index(HttpServletRequest request){
        return employeesService.findAll();
    }
    @GetMapping("/employees/{id}")
    public ResponseEntity<?> mostrar(@PathVariable Long id){
        Employees employees;
        Map<String,Object> response=new HashMap<>();
        try{
            employees=employeesService.findById(id);
        }catch (DataAccessException e){
            response.put("mensaje","Error al hacer la consulta en la base de datos");
            response.put("error",e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String,Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if (employees==null){
            response.put("mensaje","Id del cliente:".concat(id.toString().concat("no existe en la base de datos")));
            return new ResponseEntity<Map<String,Object>>(response,HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Employees>(employees,HttpStatus.OK);
    }
    @PostMapping("/employees")
    public ResponseEntity<?> crear(@Valid @RequestBody Employees employees, BindingResult result){
        Employees c =null;

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
            c=employeesService.save(employees);
        }catch (DataAccessException e){
            response.put("mensaje","No se pudo insertar en la base de datos");
            response.put("error",e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
            new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("mensaje","El cliente fue creado satisfactoriamente");
        response.put("Employees",c);
        return new ResponseEntity<Map<String,Object>>(response,HttpStatus.CREATED);
    }

    @DeleteMapping("/employees/{id}")
    public ResponseEntity<?> borrar(@PathVariable Long id){
        Map<String, Object> response = new HashMap<>();
        try {
            employeesService.deleteById(id);
        }catch (DataAccessException e){
            response.put("mensaje","Error al hacer la consulta en la base de datos");
            response.put("error",e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String,Object>>(response,HttpStatus.NOT_FOUND);
        }
        response.put("mensaje","Country successfully removed!");
        return new ResponseEntity<Map<String,Object>>(response,HttpStatus.NOT_FOUND);
    }

    @PutMapping("/employees/{id}")
    public ResponseEntity<?> editar(@Valid @RequestBody Employees employees,BindingResult result,@PathVariable Long id){
        Employees actualEmployee=employeesService.findById(id);
        Employees updatedEmployee=null;
        Map<String, Object> response = new HashMap<>();

        if (result.hasErrors()){
            List<String> errors=result.getFieldErrors()
                    .stream()
                    .map(err -> "El campo"+err.getField()+""+err.getDefaultMessage())
                    .collect(Collectors.toList());
            response.put("errors",errors);
            new ResponseEntity<Map<String,Object>>(response,HttpStatus.BAD_REQUEST);
        }
        if (actualEmployee==null){
            response.put("mensaje","Error: problema al editar ,el ID".concat(id.toString()).concat("no existe en la base de datos"));
            return new ResponseEntity<Map<String,Object>>(response,HttpStatus.NOT_FOUND);
        }
        try{
            actualEmployee.setFirst_name(employees.getFirst_name());
            actualEmployee.setLast_name(employees.getLast_name());
            actualEmployee.setEmail(employees.getEmail());
            actualEmployee.setPhone_number(employees.getPhone_number());
            actualEmployee.setHire_date(employees.getHire_date());
            actualEmployee.setSalary(employees.getSalary());
            actualEmployee.setCommission_pct(employees.getCommission_pct());

            updatedEmployee=employeesService.save(actualEmployee);
        }catch (DataAccessException e){
            response.put("mensaje","Error al actualizar cliente en la base de datos");
            response.put("error",e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("mensaje","The country has been updated successfully");
        response.put("country",updatedEmployee);
        return new ResponseEntity<Map<String, Object>>(response,HttpStatus.CREATED);
    }
}
