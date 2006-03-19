package org.grouter.common.jndi;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * A dto used by {@link JNDIUtils} holding information about implementation and where
 * to bindthe implementation in the jndi providers tree.
 *
 * @author Georges Polyzois
 * @see JNDIUtils
 */
public class BindingItem
{
    private String jndiName;
    private Object implemenation;
    private String[] jndipath;


    /**
     * E.g. consideer binding a sessionfactory (jndiname) to java:env/comp/mbeans (jndipath).
     * The jndipath provided should then look like :
     * String[] jndpath = {"java:env","comp/mbeans"} and jndiname could be "mysessionfactory"
     * and implementation would be Object implemenation = new SessionFactory(). A mocked
     * sessionfactory could come in handyfor test case scenarios.
     *
     *
     * @param jndiName name of object bound to a jndipath
     * @param implemenation a instance with provides some service
     * @param jndipath the path to the jndi bound instance, can only be of length 2
     */
    public BindingItem(String jndiName, Object implemenation, String[] jndipath)
    {
        if(jndipath.length >2)
        {
            throw new IllegalArgumentException("Only two elements allowed. Found : " + jndipath.length +  " comp and env, e.g. {\"java:comp\",\"mbeans\"}");
        }
        this.jndiName = jndiName;
        this.implemenation = implemenation;
        this.jndipath = jndipath;
    }

    public String getJndiName()
    {
        return jndiName;
    }


    public Object getImplemenation()
    {
        return implemenation;
    }


    public String[] getJndipath()
    {
        return jndipath;
    }

    /**
     * Will return something like java:comp/mbeans/mybean
     * @return
     */
    public String getFullJnidPath()
    {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.jndipath[0] );
        stringBuilder.append("/");
        stringBuilder.append(this.jndipath[1] );
        stringBuilder.append("/");
        stringBuilder.append(this.jndiName);
        return stringBuilder.toString();
    }
}
