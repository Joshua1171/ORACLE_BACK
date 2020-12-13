package com.indra.bbva.crudnorelations.services.locations;

import com.indra.bbva.crudnorelations.dao.ILocationsDao;

import com.indra.bbva.crudnorelations.entities.Locations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LocationsServiceImpl implements ILocationsService {

    @Autowired
    private ILocationsDao locationsDao;
    @Override
    public List<Locations> findAll() {
        return (List<Locations>) locationsDao.findAll();
    }

    @Override
    public Locations findById(Long id) {
        return locationsDao.findById(id).orElse(null);
    }

    @Override
    public void deleteById(Long id) {
        locationsDao.deleteById(id);
    }

    @Override
    public Locations save(Locations c) {
        return locationsDao.save(c);
    }

    @Override
    public List<Locations> findRelations() {
        return locationsDao.buscarRelacion();
    }
}
