package com.lotaris.jee.rest;

import com.lotaris.jee.validation.ApiErrorResponse;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * API response builder to create a Jersey response.
 *
 * <p>In addition to its chain methods, implementations of {@link IApiResponseConfig} can be passed
 * to {@link #configure(com.lotaris.dcc.service.rest.IApiResponseConfig)} to further configure the
 * response. For example, {@link ApiHttpStatusCodes} implements this interface.</p>
 *
 * <pre>
 * new ApiResponse(transferObject).configure(ApiHttpStatusCodes.BAD_REQUEST).build();
 * </pre>
 *
 * @see IApiResponseConfig
 * @see EApiHttpStatusCodes
 * @see ApiHttpHeaders
 * @author Simon Oulevay (simon.oulevay@lotaris.com)
 */
public class ApiResponse {

	private Response.ResponseBuilder jerseyResponseBuilder;

	/**
	 * Constructs an HTTP 200 OK response with no body. Call <tt>configure</tt> with an
	 * {@link EApiHttpStatusCodes} to change the status code.
	 */
	public ApiResponse() {
		jerseyResponseBuilder = Response.ok();
	}

	/**
	 * Constructs an HTTP 200 OK response with the specified entity as the body. Call
	 * <tt>configure</tt> with an {@link EApiHttpStatusCodes} to change the status code.
	 *
	 * @param entity the entity to serialize
	 */
	public ApiResponse(Object entity) {
		jerseyResponseBuilder = Response.ok(entity);
	}

	/**
	 * Constructs a response with the specified response error as the body and
	 * call the configure method on the error with this.
	 *
	 * @param error the error to serialize
	 */
	public ApiResponse(ApiErrorResponse error) {
		jerseyResponseBuilder = Response.serverError();
		this.configure(error);
	}

	public ApiResponse entity(Object entity) {
		jerseyResponseBuilder.entity(entity);
		return this;
	}

	public ApiResponse header(String name, Object value) {
		jerseyResponseBuilder.header(name, value);
		return this;
	}

	/**
	 * Configures the response with a config object.
	 *
	 * @param config the configuration implementation
	 * @return the updated builder
	 */
	public ApiResponse configure(IApiResponseConfig config) {
		config.configure(jerseyResponseBuilder);
		return this;
	}

	// DO: Comment
	public ApiResponse configure(ApiErrorResponse apiErrorResponse) {
		// make sure the content type of the response is JSON
		jerseyResponseBuilder.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON).status(apiErrorResponse.getHttpStatusCode());
		jerseyResponseBuilder.entity(apiErrorResponse);
		return this;
	}
	/**
	 * Builds a Jersey response corresponding to this builder's configuration.
	 *
	 * @return a Jersey response
	 */
	public Response build() {
		return jerseyResponseBuilder.build();
	}
}
