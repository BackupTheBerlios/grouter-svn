package org.grouter.core;

import org.apache.log4j.Category;
import org.apache.log4j.Logger;
import org.apache.log4j.NDC;
import org.apache.log4j.PropertyConfigurator;

import java.util.*;

/**
 * Main class for Siri. 
 *
 * Reads in all config and starts up all services, archiving thread, and binds to
 * jndi in j2ee app server.
 *
 * Adds shutdown hook to vm so that we get to send an email when we close down gracefully
 * using ctrl+c (win) or kill -15 (*nix). 
 * 
 * @author Georges Polyzois
 * @version
 *
 */
public class GRouterServiceImpl implements GRouterService
{

    public String ping()
    {
        return "hello world";
    }
}
