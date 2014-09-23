package com.forbesdigital.jee.rest.deserializer;

import com.lotaris.rox.annotations.RoxableTest;
import com.lotaris.rox.annotations.RoxableTestClass;
import java.util.HashMap;
import java.util.Map;
import org.codehaus.jackson.map.JsonDeserializer;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.type.SimpleType;
import org.codehaus.jackson.map.type.TypeBase;
import org.codehaus.jackson.type.JavaType;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

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
	
	private JsonRootWrapper<Object> jsonRootWrapper = new JsonRootWrapper<>();
	
	@Mock
	private Map<Class, JsonRootWrapperDeserializer> deserializers;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);

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
		JavaType type = new TestType(JsonRootWrapper.class, Integer.class);
		
		JsonRootWrapperDeserializer expectedDeserializer = new JsonRootWrapperDeserializer(type.containedType(0).getRawClass());
		JsonDeserializer actualDeserializer = null;
		
		Whitebox.setInternalState(jsonRootDeserializer, "deserializers", deserializers);
		when(deserializers.containsKey(type.containedType(0).getRawClass())).thenReturn(Boolean.TRUE);
		when(deserializers.get(type.containedType(0).getRawClass())).thenReturn(expectedDeserializer);
			
		try {
			actualDeserializer = jsonRootDeserializer.findBeanDeserializer(type, null, null, null, null);
		} catch (JsonMappingException ex) {
			fail("Unexpected exception when finding Bean Deserializer");
		}
		
		assertEquals(expectedDeserializer, actualDeserializer);
	}
	
	@Test
	@RoxableTest(key = "59523c16bbb0")
	public void jsonRootDeserializerShouldReturnNewDeserializerIfAny() {
		JavaType type = new TestType(JsonRootWrapper.class, Integer.class);
		
		Object actualDeserializer = null;
		
		Whitebox.setInternalState(jsonRootDeserializer, "deserializers", deserializers);
		when(deserializers.containsKey(type.containedType(0).getRawClass())).thenReturn(Boolean.FALSE);
			
		try {
			actualDeserializer = jsonRootDeserializer.findBeanDeserializer(type, null, null, null, null);
		} catch (JsonMappingException ex) {
			fail("Unexpected exception when finding Bean Deserializer");
		}
		
		assertTrue(actualDeserializer instanceof JsonRootWrapperDeserializer);
		verify(deserializers).put(eq(type.containedType(0).getRawClass()), argThat(new BaseMatcher<JsonRootWrapperDeserializer>() {

			@Override
			public boolean matches(Object item) {
				return item instanceof JsonRootWrapperDeserializer;
			}

			@Override
			public void describeTo(Description description) {
				description.appendText("an instance of JsonRootWrapperDeseralizer");
			}
			
		}));
	}

	public static class TestType extends TypeBase {
		private Class genericClass;
		public TestType(Class baseClass, Class genericClass) {
			super(baseClass, 1);
			this.genericClass = genericClass;
		}
	
		@Override protected String buildCanonicalName() { return JsonRootWrapper.class.getCanonicalName(); }
		@Override public StringBuilder getGenericSignature(StringBuilder sb) { return sb; }
		@Override public StringBuilder getErasedSignature(StringBuilder sb) { return sb; }
		@Override public JavaType withTypeHandler(Object h) { return SimpleType.construct(h.getClass()); }
		@Override public JavaType withContentTypeHandler(Object h) { return SimpleType.construct(h.getClass()); }
		@Override protected JavaType _narrow(Class<?> subclass) { return SimpleType.construct(subclass); }
		@Override public JavaType narrowContentsBy(Class<?> contentClass) { return SimpleType.construct(contentClass); }
		@Override public JavaType widenContentsBy(Class<?> contentClass) { return SimpleType.construct(contentClass); }
		@Override public boolean isContainerType() { return true; }
		@Override public String toString() { return ""; }
		@Override public boolean equals(Object o) { return o == this; }
			
		@Override 
		public JavaType containedType(int index) {
			return SimpleType.construct(genericClass);
		}
	}
}
