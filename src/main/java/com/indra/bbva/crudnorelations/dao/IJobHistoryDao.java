package com.indra.bbva.crudnorelations.dao;

import com.indra.bbva.crudnorelations.entities.JobHistory;

import com.indra.bbva.crudnorelations.entities.JobHistoryPK;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

public interface IJobHistoryDao extends CrudRepository<JobHistory, JobHistoryPK> {

    @Query("select j from JobHistory j")
    public JobHistory buscarTodo();

    @Query("select j from JobHistory j where j.start_date = ?1")
    public JobHistory buscarPorFecha(String id);

    @Query(value = "select * from JOB_HISTORY where employee_id = ?1",nativeQuery = true)
    public List<JobHistory> buscarTodoPorId(Long id);




    @Query("select j from JobHistory j where j.employee_id = ?1")
    public JobHistory buscarPorEmpleado(Long id);


    //@Query(value = "DELETE FROM COUNTRIES WHERE COUNTRY_ID = ?1", nativeQuery = true)

    @Query(value = "insert into JobHistory (employee_id,start_date,end_date,job_id,department_id) values (:employee_id,:start_date,:end_date,:job_id,:department_id)",nativeQuery = true)
    public JobHistory guardar(@Param("employee_id") Long employee_id, @Param("start_date")Date start_date,@Param("end_date")Date end_date,@Param("job_id")String job_id,@Param("department_id")Long department_id);

    @Transactional
    @Modifying
    @Query("delete from JobHistory j where j.employee_id= ?1")
    public void eliminarPorIdEmpleado(Long id);

    @Transactional
    @Modifying
    @Query("delete from JobHistory j where j.start_date= ?1")
    public void eliminarPorIdFecha(Date id);


}
