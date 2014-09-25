package com.forbesdigital.jee.rest.mappers;

import com.lotaris.rox.annotations.RoxableTest;
import com.lotaris.rox.annotations.RoxableTestClass;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @see MapperMappingDefinition
 * @author Baptiste Roth (baptiste.roth@lotaris.com)
 */
@RoxableTestClass(tags = {"mappers"})
public class MapperMappingDefinitionUnitTest {
	
	private MapperMappingDefinition mapperMappingDefinition;
	
	@Test
	@RoxableTest(key = "c4fdf54824c0")
	public void mapperMappingDefinitionShouldNotSetLocationTypeIfConfigurationIsNull() {
		mapperMappingDefinition = new MapperMappingDefinition(TestWithoutAnnotation.class, null);
		assertNull(mapperMappingDefinition.getLocationType());
	}
	
	@Test
	@RoxableTest(key = "579bd7912ed1")
	public void mapperMappingDefinitionShouldSetLocationTypeIfConfigurationIsProvided() {
		mapperMappingDefinition = new MapperMappingDefinition(TestWithAnnotation.class, null);
		assertNotNull(mapperMappingDefinition.getLocationType());
		assertEquals("test", mapperMappingDefinition.getLocationType().getLocationType());
	}
	
	public static class TestWithoutAnnotation extends ApiErrorsExceptionMapper {	
	}
	
	@DefaultLocationType("test")
	public static class TestWithAnnotation extends ApiErrorsExceptionMapper {	
	}
}
