package com.indra.bbva.crudnorelations.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class JobHistoryPK implements Serializable {
    private Long employee_id;
    private Date start_date;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof JobHistoryPK)) return false;
        JobHistoryPK that = (JobHistoryPK) o;
        return getEmployee_id().equals(that.getEmployee_id()) && getStart_date().equals(that.getStart_date());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getEmployee_id(), getStart_date());
    }

    public Long getEmployee_id() {
        return employee_id;
    }

    public void setEmployee_id(Long employee_id) {
        this.employee_id = employee_id;
    }

    public Date getStart_date() {
        return start_date;
    }

    public void setStart_date(Date start_date) {
        this.start_date = start_date;
    }
}
