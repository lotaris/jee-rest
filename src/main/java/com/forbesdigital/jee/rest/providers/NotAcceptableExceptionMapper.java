package com.forbesdigital.jee.rest.providers;

import com.forbesdigital.jee.rest.mappers.AbstractApiExceptionMapper;
import com.forbesdigital.jee.rest.mappers.DefaultLocationType;
import com.forbesdigital.jee.rest.mappers.DefaultLocationType.LocationTypes;
import com.forbesdigital.jee.validation.ApiErrorResponse;
import javax.ws.rs.NotAcceptableException;
import javax.ws.rs.ext.Provider;

/**
 * Maps Jersey not acceptable exceptions to an HTTP 406 Not Acceptable API response.
 *
 * @author Simon Oulevay <simon.oulevay@lotaris.com>
 */
@Provider
@DefaultLocationType(LocationTypes.HEADER_LOCATION_TYPE)
public class NotAcceptableExceptionMapper extends AbstractApiExceptionMapper<NotAcceptableException> {

	@Override
	protected ApiErrorResponse toApiErrorResponse(NotAcceptableException exception) {
		return new ApiErrorResponse(
			"The requested media type is not available.", 
			getCode(), 
			getLocationType(), 
			"Accept"
			);
	}
}
