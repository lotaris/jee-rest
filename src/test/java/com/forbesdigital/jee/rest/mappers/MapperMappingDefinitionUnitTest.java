package com.forbesdigital.jee.rest.mappers;

import com.forbesdigital.jee.validation.IErrorCode;
import com.lotaris.rox.annotations.RoxableTest;
import com.lotaris.rox.annotations.RoxableTestClass;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for the MapperMappingDefinition.
 * 
 * @author Baptiste Roth (baptiste.roth@lotaris.com)
 */
@RoxableTestClass(tags = {"mappers"})
public class MapperMappingDefinitionUnitTest {
	
	private MapperMappingDefinition mapperMappingDefinition;
	
	@Mock
	private Class<ApiErrorsExceptionMapper> apiErrorsExceptionMapper;
	@Mock
	private IErrorCode errorCode;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	@RoxableTest(key = "c4fdf54824c0")
	public void mapperMappingDefinitionShouldNotSetLocationTypeIfConfigurationIsNull() {
		when(apiErrorsExceptionMapper.getAnnotation(DefaultLocationType.class)).thenReturn(null);
		mapperMappingDefinition = new MapperMappingDefinition(apiErrorsExceptionMapper, errorCode);
		assertNull(mapperMappingDefinition.getLocationType());
	}
}
