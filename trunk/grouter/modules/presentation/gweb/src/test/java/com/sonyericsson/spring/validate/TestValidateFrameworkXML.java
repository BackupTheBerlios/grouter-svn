package com.sonyericsson.spring.validate;

import junit.framework.TestCase;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

public class TestValidateFrameworkXML extends TestCase {
	private Logger logger = Logger.getLogger(TestValidateFrameworkXML.class);
	private String configLocation;

	public void setUp() {
		configLocation = "src/main/webapp/WEB-INF/framework.xml";
		// getBaseDir if run via maven-surefire-plugin
		String baseDir = System.getProperty("basedir");
		if (baseDir != null) {
			configLocation = "/" + baseDir + "/" + configLocation;
		}
	}
	
	public void testFrameworkXML() throws Exception {
		Resource r = new FileSystemResource(configLocation);
		BeanFactory factory = new XmlBeanFactory(r);
		logger.debug("factory: " + factory);
	}

	public void testLoadXMLContext() throws Exception {
		try {
			boolean fail = false;
			System.setProperty("java.naming.factory.initial", "com.sonyericsson.spring.validate.MockInitialContextFactory");
			FileSystemXmlApplicationContext fileSystemXmlApplicationContext = new FileSystemXmlApplicationContext(configLocation);
			String[] beanNamesApp = fileSystemXmlApplicationContext.getBeanDefinitionNames();
			for (int j = 0; j < beanNamesApp.length; j++) {
				try {
					String string = beanNamesApp[j];
					Object object = fileSystemXmlApplicationContext.getBean(string);
					logger.info(string + " <-> " + object.getClass().getName());
				} catch (Exception e) {
					logger.error(e, e);
					fail = true;
				}
			}
			if (fail) {
				fail("Could not read all neccessary spring config files");
			}
		}catch(Exception e) {
			logger.error(e);
		}
	}
}
