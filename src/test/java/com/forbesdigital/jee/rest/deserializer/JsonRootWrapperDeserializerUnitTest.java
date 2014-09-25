package com.forbesdigital.jee.rest.deserializer;

import com.lotaris.rox.annotations.RoxableTest;
import com.lotaris.rox.annotations.RoxableTestClass;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Iterator;
import org.codehaus.jackson.Base64Variant;
import org.codehaus.jackson.JsonLocation;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonStreamContext;
import org.codehaus.jackson.JsonToken;
import org.codehaus.jackson.ObjectCodec;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ArrayNode;
import org.codehaus.jackson.node.IntNode;
import org.codehaus.jackson.node.JsonNodeFactory;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * @see JsonRootWrapperDeserializer 
 * @author Baptiste Roth (baptiste.roth@forbes-digital.com)
 */
@RoxableTestClass(tags = {"jsonDeserializers"})
public class JsonRootWrapperDeserializerUnitTest {
	
	private JsonRootWrapperDeserializer jsonRootWrapperDeserializer;
	
	@Mock
	private final JsonNode root = new ArrayNode(JsonNodeFactory.instance);
	@Mock
	private ObjectMapper om;
	private TestJsonParser jp;
	
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		jp = new TestJsonParser(om);
		jsonRootWrapperDeserializer = new JsonRootWrapperDeserializer(JsonRootWrapper.class);
	}
	
	@Test
	@RoxableTest(key = "17fd717fc174")
	public void jsonRootWrapperDeserializerConstructorShouldThrowExceptionIfContentTypeIsNull() {
		try {
			jsonRootWrapperDeserializer = new JsonRootWrapperDeserializer(null);
			fail("Building a JsonRootWrapperDeserializer without contentType should throw exception");
		} catch (IllegalArgumentException ex) {
		}
	}
	
	@Test
	@RoxableTest(key = "cfea7b92a7d4")
	public void jsonRootWrapperDeserializerShouldDeserializeAsArray() throws IOException {
		int nbElements = 3;

		when(om.readTree(jp)).thenReturn(root);
		when(root.isArray()).thenReturn(Boolean.TRUE);
		when(root.getElements()).thenReturn(new TestIterator(nbElements));
		
		jsonRootWrapperDeserializer.deserialize(jp, null);
		
		verify(om, times(nbElements)).readValue(any(JsonNode.class), eq(JsonRootWrapper.class));
	}
	
	@Test
	@RoxableTest(key = "9d9195655580")
	public void jsonRootWrapperDeserializerShouldDeserializeAsSingleObject() throws IOException {

		when(om.readTree(jp)).thenReturn(root);
		when(root.isArray()).thenReturn(Boolean.FALSE);
		
		jsonRootWrapperDeserializer.deserialize(jp, null);
		
		verify(om).readValue(any(JsonNode.class), eq(JsonRootWrapper.class));
	}
	
	public static class TestJsonParser extends JsonParser {
		
		private final ObjectMapper objectMapper;
		
		public TestJsonParser(ObjectMapper om) {
			this.objectMapper = om;
		}
		
		@Override
		public ObjectCodec getCodec() {
			return objectMapper;
		}

		// <editor-fold defaultstate="collapsed" desc="Unimplemented methods">
		@Override public void setCodec(ObjectCodec c) {throw new UnsupportedOperationException("Not required for tests.");}
		@Override public void close() throws IOException {throw new UnsupportedOperationException("Not required for tests.");}
		@Override public JsonToken nextToken() throws IOException, JsonParseException {throw new UnsupportedOperationException("Not required for tests.");}
		@Override public JsonParser skipChildren() throws IOException, JsonParseException {throw new UnsupportedOperationException("Not required for tests.");}
		@Override public boolean isClosed() {throw new UnsupportedOperationException("Not required for tests.");}
		@Override public String getCurrentName() throws IOException, JsonParseException {throw new UnsupportedOperationException("Not required for tests.");}
		@Override public JsonStreamContext getParsingContext() {throw new UnsupportedOperationException("Not required for tests.");}
		@Override public JsonLocation getTokenLocation() {throw new UnsupportedOperationException("Not required for tests.");}
		@Override public JsonLocation getCurrentLocation() {throw new UnsupportedOperationException("Not required for tests.");}
		@Override public String getText() throws IOException, JsonParseException {throw new UnsupportedOperationException("Not required for tests.");}
		@Override public char[] getTextCharacters() throws IOException, JsonParseException {throw new UnsupportedOperationException("Not required for tests.");}
		@Override public int getTextLength() throws IOException, JsonParseException {throw new UnsupportedOperationException("Not required for tests.");}
		@Override public int getTextOffset() throws IOException, JsonParseException {throw new UnsupportedOperationException("Not required for tests.");}
		@Override public Number getNumberValue() throws IOException, JsonParseException {throw new UnsupportedOperationException("Not required for tests.");}
		@Override public JsonParser.NumberType getNumberType() throws IOException, JsonParseException {throw new UnsupportedOperationException("Not required for tests.");}
		@Override public int getIntValue() throws IOException, JsonParseException {throw new UnsupportedOperationException("Not required for tests.");}
		@Override public long getLongValue() throws IOException, JsonParseException {throw new UnsupportedOperationException("Not required for tests.");}
		@Override public BigInteger getBigIntegerValue() throws IOException, JsonParseException {throw new UnsupportedOperationException("Not required for tests.");}
		@Override public float getFloatValue() throws IOException, JsonParseException {throw new UnsupportedOperationException("Not required for tests.");}
		@Override public double getDoubleValue() throws IOException, JsonParseException {throw new UnsupportedOperationException("Not required for tests.");}
		@Override public BigDecimal getDecimalValue() throws IOException, JsonParseException {throw new UnsupportedOperationException("Not required for tests.");}
		@Override public byte[] getBinaryValue(Base64Variant b64variant) throws IOException, JsonParseException {throw new UnsupportedOperationException("Not required for tests.");}
		// </editor-fold>
	}
	
	public static class TestIterator implements Iterator<JsonNode> {
		
		public static int nbElements;
		
		public TestIterator(int nbElements) {
			TestIterator.nbElements = nbElements;
		}

		@Override
		public boolean hasNext() {
			if (nbElements > 0) {
				nbElements--;
				return true;
			}
			return false;
		}

		@Override
		public JsonNode next() {
			return new IntNode(nbElements);
		}

		// <editor-fold defaultstate="collapsed" desc="Unimplemented methods">
		@Override public void remove() {throw new UnsupportedOperationException("Not required for tests.");}
		// </editor-fold>
	}
}
