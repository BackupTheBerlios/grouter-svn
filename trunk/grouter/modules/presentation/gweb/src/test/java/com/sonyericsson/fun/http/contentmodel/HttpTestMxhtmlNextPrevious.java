/*
 * Created on 2005-jul-11
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.sonyericsson.fun.http.contentmodel;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;

import org.jmock.RecorderTestCase;
import org.xml.sax.SAXException;

import com.meterware.httpunit.WebLink;
import com.meterware.httpunit.WebResponse;
import com.meterware.servletunit.ServletRunner;
import com.meterware.servletunit.ServletUnitClient;

/**
 * @author christoffer.hellgren
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class HttpTestMxhtmlNextPrevious extends RecorderTestCase {
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
	    client.getClientProperties().setUserAgent("SonyEricssonS700i");
	}

	public void testLessThan10() throws MalformedURLException, IOException, SAXException{
		response = client.getResponse("http://localhost/mxhtml/download1_1?cc=SE&lc=sv&files=4711/4715");
		
		assertLinkDoesNotExist(response, "Next");
	}
	
	public void testMoreThan10LessThan20Page1() throws MalformedURLException, IOException, SAXException {
		client.getClientProperties().setUserAgent("SonyEricssonF500i");
		response = client.getResponse("http://localhost/mxhtml/download1_1?cc=SE&lc=sv&files=4711/4716&Page=0");
		printLinks(response.getLinks());	
		WebLink nextLink = response.getLinkWith("Next 5");
		assertNotNull(nextLink);
		
		assertLinkDoesNotExist(response, "F&ouml;reg&aring;ende");
	}
	
	public void testMoreThan10LessThan20Page2() throws MalformedURLException, IOException, SAXException{
		client.getClientProperties().setUserAgent("SonyEricssonF500i");
		response = client.getResponse("http://localhost/mxhtml/download1_1?cc=SE&lc=sv&files=4711/4716&Page=1");
		WebLink prevLink = response.getLinkWith("Previous 10");
		printLinks(response.getLinks());
		assertNotNull(prevLink);
		assertEquals("/mxhtml/download1_1?cc=SE&dn=F500i&files=4711%2f4716&itemsPerPage=10&lc=sv&page=0&products=F500i", prevLink.getURLString());
		
		assertLinkDoesNotExist( response, "Next");
	}

	/**
	 * @param links
	 */
	private void printLinks(WebLink[] links) {
		for (int i = 0; i < links.length; i++) {
			WebLink link = links[i];
			System.out.println("->"+link.getText());
			
		}
		
	}

	public void testMoreThan20Page1() throws MalformedURLException, IOException, SAXException {
	    client.getClientProperties().setUserAgent("SonyEricssonF500i");
	    response = client.getResponse("http://localhost/mxhtml/download1_1?cc=SE&lc=sv&Page=0");
		WebLink nextLink = response.getLinkWith("Next 10");
		printLinks(response.getLinks());
		assertNotNull( nextLink );
		assertEquals("/mxhtml/download1_1?cc=SE&dn=F500i&itemsPerPage=10&lc=sv&page=1&products=F500i", nextLink.getURLString());
		assertLinkDoesNotExist(response, "previous");
	}

	public void testMoreThan20Page2() throws MalformedURLException, IOException, SAXException{
	    client.getClientProperties().setUserAgent("SonyEricssonK500i");
	    response = client.getResponse("http://localhost/mxhtml/download1_1?cc=SE&dn=F500i&itemsPerPage=10&lc=sv&page=1");
		WebLink nextLink = response.getLinkWith("Next 5");
		printLinks(response.getLinks());
		assertNotNull( nextLink );
		assertEquals("/mxhtml/download1_1?cc=SE&dn=F500i&itemsPerPage=10&lc=sv&page=2&products=F500i", nextLink.getURLString());
		
		WebLink prevLink = response.getLinkWith("Previous 10");
		assertEquals("/mxhtml/download1_1?cc=SE&dn=F500i&itemsPerPage=10&lc=sv&page=0&products=F500i", prevLink.getURLString());
	}

	public void testMoreThan20Page3() throws MalformedURLException, IOException, SAXException {
		client.getClientProperties().setUserAgent("SonyEricssonK500i");
		response = client.getResponse("http://localhost/mxhtml/download1_1?cc=SE&dn=F500i&itemsPerPage=10&lc=sv&page=2");
		WebLink prevLink = response.getLinkWith("Previous 10");
		printLinks(response.getLinks());
		assertNotNull(prevLink);
		assertEquals("/mxhtml/download1_1?cc=SE&dn=F500i&itemsPerPage=10&lc=sv&page=1&products=F500i", prevLink.getURLString());
		assertLinkDoesNotExist( response, "Next");
	}
	
	/**
	 * Checks that a link with the specified text does not exist in the response
	 * @param links
	 */
	private void assertLinkDoesNotExist(WebResponse webResponse, String linkText) throws SAXException{
		WebLink[] links = webResponse.getLinks();
		for (int i = 0; i < links.length; i++) {
			assertFalse(links[i].getText().toUpperCase().startsWith(linkText.toUpperCase()));
		}
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
