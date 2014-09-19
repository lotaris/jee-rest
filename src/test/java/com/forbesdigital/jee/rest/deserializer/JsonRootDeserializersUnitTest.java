package com.forbesdigital.jee.rest.deserializer;

import com.lotaris.rox.annotations.RoxableTest;
import com.lotaris.rox.annotations.RoxableTestClass;
import java.util.HashMap;
import java.util.Map;
import org.codehaus.jackson.map.JsonDeserializer;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.type.SimpleType;
import org.codehaus.jackson.type.JavaType;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import org.mockito.internal.util.reflection.Whitebox;

/**
 *
 * @author Baptiste Roth (baptiste.roth@forbes-digital.com)
 */
@RoxableTestClass(tags = {"jsonDeserializers"})
public class JsonRootDeserializersUnitTest {
	
	private JsonRootDeserializers jsonRootDeserializer;
	
	private JsonRootWrapper<Map<String, Integer>> jsonRootWrapper = new JsonRootWrapper<>();
	
	@Before
	public void setUp() {
		jsonRootDeserializer = new JsonRootDeserializers();
	}
	
	@Test
	@RoxableTest(key = "c03c81c0e9eb")
	public void jsonRootDeserializerShouldReturnNullForNonJsonRootWrapperType() {
		JavaType type = SimpleType.construct(Integer.class);
		JsonDeserializer d = null;
			
		try {
			d = jsonRootDeserializer.findBeanDeserializer(type, null, null, null, null);
		} catch (JsonMappingException ex) {
			fail("Unexpected exception when finding Bean Deserializer");
		}
		
		assertNull(d);
	}
	
	@Test
	@RoxableTest(key = "ee1d7bd61b13")
	public void jsonRootDeserializerShouldReturnExistingDeserializerIfAny() {
		JavaType type = SimpleType.construct(jsonRootWrapper.getClass());
		JsonRootWrapperDeserializer expectedDeserializer = new JsonRootWrapperDeserializer(type.containedType(0).getRawClass());
		JsonDeserializer actualDeserializer = null;
		
		HashMap<Class, JsonRootWrapperDeserializer> deserializers = (HashMap<Class, JsonRootWrapperDeserializer>) Whitebox.getInternalState(jsonRootDeserializer, "deserializers");
		when(deserializers.containsKey(type.containedType(0).getRawClass())).thenReturn(Boolean.TRUE);
		when(deserializers.get(type.containedType(0).getRawClass())).thenReturn(expectedDeserializer);
			
		try {
			actualDeserializer = jsonRootDeserializer.findBeanDeserializer(type, null, null, null, null);
		} catch (JsonMappingException ex) {
			fail("Unexpected exception when finding Bean Deserializer");
		}
		
		assertEquals(expectedDeserializer, actualDeserializer);
	}
	
}
