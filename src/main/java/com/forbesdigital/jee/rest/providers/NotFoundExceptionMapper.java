package com.forbesdigital.jee.rest.providers;

import com.forbesdigital.jee.rest.mappers.AbstractApiExceptionMapper;
import com.forbesdigital.jee.validation.ApiErrorResponse;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.ext.Provider;

/**
 * Maps Jersey not found exceptions to an HTTP 404 Not Found API response.
 *
 * @author Simon Oulevay <simon.oulevay@lotaris.com>
 */
@Provider
public class NotFoundExceptionMapper extends AbstractApiExceptionMapper<NotFoundException> {

	@Override
	protected ApiErrorResponse toApiErrorResponse(NotFoundException exception) {
		return new ApiErrorResponse(
			"Resource not found.", 
			getCode()
		);
	}
}
