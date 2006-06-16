package com.sonyericsson.fun.http.common;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.spi.InitialContextFactory;

/**
 * @author torben.hc
 *
 */
public class MockInitialContextFactory implements InitialContextFactory {

	private MockInitialContext mockInitialContext = null;

	/**
	 * Builds and returns a MockInitialContext object 
	 * @param env a Hashtable of properies to instantiate the InitialContext.
	 * @return a MockInitialContext object.
	 * @exception NamingException if an error occurs building an InitialContext
	 */
	public final Context getInitialContext(final Hashtable env) throws NamingException {
		try {
			if (mockInitialContext == null) {
				mockInitialContext = new MockInitialContext();
				mockInitialContext.bind("java:comp/env/propertyhost", "localhost");
			}
			return mockInitialContext;
		} catch (Exception e) {
			e.printStackTrace();
			throw new NamingException(e.getMessage());
		}
	}
}