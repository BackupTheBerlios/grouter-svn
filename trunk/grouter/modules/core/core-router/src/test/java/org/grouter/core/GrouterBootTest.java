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
public class GrouterBootTest extends TestCase
{
    private static Logger logger = Logger.getLogger(GrouterBootTest.class);
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
        tmpdir.mkdirs();
        File infolder = new File(tmpDir + "/in");
        infolder.mkdirs();
        FileUtils.writeStringToFile(new File(infolder.getPath() + "/testdata.txt"), "test data", "UTF-8");
        File outfolder = new File(tmpDir + "/out");
        outfolder.mkdirs();
        logger.info(outfolder.exists() + " Created temporary folders : " + infolder + " and : " + outfolder);

        InFolderConfig inFolderConfig = new InFolderConfig(infolder, 2000, false, null, null);
        OutFolderConfig outFolderConfig = new OutFolderConfig(outfolder);
        NodeConfig nodeConfig = new NodeConfig(NodeConfig.Type.FILE_TO_FILE, "id_1", true, inFolderConfig, outFolderConfig, null);
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
     * No system property nor a path given - should fail this test.
     */
    public void testConfigBoot()
    {
        logger.debug("Starting test...");
        try
        {
            new GRouter();
        }
        catch (Throwable e)
        {
            logger.info("Got expected exception : " + e.getMessage());
            assertTrue(true);
        }
    }

    /**
     * Test with an invalid file path to config file.
     */
    public void testSystemProp()
    {
        System.setProperty("grouter.config", "/hopefullynonexistingfolder");
        try
        {
            new GRouter();
        }
        catch (Throwable e)
        {
            logger.info("Got expected exception : " + e.getMessage());
            assertTrue(true);
            System.setProperty("grouter.config", "");
        }

    }


    /**
     * Using the Grouter construct with a null nodes should fail.
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

            logger.info("Got expected exception : " + e.getMessage());
            assertTrue(true);
        }
    }


    public void testWithSomeValidData()
    {
        try
        {
            new GRouter(nodeConfigs);
        }
        catch (Throwable e)
        {

            logger.info("Got expected exception : " + e.getMessage());
            assertTrue(true);
        }
    }

    public void testStartFrouterFileToFile()
    {
        String grouterHome = System.getProperty("user.dir");
        logger.info("Working dir : " + grouterHome);
        String configFile = "/router/core/src/config/grouter-file-file.xml";


        GRouter grouter = new GRouter(grouterHome + configFile);


        try
        {
            Thread.sleep(10000);
        } catch (InterruptedException e)
        {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

    }


}
