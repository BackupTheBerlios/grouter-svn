package codesnipps.designpatterns.simplefactory;

import java.util.HashMap;

/**
 * Cars shoulds inherit from this abstract superclass.
 * 
 * @stereotype abstract
 */
public abstract class Car
{
    String color;

    abstract String getColor();
    
}
