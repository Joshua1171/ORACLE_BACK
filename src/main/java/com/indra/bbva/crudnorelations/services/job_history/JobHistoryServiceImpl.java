package com.indra.bbva.crudnorelations.services.job_history;


import com.indra.bbva.crudnorelations.dao.IJobHistoryDao;
import com.indra.bbva.crudnorelations.entities.JobHistory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobHistoryServiceImpl implements IJobHistoryService{

    @Autowired
    private IJobHistoryDao jobHistoryDao;

    @Override
    public List<JobHistory> findAll() {
        return (List<JobHistory>) jobHistoryDao.findAll();
    }

    @Override
    public List<JobHistory> findAllById(Long id) {
        return jobHistoryDao.buscarTodoPorId(id);
    }

    @Override
    public JobHistory findOneById(Long id) {
        return jobHistoryDao.buscarPorEmpleado(id);
    }

    @Override
    public void deleteById(Long id) {
        jobHistoryDao.eliminarPorIdEmpleado(id);
    }

    @Override
    public JobHistory save(JobHistory jobHistory) {
        return jobHistoryDao.save(jobHistory);
    }
}
