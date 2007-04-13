package org.grouter.domain.servicelayer.spring;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.grouter.domain.servicelayer.RouterService;
import org.grouter.domain.entities.*;
import org.grouter.domain.daolayer.MessageDAO;
import org.springframework.jms.core.JmsTemplate;

/**
 * Tests for the GRouter service interface.
 *
 * @author Georges Polyzois
 */
public class GRouterQueueListenerImplTest extends AbstractServiceTests
{
    private static Log logger = LogFactory.getLog(GRouterQueueListenerImplTest.class);
    RouterService service;
    MessageDAO messageDAO;
    JmsTemplate jmsTemplate;


    public GRouterQueueListenerImplTest()
    {
       setAutowireMode(AUTOWIRE_BY_NAME);
    }


    public void testCreateMessage() throws Exception
    {
        assertTrue(true);

        Message message =  messageDAO.findById( MESSAGE_ID );
        assertNotNull( message );

        message.setContent( "Some content" );

        jmsTemplate.convertAndSend( message );


        Message message2 =  messageDAO.findById( MESSAGE_ID );

        logger.debug("####  " + message2.getContent() );

    }

    public void setJmsTemplate(JmsTemplate jmsTemplate)
    {
        this.jmsTemplate = jmsTemplate;
    }

    public void setMessageDAO(MessageDAO messageDAO)
    {
        this.messageDAO = messageDAO;
    }

    public void setGrouterService(RouterService service)
    {
        this.service = service;
    }


}