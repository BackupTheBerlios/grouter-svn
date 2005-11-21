package org.grouter.core.config;

//import com.thoughtworks.xstream.XStream;
//import com.thoughtworks.xstream.io.xml.DomDriver;


import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

import javax.xml.parsers.FactoryConfigurationError;

/**
 * Class description.
 */
public class ConfigHandler
{
    /**
     * Logger.
     */
    private static Logger logger = Logger.getLogger(ConfigHandler.class);
     static
    {
        try
        {
            DOMConfigurator.configure("log4j.xml");
        } catch (FactoryConfigurationError factoryConfigurationError)
        {
            factoryConfigurationError.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

  //  XStream xstream = new XStream(new DomDriver());

    private String readXMLConfig(String xml)
    {
    //    xstream.fromXML(xml);

        logger.debug(xml);
        return "";
    }
        /*
    private String writeXMLConfig(SystemConfig systemConfig)
    {
  //      String xml = xstream.toXML(systemConfig);
      //  logger.debug(xml);

        return xml;
    }       */

    public static void main(String[] args)
    {
        ConfigHandler configReader = new ConfigHandler();
        //configReader.readXMLConfig("C:\\gepo\\intellij_siri\\modules\\feeder\\src\\config\\feeder.xml");
        SystemConfig systemConfig = new SystemConfig();
        GlobalSettings globalSettings = new GlobalSettings();
        ArchiveHandler archiveHandler = new ArchiveHandler();
        CronJob cronJob = new CronJob();
        cronJob.setIntervall("# ¤ 45 2345 2435 23");
        archiveHandler.setCronJob(cronJob);


        globalSettings.setArchiveHandler(archiveHandler);
        systemConfig.setGlobalSettings(globalSettings);
 //       configReader.writeXMLConfig(systemConfig);
    }
}
