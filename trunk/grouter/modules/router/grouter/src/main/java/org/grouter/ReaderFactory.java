/**
 * ReaderFactory.java
 */
package org.grouter;

import org.apache.log4j.Logger;
/*import org.iris.server.filehandlers.*;
import org.iris.server.filehandlers.FileMessageHandlerFactory;
import org.iris.server.util.config.*;
import org.iris.server.pollhandlers.PollEJBMessageHandler;
  */

/**
 * 
 */
public class ReaderFactory
{
	/** Logger. */
    static Logger logger = Logger.getLogger(ReaderFactory.class.getName());

    /**
     * Create Reader based on service config parameters fro this service.
     * Set status flag of readers to not started - we will start these up
     * later.
     *
     * @param service
     * @return
     * @throws Exception
     */
    //public static FileMessageHandler createNotStartedInstance(Service service) throws Exception
    //{
        /*
        *
        if (service instanceof ServicePollEJB)
        {
            ServicePollEJB servicePollEjb = (ServicePollEJB) service;
            FileMessageHandler messanger = new PollEJBMessageHandler(servicePollEjb);
            messanger.setServiceStatus(FileMessageHandler.NOTSTARTED);
            return messanger;

        }
        else if (service instanceof ServiceFileEmail)
        {
            ServiceFileEmail serviceF = (ServiceFileEmail) service;
            FileMessageHandler messanger = new FileToEmailMessegeHandler(serviceF);
            messanger.setServiceStatus(FileMessageHandler.NOTSTARTED);
            return messanger;
        }*/
/*        if (service instanceof ServiceFileFile)
        {
            ServiceFileFile serviceF = (ServiceFileFile) service;
            FileMessageHandler messanger = new FileToFileMessageHandler(serviceF);
            messanger.setServiceStatus(FileMessageHandler.NOTSTARTED);
            return messanger;
        }
  */      /*else if (service instanceof ServiceFileEJB)
        {
            ServiceFileEJB serviceEjb = (ServiceFileEJB) service;

            FileMessageHandler messanger = null;
            messanger = new FileToEJBMessegeHandler(serviceEjb);
            messanger.setServiceStatus(FileMessageHandler.NOTSTARTED);
            return messanger;
        } */
        /*else
        {
            throw new Exception("This service is not handled - " + service.getId());
        }
    } */



}



