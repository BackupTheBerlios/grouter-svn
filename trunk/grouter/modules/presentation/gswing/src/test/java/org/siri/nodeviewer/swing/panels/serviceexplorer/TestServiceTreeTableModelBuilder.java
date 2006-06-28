/**
 * Copyright 2005 Sony Ericsson Mobile Communications AB. All rights reserved.
 *
 * Description...
 *
 * @author Georges Polyzois
 * @since $Date: 2006/04/07 15:33:16 $
 * @version $Revision: 1.1.4.2 $
 */
package org.siri.nodeviewer.swing.panels.serviceexplorer;

import junit.framework.TestCase;
import org.apache.log4j.Logger;
import org.siri.nodeviewer.swing.util.Constants;
import org.siri.nodeviewer.swing.util.IconFactory;

public class TestServiceTreeTableModelBuilder extends TestCase
{

    private static Logger logger = Logger.getLogger(TestServiceTreeTableModelBuilder.class);

    public TestServiceTreeTableModelBuilder(String test)
    {
        super(test);

    }

    /**
     * The fixture set up called before every test method.
     */
    protected void setUp() throws Exception
    {
    }

    /**
     * The fixture clean up called after every test method.
     */
    protected void tearDown() throws Exception
    {
    }

    public void testSomething() throws Exception
    {
        ServiceNode root = new ServiceNode(new ServiceNodeItem("root", "root", "", "", IconFactory.getImageIcon(Constants.FLAG)));
        ServiceNode router1 = new ServiceNode(new ServiceNodeItem("child_level1", "child_level1", "name", "running", IconFactory.getImageIcon(Constants.FLAG)));
        ServiceNode router2 = new ServiceNode(new ServiceNodeItem("child_level2", "child_level2", "name", "running", IconFactory.getImageIcon(Constants.FLAG)));

        ServiceTreeTableModelBuilder builder = new ServiceTreeTableModelBuilder(root);
        builder.addChild(router1);
        builder.addSibling(router2);
        builder.toString();

        logger.debug(builder.toString());
        //assertEquals(3, builder.getServiceTreeTableModel().getRowCount());


    }
}