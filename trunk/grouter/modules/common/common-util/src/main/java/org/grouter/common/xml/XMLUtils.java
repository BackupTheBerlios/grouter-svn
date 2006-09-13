package org.grouter.common.xml;

import org.w3c.dom.Document;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileOutputStream;

import com.sun.org.apache.xml.internal.serialize.OutputFormat;
import com.sun.org.apache.xml.internal.serialize.XMLSerializer;

/**
 * Class description.
 */
public class XMLUtils
{
    /** Logger. */  
    private static Logger logger = Logger.getLogger(XMLUtils.class);

    /**
     * A utility method for outputting xml xontent to a file.
     *  
     * @param file a file descriptor to output xml content to.
     * @param doc  a org.w3c.dom.Document containing the xml.
     * @throws Exception
     */
    public static void writeXMLFile(File file, Document doc) throws Exception
    {
        try
        {
            FileOutputStream fos = new FileOutputStream(file);
            OutputFormat format = new OutputFormat(doc);
            format.setIndent(3);
            XMLSerializer serial = new XMLSerializer(fos, format);
            serial.asDOMSerializer();
            serial.serialize(doc.getDocumentElement());
            fos.close();
        }
        catch (Exception e)
        {
            logger.error("Failed serializing document to file",e);
            throw e;
        }
    }
}
