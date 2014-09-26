package com.forbesdigital.jee.rest.test.dummyresourcepackage;

import javax.ws.rs.Path;

/**
 * Dummy class definition with @Path annotation to validate the 
 * REST Application package scanning.
 * 
 * @see AbstractRestApplicationUnitTest
 * @author Baptiste Roth (baptiste.roth@lotaris.com)
 */
@Path(value = "anotherTest")
public class AnotherPathAnnotatedForAbstractRestApplication {
}
