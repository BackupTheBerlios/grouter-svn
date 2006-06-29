package org.msgrouter.feeder;

import org.apache.log4j.Logger;  //
import org.apache.log4j.xml.DOMConfigurator;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.beans.factory.BeanFactory;

import javax.xml.parsers.FactoryConfigurationError;

/**
 * @author Georges Polyzois
 */
public class PluginInvoker
{
    /** Logger. */
    private static Logger logger = Logger.getLogger(PluginInvoker.class);
    private String beansConfigFile;

    static
    {
        try
        {
            DOMConfigurator.configure(System.getProperty("log4j.configuration"));
            //org.apache.log4j.MDC.put("host", getLocalHostName());
        }
        catch (FactoryConfigurationError ex)
        {
            System.out.println("Log4j can not start properly!!! Have you added -Dlog4j.configuration=<path to your log4j.xml file>?");
            ex.printStackTrace();
        }
    }

    public PluginInvoker(String beansConfigFile, String beanName)
    {
        this.beansConfigFile = beansConfigFile;
        BeanFactory factory = new ClassPathXmlApplicationContext(beansConfigFile);
        FeederPlugin ref = (XSLTransformerFeederPluginImpl)factory.getBean(beanName);
        ref.execute();
    }


    public static final void main(String[] args) {
        String beansConfigFile = "plugin-xsltransformer.xml";
        String beanId = "xsltransformplugin";
        PluginInvoker pluginInvoker = new PluginInvoker(beansConfigFile,beanId);
    }

}
