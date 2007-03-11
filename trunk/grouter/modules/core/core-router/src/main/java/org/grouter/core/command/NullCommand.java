package org.grouter.core.command;

import org.apache.log4j.Logger;

/**
 * Created by IntelliJ IDEA.
 * User: georges.polyzois
 * Date: 2006-aug-29
 * Time: 23:39:24
 * To change this template use File | Settings | File Templates.
 */
public class NullCommand extends AbstractCommandWriter
{
    //Logger.
    private static Logger logger = Logger.getLogger(NullCommand.class);
    public void execute()
    {
        logger.info("A null command");
    }

    public void transform()
    {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void write()
    {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void backup()
    {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void log()
    {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
