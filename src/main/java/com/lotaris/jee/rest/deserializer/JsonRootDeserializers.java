package com.lotaris.jee.rest.deserializer;

import java.util.HashMap;
import java.util.Map;
import org.codehaus.jackson.map.BeanDescription;
import org.codehaus.jackson.map.BeanProperty;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.DeserializerProvider;
import org.codehaus.jackson.map.Deserializers;
import org.codehaus.jackson.map.JsonDeserializer;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.type.JavaType;

/**
 * This class offer a way to manage a registry of root wrapper deserializer.
 * 
 * A deserializer is instantiated lazily on demand and is cached for further usages.
 * 
 * @author Laurent Prevost <laurent.prevost@lotaris.com>
 */
public class JsonRootDeserializers extends Deserializers.Base {
	/**
	 * Cache the deserializers
	 */
	private Map<Class, JsonRootWrapperDeserializer> deserializers = new HashMap<>();
	
	@Override
	public JsonDeserializer<?> findBeanDeserializer(JavaType type, DeserializationConfig config,
			DeserializerProvider provider, BeanDescription beanDesc, BeanProperty property) throws JsonMappingException {
		// Check if we have to find a deserializer for the root level.
		if (type.getRawClass() == JsonRootWrapper.class) {
			Class contentType = type.containedType(0).getRawClass();

			// Check if a deserializer already exists
			if (deserializers.containsKey(contentType)) {
				return deserializers.get(contentType);
			} else {
				// Create the new deserializer and cache it.
				JsonRootWrapperDeserializer deserializer = new JsonRootWrapperDeserializer(contentType);
				deserializers.put(contentType, deserializer);
				return deserializer;
			}
		}
		return null;
	}
}
