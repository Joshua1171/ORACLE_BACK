package com.indra.bbva.crudnorelations.services.countries;



import com.indra.bbva.crudnorelations.entities.Countries;


import java.util.List;

public interface ICountriesService {

    public List<Countries> findAll();
    public Countries findById(String id);
    public void deleteById(String id);
    public Countries save(Countries c);
    public List<Countries>findRelations();
}
