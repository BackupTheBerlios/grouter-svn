package org.grouter.core.readers;

import org.apache.log4j.Logger;
import org.apache.commons.io.filefilter.NotFileFilter;
import org.apache.commons.io.filefilter.WildcardFilter;
import org.apache.commons.io.FileUtils;
import org.grouter.core.command.AbstractCommandWriter;
import org.grouter.core.command.CommandHolder;
import org.grouter.domain.entities.Node;
import org.quartz.JobExecutionContext;
import org.quartz.JobDataMap;
import org.quartz.SchedulerException;


import org.apache.commons.lang.time.StopWatch;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.commons.net.ftp.FTPFile;

import java.util.concurrent.BlockingQueue;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.FileFilter;
import java.io.File;
import java.io.IOException;
import java.io.FileOutputStream;

/**
 * Creates commands an puts them on queue for further handling by consumers.
 *
 * @author Georges Polyzois
 */
public class FtpReaderJob extends AbstractReader
{
    private static Logger logger = Logger.getLogger(FtpReaderJob.class);
    //private NodeConfig node;
    private BlockingQueue<AbstractCommandWriter> queue;
    private NotFileFilter notFileFilter;
    private WildcardFilter wildcardFilter;
    public final static String FTP_AUTH_USER = "ftpAuthUser";
    public final static String FTP_AUTH_PASSWORD = "ftpAuthPassword";
    public final static String FTP_HOST = "ftpHost";
    public final static String FTP_PORT = "ftpPort";

    public final static String FILE_LIST = "fileList";
    private int numberOfDownloadedFiles = 0;
    private static final int FTP_DEFAULT_PORT = 21;


    /**
     * Empty - needed by Quartz framework.
     */
    public FtpReaderJob()
    {
    }

    private void init(final Node node, BlockingQueue<AbstractCommandWriter> blockingQueue)
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
     * Forced by abstract method.
     *
     * @return List<CommandHolder>
     */
    protected List<CommandHolder> readFromSource()
    {
        logger.info("Reading files from " + node.getInBound().getUri());

        long totalTime = 0;
        List<String> pathWithFile = getPathIncludingFile(node.getInBound().getEndPointContext().get(FILE_LIST));
        String host = null;
        String strPort = null;
        FTPClient client = null;
        try
        {
            File importDir = new File(node.getInBound().getEndPointContext().get("IMPORT_DIR"));
            if (!importDir.canWrite())
            {
                throw new SchedulerException("Import directory " + importDir.getName() + " was not writeable");
            }
            host = node.getInBound().getEndPointContext().get(FTP_HOST);
            strPort = node.getInBound().getEndPointContext().get(FTP_PORT);
            client = new FTPClient();
            int port = FTP_DEFAULT_PORT;

            if (StringUtils.isNotEmpty(strPort))
            {
                port = Integer.parseInt(node.getInBound().getEndPointContext().get(FTP_PORT));
                client.connect(host, port);
                int reply = client.getReplyCode();
                System.out.println("[  " + reply);
                if (FTPReply.isPositiveCompletion(reply))
                {
                    logger.info("Connected to ftp server :" + client.getRemoteAddress().getHostAddress());
                }
            }

            String user = node.getInBound().getEndPointContext().get(FTP_AUTH_USER);
            String pwd = node.getInBound().getEndPointContext().get(FTP_AUTH_PASSWORD);
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

            for (String path : pathWithFile)
            {
                FTPFile[] ftpFilesAtPath = client.listFiles(path);
                if (ftpFilesAtPath.length > 0)
                {
                    String localFileName = path;
                    File localFile = new File(importDir + File.separator + StringUtils.replace(localFileName, "/", "_"));

                    FileOutputStream fos = new FileOutputStream(localFile);
                    logger.info("Downloading remote file :" + path);
                    // we have a valid path and there is a file at that path
                    StopWatch stopWatch = new StopWatch();
                    stopWatch.start();
                    boolean status = client.retrieveFile(path, fos);
                    if (status)
                    {
                        logger.info("Downloading complete :" + localFile);
                        localFile.setLastModified(ftpFilesAtPath[0].getTimestamp().getTimeInMillis());
                        // increase internal counter
                        numberOfDownloadedFiles++;
                    } else
                    {
                        logger.error("Failed to download remote file : " + path);
                    }
                    fos.close();
                    stopWatch.stop();
                    logger.info("Time to download file was : " + stopWatch.getTime());
                    totalTime = totalTime + stopWatch.getTime();
                    logger.info("Total time sofar totalTime : " + totalTime);

                }
            }

        }
        catch (Exception e)
        {
            logger.warn("Connection problem with FTP server : " + "{host,port}={" + host + "," + strPort + "}", e);
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

        /*
if (curFiles == null || curFiles.length == 0)
{
logger.debug("No files found - empty : " + node.getInBound().getUri());
return null;
}

logger.debug("Found number of files and folders : " + curFiles.length);

List<CommandHolder> commandMessages = new ArrayList<CommandHolder>(curFiles.length);
for (File curFile : curFiles)
{
if (curFile.isDirectory())
{
logger.debug("A folder... removing it.");
try
{
FileUtils.forceDelete(curFile);
} catch (IOException e)
{
logger.info("Could not delete foder", e);
}
} else
{
try
{
String message = FileUtils.readFileToString(new File(curFile.getPath()), "UTF-8");
CommandHolder cmdHolder = new CommandHolder(message);
commandMessages.add(cmdHolder);

// remove file from infolder
curFile.delete();
}
catch (Exception ex)
{
logger.info(ex, ex);
}
}
}                     */
        return null;//commandMessages;
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


    /**
     * We are a thread - are we not...
     */
    public void run()
    {
        try
        {
            execute();
        } catch (Exception e)
        {
            logger.error(e, e);
        }
    }

    public void setJobExecutionContext(JobExecutionContext jobExecutionContext)
    {
        JobDataMap jobDataMap = jobExecutionContext.getMergedJobDataMap();
        node = (Node) jobDataMap.get("node");
        BlockingQueue<AbstractCommandWriter> blockingQueue = (BlockingQueue<AbstractCommandWriter>) jobDataMap.get("queue");
        init(node, blockingQueue);
    }

    public void execute(JobExecutionContext jobExecutionContext)
    {
        setJobExecutionContext(jobExecutionContext);
        execute();
    }


    public void setQueue(BlockingQueue<AbstractCommandWriter> queue)
    {
        this.queue = queue;
    }

}
