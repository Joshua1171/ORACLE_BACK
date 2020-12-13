package com.indra.bbva.crudnorelations.dao;


import com.indra.bbva.crudnorelations.entities.Countries;
import com.indra.bbva.crudnorelations.entities.Locations;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ILocationsDao extends CrudRepository<Locations,Long> {
    @Query(value = "select * from Locations l INNER JOIN Countries c ON l.country_id = c.country_id",nativeQuery = true)
    public List<Locations> buscarRelacion();
}
