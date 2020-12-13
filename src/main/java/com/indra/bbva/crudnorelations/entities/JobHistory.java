package com.indra.bbva.crudnorelations.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Entity
@IdClass(JobHistoryPK.class)
@JsonIgnoreProperties({"hibernateLazyInitializer"})
@Table(name = "job_history")
public class JobHistory implements Serializable {

    private static final long serialVersionUID = 7598432306797093770L;

    //@MapsId("employee_id")
    @Id
    private Long employee_id;

    //@EmbeddedId
    //private Algo llave_compuesta;

    //@MapsId("start_date")
    @Id
    private Date start_date;


    private Date end_date;

    private String job_id;

    private Long department_id;


    @Override
    public String toString() {
        return "JobHistory{" +
                "employee_id=" + employee_id +
                ", start_date=" + start_date +
                ", end_date=" + end_date +
                ", job_id='" + job_id + '\'' +
                ", department_id=" + department_id +
                '}';
    }

    public Date getStart_date() {
        return start_date;
    }

    public void setStart_date(Date start_date) {
        this.start_date = start_date;
    }

    public Date getEnd_date() {
        return end_date;
    }

    public void setEnd_date(Date end_date) {
        this.end_date = end_date;
    }

    public Long getEmployee_id() {
        return employee_id;
    }

    public void setEmployee_id(Long employee_id) {
        this.employee_id = employee_id;
    }



    public String getJob_id() {
        return job_id;
    }

    public void setJob_id(String job_id) {
        this.job_id = job_id;
    }

    public Long getDepartment_id() {
        return department_id;
    }

    public void setDepartment_id(Long department_id) {
        this.department_id = department_id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof JobHistory)) return false;
        JobHistory that = (JobHistory) o;
        return getEmployee_id().equals(that.getEmployee_id()) && getStart_date().equals(that.getStart_date());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getEmployee_id(), getStart_date());
    }
}
