package com.indra.bbva.crudnorelations.dao;

import com.indra.bbva.crudnorelations.entities.Countries;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ICountriesDao extends JpaRepository<Countries,String> {

    @Query("select c from Countries c where c.country_id = ?1")
    public Countries buscarPorId(String id);

    //@Query(value = "DELETE FROM COUNTRIES WHERE COUNTRY_ID = ?1", nativeQuery = true)

    //@Query(value = "select c.country_name, r.region_name from Countries c INNER JOIN Regions r ON c.region_id = r.region_id",nativeQuery = true)
    //public List<CountriesRegions> buscarRelacion();

    //@Query(value = "select c.country_name, r.region_name from Countries c INNER JOIN Regions r ON c.region_id = r.region_id",nativeQuery = true)
    @Query(value = "select * from Countries c INNER JOIN Regions r ON c.region_id = r.region_id",nativeQuery = true)
    public List<Countries> buscarRelacion();

    //@Query("select c from Countries c JOIN fetch c.regions where r.region_id =?1")
   // public Countries findCountryByRegionId(Long id);


    @Transactional
    @Modifying
    @Query("delete from Countries c where c.country_id= ?1")
    public void eliminarPorId(String id);
}
