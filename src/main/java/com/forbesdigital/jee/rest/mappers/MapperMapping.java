package com.forbesdigital.jee.rest.mappers;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Keep the mappings between the ExceptionMapper class and the ErrorCode
 * 
 * @author Laurent Prevost <laurent.prevost@lotaris.com>
 */
public class MapperMapping {
	/**
	 * Mapping configuration
	 */
	private static final Map<Class<? extends AbstractApiExceptionMapper>, MapperMappingDefinition> mapping = new HashMap<>();
	
	/**
	 * Add a new mapping definition to the configuration
	 * 
	 * @param mappingDefinition The mapping definition to add
	 */
	public static void addMapping(MapperMappingDefinition mappingDefinition) {
		mapping.put(mappingDefinition.getMapper(), mappingDefinition);
	}
	
	/**
	 * Retrieve a specific mapping for an ExceptionMapper
	 * 
	 * @param exceptionClass The class of the ExceptionMapper
	 * @return The mapper found, otherwise null is returned
	 */
	public static MapperMappingDefinition getMapping(AbstractApiExceptionMapper exceptionClass) {
		return mapping.get(exceptionClass.getClass());
	}
	
	/**
	 * @return Retrieve the list of mappings
	 */
	public static Set<Class<? extends AbstractApiExceptionMapper>> getExceptionMapperClasses() {
		return mapping.keySet();
	}
}
