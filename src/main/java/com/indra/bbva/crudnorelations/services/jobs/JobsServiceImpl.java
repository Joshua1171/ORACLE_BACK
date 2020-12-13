package com.indra.bbva.crudnorelations.services.jobs;


import com.indra.bbva.crudnorelations.dao.IJobsDao;
import com.indra.bbva.crudnorelations.entities.Jobs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobsServiceImpl implements IJobsService{

    @Autowired
    private IJobsDao jobsDao;


    @Override
    public List<Jobs> findAll() {
        return (List<Jobs>) jobsDao.findAll();
    }

    @Override
    public Jobs findById(String id) {
        return jobsDao.buscarPorId(id);
    }

    @Override
    public void deleteById(String id) {
        jobsDao.eliminarPorId(id);
    }

    @Override
    public Jobs save(Jobs c) {
        return jobsDao.save(c);
    }
}
