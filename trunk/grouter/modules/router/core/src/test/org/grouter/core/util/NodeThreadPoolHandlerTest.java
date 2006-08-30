package org.grouter.core.util;

import junit.framework.TestCase;
import org.grouter.core.config.Node;
import org.grouter.core.config.InFolder;
import org.grouter.core.config.BatchRead;
import org.grouter.core.config.OutFolder;

import java.io.File;

/**
 * Created by IntelliJ IDEA.
 * User: georges.polyzois
 * Date: 2006-aug-29
 * Time: 07:59:51
 * To change this template use File | Settings | File Templates.
 */
public class NodeThreadPoolHandlerTest extends TestCase
{
    private Node[] nodes = new Node[1];


    /**
     * Setup.
     *
     * @throws Exception
     */
    public void setUp() throws Exception
    {
        super.setUp();
        //@ todo replace with temp dir   System.getProperty("
        InFolder inFolder = new InFolder(new File("c:/temp/kalle/in"), 2000, false, null);
        OutFolder outFolder = new OutFolder(new File("c:/temp/kalle/out"));
        Node node = new Node(Node.Type.FILE_TO_FILE, "id_1", true, inFolder, outFolder);
        nodes[0] = node;
    }



    /**
     * Tear down.
     *
     * @throws Exception
     */
    public void tearDown() throws Exception
    {
        super.tearDown();
    }


    public void testNTPHandler()
    {
        NodeThreadPoolHandler nodeThreadPoolHandler = new NodeThreadPoolHandler();

        nodeThreadPoolHandler.initNodeThreadScheduling(nodes);

    }
}
