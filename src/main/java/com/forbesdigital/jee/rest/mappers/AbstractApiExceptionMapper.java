package com.forbesdigital.jee.rest.mappers;

import com.forbesdigital.jee.validation.ApiErrorResponse;
import com.forbesdigital.jee.rest.ApiResponse;
import com.forbesdigital.jee.validation.IErrorCode;
import com.forbesdigital.jee.validation.IErrorLocationType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

/**
 * Abstract mapper that responds with a JSON API error response.
 *
 * @author Simon Oulevay <simon.oulevay@lotaris.com>
 */
public abstract class AbstractApiExceptionMapper<E extends Exception> implements ExceptionMapper<E> {

	@Override
	public Response toResponse(E exception) {

		final ApiResponse response = new ApiResponse();

		// get error response from subclass
		response.configure(toApiErrorResponse(exception));

		// enrich the response
		enrich(response);
		
		return response.build();
	}

	protected void enrich(ApiResponse apiResponse) {
		// do nothing to avoid forcing to implement that method
	}
	
	protected IErrorCode getCode() {
		return MapperMapping.getMapping(this).getCode();
	}
	
	protected IErrorLocationType getLocationType() {
		return MapperMapping.getMapping(this).getLocationType();
	}
	
	/**
	 * Returns the API error response that will be used as the response body.
	 *
	 * @param exception the exception to map
	 * @return an API error response
	 */
	protected abstract ApiErrorResponse toApiErrorResponse(E exception);
}
