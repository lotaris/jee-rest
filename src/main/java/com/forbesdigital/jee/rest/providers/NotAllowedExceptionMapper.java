package com.forbesdigital.jee.rest.providers;

import com.forbesdigital.jee.rest.mappers.AbstractApiExceptionMapper;
import com.forbesdigital.jee.validation.ApiErrorResponse;
import javax.ws.rs.NotAllowedException;
import javax.ws.rs.ext.Provider;

/**
 * Maps Jersey not allowed exceptions to an HTTP 405 Not Allowed API response.
 *
 * @author Simon Oulevay <simon.oulevay@lotaris.com>
 */
@Provider
public class NotAllowedExceptionMapper extends AbstractApiExceptionMapper<NotAllowedException> {

	@Override
	protected ApiErrorResponse toApiErrorResponse(NotAllowedException exception) {
		return new ApiErrorResponse(
			"This resource does not support this HTTP verb.", 
			getCode()
		);
	}
}
