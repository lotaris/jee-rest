package com.lotaris.jee.rest;

import com.lotaris.jee.rest.mappers.ApiErrorsExceptionMapper;
import com.lotaris.jee.rest.mappers.JsonObjectMapper;
import com.lotaris.jee.rest.mappers.MapperMappingDefinition;
import com.lotaris.jee.rest.providers.JsonMappingExceptionMapper;
import com.lotaris.jee.rest.test.dummyresourcepackage.AnotherPathAnnotatedForAbstractRestApplication;
import com.lotaris.jee.rest.test.dummyresourcepackage.PathAnnotatedClassForAbstractRestApplicationUnitTest;
import com.lotaris.rox.annotations.RoxableTest;
import com.lotaris.rox.annotations.RoxableTestClass;
import java.util.HashSet;
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
	@SuppressWarnings("unchecked")
	public void abstractRestApplicationConstructorShouldAddMappersConfiguration() {
		restApplication = new TestRestApplicationWithMapperConfiguration();
		Set<Class<?>> classes = (Set<Class<?>>) Whitebox.getInternalState(restApplication, "classes");
		assertTrue(classes.contains(JsonMappingExceptionMapper.class));
	}
	
	@Test
	@RoxableTest(key = "b1c834eb0950")
	@SuppressWarnings("unchecked")
	public void abstractRestApplicationConstructorShouldAddPackageConfiguration() {
		restApplication = new TestRestApplicationWithPackageConfiguration();
		Set<Class<?>> classes = (Set<Class<?>>) Whitebox.getInternalState(restApplication, "classes");
		assertTrue(classes.contains(PathAnnotatedClassForAbstractRestApplicationUnitTest.class));
		assertTrue(classes.contains(AnotherPathAnnotatedForAbstractRestApplication.class));
	}
	
	@Test
	@RoxableTest(key = "00c8a2668f95")
	@SuppressWarnings("unchecked")
	public void abstractRestApplicationConstructorShouldAddThreeClasses() {
		restApplication = new TestRestApplication();
		Set<Class<?>> classes = (Set<Class<?>>) Whitebox.getInternalState(restApplication, "classes");
		assertTrue(classes.contains(JacksonFeature.class));
		assertTrue(classes.contains(JsonObjectMapper.class));
		assertTrue(classes.contains(ApiErrorsExceptionMapper.class));
	}
	
	@Test
	@RoxableTest(key = "2117f852fd25")
	@SuppressWarnings("unchecked")
	public void abstractRestApplicationConstructorShouldAddAdditionalClasses() {
		restApplication = new TestRestApplicationWithMoreClasses();
		Set<Class<?>> classes = (Set<Class<?>>) Whitebox.getInternalState(restApplication, "classes");
		assertTrue(classes.contains(JacksonFeature.class));
		assertTrue(classes.contains(JsonObjectMapper.class));
		assertTrue(classes.contains(ApiErrorsExceptionMapper.class));
		assertTrue(classes.contains(Object.class));
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
			packages[0] = "com.lotaris.jee.rest.test.dummyresourcepackage";
			return packages;
		}

		@Override
		protected MapperMappingDefinition[] retrieveMappersConfiguration() {
			return new MapperMappingDefinition[0];
		}
		
	}
	
	public static class TestRestApplicationWithMoreClasses extends AbstractRestApplication {

		@Override
		protected String[] getPackages() {
			String [] packages = new String[1];
			packages[0] = "com.lotaris.jee.rest.test.dummyresourcepackage";
			return packages;
		}

		@Override
		protected Set<Class<?>> addMoreClasses() {
			Set<Class<?>> classes = new HashSet<>();
			
			classes.add(Object.class);
			
			return classes;
		}
		
		@Override
		protected MapperMappingDefinition[] retrieveMappersConfiguration() {
			return new MapperMappingDefinition[0];
		}
		
	}
}
