package org.siri.nodeviewer.swing.util;

import org.apache.log4j.Logger;

import javax.swing.*;

/**
 * @author Georges Polyzois
 */
public class IconFactory
{

    /**
     * Logger.
     */
    private static Logger logger = Logger.getLogger(IconFactory.class);
    public final static String ROOTPATH = "icons/";
    private static ClassLoader cl = IconFactory.class.getClassLoader();

    public static ImageIcon getImageIcon(String imageName)
    {
        ImageIcon im = null;
        try
        {
            if (cl != null)
            {
                im = new ImageIcon(cl.getResource(ROOTPATH + imageName));
            } else
            {
                logger.error("Could not create icon for " + imageName + " .Null classloader!!!");
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            logger.error("Could not create icon for " + imageName, ex);

        }
        return im;
    }

}
