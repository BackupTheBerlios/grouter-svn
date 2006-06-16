package com.sonyericsson.fun.http.common;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import javax.naming.Context;
import javax.naming.Name;
import javax.naming.NameParser;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;

/**
 * Overrides the InitialContext to provide an in-memory lookup mechanism
 * instead of network lookup. The only methods overriden are bind() and 
 * lookup().
 * @author Sujit Pal (spal@users.sourceforge.net)
 * @version $Revision: 1.1.8.2 $
 */
public class MockInitialContext implements Context {

	private Map map;

	/**
	 * Default constructor used by the MockInitialContextFactory.
	 * @exception NamingException if an error occurs in initialization.
	 */
	public MockInitialContext() throws NamingException {
		//        super();
		map = new HashMap();
	}

	/**
	 * Binds an Object into the Context by name.
	 * @param name the key to look the Object up by.
	 * @param obj the Object to bind into the Context.
	 * @exception NamingException if there is a problem with binding.
	 */
	public final void bind(final String name, final Object obj) throws NamingException {
		map.put(name, obj);
	}

	/**
	 * Retrieves an Object from the Context by name.
	 * @param name the name of the Object in the context.
	 * @return the value of the Object.
	 * @exception NamingException if there is a problem with lookup.
	 */
	public final Object lookup(final String name) throws NamingException {
		return map.get(name);
	}

	public Object lookup(Name name) throws NamingException {
		return null;
	}

	public void bind(Name name, Object obj) throws NamingException {
	}

	public void rebind(Name name, Object obj) throws NamingException {
	}

	public void rebind(String name, Object obj) throws NamingException {
	}

	public void unbind(Name name) throws NamingException {
	}

	public void unbind(String name) throws NamingException {
	}

	public void rename(Name oldName, Name newName) throws NamingException {
	}

	public void rename(String oldName, String newName) throws NamingException {
	}

	public NamingEnumeration list(Name name) throws NamingException {
		return null;
	}

	public NamingEnumeration list(String name) throws NamingException {
		return null;
	}

	public NamingEnumeration listBindings(Name name) throws NamingException {
		return null;
	}

	public NamingEnumeration listBindings(String name) throws NamingException {
		return null;
	}

	public void destroySubcontext(Name name) throws NamingException {
	}

	public void destroySubcontext(String name) throws NamingException {
	}

	public Context createSubcontext(Name name) throws NamingException {
		return null;
	}

	public Context createSubcontext(String name) throws NamingException {
		return null;
	}

	public Object lookupLink(Name name) throws NamingException {
		return null;
	}

	public Object lookupLink(String name) throws NamingException {
		return null;
	}

	public NameParser getNameParser(Name name) throws NamingException {
		return null;
	}

	public NameParser getNameParser(String name) throws NamingException {
		return null;
	}

	public Name composeName(Name name, Name prefix) throws NamingException {
		return null;
	}

	public String composeName(String name, String prefix) throws NamingException {
		return null;
	}

	public Object addToEnvironment(String propName, Object propVal) throws NamingException {
		return null;
	}

	public Object removeFromEnvironment(String propName) throws NamingException {
		return null;
	}

	public Hashtable getEnvironment() throws NamingException {
		return null;
	}

	public void close() throws NamingException {

	}

	public String getNameInNamespace() throws NamingException {
		return null;
	}

}