package com.wm.core.model.accounts;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name = "wm_accounting_rules")
public class AccountingRule {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "wm_accounting_rule_seq")
	@SequenceGenerator(name = "wm_accounting_rule_seq", sequenceName = "wm_accounting_rule_seq", initialValue = 1, allocationSize = 1)
	private Long id;

	@NotBlank(message = "Specify the accounting rule")
	private String rule;

	private Long ruleTypeId;

	@NotBlank(message = "Specify the suitable description/purpose of the accounting rule")
	private String description;

	private Long userId;

	@JsonProperty(value = "date_created")
	private Date date_created;

	@JsonProperty(value = "last_updated")
	private Date last_updated;

	public AccountingRule() {
		super();
	}

	public AccountingRule(@NotBlank(message = "Specify the accounting rule") String rule, Long ruleTypeId, @NotBlank(message = "Specify the suitable description/purpose of the accounting rule") String description, Long userId) {
		this.rule = rule;
		this.ruleTypeId = ruleTypeId;
		this.description = description;
		this.userId = userId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRule() {
		return rule;
	}

	public void setRule(String rule) {
		this.rule = rule;
	}

	public Long getRuleTypeId() {
		return ruleTypeId;
	}

	public void setRuleTypeId(Long ruleTypeId) {
		this.ruleTypeId = ruleTypeId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	public AccountingRule(Long id, @NotBlank(message = "Specify the accounting rule") String rule, Long ruleTypeId, @NotBlank(message = "Specify the suitable description/purpose of the accounting rule") String description, Long userId, Date date_created, Date last_updated) {
		this.id = id;
		this.rule = rule;
		this.ruleTypeId = ruleTypeId;
		this.description = description;
		this.userId = userId;
		this.date_created = date_created;
		this.last_updated = last_updated;
	}
}
