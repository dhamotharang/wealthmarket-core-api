package com.wm.core.model.response;

public class BaseResponse {

	private int statusCode;

	private String description;

	private Object data;

	private Object errors;

	/**
	 * @return the data
	 */
	public Object getData() {
		return data;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @return the errors
	 */
	public Object getErrors() {
		return errors;
	}

	/**
	 * @return the statusCode
	 */
	public int getStatusCode() {
		return statusCode;
	}

	/**
	 * @param data the data to set
	 */
	public void setData(Object data) {
		this.data = data;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @param errors the errors to set
	 */
	public void setErrors(Object errors) {
		this.errors = errors;
	}

	/**
	 * @param statusCode the statusCode to set
	 */
	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

}
