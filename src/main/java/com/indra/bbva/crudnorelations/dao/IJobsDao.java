package com.indra.bbva.crudnorelations.dao;


import com.indra.bbva.crudnorelations.entities.Jobs;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

public interface IJobsDao extends CrudRepository<Jobs,String> {


    @Query("select j from Jobs j where j.job_id = ?1")
    public Jobs buscarPorId(String id);

    @Transactional
    @Modifying
    @Query("delete from Jobs j where j.job_id= ?1")
    public void eliminarPorId(String id);
}
