package org.grouter.core.readers;

import org.apache.log4j.Logger;
import org.apache.commons.io.filefilter.NotFileFilter;
import org.apache.commons.io.filefilter.WildcardFilter;
import org.grouter.core.command.AbstractCommand;
import org.grouter.core.command.CommandMessage;
import org.grouter.domain.entities.Node;
import org.grouter.domain.entities.EndPointContext;
import org.quartz.JobExecutionContext;
import org.quartz.JobDataMap;
import org.quartz.UnableToInterruptJobException;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.commons.net.ftp.FTPFile;

import java.util.concurrent.BlockingQueue;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.IOException;
import java.io.FileOutputStream;

/**
 * Connects to ftp server and dwonloads files locally to the inbound Endpoint of the Node. Then we create commandmessages
 * and put them on queue for further processing.
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
    public final static String FTP_HOST = "ftpHost";
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

    }


    /**
     *
     * @return a list of CommnadHolder instances or a null if validation fails
     */
    @Override
    protected List<CommandMessage> readFromSource()
    {
        logger.info("Reading files from :" + node.getInBound().getUri());

        // a list of full paths on ftp server we will download from
        EndPointContext endPointContext= (EndPointContext) node.getInBound().getEndPointContext().get(FILE_LIST);
        List<String> remoteFtpUriToFile = getPathIncludingFile(endPointContext.getValue());
        List<CommandMessage> commandMessages = null;
        FTPClient client = null;
        try
        {
            client = initConnection();
            for (String path : remoteFtpUriToFile)
            {
                // should only return one file - since we are using a complete file uri and not a uri to a folder
                FTPFile[] ftpFilesAtPath = client.listFiles(path);
                if (ftpFilesAtPath.length > 0)
                {
                    //String localFileName = path;
                    File localFile = new File(node.getInBound().getUri() + File.separator + StringUtils.replace(path, "/", "_"));
                    FileOutputStream fos = new FileOutputStream(localFile);
                    logger.info("Downloading file from ftp server:" + path);
                    // we have a valid path and there is a file at that path
                    boolean status = client.retrieveFile(path, fos);
                    if (status)
                    {
                        logger.info("Downloading complete :" + localFile);
                        localFile.setLastModified(ftpFilesAtPath[0].getTimestamp().getTimeInMillis());
                    } else
                    {
                        logger.error("Failed to download remote file :" + path);
                    }
                    fos.close();
                }
            }
            // all files downloaded to in folder - time to create and move them internally
            commandMessages = FileReaderHelper.getCommands( node );
        }
        catch (Exception e)
        {
            logger.warn("Connection problem with FTP server." , e);
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
        EndPointContext endPointContext = (EndPointContext) node.getInBound().getEndPointContext().get(FILE_LIST);
        Validate.notNull( endPointContext.getValue(), "Can not use an empty file list to fetch data from." );

        endPointContext = (EndPointContext) node.getInBound().getEndPointContext().get(FTP_HOST);
        Validate.notNull( endPointContext.getValue(), "Can not use an empty file list to fetch data from." );

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
            host = ((EndPointContext) node.getInBound().getEndPointContext().get(FTP_HOST)).getValue(); //node.getInBound().getEndPointContext().get(FTP_HOST);
            strPort =  ((EndPointContext) node.getInBound().getEndPointContext().get(FTP_PORT)).getValue();// node.getInBound().getEndPointContext().get(FTP_PORT);
            client = new FTPClient();
            int port = FTP_DEFAULT_PORT;
            if (StringUtils.isNotEmpty(strPort))
            {
                port = Integer.parseInt( strPort );
                client.connect(host, port);
                int reply = client.getReplyCode();
                logger.debug("reply :" + reply);
                if (FTPReply.isPositiveCompletion(reply))
                {
                    logger.info("Connected to ftp server :" + client.getRemoteAddress().getHostAddress());
                }
            }

            String user = (String) node.getInBound().getEndPointContext().get(FTP_AUTH_USER);
            String pwd = (String) node.getInBound().getEndPointContext().get(FTP_AUTH_PASSWORD);
            if (StringUtils.isNotEmpty(user) && StringUtils.isNotEmpty(pwd))
            {
                client.login(user, pwd);
                int reply = client.getReplyCode();
                if (FTPReply.isPositiveCompletion(reply))
                {
                    logger.info("Logged into ftp server :" + host);
                }
            } else
            {
                client.login("anonymous", "mymail@mail.com");
                int reply = client.getReplyCode();
                if (FTPReply.isPositiveCompletion(reply))
                {
                    logger.info("Logged into ftp server :" + host);
                }
            }
        } catch (NumberFormatException e)
        {
            logger.error("Could not establish connection with ftp endpoint using host:" + host);
        } catch (IOException e)
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
}
