package com.lotaris.jee.rest.deserializer;

import com.lotaris.rox.annotations.RoxableTest;
import com.lotaris.rox.annotations.RoxableTestClass;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @see JsonRootWrapper
 * @author Baptiste Roth (baptiste.roth@lotaris.com)
 */
@RoxableTestClass(tags = {"jsonDeserializers"})
public class JsonRootWrapperUnitTest {
	
	private JsonRootWrapper jsonRootWrapper;
	
	@Before
	public void setUp() {
		jsonRootWrapper = new JsonRootWrapper();
	}
	
	@Test
	@RoxableTest(key = "fe0307f2a74b")
	@SuppressWarnings("unchecked")
	public void jsonRootWrapperIsSingleObjectShouldReturnTrueIfListIsNullAndObjectIsNot() {
		jsonRootWrapper.setList(null);
		jsonRootWrapper.setSingleObject(new Object());
		assertTrue(jsonRootWrapper.isSingleObject());
	}
	
	@Test
	@RoxableTest(key = "d75a43770285")
	@SuppressWarnings("unchecked")
	public void jsonRootWrapperIsSingleObjectShouldReturnFalseIfListIsNotNull() {
		jsonRootWrapper.setList(new ArrayList());
		jsonRootWrapper.setSingleObject(new Object());
		assertFalse(jsonRootWrapper.isSingleObject());
	}
	
	@Test
	@RoxableTest(key = "e278f1313877")
	@SuppressWarnings("unchecked")
	public void jsonRootWrapperIsSingleObjectShouldReturnFalseIfListAndObjectAreBothNull() {
		jsonRootWrapper.setList(null);
		jsonRootWrapper.setSingleObject(null);
		assertFalse(jsonRootWrapper.isSingleObject());
	}
	
	@Test
	@RoxableTest(key = "a4da57cda6e6")
	@SuppressWarnings("unchecked")
	public void jsonRootWrapperGetNumberOfWrappedElementsShouldReturnOneForSingleObject() {
		jsonRootWrapper.setList(null);
		jsonRootWrapper.setSingleObject(new Object());
		assertEquals(1, jsonRootWrapper.getNumberOfWrappedElements());
	}
	
	@Test
	@RoxableTest(key = "f978d1041bd2")
	@SuppressWarnings("unchecked")
	public void jsonRootWrapperGetNumberOfWrappedElementsShouldReturnListSizeForList() {
		List<String> list = new ArrayList<>();
		list.add("element1");
		list.add("element2");
		list.add("element3");
		jsonRootWrapper.setList(list);
		assertEquals(list.size(), jsonRootWrapper.getNumberOfWrappedElements());
	}
	
}
