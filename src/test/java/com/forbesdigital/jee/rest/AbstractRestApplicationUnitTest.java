package com.forbesdigital.jee.rest;

import com.forbesdigital.jee.rest.mappers.ApiErrorsExceptionMapper;
import com.forbesdigital.jee.rest.mappers.JsonObjectMapper;
import com.forbesdigital.jee.rest.mappers.MapperMappingDefinition;
import com.forbesdigital.jee.rest.providers.JsonMappingExceptionMapper;
import com.forbesdigital.jee.test.utils.AnotherPathAnnotatedForAbstractRestApplication;
import com.forbesdigital.jee.test.utils.PathAnnotatedClassForAbstractRestApplicationUnitTest;
import com.lotaris.rox.annotations.RoxableTest;
import com.lotaris.rox.annotations.RoxableTestClass;
import java.util.Set;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;
import org.mockito.internal.util.reflection.Whitebox;

import static org.junit.Assert.*;

/**
 * @see AbstractRestApplication
 * @author Baptiste Roth (baptiste.roth@lotaris.com)
 */
@RoxableTestClass(tags = {"rest"})
public class AbstractRestApplicationUnitTest {
	
	private AbstractRestApplication restApplication;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	@RoxableTest(key = "8d9bd6743a01")
	public void abstractRestApplicationConstructorShouldAddMappersConfiguration() {
		restApplication = new TestRestApplicationWithMapperConfiguration();
		Set<Class<?>> classes = (Set<Class<?>>) Whitebox.getInternalState(restApplication, "classes");
		assertTrue(classes.contains(JsonMappingExceptionMapper.class));
	}
	
	@Test
	@RoxableTest(key = "b1c834eb0950")
	public void abstractRestApplicationConstructorShouldAddPackageConfiguration() {
		restApplication = new TestRestApplicationWithPackageConfiguration();
		Set<Class<?>> classes = (Set<Class<?>>) Whitebox.getInternalState(restApplication, "classes");
		assertTrue(classes.contains(PathAnnotatedClassForAbstractRestApplicationUnitTest.class));
		assertTrue(classes.contains(AnotherPathAnnotatedForAbstractRestApplication.class));
	}
	
	@Test
	@RoxableTest(key = "00c8a2668f95")
	public void abstractRestApplicationConstructorShouldAddThreeClasses() {
		restApplication = new TestRestApplication();
		Set<Class<?>> classes = (Set<Class<?>>) Whitebox.getInternalState(restApplication, "classes");
		assertTrue(classes.contains(JacksonFeature.class));
		assertTrue(classes.contains(JsonObjectMapper.class));
		assertTrue(classes.contains(ApiErrorsExceptionMapper.class));
	}
	
	public static class TestRestApplication extends AbstractRestApplication {

		@Override
		protected String[] getPackages() {
			return new String[0];
		}

		@Override
		protected MapperMappingDefinition[] retrieveMappersConfiguration() {
			return new MapperMappingDefinition[0];
		}
		
	}
	
	public static class TestRestApplicationWithMapperConfiguration extends AbstractRestApplication {

		@Override
		protected String[] getPackages() {
			return new String[0];
		}

		@Override
		protected MapperMappingDefinition[] retrieveMappersConfiguration() {
			MapperMappingDefinition[] mapperMappingDefinition = new MapperMappingDefinition[1];
			mapperMappingDefinition[0] = new MapperMappingDefinition(JsonMappingExceptionMapper.class, null); 
			return mapperMappingDefinition;
		}
		
	}
	
	public static class TestRestApplicationWithPackageConfiguration extends AbstractRestApplication {

		@Override
		protected String[] getPackages() {
			String [] packages = new String[1];
			packages[0] = "com.forbesdigital.jee.test";
			return packages;
		}

		@Override
		protected MapperMappingDefinition[] retrieveMappersConfiguration() {
			return new MapperMappingDefinition[0];
		}
		
	}
	
}
