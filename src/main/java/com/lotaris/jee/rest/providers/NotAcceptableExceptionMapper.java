package com.lotaris.jee.rest.providers;

import com.lotaris.jee.rest.mappers.AbstractApiExceptionMapper;
import com.lotaris.jee.rest.mappers.DefaultLocationType;
import com.lotaris.jee.rest.mappers.DefaultLocationType.LocationTypes;
import com.lotaris.jee.validation.ApiErrorResponse;
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
