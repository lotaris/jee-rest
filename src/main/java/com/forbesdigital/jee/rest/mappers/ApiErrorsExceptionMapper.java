package com.forbesdigital.jee.rest.mappers;

import com.forbesdigital.jee.validation.ApiErrorResponse;
import com.forbesdigital.jee.validation.ApiErrorsException;
import javax.ws.rs.ext.Provider;

/**
 * Maps API errors exceptions to an API response. The API error response enclosed in the exception
 * determines the HTTP status code and is used as the response body.
 *
 * @author Simon Oulevay (simon.oulevay@lotaris.com)
 */
@Provider
public class ApiErrorsExceptionMapper extends AbstractApiExceptionMapper<ApiErrorsException> {

	@Override
	protected ApiErrorResponse toApiErrorResponse(ApiErrorsException exception) {
		return exception.getErrorResponse();
	}
}
