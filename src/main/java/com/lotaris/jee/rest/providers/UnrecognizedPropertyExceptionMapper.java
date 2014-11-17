package com.lotaris.jee.rest.providers;

import com.lotaris.jee.rest.mappers.AbstractJsonExceptionMapper;
import com.lotaris.jee.rest.mappers.DefaultLocationType;
import com.lotaris.jee.rest.mappers.DefaultLocationType.LocationTypes;
import com.lotaris.jee.validation.ApiErrorResponse;
import org.codehaus.jackson.map.exc.UnrecognizedPropertyException;

import javax.ws.rs.ext.Provider;

/**
 * Maps JSON unrecognized property exceptions to an HTTP 400 Bad Request API response.
 *
 * @author Laurent Prevost, laurent.prevost@lotaris.com
 * @author Simon Oulevay <simon.oulevay@lotaris.com>
 */
@Provider
@DefaultLocationType(LocationTypes.JSON_LOCATION_TYPE)
public class UnrecognizedPropertyExceptionMapper extends AbstractJsonExceptionMapper<UnrecognizedPropertyException> {

	@Override
	protected ApiErrorResponse toApiErrorResponse(UnrecognizedPropertyException upe) {
		return new ApiErrorResponse(
			buildMessage(upe), 
			getCode(),
			getLocationType(),
			upe.getUnrecognizedPropertyName()
		);
	}

	/**
	 * Builds an error message indicating which property is unknown.
	 *
	 * @param upe the cause
	 * @return a message indicating the position (line and column) of the unknown property in the
	 * JSON document
	 */
	private String buildMessage(UnrecognizedPropertyException upe) {
		return "JSON parsing error due to unknown JSON object property "
				+ upe.getUnrecognizedPropertyName() + getLocationDetails(upe) + ".";
	}
}
