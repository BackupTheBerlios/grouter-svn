package org.grouter.core.readers;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.net.ServerSocket;

import org.apache.log4j.Logger;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.io.FileUtils;
import org.apache.ftpserver.FtpServer;
import org.apache.ftpserver.ConfigurableFtpServerContext;
import org.apache.ftpserver.config.PropertiesConfiguration;
import org.apache.ftpserver.listener.mina.MinaListener;
import org.apache.ftpserver.interfaces.FtpServerContext;
import org.grouter.core.AbstractRouterTests;
import org.grouter.domain.entities.Node;
import org.grouter.domain.entities.EndPoint;
import org.grouter.domain.entities.EndPointType;
import org.grouter.domain.entities.EndPointContext;
import org.springframework.core.io.ClassPathResource;

/**
 * Extend this class for tests against an inmemoty ftp server instance.
 *
 * @author Georges Polyzois
 */
public abstract class AbstractFtpReaderTests extends AbstractRouterTests
{
    private static Logger logger = Logger.getLogger(AbstractFtpReaderTests.class);

    protected static final String ADMIN_PASSWORD = "admin";
    protected static final String ADMIN_USERNAME = "admin";
    protected static final String ANONYMOUS_PASSWORD = "foo@bar.com";
    protected static final String ANONYMOUS_USERNAME = "anonymous";
    protected static final String TESTUSER_PASSWORD = "password";
    protected FtpServer server;
    protected Node ftpToFile;
    protected int port = -1;
    private FtpServerContext serverContext;
    protected FTPClient client;
    private static final ClassPathResource classPathResource;
    private static File USERS_FILE = null;
    private static final File FTP_TEST_TMP_DIR;
    protected static final File FTP_ROOT_DIR;
    private static final int DEFAULT_PORT = 12345;

    static
    {
        classPathResource = new ClassPathResource("user.gen");
        try
        {
            USERS_FILE = classPathResource.getFile();
        } catch (IOException e)
        {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        FTP_TEST_TMP_DIR = new File("test-tmp");
        FTP_ROOT_DIR = new File(FTP_TEST_TMP_DIR, "ftproot");

    }


    /**
     * Do a setup for every test so we have a clean router for every run. onSetup is final and
     * Spring provides us with this method to hook us into.
     *
     * @throws Exception
     */
    @Override
    public void onSetUpBeforeTransaction() throws Exception
    {
        createRouter();
        createDirs();
        initServer();
    }


    @Override
    public void createRouter()
    {
        createNode();
        Set<Node> nodes = new HashSet<Node>();
        nodes.add(ftpToFile);
        router.setNodes(nodes);
    }

    /**
     * Create the entity and and set realtionships.
     */
    public void createNode()
    {
        ftpToFile = new Node("node_id_ftpToFileNode", "ftpToFileNode");
        ftpToFile.setBackupUri(BASE_FOLDER_FOR_TEST + File.separator + ftpToFile.getName() + "/backup");
        ftpToFile.setInternalQueueUri(BASE_FOLDER_FOR_TEST + File.separator + ftpToFile.getName() + "/internalq");

        EndPoint inbound = new EndPoint();
        inbound.setUri(BASE_FOLDER_FOR_TEST + File.separator + ftpToFile.getName() + "/in");
        inbound.setEndPointType(EndPointType.FILE_READER);
        inbound.setScheduleCron("0/5 * * * * ?");
        inbound.setId("1");
        EndPointContext endPointContext3 = new EndPointContext(FtpReaderJob.FTP_PORT, "12345", inbound);
        Map map = new HashMap();
        map.put(endPointContext3.getKeyname(), endPointContext3);
        inbound.setEndPointContext(map);


        EndPoint outbound = new EndPoint();
        outbound.setEndPointType(EndPointType.FILE_WRITER);
        outbound.setUri(BASE_FOLDER_FOR_TEST + File.separator + ftpToFile.getName() + "/out");
        outbound.setEndPointType(EndPointType.FILE_WRITER);
        outbound.setScheduleCron("0/5 * * * * ?");
        outbound.setId("2");

        ftpToFile.setInBound(inbound);
        ftpToFile.setOutBound(outbound);
    }

    /**
     * Use this to populate ftp server tests with files.
     * @param listWithFileNames comma separated list with files.
     */
    protected void setFileList( String listWithFileNames )
    {
        EndPointContext endPointContext1 = new EndPointContext(FtpReaderJob.FILE_LIST, listWithFileNames, ftpToFile.getInBound());
        ftpToFile.getInBound().getEndPointContext().put( FtpReaderJob.FILE_LIST, endPointContext1 );
    }


    /**
     * Setup up dirs for the router.
     *
     * @throws Exception
     */
    private void createDirs() throws Exception
    {
        logger.debug("## Nodes");
        for (Node node : router.getNodes())
        {
            FileUtils.forceMkdir(new File(node.getInBound().getUri()));
            FileUtils.forceMkdir(new File(node.getInBound().getUri() + "/emptyfolder"));
            logger.debug("Created dir : " + node.getInBound().getUri());

            FileUtils.forceMkdir(new File(node.getOutBound().getUri()));
            logger.debug("Created dir : " + node.getOutBound().getUri());

            FileUtils.forceMkdir(new File(node.getBackupUri()));
            logger.debug("Created dir : " + node.getBackupUri());

            FileUtils.forceMkdir(new File(node.getInternalQueueUri()));
            logger.debug("Created dir : " + node.getInternalQueueUri());
        }
    }


    /**
     * Configuration for the ftp server.
     *
     * @return the configuration
     */
    protected Properties createConfig()
    {
        assertTrue(USERS_FILE.getAbsolutePath() + " must exist", USERS_FILE.exists());
        Properties configProps = new Properties();
        configProps.setProperty("config.listeners.default.class", MinaListener.class.getName());
        configProps.setProperty("config.listeners.default.port", Integer.toString(port));
        configProps.setProperty("config.user-manager.class", "org.apache.ftpserver.usermanager.PropertiesUserManager");
        configProps.setProperty("config.user-manager.admin", "admin");
        configProps.setProperty("config.user-manager.prop-password-encrypt", "false");
        configProps.setProperty("config.user-manager.prop-file", USERS_FILE.getAbsolutePath());
        configProps.setProperty("config.create-default-user", "false");
        return configProps;
    }


    /**
     * Creates a running inmemory ftp server.
     * @throws Exception
     */
    protected void initServer() throws Exception
    {
        findFreePort();
        serverContext = new ConfigurableFtpServerContext(new PropertiesConfiguration(createConfig()));
        server = new FtpServer(serverContext);
        server.start();
    }


    @Override
    public void onTearDownInTransaction() throws Exception
    {
        // stop ftp server
        if (server != null)
        {
            server.stop();
        }
        cleanTmpDirs();
    }



    /**
     * Clean ftp temp dir.
     * @throws IOException
     */
    private void cleanTmpDirs() throws IOException
    {
        if (FTP_TEST_TMP_DIR.exists())
        {
            FileUtils.forceDelete(FTP_TEST_TMP_DIR);
        }
    }


    /**
     * Try with the default port if it does not work try other ports up to port 1024.
     * @return a port
     * @throws IOException
     */
    private void findFreePort() throws IOException
    {
        ServerSocket tmpSocket = null;
        try
        {
            // first try the default port
            tmpSocket = new ServerSocket(DEFAULT_PORT);
            this.port = DEFAULT_PORT;
        } catch (IOException e)
        {
            System.out.println("Failed to use default port");
            try
            {
                // didn't work, try to find one dynamically
                int attempts = 0;
                while (port < 1024 && attempts < 1000)
                {
                    attempts++;
                    tmpSocket = new ServerSocket(0);
                    this.port = tmpSocket.getLocalPort();
                }

            } catch (IOException e1)
            {
                throw new IOException("Failed to find a port to use for testing: "
                        + e1.getMessage());
            }
        } finally
        {
            if (tmpSocket != null)
            {
                try
                {
                    tmpSocket.close();
                } catch (IOException e)
                {
                    // ignore
                }
                tmpSocket = null;
            }
        }
    }
}