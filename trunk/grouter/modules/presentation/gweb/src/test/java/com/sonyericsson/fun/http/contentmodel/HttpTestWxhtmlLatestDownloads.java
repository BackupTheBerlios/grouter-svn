package com.sonyericsson.fun.http.contentmodel;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;

import org.jmock.RecorderTestCase;
import org.xml.sax.SAXException;

import com.meterware.httpunit.HTMLElement;
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
public class HttpTestWxhtmlLatestDownloads extends RecorderTestCase {
	
	private ServletRunner sr;
	private ServletUnitClient client;
	private WebResponse response;

	/**
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
		
		// Make sure to only load the spring config once.
		if ( sr == null) {
			InputStream webXml = new FileInputStream("web.xml");
			sr = new ServletRunner( webXml);     
		}
	    client = sr.newClient();
	    client.getClientProperties().setUserAgent("Mozilla");
	}
	
	public void testLatestDownloads() throws MalformedURLException, IOException, SAXException {
		//response = client.getResponse("http://localhost/wxhtml/download1?cc=GB&lc=en");
		response = client.getResponse("http://localhost/wxhtml/download1?cc=CA&lc=en");
		this.writeResponseToFile(response, "c:\\logs\\testLatestDownloads.html");

		HTMLElement elementWithID = response.getElementWithID("ringtonesLink3031");
		assertEquals("/wxhtml/download1/downloadDetails?cc=CA&contentId=3031&lc=en", elementWithID.getAttribute("href"));

		HTMLElement gamesWithID = response.getElementWithID("games2372");
		assertEquals("http://localhost/origin/images/thumbnails/GME_MiniGolf_T300_anim_listthmb_en_GLOBAL.jpg", gamesWithID.getAttribute("src"));

		
	}
	/**
	 * Writes the specified response:s text (response.getText()) to the specified file.  
	 * @param response The response to write
	 * @param fileNameWithFullPath The file to write the response text to
	 * @throws IOException
	 */
	private void writeResponseToFile(WebResponse response, String fileNameWithFullPath) throws IOException {
		
		try {
			BufferedWriter out = new BufferedWriter(new FileWriter(
					fileNameWithFullPath));
			out.write(response.getText());
			out.close();
			System.out.println("File " + fileNameWithFullPath
					+ " now written containing response output.");
		} catch (Exception e) {
			//Probably in running in the unix environment...igonore
			System.out.println("Could not create the file " + fileNameWithFullPath );
		}
	}
	
}


