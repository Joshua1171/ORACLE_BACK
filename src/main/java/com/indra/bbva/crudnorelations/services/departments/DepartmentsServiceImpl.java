package com.indra.bbva.crudnorelations.services.departments;

import com.indra.bbva.crudnorelations.dao.IDepartmetsDao;

import com.indra.bbva.crudnorelations.entities.Departments;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentsServiceImpl implements IDepartmentsService{

    @Autowired
    private IDepartmetsDao departmetsDao;

    @Override
    public List<Departments> findAll() {
        return (List<Departments>) departmetsDao.findAll();
    }

    @Override
    public Departments findById(Long id) {
        return departmetsDao.findById(id).orElse(null);
    }

    @Override
    public void deleteById(Long id) {
        departmetsDao.deleteById(id);
    }

    @Override
    public Departments save(Departments c) {
        return null;
    }
}
