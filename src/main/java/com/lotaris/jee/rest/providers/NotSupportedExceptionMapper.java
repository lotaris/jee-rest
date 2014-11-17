package com.lotaris.jee.rest.providers;

import com.lotaris.jee.rest.mappers.AbstractApiExceptionMapper;
import com.lotaris.jee.rest.mappers.DefaultLocationType;
import com.lotaris.jee.rest.mappers.DefaultLocationType.LocationTypes;
import com.lotaris.jee.validation.ApiErrorResponse;
import javax.ws.rs.NotSupportedException;
import javax.ws.rs.ext.Provider;

/**
 * Maps Jersey not supported exceptions to an HTTP 415 Unsupported Media Type API response.
 *
 * @author Simon Oulevay <simon.oulevay@lotaris.com>
 */
@Provider
@DefaultLocationType(LocationTypes.HEADER_LOCATION_TYPE)
public class NotSupportedExceptionMapper extends AbstractApiExceptionMapper<NotSupportedException> {

	@Override
	protected ApiErrorResponse toApiErrorResponse(NotSupportedException exception) {
		return new ApiErrorResponse(
			"The media type of the request body is unsupported.", 
			getCode(), 
			getLocationType(), 
			"Content-Type"
		);
	}
}
