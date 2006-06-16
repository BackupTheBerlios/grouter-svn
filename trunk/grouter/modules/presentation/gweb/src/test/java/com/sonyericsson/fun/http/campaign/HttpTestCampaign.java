package com.sonyericsson.fun.http.campaign;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.jmock.RecorderTestCase;
import org.xml.sax.SAXException;

import com.meterware.httpunit.WebLink;
import com.meterware.httpunit.WebResponse;
import com.meterware.servletunit.ServletRunner;
import com.meterware.servletunit.ServletUnitClient;



/**
 * Copyright 2005 Sony Ericsson Mobile Communications AB. All rights reserved.
 * @author The Platform Upgrade Project
 * @since $Date: 2006/03/15 12:02:33 $
 * @version $Revision: 1.1.8.1 $
 * 
 */
public class HttpTestCampaign extends RecorderTestCase {
	
	public void testCampaignUrlEncoding() throws IOException, SAXException {
		InputStream webXml = new FileInputStream("web.xml");
		
		ServletRunner sr = new ServletRunner( webXml );       // (1) use the web.xml file to define mappings
	    ServletUnitClient client = sr.newClient();               // (2) create a client to invoke the application
	    client.getClientProperties().setUserAgent("Mozilla");
	    
	    WebResponse response = client.getResponse("http://localhost/wxhtml/promotion1/promotiondetail1?campid=2&cc=GB&lc=en&type=promotions");
	    String strResponse = response.getText();
	    System.out.println(strResponse);
	    WebLink ringtones = response.getLinkWith("Get ringtones!");
	    assertEquals("/wxhtml/download1/download1_1?brands=Sony%20Ericsson&cc=GB&files=559&lc=en",ringtones.getURLString());

	}
}
	