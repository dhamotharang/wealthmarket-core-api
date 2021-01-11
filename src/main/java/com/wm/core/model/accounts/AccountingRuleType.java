package com.wm.core.model.accounts;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name = "wm_accounting_rule_types")
public class AccountingRuleType {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "wm_accounting_rule_type_seq")
	@SequenceGenerator(name = "wm_accounting_rule_type_seq", sequenceName = "wm_accounting_rule_type_seq", initialValue = 1, allocationSize = 1)
	private Long id;

	@NotBlank(message = "Specify the name of the accounting rule type")
	private String name;

	@NotBlank(message = "Specify the suitable description/purpose of the accounting rule type")
	private String description;

	private Long userId;

	@JsonProperty(value = "date_created")
	private Date date_created;

	@JsonProperty(value = "last_updated")
	private Date last_updated;

	public AccountingRuleType() {
		super();
	}

	public AccountingRuleType(@NotBlank(message = "Specify the name of the account") String name, @NotBlank(message = "Specify the suitable description/purpose of the account") String description, @NotNull(message = "Specify the instrument to assign to the account") Long instrumentId, @NotNull(message = "Specify the account type") Long subledgerId) {
		this.name = name;
		this.description = description;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public AccountingRuleType(Long id, @NotBlank(message = "Specify the name of the accounting rule type") String name, @NotBlank(message = "Specify the suitable description/purpose of the accounting rule type") String description, Long userId, Date date_created, Date last_updated) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.userId = userId;
		this.date_created = date_created;
		this.last_updated = last_updated;
	}
}
