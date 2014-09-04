package com.forbesdigital.jee.rest.providers;

import com.forbesdigital.jee.rest.mappers.AbstractJsonExceptionMapper;
import com.forbesdigital.jee.rest.mappers.DefaultLocationType;
import com.forbesdigital.jee.validation.ApiErrorResponse;
import javax.ws.rs.ext.Provider;
import org.codehaus.jackson.JsonParseException;

/**
 * Maps JSON parsing exceptions to an HTTP 400 Bad Request API response.
 *
 * @author Simon Oulevay (simon.oulevay@lotaris.com)
 */
@Provider
@DefaultLocationType(DefaultLocationType.LocationTypes.JSON_LOCATION_TYPE)
public class JsonParseExceptionMapper extends AbstractJsonExceptionMapper<JsonParseException> {

	@Override
	protected ApiErrorResponse toApiErrorResponse(JsonParseException jpe) {
		return new ApiErrorResponse(buildMessage(jpe), 
			getCode(),
			getLocationType(),
			""
		);
	}

	private String buildMessage(JsonParseException jpe) {
		return "JSON parsing error due to invalid syntax" + getLocationDetails(jpe) + ".";
	}
}
