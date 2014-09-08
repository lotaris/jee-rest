package com.forbesdigital.jee.rest;

import com.forbesdigital.jee.validation.IConstraintConverter;
import com.forbesdigital.jee.validation.preprocessing.ApiPreprocessingContext;
import com.forbesdigital.jee.validation.preprocessing.DefaultPreprocessingChain;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

/**
 * Useful utilities for REST resources.
 *
 * @author Simon Oulevay (simon.oulevay@lotaris.com)
 */
public abstract class AbstractResource {
	@Inject
	private DefaultPreprocessingChain preprocessingChain;
		
	/**
	 * Information about the request
	 */
	@Inject
	protected HttpServletRequest request;
	
	@PostConstruct
	private void postConstruct() {
		preprocessingChain.getBeanValidationProcessor().setConstraintConverter(getConstraintConverter());
		doPostConstruct();
	}
	
	/**
	 * Allow to add logic in the post construct phase
	 */
	protected void doPostConstruct() {
	}
	
	/**
	 * @return Retrieve the implementation of a ConstraintConverter
	 */
	protected abstract IConstraintConverter getConstraintConverter();
	
	/**
	 * Returns a context that can configure and run the default preprocessing chain (modifiers and
	 * validations).
	 *
	 * @return a preprocessing context
	 */
	protected ApiPreprocessingContext preprocessing() {
		return new ApiPreprocessingContext(preprocessingChain);
	}
}
