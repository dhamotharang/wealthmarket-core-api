package com.wm.core.model.user;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

public class NewOrganization {

    private long owner_userId;

    private long creator_userId;

    @NotBlank(message = "Email is mandatory")
    @Email(message = "Email must be a valid email address")
    private String email;

    @NotBlank(message = "Phone is mandatory")
    private String phone;

    @NotBlank(message = "Name is mandatory")
    private String name;

    @NotNull(message = "group type is mandatory")
    private long group_typeId;

    private long groupId;

    private long sub_groupId;

    private long member_typeId;

    private long sub_member_typeId;


    //--------------------------Agencies specific
    @NotBlank(message = "status is mandatory for agency")
    private long statusId;

    private String agency_cac_number;


    //-------------------Producers specific
    private String producer_cac_number;


    //---------------------Businesses specific
    private String business_description;

    private long business_sectorId;

    private long business_typeId;

    private  String business_cac_number;

    private Date date_founded;

    private String website_link;


    public NewOrganization() {
        super();
    }

    public long getOwner_userId() {
        return owner_userId;
    }

    public void setOwner_userId(long owner_userId) {
        this.owner_userId = owner_userId;
    }

    public long getCreator_userId() {
        return creator_userId;
    }

    public void setCreator_userId(long creator_userId) {
        this.creator_userId = creator_userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getGroup_typeId() {
        return group_typeId;
    }

    public void setGroup_typeId(long group_typeId) {
        this.group_typeId = group_typeId;
    }

    public long getGroupId() {
        return groupId;
    }

    public void setGroupId(long groupId) {
        this.groupId = groupId;
    }

    public long getSub_groupId() {
        return sub_groupId;
    }

    public void setSub_groupId(long sub_groupId) {
        this.sub_groupId = sub_groupId;
    }

    public long getMember_typeId() {
        return member_typeId;
    }

    public void setMember_typeId(long member_typeId) {
        this.member_typeId = member_typeId;
    }

    public long getSub_member_typeId() {
        return sub_member_typeId;
    }

    public void setSub_member_typeId(long sub_member_typeId) {
        this.sub_member_typeId = sub_member_typeId;
    }

    public long getStatusId() {
        return statusId;
    }

    public void setStatusId(long statusId) {
        this.statusId = statusId;
    }
  
    public String getAgency_cac_number() {
        return agency_cac_number;
    }

    public void setAgency_cac_number(String agency_cac_number) {
        this.agency_cac_number = agency_cac_number;
    }

    public String getProducer_cac_number() {
        return producer_cac_number;
    }

    public void setProducer_cac_number(String producer_cac_number) {
        this.producer_cac_number = producer_cac_number;
    }

    public String getBusiness_description() {
        return business_description;
    }

    public void setBusiness_description(String business_description) {
        this.business_description = business_description;
    }

    public long getBusiness_sectorId() {
        return business_sectorId;
    }

    public void setBusiness_sectorId(long business_sectorId) {
        this.business_sectorId = business_sectorId;
    }

    public long getBusiness_typeId() {
        return business_typeId;
    }

    public void setBusiness_typeId(long business_typeId) {
        this.business_typeId = business_typeId;
    }

    public String getBusiness_cac_number() {
        return business_cac_number;
    }

    public void setBusiness_cac_number(String business_cac_number) {
        this.business_cac_number = business_cac_number;
    }

    public Date getDate_founded() {
        return date_founded;
    }

    public void setDate_founded(Date date_founded) {
        this.date_founded = date_founded;
    }

    public String getWebsite_link() {
        return website_link;
    }

    public void setWebsite_link(String website_link) {
        this.website_link = website_link;
    }

    @Override
    public String toString() {
        return "NewOrganization{" +
                "owner_userId=" + owner_userId +
                ", creator_userId=" + creator_userId +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", name='" + name + '\'' +
                ", group_typeId=" + group_typeId +
                ", groupId=" + groupId +
                ", sub_groupId=" + sub_groupId +
                ", member_typeId=" + member_typeId +
                ", sub_member_typeId=" + sub_member_typeId +
                ", statusId=" + statusId +
                ", agency_cac_number='" + agency_cac_number + '\'' +
                ", producer_cac_number='" + producer_cac_number + '\'' +
                ", business_description='" + business_description + '\'' +
                ", business_sectorId=" + business_sectorId +
                ", business_typeId=" + business_typeId +
                ", business_cac_number='" + business_cac_number + '\'' +
                ", date_founded=" + date_founded +
                ", website_link='" + website_link + '\'' +
                '}';
    }
}
