package com.forbesdigital.jee.rest.providers;

import com.forbesdigital.jee.rest.mappers.AbstractJsonExceptionMapper;
import com.forbesdigital.jee.rest.mappers.DefaultLocationType;
import com.forbesdigital.jee.rest.mappers.DefaultLocationType.LocationTypes;
import com.forbesdigital.jee.validation.ApiErrorResponse;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.Provider;
import org.codehaus.jackson.map.JsonMappingException;

/**
 * Maps JSON mapping exceptions (type check errors) to an HTTP 400 Bad Request API response.
 *
 * @author Simon Oulevay (simon.oulevay@lotaris.com)
 */
@Provider
@Produces(MediaType.APPLICATION_JSON)
@DefaultLocationType(LocationTypes.JSON_LOCATION_TYPE)
public class JsonMappingExceptionMapper extends AbstractJsonExceptionMapper<JsonMappingException> {

	@Override
	protected ApiErrorResponse toApiErrorResponse(JsonMappingException jme) {
		return new ApiErrorResponse(
			buildMessage(jme), 
			getCode(), 
			getLocationType(), 
			""
		);
	}

	private String buildMessage(JsonMappingException jme) {
		return "JSON parsing error due to invalid JSON value type" + getLocationDetails(jme) + ".";
	}
}
