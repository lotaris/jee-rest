package com.lotaris.jee.rest;

import javax.ws.rs.core.Response;

/**
 * Configuration object for an {@link ApiResponse} builder. Implementations will be passed a
 * builder's internal Jersey response builder to configure.
 *
 * <p><pre>
 *	public class MyApiConfig implements IApiResponseConfig {
 *
 *		@Override
 *		public void configure(Response.ResponseBuilder builder) {
 *			builder.status(400);
 *			builder.entity("Request data is invalid.");
 *		}
 *	}
 * </pre>
 *
 * @author Simon Oulevay (simon.oulevay@lotaris.com)
 */
public interface IApiResponseConfig {

	/**
	 * Configures a Jersey response.
	 *
	 * @param builder the Jersey response builder
	 */
	void configure(Response.ResponseBuilder builder);
}
