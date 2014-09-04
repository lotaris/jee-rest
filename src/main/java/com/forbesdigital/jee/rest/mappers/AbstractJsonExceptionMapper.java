package com.forbesdigital.jee.rest.mappers;

import org.codehaus.jackson.JsonProcessingException;

/**
 * Functionality common to JSON exception mappers.
 *
 * @author Simon Oulevay (simon.oulevay@lotaris.com)
 */
public abstract class AbstractJsonExceptionMapper<E extends JsonProcessingException> extends AbstractApiExceptionMapper<E> {

	/**
	 * Returns the location of the JSON processing error.
	 *
	 * @param jsonProcessingException the exception to map
	 * @return an empty string if no location can be determined, or a description of the location of
	 * the error in the JSON document
	 */
	protected String getLocationDetails(E jsonProcessingException) {
		if (jsonProcessingException.getLocation() == null) {
			return "";
		}

		return " at line " + jsonProcessingException.getLocation().getLineNr()
				+ ", column " + jsonProcessingException.getLocation().getColumnNr();
	}
}
