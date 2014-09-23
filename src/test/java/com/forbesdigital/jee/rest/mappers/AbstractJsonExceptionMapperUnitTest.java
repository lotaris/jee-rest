package com.forbesdigital.jee.rest.mappers;

import com.forbesdigital.jee.validation.ApiErrorResponse;
import com.lotaris.rox.annotations.RoxableTest;
import com.lotaris.rox.annotations.RoxableTestClass;
import org.codehaus.jackson.JsonLocation;
import org.codehaus.jackson.JsonProcessingException;
import org.junit.Before;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.*;
import org.junit.Test;
import static org.mockito.Mockito.*;

/**
 *
 * @author Baptiste Roth (baptiste.roth@lotaris.com)
 */
@RoxableTestClass(tags = {"mappers"})
public class AbstractJsonExceptionMapperUnitTest {
	
	private TestJsonExceptionMapper jsonExceptionMapper;
	private static final int HTTP_BAD_REQUEST = 400;
	
	@Mock
	JsonProcessingException jsonProcessingException;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		
		jsonExceptionMapper = new TestJsonExceptionMapper();
	}
	
	@Test
	@RoxableTest(key = "32feae5ad598")
	public void abstractJsonExceptionMapperShouldReturnEmptyStringWithoutLocation() {
		when(jsonProcessingException.getLocation()).thenReturn(null);
		String location = jsonExceptionMapper.getLocationDetails(jsonProcessingException);
		assertEquals("", location);
	}
	
	@Test
	@RoxableTest(key = "1e94cfd8f281")
	public void abstractJsonExceptionMapperShouldReturnLineNumberAndColumnNumberWithLocation() {
		when(jsonProcessingException.getLocation()).thenReturn(new JsonLocation(null, 0, 10000, 20000));
		String location = jsonExceptionMapper.getLocationDetails(jsonProcessingException);
		assertTrue("Location should contain column and line number.", location.contains("10000") && location.contains("20000"));
	}
	
	public static class TestJsonExceptionMapper extends AbstractJsonExceptionMapper<JsonProcessingException> {

		@Override
		protected ApiErrorResponse toApiErrorResponse(JsonProcessingException exception) {
			return new ApiErrorResponse(HTTP_BAD_REQUEST);
		}
		
	}
}
