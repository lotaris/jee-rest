package com.forbesdigital.jee.test;

import javax.ws.rs.Path;

/**
 * This class is used in AbstractRestApplicationUnitTest to test the 
 * loading of @Path classes by package.
 * 
 * @author Baptiste Roth (baptiste.roth@lotaris.com)
 */
@Path(value = "anotherTest")
public class AnotherPathAnnotatedForAbstractRestApplication {
}
