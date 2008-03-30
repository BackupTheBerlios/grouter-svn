/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.grouter.core.util.file;


import org.apache.log4j.Logger;

import java.io.*;

/**
 * Misc file operations.
 *
 * @author Georges Polyzois
 */
public class FileUtils
{
    static Logger logger = Logger.getLogger(FileUtils.class.getName());

    /**
     * Send in the file and get a string back.
     *
     * @param file
     * @param skipfirstblankline
     * @return a string with content
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
            if (fileReader != null)
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
     * @param fileReader         a file reader that will be decorated using a BufferedReader
     * @param skipfirstblankline weteher to skip first ine in file
     * @return String message contents of file
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
                if (line != null)
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
     * Counts number of file in a folder - excluding folders.
     *
     * @param directory the directory to count number of files in
     * @return Long number of files excluding folders.
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
     * @param dir
     * @return java.util.Date date of last modification of file
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
     * Verify if path to a given file is a valid.
     *
     * @param path the path 
     * @return true if valid and false if invalid path
     */
    public static boolean isValidFile(String path)
    {
        if (path == null)
        {
            return false;
        }

        File file = new File(path);
        return file.isFile();
    }

    /**
     * Verify if path to a given file is a valid.
     *
     * @param path the path to the dir
     * @return true if valid and false if invalid path
     */
    public static boolean isValidDir(String path)
    {
        if (path == null)
        {
            return false;
        }

        File file = new File(path);
        return file.isDirectory();
    }

    /**
     * Check if ok to write file to this location.
     *
     * @param destinationFile 
     * @throws FileCopyException if failure to write file to this destination
     */
    private static void isDestinationWritable(File destinationFile)
            throws FileCopyException
    {
        if (destinationFile.exists())
        {
            if (destinationFile.isFile())
            {
                if (!destinationFile.canWrite())
                {
                    throw new FileCopyException("FileCreate: destination file is unwriteable: " + destinationFile.getName());
                }
            } else
            {
                throw new FileCopyException("FileCreate: destination is not a file: " + destinationFile.getName());
            }
        } else
        {
            File parentdir = parent(destinationFile);
            if (!parentdir.exists())
            {
                throw new FileCopyException("FileCreate: destination directory doesn't exist: " + destinationFile.getName());
            }
            if (!parentdir.canWrite())
            {
                throw new FileCopyException("FileCreate: destination directory is unwriteable: " + destinationFile.getName());
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
            } else
            {
                return new File(System.getProperty("user.dir"));
            }
        }
        return new File(dirname);
    }

    /**
     * Overloaded copy method.
     *
     * @param sourceFile copy form this file
     * @param destFile copy to this file
     * @throws IOException
     */
    public static void copy(File sourceFile, File destFile) throws IOException
    {
        copy(sourceFile.getAbsolutePath(), destFile.getAbsolutePath());
    }

    /**
     * Copy one file to another file
     *
     * @param source_name the source file path
     * @param dest_name the destination file path
     * @throws java.io.IOException
     * @throws FileCopyException if failure to copu file
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
                } else
                {
                    throw new FileCopyException("FileCopy: destination " + "is not a file: " + dest_name);
                }
            } else
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
                    //ignore
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
                    //ignorw
                }
            }
        }
    }

    /**
     * Delete directory - deletes files prior to directory delete. Directory
     * delete will not work if you have files in the directory -> use this instead
     * to remove files prior to deleteing directory.
     *
     * @param deleteDirectory directory to delete
     * @param recursive wether to delete recursively into sub folder
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