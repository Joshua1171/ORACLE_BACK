package com.indra.bbva.crudnorelations.dao;


import com.indra.bbva.crudnorelations.entities.Regions;
import org.springframework.data.repository.CrudRepository;

public interface IRegionsDao extends CrudRepository<Regions,Long> {
}
