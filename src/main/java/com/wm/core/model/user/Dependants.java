package com.wm.core.model.user;

import javax.persistence.*;

@Entity
@Table(name = "wm_dependants")
public class Dependants {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "wm_dependants_seq")
    @SequenceGenerator(name = "wm_dependants_seq", sequenceName = "wm_dependants_seq", initialValue = 1, allocationSize = 1)
    private long id;

    private long parentuserId;

    private long dependantuserId;

    private long statusId;

    public Dependants() {
        super();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Dependants(long parentuserId, long dependantuserId, long statusId) {
        this.parentuserId = parentuserId;
        this.dependantuserId = dependantuserId;
        this.statusId = statusId;
    }

    public long getParentuserId() {
        return parentuserId;
    }

    public void setParentuserId(long parentuserId) {
        this.parentuserId = parentuserId;
    }

    public long getDependantuserId() {
        return dependantuserId;
    }

    public void setDependantuserId(long dependantuserId) {
        this.dependantuserId = dependantuserId;
    }

    public long getStatusId() {
        return statusId;
    }

    public void setStatusId(long statusId) {
        this.statusId = statusId;
    }

    @Override
    public String toString() {
        return "Dependants{" +
                "id=" + id +
                ", parentuserId=" + parentuserId +
                ", dependantuserId=" + dependantuserId +
                ", statusId=" + statusId +
                '}';
    }
}
