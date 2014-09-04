package com.forbesdigital.jee.rest.mappers;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author Laurent Prevost <laurent.prevost@lotaris.com>
 */
public class MapperMapping {
	private static final Map<Class<? extends AbstractApiExceptionMapper>, MapperMappingDefinition> mapping = new HashMap<>();
	
	public static void addMapping(MapperMappingDefinition mappingDefinition) {
		mapping.put(mappingDefinition.getMapper(), mappingDefinition);
	}
	
	public static MapperMappingDefinition getMapping(AbstractApiExceptionMapper exceptionClass) {
		return mapping.get(exceptionClass.getClass());
	}
	
	public static Set<Class<? extends AbstractApiExceptionMapper>> getExceptionMapperClasses() {
		return mapping.keySet();
	}
}
