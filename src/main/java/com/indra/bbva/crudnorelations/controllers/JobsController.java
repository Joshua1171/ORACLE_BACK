package com.indra.bbva.crudnorelations.controllers;


import com.indra.bbva.crudnorelations.entities.Jobs;
import com.indra.bbva.crudnorelations.services.jobs.IJobsService;
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
public class JobsController {

    @Autowired
    private IJobsService jobsService;

    @GetMapping("/jobs")
    public List<Jobs> index(HttpServletRequest request){
        return jobsService.findAll();
    }
    @GetMapping("/jobs/{id}")
    public ResponseEntity<?> mostrar(@PathVariable String id){
        Jobs jobs;
        Map<String,Object> response=new HashMap<>();
        try{
            jobs=jobsService.findById(id);
        }catch (DataAccessException e){
            response.put("mensaje","Error al hacer la consulta en la base de datos");
            response.put("error",e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String,Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if (jobs==null){
            response.put("mensaje","Id del cliente:".concat(id.concat("no existe en la base de datos")));
            return new ResponseEntity<Map<String,Object>>(response,HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Jobs>(jobs,HttpStatus.OK);
    }
    @PostMapping("/jobs")
    public ResponseEntity<?> crear(@Valid @RequestBody Jobs jobs, BindingResult result){
        Jobs j =null;

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
            j=jobsService.save(jobs);
        }catch (DataAccessException e){
            response.put("mensaje","No se pudo insertar en la base de datos");
            response.put("error",e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
            new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("mensaje","El cliente fue creado satisfactoriamente");
        response.put("Jobs",j);
        return new ResponseEntity<Map<String,Object>>(response,HttpStatus.CREATED);
    }

    @DeleteMapping("/jobs/{id}")
    public ResponseEntity<?> borrar(@PathVariable String id){
        Map<String, Object> response = new HashMap<>();
        try {
            jobsService.deleteById(id);
        }catch (DataAccessException e){
            response.put("mensaje","Error al hacer la consulta en la base de datos");
            response.put("error",e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String,Object>>(response,HttpStatus.NOT_FOUND);
        }
        response.put("mensaje","Country successfully removed!");
        return new ResponseEntity<Map<String,Object>>(response,HttpStatus.NOT_FOUND);
    }

    @PutMapping("/jobs/{id}")
    public ResponseEntity<?> editar(@Valid @RequestBody Jobs jobs,BindingResult result,@PathVariable String id){
        Jobs actualJob=jobsService.findById(id);
        Jobs updatedJob=null;
        Map<String, Object> response = new HashMap<>();

        if (result.hasErrors()){
            List<String> errors=result.getFieldErrors()
                    .stream()
                    .map(err -> "El campo"+err.getField()+""+err.getDefaultMessage())
                    .collect(Collectors.toList());
            response.put("errors",errors);
            new ResponseEntity<Map<String,Object>>(response,HttpStatus.BAD_REQUEST);
        }
        if (actualJob==null){
            response.put("mensaje","Error: problema al editar ,el ID".concat(id).concat("no existe en la base de datos"));
            return new ResponseEntity<Map<String,Object>>(response,HttpStatus.NOT_FOUND);
        }
        try{
            actualJob.setJob_tittle(jobs.getJob_tittle());
            actualJob.setMin_salary(jobs.getMin_salary());
            actualJob.setMax_salary(jobs.getMax_salary());
            updatedJob=jobsService.save(actualJob);
        }catch (DataAccessException e){
            response.put("mensaje","Error al actualizar cliente en la base de datos");
            response.put("error",e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("mensaje","The country has been updated successfully");
        response.put("country",updatedJob);
        return new ResponseEntity<Map<String, Object>>(response,HttpStatus.CREATED);
    }
}
