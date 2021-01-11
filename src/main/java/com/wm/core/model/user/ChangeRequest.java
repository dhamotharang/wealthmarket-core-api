package com.wm.core.model.user;

import javax.validation.constraints.NotBlank;

public class ChangeRequest {

    @NotBlank(message = "subject is mandatory")
    private String subject;

    @NotBlank(message = "please provide password")
    private String password;

    @NotBlank(message = "new detail to set is mandatory")
    private String new_detail;

    public ChangeRequest(@NotBlank(message = "subject is mandatory") String subject, @NotBlank(message = "detail to change is mandatory") String password, @NotBlank(message = "new detail to set is mandatory") String new_detail) {
        super();
        this.subject = subject;
        this.password = password;
        this.new_detail = new_detail;
    }

    public ChangeRequest() {
        super();
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNew_detail() {
        return new_detail;
    }

    public void setNew_detail(String new_detail) {
        this.new_detail = new_detail;
    }

    @Override
    public String toString() {
        return "ChangeRequest{" +
                "subject='" + subject + '\'' +
                ", password='" + password + '\'' +
                ", new_detail='" + new_detail + '\'' +
                '}';
    }
}
