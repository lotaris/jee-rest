package com.lotaris.jee.rest;

import com.lotaris.rox.annotations.RoxableTest;
import com.lotaris.rox.annotations.RoxableTestClass;
import javax.ws.rs.core.Response;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @see ApiResponse
 * @author Simon Oulevay (simon.oulevay@lotaris.com)
 */
@RoxableTestClass(tags = {"api", "apiResponse"})
public class ApiResponseUnitTest {

	@Test
	@RoxableTest(key = "7eb462858bbd")
	public void apiResponseShouldBuildAnOkResponseByDefault() {
		assertEquals(new ApiResponse().build().getStatus(), 200);
	}

	@Test
	@RoxableTest(key = "6b5d90f8a2eb")
	public void apiResponseShouldBuildAnOkResponseWithBody() {
		final Object entity = new Object();
		final Response response = new ApiResponse(entity).build();
		assertEquals(response.getStatus(), 200);
		assertSame(response.getEntity(), entity);
	}

	@Test
	@RoxableTest(key = "e006f695714b")
	public void apiResponseShouldAddHeaders() {
		final Response response = new ApiResponse().header("Content-Type", "foo").header("Accept", "bar").build();
		assertEquals(response.getHeaderString("Content-Type"), "foo");
		assertEquals(response.getHeaderString("Accept"), "bar");
	}

	@Test
	@RoxableTest(key = "81e5ff9e06b2")
	public void emptyApiResponseShouldSetEntity() {
		final Object entity = new Object();
		final Response response = new ApiResponse().entity(entity).build();
		assertSame(response.getEntity(), entity);
	}

	@Test
	@RoxableTest(key = "1bf40a049c61")
	public void apiResponseShouldSetEntity() {
		final Object entity = new Object();
		final Object otherEntity = new Object();
		final Response response = new ApiResponse(entity).entity(otherEntity).build();
		assertSame(response.getEntity(), otherEntity);
	}

	@Test
	@RoxableTest(key = "061c4e5ac810")
	public void apiResponseShouldBeConfigurable() {
		
		final IApiResponseConfig config = new IApiResponseConfig() {

			@Override
			public void configure(Response.ResponseBuilder builder) {
				builder.status(206);
			}
		};

		final Response response = new ApiResponse().configure(config).build();
		assertEquals(response.getStatus(), 206);
	}
}