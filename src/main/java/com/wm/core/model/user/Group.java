package com.wm.core.model.user;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name = "wm_groups")
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "wm_groups_seq")
    @SequenceGenerator(name = "wm_groups_seq", sequenceName = "wm_groups_seq", initialValue = 1, allocationSize = 1)
    private long id;

    @NotBlank(message = "name is mandatory for group")
    @Column(unique = true)
    private String name;

    private long grouptypeId;

    @NotNull(message = "user is mandatory for creating Group")
    private long userId;

    @JsonProperty(value = "date_created")
    private Date date_created;

    @JsonProperty(value = "last_updated")
    private Date last_updated;

    public Group() {
        super();
    }

    public Group(@NotBlank(message = "name is mandatory for group") String name) {
        this.name = name;
    }

    public Group(long id, @NotBlank(message = "name is mandatory for group") String name) {
        this.id = id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getGrouptypeId() {
        return grouptypeId;
    }

    public void setGrouptypeId(long grouptypeId) {
        this.grouptypeId = grouptypeId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public Date getDate_created() {
        return date_created;
    }

    public void setDate_created(Date date_created) {
        this.date_created = date_created;
    }

    public Date getLast_updated() {
        return last_updated;
    }

    public void setLast_updated(Date last_updated) {
        this.last_updated = last_updated;
    }

    public Group(@NotBlank(message = "name is mandatory for group") String name, long grouptypeId, @NotNull(message = "user is mandatory for creating Group") long userId, Date date_created, Date last_updated) {
        this.name = name;
        this.grouptypeId = grouptypeId;
        this.userId = userId;
        this.date_created = date_created;
        this.last_updated = last_updated;
    }

    @Override
    public String toString() {
        return "Group{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", grouptypeId=" + grouptypeId +
                ", userId=" + userId +
                ", date_created=" + date_created +
                ", last_updated=" + last_updated +
                '}';
    }
}
