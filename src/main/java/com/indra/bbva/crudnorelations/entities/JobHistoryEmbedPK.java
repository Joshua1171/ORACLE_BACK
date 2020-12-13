package com.indra.bbva.crudnorelations.entities;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Embeddable
public class JobHistoryEmbedPK implements Serializable {

    @Column(name = "employee_id")
    private Long employee_id;

    @Column(name = "start_date")
    private Date start_date;

    public JobHistoryEmbedPK(Long employee_id, Date start_date) {
        this.employee_id = employee_id;
        this.start_date = start_date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof JobHistoryEmbedPK)) return false;
        JobHistoryEmbedPK that = (JobHistoryEmbedPK) o;
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
