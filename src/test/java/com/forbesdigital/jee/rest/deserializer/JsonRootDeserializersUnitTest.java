package com.forbesdigital.jee.rest.deserializer;

import com.lotaris.rox.annotations.RoxableTest;
import com.lotaris.rox.annotations.RoxableTestClass;
import org.codehaus.jackson.map.JsonDeserializer;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.type.SimpleType;
import org.codehaus.jackson.type.JavaType;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 *
 * @author Baptiste Roth (baptiste.roth@forbes-digital.com)
 */
@RoxableTestClass(tags = {"jsonDeserializers"})
public class JsonRootDeserializersUnitTest {
	
	private JsonRootDeserializers jsonRootDeserializer;
	
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
		JavaType type = SimpleType.construct(JsonRootWrapper.class);
		JsonDeserializer d = null;
		
		when(type.containedType(0).getRawClass()).thenReturn(type.getClass());
			
		try {
			d = jsonRootDeserializer.findBeanDeserializer(type, null, null, null, null);
		} catch (JsonMappingException ex) {
			fail("Unexpected exception when finding Bean Deserializer");
		}
		
		assertNull(d);
	}
	
}
