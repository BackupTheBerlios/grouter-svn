package com.sonyericsson.fun.http.directlinks;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.xml.sax.SAXException;

import com.meterware.httpunit.WebResponse;
import com.meterware.servletunit.ServletRunner;
import com.meterware.servletunit.ServletUnitClient;
import com.sonyericsson.fun.http.common.HttpUnitTestCase;



/**
 * Copyright 2005 Sony Ericsson Mobile Communications AB. All rights reserved.
 * @author The Platform Upgrade Project
 * @since $Date: 2006/03/15 12:02:33 $
 * @version $Revision: 1.1.8.1 $
 * 
 */
public class HttpTestDirectlinks extends HttpUnitTestCase {
	
	public void testDirectlinksHome() throws IOException, SAXException {
		InputStream webXml = new FileInputStream("web.xml");
		
		ServletRunner sr = new ServletRunner(  webXml, "/httpunit" );       // (1) use the web.xml file to define mappings
	    ServletUnitClient client = sr.newClient();               // (2) create a client to invoke the application
	    client.getClientProperties().setUserAgent("SonyEricssonD750i");
	    
	    WebResponse response = client.getResponse("http://localhost/httpunit/download.do?cmd=owp&appl=home");
	    this.writeResponseToFile(response, "c:/temp/directlinksHome.html");
	    
	}
}
	