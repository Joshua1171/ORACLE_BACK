package com.indra.bbva.crudnorelations.services.locations;

import com.indra.bbva.crudnorelations.entities.Countries;
import com.indra.bbva.crudnorelations.entities.Locations;


import java.util.List;

public interface ILocationsService {
    public List<Locations> findAll();
    public Locations findById(Long id);
    public void deleteById(Long id);
    public Locations save(Locations c);
    public List<Locations>findRelations();
}
