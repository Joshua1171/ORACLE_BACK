package com.indra.bbva.crudnorelations.services.regions;



import com.indra.bbva.crudnorelations.entities.Regions;

import java.util.List;


public interface IRegionsService {
    public List<Regions> findAll();
    public Regions findById(Long id);
    public void deleteById(Long id);
    public Regions save(Regions c);
}
