package com.lotaris.jee.rest.mappers;

import com.lotaris.jee.validation.ApiErrorResponse;
import com.lotaris.jee.rest.ApiResponse;
import com.lotaris.jee.validation.IErrorCode;
import com.lotaris.jee.validation.IErrorLocationType;
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
	
	/**
	 * @return Error code of this ExceptionMapper
	 */
	protected IErrorCode getCode() {
		return MapperMapping.getMapping(this).getCode();
	}
	
	/**
	 * @return Location type of this ExceptionMapper
	 */
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
