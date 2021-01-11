package com.wm.core.model.user;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;

@Entity
@Table(name = "wm_password_reset")
public class UserVerification {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "wm_password_reset_seq")
    @SequenceGenerator(name = "wm_password_reset_seq", sequenceName = "wm_password_reset_seq", initialValue = 1, allocationSize = 1)
    private long id;

    @NotBlank(message = "code is mandatory for email")
    @Column(unique = true)
    private String code;

    private long userId;

    private String email;

    private Date date_created;

    public UserVerification() {
        super();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getDate() {
        return date_created;
    }

    public void setDate(Date date) {
        this.date_created = date;
    }

    public UserVerification(@NotBlank(message = "code is mandatory for email") String code, long userId, String email, Date date_created) {
        this.code = code;
        this.userId = userId;
        this.email = email;
        this.date_created = date_created;
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

    @Override
    public String toString() {
        return "UserVerification{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", userId=" + userId +
                ", email='" + email + '\'' +
                ", date_created=" + date_created +
                '}';
    }
}
