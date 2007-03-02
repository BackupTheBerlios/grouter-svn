package org.grouter.domain.entities;

import java.util.Map;
import java.util.HashMap;

/**
 * Context store - used to override EndPoint default settings.
 *
 * @author Georges Polyzois
 */
public class EndPointContext
{
    Map<String, String> properties = new HashMap<String, String>();
}
