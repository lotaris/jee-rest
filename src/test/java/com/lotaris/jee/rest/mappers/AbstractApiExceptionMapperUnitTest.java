package com.lotaris.jee.rest.mappers;

import com.lotaris.jee.rest.ApiResponse;
import com.lotaris.jee.validation.ApiErrorResponse;
import com.lotaris.rox.annotations.RoxableTest;
import com.lotaris.rox.annotations.RoxableTestClass;
import javax.ws.rs.core.Response;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;


/**
 * @see AbstractApiExceptionMapper
 * @author Baptiste Roth (baptiste.roth@lotaris.com)
 */
@RoxableTestClass(tags = {"mappers"})
public class AbstractApiExceptionMapperUnitTest {
	
	private TestApiExceptionMapper apiExceptionMapper;
	private TestApiExceptionMapperWithEnrichment apiExceptionMapperWithEnrichment;
	private static final int HTTP_BAD_REQUEST = 400;
	private static final int HTTP_NOT_FOUND = 404;
	private static final String HEADER_NAME = "Enrichment-Test";
	
	@Mock
	private ApiResponse apiResponse;
			
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		
		apiExceptionMapper = new TestApiExceptionMapper();
		apiExceptionMapperWithEnrichment = new TestApiExceptionMapperWithEnrichment();
	}
	
	@Test
	@RoxableTest(key = "c799a3653006")
	public void abstractApiExceptionMapperToResponseShouldTransformAndEnrichException() {
		Exception e = new Exception();
		Response response = apiExceptionMapperWithEnrichment.toResponse(e);
		assertEquals(response.getStatus(), HTTP_NOT_FOUND);
		assertTrue(response.getHeaders().containsKey(HEADER_NAME));
	}
	
	@Test
	@RoxableTest(key = "c26c1b243eb0")
	public void abstractApiExceptionMapperEnrichShouldNotDoAnythingByDefault() {
		apiExceptionMapper.enrich(apiResponse);
		verifyZeroInteractions(apiResponse);
	}
	
	public static class TestApiExceptionMapper extends AbstractApiExceptionMapper<Exception> {

		@Override
		protected ApiErrorResponse toApiErrorResponse(Exception exception) {
			return new ApiErrorResponse(HTTP_BAD_REQUEST);
		}
		
	}
	
	public static class TestApiExceptionMapperWithEnrichment extends AbstractApiExceptionMapper<Exception> {

		@Override
		protected ApiErrorResponse toApiErrorResponse(Exception exception) {
			return new ApiErrorResponse(HTTP_NOT_FOUND);
		}
		
		@Override
		public void enrich(ApiResponse response) {
			response.header(HEADER_NAME, response);
		}
		
	}
}
