package com.forbesdigital.jee.rest.mappers;

import com.forbesdigital.jee.validation.IErrorCode;
import com.forbesdigital.jee.validation.IErrorLocationType;

/**
 *
 * @author Laurent Prevost <laurent.prevost@lotaris.com>
 */
public class MapperMappingDefinition {
	private Class<? extends AbstractApiExceptionMapper> mapper;
	
	private IErrorCode code;
	
	private IErrorLocationType locationType;
	
	public MapperMappingDefinition(Class<? extends AbstractApiExceptionMapper> mapper, IErrorCode code) {
		this.mapper = mapper;
		this.code = code;
		
		final DefaultLocationType configuration = mapper.getAnnotation(DefaultLocationType.class);
		
		if (configuration != null) {
			locationType = new IErrorLocationType() {
				@Override
				public String getLocationType() {
					return configuration.value();
				}
			};
		}
	}

	public IErrorCode getCode() {
		return code;
	}

	public IErrorLocationType getLocationType() {
		return locationType;
	}

	public Class<? extends AbstractApiExceptionMapper> getMapper() {
		return mapper;
	}
	
	public MapperMappingDefinition locationType(IErrorLocationType locationType) {
		this.locationType = locationType;
		return this;
	}
}
