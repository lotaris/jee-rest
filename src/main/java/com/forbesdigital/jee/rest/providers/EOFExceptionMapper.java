package com.forbesdigital.jee.rest.providers;

import com.forbesdigital.jee.rest.mappers.AbstractApiExceptionMapper;
import com.forbesdigital.jee.validation.ApiErrorResponse;
import java.io.EOFException;
import javax.ws.rs.ext.Provider;

/**
 * Maps EOF exceptions (for example due to an empty request body) to an HTTP 400 Bad Request API
 * response.
 *
 * @author Simon Oulevay (simon.oulevay@lotaris.com)
 */
@Provider
public class EOFExceptionMapper extends AbstractApiExceptionMapper<EOFException> {

	@Override
	public ApiErrorResponse toApiErrorResponse(EOFException eofe) {
		return new ApiErrorResponse("Parsing error due to end of input.", getCode());
	}
}
