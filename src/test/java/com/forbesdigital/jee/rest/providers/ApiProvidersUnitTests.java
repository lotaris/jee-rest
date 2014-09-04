package com.forbesdigital.jee.rest.providers;

import com.forbesdigital.jee.rest.mappers.ApiErrorsExceptionMapper;
import com.forbesdigital.jee.rest.mappers.JsonObjectMapper;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import com.lotaris.rox.annotations.RoxableTest;
import com.lotaris.rox.annotations.RoxableTestClass;
import java.util.Set;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.junit.Before;
import org.junit.Test;

/**
 * @see ApiProviders
 * @author Simon Oulevay <simon.oulevay@lotaris.com>
 */
@RoxableTestClass(tags = "apiProviders")
public class ApiProvidersUnitTests {
	
	// TODO: This tests must be totally refactored due to the new way the ExceptionMappers are used and configured. ApiMappers does not exist anymore.

	private Set<Class<?>> providerClasses;

	@Before
	public void setUp() {
//		providerClasses = ApiMappers.getClasses();
	}

	@Test
	@RoxableTest(key = "4100cc8e40ab")
	public void apiProvidersShouldIncludeJacksonFeature() {
		assertThat(providerClasses, hasItem(JacksonFeature.class));
	}

	@Test
	@RoxableTest(key = "c5c47d31995a")
	public void apiProvidersShouldIncludeJsonObjectMapper() {
		assertThat(providerClasses, hasItem(JsonObjectMapper.class));
	}

	@Test
	@RoxableTest(key = "367e5a40bbae", tickets = "DCO-165")
	public void apiProvidersShouldIncludeAllExceptionMappers() {
		assertThat(providerClasses, hasItem(ApiErrorsExceptionMapper.class));
		assertThat(providerClasses, hasItem(CatchAllExceptionMapper.class));
		assertThat(providerClasses, hasItem(EOFExceptionMapper.class));
		assertThat(providerClasses, hasItem(JsonMappingExceptionMapper.class));
		assertThat(providerClasses, hasItem(JsonParseExceptionMapper.class));
		assertThat(providerClasses, hasItem(NotAcceptableExceptionMapper.class));
		assertThat(providerClasses, hasItem(NotAllowedExceptionMapper.class));
		assertThat(providerClasses, hasItem(NotFoundExceptionMapper.class));
		assertThat(providerClasses, hasItem(NotSupportedExceptionMapper.class));
		assertThat(providerClasses, hasItem(UnrecognizedPropertyExceptionMapper.class));
	}
}
