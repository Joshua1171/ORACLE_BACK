package com.indra.bbva.crudnorelations.dao;

import com.indra.bbva.crudnorelations.entities.Employees;
import org.springframework.data.repository.CrudRepository;

public interface IEmployeesDao extends CrudRepository<Employees,Long> {
}
