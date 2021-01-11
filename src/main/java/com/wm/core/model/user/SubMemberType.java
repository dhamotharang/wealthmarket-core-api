package com.wm.core.model.user;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name = "wm_sub_member_types")
public class SubMemberType {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "wm_sub_member_types_seq")
    @SequenceGenerator(name = "wm_sub_member_types_seq", sequenceName = "wm_sub_member_types_seq", initialValue = 1, allocationSize = 1)
    private long id;

    @NotBlank(message = "name is mandatory for member type")
    @Column(unique = true)
    private String name;

    private long membertypeId;

    @NotNull(message = "user is mandatory for creating Sub Member Type")
    private long userId;

    @JsonProperty(value = "date_created")
    private Date date_created;

    @JsonProperty(value = "last_updated")
    private Date last_updated;

    public SubMemberType() {
        super();
    }

    public SubMemberType(@NotBlank(message = "name is mandatory for member type") String name, long membertypeId, @NotNull(message = "user is mandatory for creating Sub Member Type") long userId, Date date_created, Date last_updated) {
        this.name = name;
        this.membertypeId = membertypeId;
        this.userId = userId;
        this.date_created = date_created;
        this.last_updated = last_updated;
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

    public long getMembertypeId() {
        return membertypeId;
    }

    public void setMembertypeId(long membertypeId) {
        this.membertypeId = membertypeId;
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

    @Override
    public String toString() {
        return "SubMemberType{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", membertypeId=" + membertypeId +
                ", userId=" + userId +
                ", date_created=" + date_created +
                ", last_updated=" + last_updated +
                '}';
    }
}
