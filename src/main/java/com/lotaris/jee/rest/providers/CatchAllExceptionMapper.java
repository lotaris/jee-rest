package com.lotaris.jee.rest.providers;

import com.lotaris.jee.rest.mappers.AbstractApiExceptionMapper;
import com.lotaris.jee.validation.ApiErrorResponse;
import javax.ws.rs.ext.Provider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Maps any exception not caught by a more specific mapper to an HTTP 500 Internal Server Error API
 * response. Also makes an INFO log of the exception so it can be decided whether to add a more
 * specific mapper.
 *
 * @author Simon Oulevay <simon.oulevay@lotaris.com>
 */
@Provider
public class CatchAllExceptionMapper extends AbstractApiExceptionMapper<Exception> {

	//<editor-fold defaultstate="collapsed" desc="Logger">
	private static final Logger LOG = LoggerFactory.getLogger(CatchAllExceptionMapper.class);
	//</editor-fold>

	@Override
	public ApiErrorResponse toApiErrorResponse(Exception exception) {

		LOG.info("UNCAUGHT API EXCEPTION, will respond with HTTP 500 Internal Server Error;"
				+ " check if an exception mapper can be added to the providers package to provide a"
				+ " more detailed response to the API user (new mappers must also be registered in"
				+ " AbstractRestApplication).", exception);

		return new ApiErrorResponse("The request could not be completed due to a server error.", getCode());
	}
}
