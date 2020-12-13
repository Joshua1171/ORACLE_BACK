package com.indra.bbva.crudnorelations.services.employees;

import com.indra.bbva.crudnorelations.dao.IEmployeesDao;
import com.indra.bbva.crudnorelations.entities.Employees;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeesServiceImpl implements IEmployeesServices {

    @Autowired
    private IEmployeesDao employeesDao;

    @Override
    public List<Employees> findAll() {
        return (List<Employees>) employeesDao.findAll();
    }

    @Override
    public Employees findById(Long id) {
        return employeesDao.findById(id).orElse(null);
    }

    @Override
    public void deleteById(Long id) {
        employeesDao.deleteById(id);
    }

    @Override
    public Employees save(Employees e) {
        return employeesDao.save(e);
    }
}
