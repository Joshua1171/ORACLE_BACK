package com.indra.bbva.crudnorelations.services.job_history;



import com.indra.bbva.crudnorelations.entities.JobHistory;

import java.util.List;

public interface IJobHistoryService {
    public List<JobHistory> findAll();
    public List<JobHistory> findAllById(Long id);
    public JobHistory findOneById(Long id);
    public void deleteById(Long id);
    public JobHistory save(JobHistory jobHistory);
}
