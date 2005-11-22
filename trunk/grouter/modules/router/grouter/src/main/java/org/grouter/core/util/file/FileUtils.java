/**
 * FileUtils.java
 */
package org.grouter.core.util.file;

//import com.sun.xml.bind.StringInputStream;
import org.apache.log4j.Logger;

import java.io.*;

/**
 * Misc file operations.
 *
 * @author Georges
 */
public class FileUtils
{
    static  Logger logger = Logger.getLogger( FileUtils.class.getName());

    /**
     * Send in the file and get a string back.
     *
     * @param file
     * @param skipfirstblankline
     * @return
     */
    public static String getMessageContent(File file, boolean skipfirstblankline)
    {
        FileReader fileReader = null;
        try
        {
            fileReader = new FileReader(file);
            return getMessageContent(fileReader, skipfirstblankline);
        }
        catch (FileNotFoundException e)
        {
            logger.debug("Caught exception: ", e);
        }
        finally
        {
            if ( fileReader != null )
                try
                {
                    fileReader.close();
                }
                catch (IOException e)
                {
                    logger.debug("Caught exception: ", e);
                }
        }
        return null;

    }

    /**
     * Send in the filereader and get a string back. String return has not trailing
     * whitespaces.
     *
     * @param fileReader a file reader that will be decorated using a BufferedReader
     * @param skipfirstblankline
     * @return String message contents of file
     * TODO needs test
     */
    public static String getMessageContent(FileReader fileReader, boolean skipfirstblankline)
    {
        String returnval = null;
        try
        {
            BufferedReader inputBuffer = new BufferedReader(fileReader);
            StringBuffer buffer = new StringBuffer();
            String line = inputBuffer.readLine();
            if (skipfirstblankline)
            {
                line = inputBuffer.readLine();
            }
            while (line != null)
            {
                buffer.append(line);
                line = inputBuffer.readLine();
                if (line!=null)
                {
                    buffer.append("\n");
                }
            }
            returnval = buffer.toString();
            returnval = returnval.trim();
            fileReader.close();
        }
        catch (Exception pce)
        {
            logger.error(pce);
        }
        return returnval;
    }

    /**
     * Send in path to file and get the first line of the file back -
     * for IOR files there are only one line.
     *
     * @param iorFile
     * @return
     * @throws Exception
     */
    public static String getIORFileContent(String iorFile) throws Exception
    {
        String ior = null;
        BufferedReader inputBuffer = null;
        try
        {
            inputBuffer = new BufferedReader(new FileReader(iorFile));
            ior = inputBuffer.readLine();
            inputBuffer.close();
        }
        catch (IOException ex)
        {
            throw new IOException("Could not find file : " + iorFile);
        }
        catch (Exception ex)
        {
            throw new Exception("Could not create string representation of ior file : " + iorFile);
        }
        finally
        {
            if ( inputBuffer != null )
            {
                inputBuffer.close();
            }
        }
        return ior;
    }


    /**
     * Counts number of file in a folder - excluding folders.
     *
     * @param directory
     * @return Long number of files excluding folders.
     * TODO needs test
     */
    public static long countNumberOfFileInFolder(String directory)
    {
        long numberOfFiles = 0;
        File sourceDir = new File(directory);
        try
        {
            FilenameFilter filter = new FilenameFilter()
            {
                public boolean accept(File sourceDir, String fileName)
                {
                    return (new File(sourceDir, fileName)).isFile();
                }
            };
            numberOfFiles = sourceDir.list(filter).length;
        }
        finally
        {
        }
        return numberOfFiles;
    }

    /**
     * Scan folder and looks for newest file not folder and returns the lastmodfied
     * java.util.Date.
     *
     *
     * @param dir
     * @return java.util.Date date of last modification of file
     * TODO needs test
     */
    public static java.util.Date lastModifiedDateOfFile(String dir)
    {
        java.util.Date last = null;
        File sourceDir = new File(dir);
        try
        {
            FileFilter filter = new FileFilter()
            {
                public boolean accept(File sourceDir)
                {
                    return sourceDir.isFile();
                }
            };

            File[] files = sourceDir.listFiles(filter);
            long lastmodified = 0;
            for (int i = 0; i < files.length; i++)
            {
                if (files[i].lastModified() > lastmodified)
                {
                    lastmodified = files[i].lastModified();
                }
            }
            last = new java.util.Date(lastmodified);
        }
        finally
        {
        }

        return last;
    }

    /**
     * Read a file and return the contents as a string
     *
     * @param readFrom
     * @return
     * @throws java.io.IOException
     */
 /*   public static String readFile(String readFrom) throws IOException
    {
        File inFile = new File(readFrom);
        FileReader reader = null;
        StringBuffer buf = new StringBuffer();
        if (inFile.exists())
        {
            if (inFile.isFile())
            {
                try
                {
                    reader = new FileReader(inFile);
                    BufferedReader bufreader = new BufferedReader(reader);
                    String line = bufreader.readLine();

                    while (line != null)
                    {
                        buf.append(line + "\n");
                        line = bufreader.readLine();
                    }
                }
                finally
                {
                    if (reader != null)
                    {
                        try
                        {
                            reader.close();
                        }
                        catch (IOException e)
                        {
                            ;
                        }
                    }
                }

            }
            else
            {
                throw new FileCopyException("FileRead: location is not a file: " + readFrom);
            }
        }
        return buf.toString();

    }
   */

    /**
     * Create message contents in a file from message (String). If destination file
     * exists or the destination file is unwritable an exception is raised.
     *
     * @param message
     * @param writeToFilePath
     * @throws FileCopyException
     */
    /*public static File createFile(String message, String writeToFilePath) throws IOException, FileNotFoundException
    {
        File writeToFile = new File(writeToFilePath);
        StringInputStream source = new StringInputStream(message);
        FileOutputStream destination = null;
        byte[] buffer;
        int bytes_read;

        try
        {
            // Check if ok to write
            isDestinationWritable(writeToFile);

            destination = new FileOutputStream(writeToFile);
            buffer = new byte[1024];
            while (true)
            {
                bytes_read = source.read(buffer);
                if (bytes_read == -1)
                {
                    break;
                }
                destination.write(buffer, 0, bytes_read);
            }
        }
        finally
        {
            if (source != null)
            {
                try
                {
                    source.close();
                }
                catch (IOException e)
                {
                    //Ignore.
                }
            }
            if (destination != null)
            {
                try
                {
                    destination.close();
                }
                catch (IOException e)
                {
                    //Ignore.
                }
            }
        }
        return writeToFile;
    }
      */




    /**
     * Check if ok to write file to this location.
     *
     * @param destination_file
     * @throws FileCopyException
     * TODO needs test
     */
    private static void isDestinationWritable(File destination_file)
            throws FileCopyException
    {
        if (destination_file.exists())
        {
            if (destination_file.isFile())
            {
                if (!destination_file.canWrite())
                {
                    throw new FileCopyException("FileCreate: destination file is unwriteable: " + destination_file.getName());
                }
            }
            else
            {
                throw new FileCopyException("FileCreate: destination is not a file: " + destination_file.getName());
            }
        }
        else
        {
            File parentdir = parent(destination_file);
            if (!parentdir.exists())
            {
                throw new FileCopyException("FileCreate: destination directory doesn't exist: " + destination_file.getName());
            }
            if (!parentdir.canWrite())
            {
                throw new FileCopyException("FileCreate: destination directory is unwriteable: " + destination_file.getName());
            }
        }
    }

    private static File parent(File f)
    {
        String dirname = f.getParent();
        if (dirname == null)
        {
            if (f.isAbsolute())
            {
                return new File(File.separator);
            }
            else
            {
                return new File(System.getProperty("user.dir"));
            }
        }
        return new File(dirname);
    }

    /**
     * Overloaded copy method.
     * @param sourceFile
     * @param destFile
     * @throws IOException
     */
    public static void copy(File sourceFile, File destFile) throws IOException
    {
        copy(sourceFile.getAbsolutePath(), destFile.getAbsolutePath());
    }

    /**
     * Copy one file to another file
     *
     * @param source_name
     * @param dest_name
     * @throws java.io.IOException
     */
    public static void copy(String source_name, String dest_name) throws IOException, FileCopyException
    {
        File source_file = new File(source_name);
        File destination_file = new File(dest_name);
        FileInputStream source = null;
        FileOutputStream destination = null;
        byte[] buffer;
        int bytes_read;
        try
        {
            if (!source_file.exists() || !source_file.isFile())
            {
                throw new FileCopyException("FileCopy: no such source file: " + source_name);
            }
            if (!source_file.canRead())
            {
                throw new FileCopyException("FileCopy: source file " + "is unreadable: " + source_name);
            }

            if (destination_file.exists())
            {
                if (destination_file.isFile())
                {
                    if (!destination_file.canWrite())
                    {
                        throw new FileCopyException("FileCopy: destination " + "file is unwriteable: " + dest_name);
                    }
                }
                else
                {
                    throw new FileCopyException("FileCopy: destination " + "is not a file: " + dest_name);
                }
            }
            else
            {
                File parentdir = parent(destination_file);
                if (!parentdir.exists())
                {
                    throw new FileCopyException("FileCopy: destination " + "directory doesn't exist: " + dest_name);
                }
                if (!parentdir.canWrite())
                {
                    throw new FileCopyException("FileCopy: destination " + "directory is unwriteable: " + dest_name);
                }
            }

            source = new FileInputStream(source_file);
            destination = new FileOutputStream(destination_file);
            buffer = new byte[1024];
            while (true)
            {
                bytes_read = source.read(buffer);
                if (bytes_read == -1)
                {
                    break;
                }
                destination.write(buffer, 0, bytes_read);
            }
            destination.flush();
        }
        finally
        {
            if (source != null)
            {
                try
                {
                    source.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
            if (destination != null)
            {
                try
                {
                    destination.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace(); ;
                }
            }
        }
    }

    /**
     * Delete directory - deletes files prior to directory delete. Directory
     * delete will not work if you have files in the directory -> use this instead
     * to remove files prior to deleteing directory.
     *
     * @param deleteDirectory
     * @param recursive
     */
    public static void deleteDirContents(File deleteDirectory, boolean recursive)
    {
        if (deleteDirectory == null)
        {
            return;
        }

        if (recursive)
        {
            String[] allDirs = deleteDirectory.list();
            if (allDirs != null)
            {
                for (int i = 0; i < allDirs.length; i++)
                {
                    deleteDirContents(new File(deleteDirectory, allDirs[i]), true);
                }
            }
        }
        String[] allFiles = deleteDirectory.list();
        if (allFiles != null)
        {
            for (int i = 0; i < allFiles.length; i++)
            {
                (new File(deleteDirectory, allFiles[i])).delete();
            }

        }
    }

}