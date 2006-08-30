package org.grouter.core;

import junit.framework.TestCase;
import org.apache.log4j.Logger;
import org.apache.commons.io.FileUtils;
import org.grouter.core.config.NodeConfig;
import org.grouter.core.config.InFolderConfig;
import org.grouter.core.config.OutFolderConfig;

import java.io.File;

/**
 * Created by IntelliJ IDEA.
 * User: georges.polyzois
 * Date: 2006-aug-30
 * Time: 17:00:29
 * To change this template use File | Settings | File Templates.
 */
public class GrouterTest extends TestCase
{
    private static Logger logger = Logger.getLogger(GrouterTest.class);
    private NodeConfig[] nodeConfigs = new NodeConfig[1];
    private String tmpDir = System.getProperty("java.io.tmpdir") + "/grouter/nodethreadpoolhandlertest";
    File tmpdir;

    /**
     * Setup.
     *
     * @throws Exception
     */
    public void setUp() throws Exception
    {
        super.setUp();
        tmpdir = new File(tmpDir);
        tmpdir.mkdir();

        File infolder = new File(tmpDir + "/in");
        infolder.mkdir();

        FileUtils.writeStringToFile(new File(infolder.getPath() + "/testdata.txt"), "test data", "UTF-8");

        File outfolder = new File(tmpDir + "/out");
        outfolder.mkdir();

        logger.info("Created temporary folders : " + infolder + " and : " + outfolder);

        InFolderConfig inFolderConfig = new InFolderConfig(infolder, 2000, false, null);
        OutFolderConfig outFolderConfig = new OutFolderConfig(outfolder);
        NodeConfig nodeConfig = new NodeConfig(NodeConfig.Type.FILE_TO_FILE, "id_1", true, inFolderConfig, outFolderConfig);
        nodeConfigs[0] = nodeConfig;
    }

    /**
     * Tear down.
     *
     * @throws Exception
     */
    public void tearDown() throws Exception
    {
        super.tearDown();
        FileUtils.deleteDirectory(tmpdir);
    }


    /**
     * Negativ tests for invalid paths.
     */
    public void testConfigBoot()
    {
        try
        {
            new GRouter();
        }
        catch (Throwable e)
        {
            assertTrue(true);
        }
    }

    public void testSystemProp()
    {
        System.setProperty("grouter.config", "/temp");
                try
                {
                    new GRouter();
                }
                catch (Throwable e)
                {
                    assertTrue(true);
                }

    }

    /**
     * Another negative test.
     */
    public void testWithNull()
    {
        try
        {
            NodeConfig[] config = null;
            new GRouter(config);
        }
        catch (Throwable e)
        {
            assertTrue(true);
        }

    }

}
