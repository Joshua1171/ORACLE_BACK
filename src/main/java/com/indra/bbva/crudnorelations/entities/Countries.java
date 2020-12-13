package com.indra.bbva.crudnorelations.entities;



import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer"})
public class Countries implements Serializable {


    private static final long serialVersionUID = -6184806735949850277L;

    @Id
    @Column(columnDefinition = "char(2)")
    private String country_id;

    private String country_name;

    private Long region_id;

    @Override
    public String toString() {
        return "Countries{" +
                "country_id='" + country_id + '\'' +
                ", country_name='" + country_name + '\'' +
                ", region_id=" + region_id +
                '}';
    }

    public Long getRegion_id() {
        return region_id;
    }

    public void setRegion_id(Long region_id) {
        this.region_id = region_id;
    }

    public String getCountry_id() {
        return country_id;
    }

    public void setCountry_id(String country_id) {
        this.country_id = country_id;
    }

    public String getCountry_name() {
        return country_name;
    }

    public void setCountry_name(String country_name) {
        this.country_name = country_name;
    }

}
