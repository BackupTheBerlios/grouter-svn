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
import org.siri.nodeviewer.swing.util.IconFactory;
import org.siri.nodeviewer.swing.util.Constants;
import org.apache.log4j.Logger;

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
        ServiceNodeRow root = new ServiceNodeRow(new ServiceNodeItem("Grouter", "", "", "", IconFactory.getImageIcon(Constants.FLAG)));
        ServiceNodeRow router1 = new ServiceNodeRow(new ServiceNodeItem("id_router1", "id1", "name", "running", IconFactory.getImageIcon(Constants.FLAG)));
        ServiceNodeRow router2 = new ServiceNodeRow(new ServiceNodeItem("id_router2", "id2", "name", "running", IconFactory.getImageIcon(Constants.FLAG)));

        ServiceTreeTableModelBuilder builder = new ServiceTreeTableModelBuilder(root);
        builder.addChild(router1);
        builder.addChild(router2);
        builder.toString();
        //assertEquals(3, builder.getServiceTreeTableModel().getRowCount());


    }
}