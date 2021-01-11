package com.wm.core.model.accounts;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;

@Entity
@Table(name = "wm_special_account_rules")
public class SpecialAccountRules {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "wm_special_account_rules_seq")
	@SequenceGenerator(name = "wm_special_account_rule_seq", sequenceName = "wm_special_account_rule_seq", initialValue = 1, allocationSize = 1)
	private Long id;

	private Long userId;

	private Long userAccountId;

	private Long ruleId;

	private Long ruleTypeId;

	private String value;

	private Long createdBy;

	@JsonProperty(value = "date_created")
	private Date date_created;

	@JsonProperty(value = "last_updated")
	private Date last_updated;

	public SpecialAccountRules() {
		super();
	}

	public SpecialAccountRules(Long id, Long userId, Long userAccountId, Long ruleId, Long ruleTypeId, String value, Long createdBy) {
		this.id = id;
		this.userId = userId;
		this.userAccountId = userAccountId;
		this.ruleId = ruleId;
		this.ruleTypeId = ruleTypeId;
		this.value = value;
		this.createdBy = createdBy;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getUserAccountId() {
		return userAccountId;
	}

	public void setUserAccountId(Long userAccountId) {
		this.userAccountId = userAccountId;
	}

	public Long getRuleId() {
		return ruleId;
	}

	public void setRuleId(Long ruleId) {
		this.ruleId = ruleId;
	}

	public Long getRuleTypeId() {
		return ruleTypeId;
	}

	public void setRuleTypeId(Long ruleTypeId) {
		this.ruleTypeId = ruleTypeId;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
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

	public SpecialAccountRules(Long userId, Long userAccountId, Long ruleId, Long ruleTypeId, String value, Long createdBy, Date date_created, Date last_updated) {
		this.userId = userId;
		this.userAccountId = userAccountId;
		this.ruleId = ruleId;
		this.ruleTypeId = ruleTypeId;
		this.value = value;
		this.createdBy = createdBy;
		this.date_created = date_created;
		this.last_updated = last_updated;
	}
}
