package com.forbesdigital.jee.rest.deserializer;

import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;
import org.codehaus.jackson.map.ObjectMapper;

/**
 * The root wrapper deserializer offers the possibility to deserialize
 * arrays or object for the root element in a JSON format.
 * 
 * The deserializer do only the root element deserialization taking care to
 * let the remaining deserialization process to the normal deserializers.
 * 
 * To be able to deserialize, a content type class of the collection wrapper
 * is required.
 * 
 * @author Laurent Prevost, laurent.prevost@lotaris.com
 */
public class JsonRootWrapperDeserializer extends JsonDeserializer<JsonRootWrapper<?>> {
	/**
	 * The type of the object to deserialize
	 */
	private Class contentType;
	
	public JsonRootWrapperDeserializer(Class contentType) {
		if (contentType == null) {
			throw new IllegalArgumentException("A content type class is mandatory to allow the deserialization process.");
		}
		this.contentType = contentType;
	}
	
	@Override
	public JsonRootWrapper deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
		// Retrieve the object mapper and read the tree.
		ObjectMapper mapper = (ObjectMapper) jp.getCodec();
		JsonNode root = mapper.readTree(jp);

		// Create an instance of the wrapper.
		JsonRootWrapper wrapper = new JsonRootWrapper();

		// Check if the root received is an array.
		if (root.isArray()) {
			List list = new LinkedList();

			// Deserialize each node of the array using the type expected.
			Iterator<JsonNode> rootIterator = root.getElements();
			while (rootIterator.hasNext()) {
				list.add(mapper.readValue(rootIterator.next(), contentType));
			}

			wrapper.setList(list);
		}
		
		// Deserialize the single object.
		else {
			wrapper.setSingleObject(mapper.readValue(root, contentType));
		}

		return wrapper;
	}
}
