package org.grouter.core.readers;

import org.apache.log4j.Logger;
import org.apache.commons.io.filefilter.NotFileFilter;
import org.apache.commons.io.filefilter.WildcardFilter;
import org.apache.commons.io.FileUtils;
import org.grouter.core.command.AbstractCommand;
import org.grouter.core.command.CommandMessage;
import org.grouter.domain.entities.Node;
import org.quartz.JobExecutionContext;
import org.quartz.JobDataMap;
import org.quartz.UnableToInterruptJobException;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.commons.net.ftp.FTPFile;

import java.util.concurrent.BlockingQueue;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Map;
import java.io.File;
import java.io.IOException;
import java.io.FileOutputStream;

/**
 * This Reader connects to ftp server and downloads files locally to the inbound Endpoint of the Node.
 * The Reader is a scheduled Quartz job and configured to run at a certain intervall. Upon execution
 * (by Quartz) the Reader initializes, validates context parameters and sets up a connection to the
 * inbound Endpoint.
 * <p/>
 * The Reader acts as a command producer and puts commands on internal queue to be consumed by a Writer
 * later on (Writers are also scheduled). This decouples produceers and consumers and is a classical
 * CommandPattern [GoF].
 *
 * @author Georges Polyzois
 */
public class FtpReaderJob extends AbstractReader
{
    private static Logger logger = Logger.getLogger(FtpReaderJob.class);
    //private NodeConfig node;
    private BlockingQueue<AbstractCommand> queue;
    private NotFileFilter notFileFilter;
    private WildcardFilter wildcardFilter;
    public final static String FTP_AUTH_USER = "ftpAuthUser";
    public final static String FTP_AUTH_PASSWORD = "ftpAuthPassword";
    public final static String FTP_PORT = "ftpPort";
    public final static String FILE_LIST = "fileList";
    private static final int FTP_DEFAULT_PORT = 21;


    /**
     * Empty - needed by Quartz framework.
     */
    public FtpReaderJob()
    {
    }

    private void init(final Node node, BlockingQueue<AbstractCommand> blockingQueue)
    {
        if (node == null || blockingQueue == null)
        {
            throw new IllegalArgumentException("Constructor called with null argument.");
        }
        this.node = node;
        this.queue = blockingQueue;
        //which type of commands should this servicenode worker handle
        command = getCommand(node);
        //       createFilter(node);
        validate(node);

    }


    /**
     * @return a list of CommnadHolder instances or a null if validation fails
     */
    @Override
    protected List<CommandMessage> readFromSource()
    {
        logger.info("Reading files from :" + node.getInBound().getUri());

        // a list of full paths on ftp server we will download from
        Map endPointContext = (Map) node.getInBound().getEndPointContext();

        List<String> remoteFtpUriToFile = getPathIncludingFile((String) endPointContext.get(FILE_LIST));
        List<CommandMessage> commandMessages = new ArrayList();
        FTPClient client = null;
        try
        {
            client = initConnection();
            for (String fullPathToFile : remoteFtpUriToFile)
            {
                // should only return one file - since we are using a complete file uri and not a uri to a folder
                FTPFile[] ftpFilesAtPath = client.listFiles(fullPathToFile);
                if (ftpFilesAtPath.length > 0)
                {
                    //String localFileName = fullPathToFile;
                    File internalInFile = new File(node.getRouter().getHomePath() + File.separator + "nodes" + File.separator + node.getId() + File.separator + "internal" + File.separator + "in" + File.separator + fullPathToFile.replace("/", "_"));
                    FileOutputStream fos = new FileOutputStream(internalInFile);
                    logger.info("Downloading file from ftp server:" + fullPathToFile);
                    // we have a valid fullPathToFile and there is a file at that fullPathToFile
                    boolean status = client.retrieveFile(fullPathToFile, fos);
                    if (status)
                    {
                        logger.info("Downloading complete :" + internalInFile);
                        internalInFile.setLastModified(ftpFilesAtPath[0].getTimestamp().getTimeInMillis());

                        // Get part of the message to store for querying purposes
                        String message = getMessage(internalInFile);
                        CommandMessage cmdMessage = new CommandMessage(message, internalInFile);
                        commandMessages.add(cmdMessage);
                    } else
                    {
                        logger.error("Failed to download remote file :" + fullPathToFile + " Status code received :" + status);
                    }
                    fos.close();
                }
            }
            // all files downloaded to in folder - time to create and move them internally
//            commandMessages = FileReaderHelper.getCommands(node);
        }
        catch (Exception e)
        {
            logger.warn("Connection problem with FTP server.", e);
        }
        finally
        {
            if (client != null)
            {
                try
                {
                    client.logout();
                    client.disconnect();
                }
                catch (IOException e)
                {
                    //ignore
                }
            }
        }
        return commandMessages;
    }

    @Override
    protected void validate(Node node)
    {
        Map endPointContext = node.getInBound().getEndPointContext();
        if (endPointContext == null)
        {
            throw new RuntimeException("Context needs to be set for this type of EndPoint.");
        }

        if (node.getInBound().getUri() == null || node.getInBound().getUri().equals(""))
        {
            throw new RuntimeException("Can not use an empty ftp host to fetch data from.");
        }


        if (endPointContext.get(FILE_LIST) == null || endPointContext.get(FILE_LIST).equals(""))
        {
            throw new RuntimeException("Can not use an empty file list to fetch data from.");
        }

        //endPointContext = (EndPointContext) node.getInBound().getEndPointContext().get(FTP_PORT);
        //Validate.notNull( endPointContext.getValue(), "Can not use an empty file list to fetch data from." );

    }

    private FTPClient initConnection()
            throws IOException
    {
        FTPClient client = null;
        String host = null;
        String strPort;
        try
        {

            Map map = node.getInBound().getEndPointContext();
            host = node.getInBound().getUri();
            strPort = (String) map.get(FTP_PORT);
            client = new FTPClient();
            int port = FTP_DEFAULT_PORT;
            if (StringUtils.isNotEmpty(strPort))
            {
                port = Integer.parseInt(strPort);
                client.connect(host, port);
                int reply = client.getReplyCode();
                logger.debug("reply :" + reply);
                if (FTPReply.isPositiveCompletion(reply))
                {
                    logger.info("Connected to ftp server :" + client.getRemoteAddress().getHostAddress());
                }
            } else
            {
                client.connect(host, port);
                int reply = client.getReplyCode();
                logger.debug("reply :" + reply);
                if (FTPReply.isPositiveCompletion(reply))
                {
                    logger.info("Connected to ftp server :" + client.getRemoteAddress().getHostAddress());
                }

            }

            String user = (String) map.get(FTP_AUTH_USER);
            String pwd = (String) map.get(FTP_AUTH_PASSWORD);
            if (StringUtils.isNotEmpty(user) && StringUtils.isNotEmpty(pwd))
            {
                client.login(user, pwd);
                int replyCode = client.getReplyCode();
                if (FTPReply.isPositiveCompletion(replyCode))
                {
                    logger.info("Logged into ftp server :" + host);
                }
            } else
            {
                client.login("anonymous", "mymail@mail.com");
                int replyCode = client.getReplyCode();
                if (FTPReply.isPositiveCompletion(replyCode))
                {
                    logger.info("Logged into ftp server :" + host);
                }
            }
        } catch (NumberFormatException e)
        {
            logger.error("Could not establish connection with ftp endpoint using host:" + host);
        } catch (Exception e)
        {
            logger.error(e, e);
        }
        return client;
    }

    /**
     * Hand it over to the in memory queue.
     */
    void pushToQueue()
    {
        logger.debug("Putting cmd on queue " + command.toStringUsingReflection());
        queue.offer(command);
    }


    /**
     * Parses comma separated string of paths.
     *
     * @param pathIncludingFiles a list with comma separated strings, e.g /tmp/kalle.xml,nisse.xml
     * @return paths
     */
    private List<String> getPathIncludingFile(String pathIncludingFiles)
    {
        List paths = new ArrayList();
        Scanner scanner = new Scanner(pathIncludingFiles);
        try
        {
            scanner.useDelimiter(",");
            while (scanner.hasNext())
            {
                String pathInclFile = scanner.next();
                logger.debug(pathInclFile);
                paths.add(pathInclFile);
            }
        }
        catch (Exception e)
        {
            logger.warn(e, e);
        }
        finally
        {
            scanner.close();
        }
        return paths;
    }

    public void setJobExecutionContext(JobExecutionContext jobExecutionContext)
    {
        JobDataMap jobDataMap = jobExecutionContext.getMergedJobDataMap();
        node = (Node) jobDataMap.get("node");
        BlockingQueue<AbstractCommand> blockingQueue = (BlockingQueue<AbstractCommand>) jobDataMap.get("queue");
        init(node, blockingQueue);
    }

    public void execute(JobExecutionContext jobExecutionContext)
    {
        setJobExecutionContext(jobExecutionContext);
        execute();
    }


    public void setQueue(BlockingQueue<AbstractCommand> queue)
    {
        this.queue = queue;
    }

    public void interrupt() throws UnableToInterruptJobException
    {
        logger.info(node.getId() + " got request to stop");
    }

    private static final int MESSAGE_LENGTH = 100;

    private static String getMessage(File currentFile)
            throws IOException
    {
        String message = FileUtils.readFileToString(new File(currentFile.getPath()), "UTF-8");
        return message.substring(0,MESSAGE_LENGTH);

    }
}
