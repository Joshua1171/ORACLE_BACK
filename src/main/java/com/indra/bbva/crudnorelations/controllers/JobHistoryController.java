package com.indra.bbva.crudnorelations.controllers;


import com.indra.bbva.crudnorelations.entities.JobHistory;
import com.indra.bbva.crudnorelations.services.job_history.IJobHistoryService;
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
public class JobHistoryController {

    @Autowired
    private IJobHistoryService jobHistoryService;

    @GetMapping("/jobHistory")
    public List<JobHistory> index(HttpServletRequest request){
        return jobHistoryService.findAll();
    }

    @GetMapping("/jobHistory/{id}")
    public ResponseEntity<?> mostrar(@PathVariable Long id){
        List<JobHistory> jobHistory;
        Map<String,Object> response=new HashMap<>();
        try{
            jobHistory=jobHistoryService.findAllById(id);
        }catch (DataAccessException e){
            response.put("mensaje","Error al hacer la consulta en la base de datos");
            response.put("error",e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String,Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if (jobHistory==null){
            response.put("mensaje","Id del cliente:".concat(id.toString().concat("no existe en la base de datos")));
            return new ResponseEntity<Map<String,Object>>(response,HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<List<JobHistory>>(jobHistory,HttpStatus.OK);
    }
    @PostMapping("/jobHistory")
    public ResponseEntity<?> crear(@Valid @RequestBody JobHistory jobHistory, BindingResult result){
        JobHistory c =null;

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
            c=jobHistoryService.save(jobHistory);
        }catch (DataAccessException e){
            response.put("mensaje","No se pudo insertar en la base de datos");
            response.put("error",e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
            new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("mensaje","El cliente fue creado satisfactoriamente");
        response.put("JobHistory",c);
        return new ResponseEntity<Map<String,Object>>(response,HttpStatus.CREATED);
    }

    @DeleteMapping("/jobHistory/{id}")
    public ResponseEntity<?> borrar(@PathVariable Long id){
        Map<String, Object> response = new HashMap<>();
        try {
            jobHistoryService.deleteById(id);
        }catch (DataAccessException e){
            response.put("mensaje","Error al hacer la consulta en la base de datos");
            response.put("error",e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String,Object>>(response,HttpStatus.NOT_FOUND);
        }
        response.put("mensaje","Country successfully removed!");
        return new ResponseEntity<Map<String,Object>>(response,HttpStatus.NOT_FOUND);
    }

    @PutMapping("/jobHistory/{id}")
    public ResponseEntity<?> editar(@Valid @RequestBody JobHistory jobHistory,BindingResult result,@PathVariable Long id){
        JobHistory actualJob=jobHistoryService.findOneById(id);
        JobHistory updatedJob=null;
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
            response.put("mensaje","Error: problema al editar ,el ID".concat(id.toString()).concat("no existe en la base de datos"));
            return new ResponseEntity<Map<String,Object>>(response,HttpStatus.NOT_FOUND);
        }
        try{
            actualJob.setEnd_date(jobHistory.getEnd_date());
            updatedJob=jobHistoryService.save(actualJob);
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
