package org.grouter.common.jms.examples;

import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueSession;
import javax.jms.QueueReceiver;

/**
 * Created by IntelliJ IDEA.
 * User: geopol
 * Date: Sep 21, 2006
 * Time: 4:35:21 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractJBossExample
{
    protected Queue que;
    protected static final String QUEUE_TEST_QUEUE = "queue/GrouterQueue";
    protected QueueConnection conn;
    protected QueueSession session;
    protected QueueReceiver queueReceiver;
    protected static final int SLEEP = 5000;
}
