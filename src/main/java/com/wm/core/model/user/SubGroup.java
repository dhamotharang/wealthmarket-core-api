package com.wm.core.model.user;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name = "wm_sub_groups")
public class SubGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "wm_sub_groups_seq")
    @SequenceGenerator(name = "wm_sub_groups_seq", sequenceName = "wm_sub_groups_seq", initialValue = 1, allocationSize = 1)
    private long id;

    @NotBlank(message = "Name is mandatory for sub-group")
    @Column(unique = true)
    private String name;


    @NotNull(message = "user is mandatory for creating sub group")
    private long userId;

    @JsonProperty(value = "date_created")
    private Date date_created;

    @JsonProperty(value = "last_updated")
    private Date last_updated;

    private long groupId;

    private long grouptypeId;

    public SubGroup() {
        super();
    }

    public SubGroup(@NotBlank(message = "Name is mandatory for sub-group") String name, @NotNull(message = "user is mandatory for creating sub group") long userId, Date date_created, Date last_updated, long groupId, long grouptypeId) {
        this.name = name;
        this.userId = userId;
        this.date_created = date_created;
        this.last_updated = last_updated;
        this.groupId = groupId;
        this.grouptypeId = grouptypeId;
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

    public long getGroupId() {
        return groupId;
    }

    public void setGroupId(long groupId) {
        this.groupId = groupId;
    }

    public long getGrouptypeId() {
        return grouptypeId;
    }

    public void setGrouptypeId(long grouptypeId) {
        this.grouptypeId = grouptypeId;
    }

    @Override
    public String toString() {
        return "SubGroup{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", userId=" + userId +
                ", date_created=" + date_created +
                ", last_updated=" + last_updated +
                ", groupId=" + groupId +
                ", grouptypeId=" + grouptypeId +
                '}';
    }
}
