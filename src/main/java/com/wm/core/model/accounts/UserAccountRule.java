package com.wm.core.model.accounts;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;

@Entity
@Table(name = "wm_account_rule")
public class UserAccountRule {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "wm_account_rule_seq")
	@SequenceGenerator(name = "wm_account_rule_seq", sequenceName = "wm_account_rule_seq", initialValue = 1, allocationSize = 1)
	private Long id;

	private Long accountId;

	@NotBlank(message = "Specify the accounting rule")
	private Long ruleId;

	@NotBlank(message = "Specify the accounting rule type")
	private Long ruleTypeId;

	@NotBlank(message = "Specify the value")
	private String value;

	private Long userId;

	@JsonProperty(value = "date_created")
	private Date date_created;

	@JsonProperty(value = "last_updated")
	private Date last_updated;

	public UserAccountRule() {
		super();
	}

	public UserAccountRule(Long accountId, @NotBlank(message = "Specify the accounting rule") Long ruleId, @NotBlank(message = "Specify the accounting rule type") Long ruleTypeId, @NotBlank(message = "Specify the value") String value, Long userId) {
		this.accountId = accountId;
		this.ruleId = ruleId;
		this.ruleTypeId = ruleTypeId;
		this.value = value;
		this.userId = userId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getAccountId() {
		return accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
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

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
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

	public UserAccountRule(Long accountId, @NotBlank(message = "Specify the accounting rule") Long ruleId, @NotBlank(message = "Specify the accounting rule type") Long ruleTypeId, @NotBlank(message = "Specify the value") String value, Long createdBy, Date date_created, Date last_updated) {
		this.accountId = accountId;
		this.ruleId = ruleId;
		this.ruleTypeId = ruleTypeId;
		this.value = value;
		this.userId = userId;
		this.date_created = date_created;
		this.last_updated = last_updated;
	}
}
