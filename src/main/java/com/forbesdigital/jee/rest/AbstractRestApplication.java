package com.forbesdigital.jee.rest;

import com.forbesdigital.jee.rest.mappers.MapperMapping;
import com.forbesdigital.jee.rest.mappers.MapperMappingDefinition;
import com.forbesdigital.jee.rest.mappers.AbstractApiExceptionMapper;
import com.forbesdigital.jee.rest.mappers.ApiErrorsExceptionMapper;
import com.forbesdigital.jee.rest.mappers.JsonObjectMapper;
import com.forbesdigital.jee.validation.IErrorCode;
import java.util.HashSet;
import java.util.Set;
import javax.ws.rs.Path;
import javax.ws.rs.core.Application;
import org.codehaus.jackson.jaxrs.JacksonJsonProvider;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.reflections.Reflections;
import org.reflections.scanners.TypeAnnotationsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.FilterBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * JAX-RS application that will be mounted at the path specified by the <tt>@ApplicationPath</tt>
 * annotation (relative to the context root). Implement {@link #configureResources(java.util.Set)}
 * and add your resources to the provided set.
 *
 * <pre>
 *	&#64;ApplicationPath("foo")
 *	public class FooRestApplication extends AbstractRestApplication {
 *
 *		&#64;Override
 *		protected void configureResources(Set<Class<?>> resources) {
 *			resources.add(ApplicationResource.class);
 *			resources.add(UserResource.class);
 *		}
 *	}
 * </pre>
 *
 * @author Simon Oulevay (simon.oulevay@lotaris.com)
 */
public abstract class AbstractRestApplication extends Application {

	private static final Logger LOG = LoggerFactory.getLogger(AbstractRestApplication.class);
	private Set<Class<?>> classes;

	public AbstractRestApplication() {

		classes = new HashSet<>();

		classes.add(JacksonFeature.class);
		classes.add(JsonObjectMapper.class);
		classes.add(ApiErrorsExceptionMapper.class);

		for (MapperMappingDefinition mmd : retrieveMappersConfiguration()) {
			MapperMapping.addMapping(mmd);
			classes.add(mmd.getMapper());
		}
		
		// Scan every packages to get @Path annotated classes
		for (String pckg : getPackages()) {
			classes.addAll(new Reflections(
					ClasspathHelper.forPackage(pckg), new TypeAnnotationsScanner(), new FilterBuilder().includePackage(pckg)).
					getTypesAnnotatedWith(Path.class));
		}

		if (LOG.isTraceEnabled()) {
			for (Class cl : classes) {
				LOG.trace("Class registered: {}", cl.getCanonicalName());
			}
		}
	}

	@Override
	public Set<Class<?>> getClasses() {
		return classes;
	}

	@Override
	public Set<Object> getSingletons() {
		final Set<Object> singletons = new HashSet<>(1);
		singletons.add(new JacksonJsonProvider());
		return singletons;
	}

	/**
	 * @return Give the list of packages to scan to retrieve resources.
	 */
	protected abstract String[] getPackages();
	
	/**
	 * @return Retrieve the mapping configuration between ExceptionMappers and ErrorCodes
	 */
	protected abstract MapperMappingDefinition[] retrieveMappersConfiguration();

	/**
	 * Build the map from a list of mapping
	 * 
	 * @param mapperMappingDefinition The list of mappings
	 * @return The mapping configuration ready to use
	 */
	protected MapperMappingDefinition[] map(MapperMappingDefinition ... mapperMappingDefinition) {
		return mapperMappingDefinition;
	}

	/**
	 * Build a mapping configuration element. Bind an ErrorCode to an ExceptionMapper
	 * 
	 * @param mapper The ExceptionMapper to bind
	 * @param code The ErrorCode for the binding
	 * @return The mapper definition
	 */
	protected MapperMappingDefinition def(Class<? extends AbstractApiExceptionMapper> mapper, IErrorCode code) {
		return new MapperMappingDefinition(mapper, code);
	}
}
