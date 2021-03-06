package com.github.damianwajser.exceptions.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.util.Assert;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ExceptionDetail implements Serializable {

	private static final long serialVersionUID = 1905128741950251207L;

	@JsonProperty("errorCode")
	private final String errorCode;
	@JsonProperty("errorDetail")
	private final Optional<Object> errorDetail;

	@JsonProperty("errorMessage")
	private String errorMessage;
	@JsonProperty("metaData")
	private Map<String, Object> metaData;

	@JsonCreator
	public ExceptionDetail(@JsonProperty("errorCode") String errorCode, @JsonProperty("errorMessage") String errorMessage, @JsonProperty("errorDetail") Optional<Object> detail) {
		Assert.notNull(errorCode, "errorCode dont be null");
		Assert.notNull(errorMessage, "errorMessage dont be null");
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
		this.errorDetail = detail;
		this.metaData = new HashMap<>();
	}

	public String getErrorCode() {
		return errorCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setMetaData(String key, Object value) {
		metaData.put(key, value);
	}

	public Map<String, Object> getMetaData() {
		return this.metaData;
	}

	public Optional<Object> getErrorDetail() {
		return errorDetail;
	}

	public void setErrorMessage(String message) {
		this.errorMessage = message;
	}

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}

	@Override
	public boolean equals(Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
	}

	/**
	 * Always treat deserialization as a full-blown constructor, by validating
	 * the final state of the de-serialized object.
	 */
	private void readObject(ObjectInputStream aInputStream)
			throws ClassNotFoundException, IOException {
		// always perform the default deserialization first
		aInputStream.defaultReadObject();
	}

	/**
	 * This is the default implementation of writeObject. Customise if
	 * necessary.
	 */
	private void writeObject(ObjectOutputStream aOutputStream)
			throws IOException {
		// perform the default serialization for all non-transient, non-static
		// fields
		aOutputStream.defaultWriteObject();
	}
}
