package com.lotaris.jee.rest.deserializer;

import com.lotaris.jee.validation.IJsonWrapper;
import com.lotaris.jee.validation.SingleObjectOrList;
import java.util.List;
import javax.validation.Valid;
import org.codehaus.jackson.annotate.JsonIgnore;

/**
 * The JSON root wrapper allows to encapsulate a list of objects
 * or a single object and to do the difference between both.
 * 
 * Annotations present on the fields allow the bean validation process
 * and the deserialization to work correctly.
 * 
 * @author Laurent Prevost, laurent.prevost@lotaris.com
 * @param <T> The single object type to know for which class the wrapper is dedicated
 */
public class JsonRootWrapper<T> implements IJsonWrapper, SingleObjectOrList<T> {

	@Valid
	@JsonIgnore
	private List<T> list;

	@Valid
	@JsonIgnore
	private T object;
	
	public JsonRootWrapper() {
	}

	@Override
	public List<T> getList() {
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}

	public void setSingleObject(T object) {
		this.object = object;
	}

	@Override
	public T getSingleObject() {
		return object;
	}
	
	@Override
	public boolean isSingleObject() {
		return list == null && object != null;
	}
	
	/**
	 * Provides the number of wrapped elements.
	 * <em>Must not be used to determine whether the {@link JsonRootWrapper} 
	 * contains a list or a single object, use {@link #isSingleObject()} instead.</em>
	 * 
	 * @return the number of wrapped elements
	 */
	public int getNumberOfWrappedElements() {
		return isSingleObject() ? 1 : list.size();
	}
}
