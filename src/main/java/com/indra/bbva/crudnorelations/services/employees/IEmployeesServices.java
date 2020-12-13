package com.indra.bbva.crudnorelations.services.employees;





import com.indra.bbva.crudnorelations.entities.Employees;

import java.util.List;

public interface IEmployeesServices {
    public List<Employees> findAll();
    public Employees findById(Long id);
    public void deleteById(Long id);
    public Employees save(Employees e);
}
