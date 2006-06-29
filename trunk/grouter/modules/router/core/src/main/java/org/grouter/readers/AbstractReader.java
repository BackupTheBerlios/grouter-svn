package org.grouter.readers;

import org.grouter.transfer.MessageVO;
import org.grouter.transformers.FileTransformBehavior;

import java.io.File;

/**
 * Readers should extend this class.
 *
 * Readers should read a message from a source and send the message
 * to the domain using either synchorous or asynchronous message - depending
 * on need.
 */
abstract class AbstractReader
{
    FileTransformBehavior transformBehavior;

    final protected MessageVO read(File file)
    {
        MessageVO vo = readFile(file);
        if(vo!=null)
        {
            transform();
            backupFile(file);
        }
        else
        {
            System.out.println("Could not read file : " + file);
        }
        return vo;
    }

    private void transform()
    {
        //To change body of created methods use File | Settings | File Templates.
    }

    abstract MessageVO readFile(File file);

    protected void backupFile(File file)
    {

    }
}
