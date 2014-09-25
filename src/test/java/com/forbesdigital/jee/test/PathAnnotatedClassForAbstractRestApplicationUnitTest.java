package com.forbesdigital.jee.test;

import javax.ws.rs.Path;

/**
 * This class is used in AbstractRestApplicationUnitTest to test the 
 * loading of @Path classes by package.
 * 
 * @see AbstractRestApplicationUnitTest
 * @author Baptiste Roth (baptiste.roth@lotaris.com)
 */
@Path(value = "test")
public class PathAnnotatedClassForAbstractRestApplicationUnitTest {
}
