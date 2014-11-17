package com.lotaris.jee.rest.mappers;

import com.lotaris.jee.validation.IErrorCode;
import com.lotaris.jee.validation.IErrorLocationType;

/**
 * Mapping definition to bind an ExceptionMapper with an ErrorCode and LocationType
 *
 * @author Laurent Prevost <laurent.prevost@lotaris.com>
 */
public class MapperMappingDefinition {
	/**
	 * Class of an ExceptionMapper
	 */
	private Class<? extends AbstractApiExceptionMapper> mapper;
	
	/**
	 * Error code bound
	 */
	private IErrorCode code;
	
	/**
	 * Location type bound
	 */
	private IErrorLocationType locationType;
	
	/**
	 * Constructor
	 * 
	 * @param mapper ExceptionMapper class
	 * @param code Error code
	 */
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
	
	/**
	 * Set the location type and return itself as a builder pattern
	 * 
	 * @param locationType Location type to set
	 * @return this
	 */
	public MapperMappingDefinition locationType(IErrorLocationType locationType) {
		this.locationType = locationType;
		return this;
	}
}
