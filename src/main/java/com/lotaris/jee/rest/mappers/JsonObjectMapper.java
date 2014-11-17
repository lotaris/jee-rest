package com.lotaris.jee.rest.mappers;

import com.lotaris.jee.rest.deserializer.JsonRootDeserializers;
import java.text.SimpleDateFormat;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.map.annotate.JsonSerialize;

/**
 * Configuration of Jackson serialization and deserialization.
 *
 * @author Simon Oulevay (simon.oulevay@lotaris.com)
 * @author Laurent Prevost, laurent.prevost@lotaris.com
 */
@Provider
@Produces(MediaType.APPLICATION_JSON)
public class JsonObjectMapper implements ContextResolver<ObjectMapper> {

	private ObjectMapper jacksonObjectMapper;

	public JsonObjectMapper() {

		jacksonObjectMapper = new ObjectMapper();

		// do not serialize null values
		jacksonObjectMapper.setSerializationInclusion(JsonSerialize.Inclusion.NON_NULL);

		/*
		 * Do not wrap objects in a single-property JSON object with the root
		 * name. The result is:
		 *
		 * { name: 'my app' }
		 *
		 * Instead of:
		 *
		 * { application: { name: 'my app' } }
		 */
		jacksonObjectMapper.configure(SerializationConfig.Feature.WRAP_ROOT_VALUE, false);
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
		jacksonObjectMapper.setDateFormat(dateFormat);

		/**
		 * As far as we go for creation of a TO per view representation, we do not want to accept
		 * unknown properties anymore. Not accepting unknown values with the "each to per view"
		 * means that you cannot write something like:
		 *
		 * { "key": null }
		 *
		 * if the TO has not the attribute key.
		 */
		jacksonObjectMapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, true);

		/**
		 * Create a deserializer registry to add the possibility to use a root wrapper
		 */
		jacksonObjectMapper.setDeserializerProvider(jacksonObjectMapper.getDeserializerProvider().withAdditionalDeserializers(new JsonRootDeserializers()));
	}

	@Override
	public ObjectMapper getContext(Class<?> arg0) {
		return jacksonObjectMapper;
	}
}
