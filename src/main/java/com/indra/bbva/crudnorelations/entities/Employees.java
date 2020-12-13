package com.indra.bbva.crudnorelations.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.io.Serializable;
import java.util.Date;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer"})
public class Employees implements Serializable {

    private static final long serialVersionUID = 3847828725908770386L;

    @Id
    private Long employee_id;

    private String first_name;
    private String last_name;

    @Email
    @Column(unique = true)
    private String email;
    private String phone_number;
    private Date hire_date;

    private String job_id;
    private Integer salary;
    private Integer commission_pct;

    private Long manager_id;

    /*
    @OneToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL,mappedBy = "manager_id")
    @JoinColumn(name = "manager_id",referencedColumnName = "manager_id")
    private Employees manager;
    */
    private Long department_id;



    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Long getEmployee_id() {
        return employee_id;
    }

    public void setEmployee_id(Long employee_id) {
        this.employee_id = employee_id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public Date getHire_date() {
        return hire_date;
    }

    public void setHire_date(Date hire_date) {
        this.hire_date = hire_date;
    }


    public Integer getSalary() {
        return salary;
    }

    public Integer getCommission_pct() {
        return commission_pct;
    }

    public Long getManager_id() {
        return manager_id;
    }

    public void setManager_id(Long manager_id) {
        this.manager_id = manager_id;
    }

    public String getJob_id() {
        return job_id;
    }

    public void setJob_id(String job_id) {
        this.job_id = job_id;
    }

    public void setSalary(Integer salary) {
        this.salary = salary;
    }

    public void setCommission_pct(Integer commission_pct) {
        this.commission_pct = commission_pct;
    }

    public Long getDepartment_id() {
        return department_id;
    }

    public void setDepartment_id(Long department_id) {
        this.department_id = department_id;
    }
}
