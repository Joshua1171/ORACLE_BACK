package com.indra.bbva.crudnorelations.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer"})
public class Regions implements Serializable {
    private static final long serialVersionUID = -4112237509759230761L;

    @Id
    private Long region_id;


    private String region_name;

    @Override
    public String toString() {
        return "Regions{" +
                "region_id=" + region_id +
                ", region_name='" + region_name + '\'' +
                '}';
    }

    public Long getRegion_id() {
        return region_id;
    }

    public void setRegion_id(Long region_id) {
        this.region_id = region_id;
    }

    public String getRegion_name() {
        return region_name;
    }

    public void setRegion_name(String region_name) {
        this.region_name = region_name;
    }
}
