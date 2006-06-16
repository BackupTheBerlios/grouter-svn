/*
 * Created on 2005-jul-11
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.sonyericsson.fun.http.contentmodel;

import java.io.IOException;
import java.net.MalformedURLException;

import org.apache.commons.lang.StringUtils;
import org.xml.sax.SAXException;

import com.meterware.httpunit.HTMLElement;
import com.meterware.httpunit.WebResponse;
import com.meterware.servletunit.ServletUnitClient;
import com.sonyericsson.fun.http.common.HttpUnitTestCase;

/**
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class HttpTestWxhtmlContentListing extends HttpUnitTestCase {
	private ServletUnitClient client;
	
	/**
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
	    client = getClient("Mozilla");
	}

	public void testListing_CheckBuyLinkURL() throws MalformedURLException, IOException, SAXException{
		//WebResponse response = client.getResponse("http://localhost/wxhtml/download1/download1_1?cc=SE&lc=sv&products=T610&Page=0");
		WebResponse response = client.getResponse("http://localhost/wxhtml/download1/download1_1?brands=Asphalt%3a%20Urban%20GT&cc=SE&lc=sv&products=T610");
		writeResponseToFile(response, "c:\\temp\\testListing_CheckBuyLinkURL.html");
		
		HTMLElement elementWithID = response.getElementWithID("buyLink10329");
		String buyLinkURL = elementWithID.getAttribute("href");
		//this.writeResponseToFile(response, "c:/temp/content_listing.html");
		System.out.println( buyLinkURL );
		//assertEquals("/wxhtml/download1/downloadDetails?cc=SE&contentId=content15&lc=sv&page=0&products=T610", buyLinkURL );
		assertEquals("/wxhtml/download1/downloadDetails?brands=Asphalt%3a%20Urban%20GT&cc=SE&contentId=10329&lc=sv&page=0&products=T610", buyLinkURL );
		//test to ensure there is a space between the currency and the price.
		assertTrue(response.getText(), StringUtils.contains(response.getText(),"SEK&nbsp;30.00"));
		printLinks(response.getLinks());	
		
	}

	
	public void testListing_viewDetailsLinkURL() throws MalformedURLException, IOException, SAXException{
		//WebResponse response = client.getResponse("http://localhost/wxhtml/download1/download1_1?cc=SE&lc=sv&products=T610&Page=0");
		WebResponse response = client.getResponse("http://localhost/wxhtml/download1/download1_1?brands=Asphalt%3a%20Urban%20GT&cc=SE&lc=sv&products=T610");
		writeResponseToFile(response, "c:\\temp\\testListing_viewDetailsLinkURL.html");
		
		HTMLElement elementWithID = response.getElementWithID("viewDetailsLink10329");
		String buyLinkURL = elementWithID.getAttribute("href");
		System.out.println( buyLinkURL );
		//assertEquals("/wxhtml/download1/downloadDetails?cc=SE&contentId=content15&lc=sv&page=0&products=T610", buyLinkURL );
		assertEquals("/wxhtml/download1/downloadDetails?brands=Asphalt%3a%20Urban%20GT&cc=SE&contentId=10329&lc=sv&page=0&products=T610", buyLinkURL );
		printLinks(response.getLinks());	
	}
	
	public void testListing_copyToMyURL() throws MalformedURLException, IOException, SAXException{
		//WebResponse response = client.getResponse("http://localhost/wxhtml/download1/download1_1?cc=SE&lc=sv&files=4712&Page=0");
		WebResponse response = client.getResponse("http://localhost/wxhtml/download1/download1_1?cc=SE&files=593&lc=sv");
		writeResponseToFile(response, "c:\\temp\\testListing_copyToMyURL.html");
		
		HTMLElement link = response.getElementWithID("copy20809");
		//assertEquals("/wxhtml/mycontent/mythemes/mycontent.copytheme?cc=SE&itemId=content21&lc=sv&publicItem=true", link.getAttribute("href") );
		assertEquals("/wxhtml/mycontent/myvideos/copyvideoList?cc=SE&itemId=20809&lc=sv&publicItem=true", link.getAttribute("href") );
		
		HTMLElement noLink = response.getElementWithID("copycontent11");
		assertNull(noLink);
	}

	public void testItemsPerPageLinks() throws MalformedURLException, IOException, SAXException{
		WebResponse response = client.getResponse("http://localhost/wxhtml/download1/download1_1?cc=SE&lc=sv&products=T610&Page=0");
		//WebResponse response = client.getResponse("http://localhost/wxhtml/download1/download1_1?cc=SE&files=593&lc=sv");
		writeResponseToFile(response, "c:\\temp\\testListing_ItemsPerPageLinks.html");
		
		HTMLElement link10 = response.getElementWithID("10ItemsPerPage");
		assertEquals("/wxhtml/download1/download1_1?cc=SE&itemsPerPage=10&lc=sv&page=0&products=T610", link10.getAttribute("href") );
		//assertEquals("/wxhtml/download1/download1_1?cc=SE&files=593&itemsPerPage=10&lc=sv&page=0", link10.getAttribute("href") );
		HTMLElement link20 = response.getElementWithID("20ItemsPerPage");
		assertEquals("/wxhtml/download1/download1_1?cc=SE&itemsPerPage=20&lc=sv&page=0&products=T610", link20.getAttribute("href") );
		//assertEquals("/wxhtml/download1/download1_1?cc=SE&files=593&itemsPerPage=20&lc=sv&page=0", link20.getAttribute("href") );
		HTMLElement link50 = response.getElementWithID("50ItemsPerPage");
		assertEquals("/wxhtml/download1/download1_1?cc=SE&itemsPerPage=50&lc=sv&page=0&products=T610", link50.getAttribute("href") );
		//assertEquals("/wxhtml/download1/download1_1?cc=SE&files=593&itemsPerPage=50&lc=sv&page=0", link50.getAttribute("href") );
	}
	
	public void testListing_x() throws MalformedURLException, IOException, SAXException{
		WebResponse response = client.getResponse("http://localhost/wxhtml/download1/download1_1?cc=GB&files=600&lc=en&products=P900");
		//http://dev2.dev.cybercom.se/fun/wxhtml/download1/downloadDetails?brands=Vijay%20Singh%20Pro%20Golf&cc=GB&contentId=10330&lc=en&page=0&products=Z600
		writeResponseToFile( response, "c:\\temp\\test.html");
	}

	public void testPreviewUrl() throws MalformedURLException, IOException, SAXException
	{
		
		
		WebResponse response = client.getResponse("http://localhost/wxhtml/download1/download1_1?cc=SE&files=593&lc=sv");
		this.writeResponseToFile(response, "c:/temp/contentListing_preview.html");
		HTMLElement element = response.getElementWithID("preview20809");
		assertNotNull(element);
		
	}
	

	public void testListing_phoneIcons_SelectedFacets() throws MalformedURLException, IOException, SAXException{
		WebResponse response = client.getResponse("http://localhost/wxhtml/download1/download1_1?cc=GB&files=559%2f560&lc=en&products=Z200");
			
		System.out.println(" " + response.getText());
		HTMLElement elementWithID = response.getElementWithID("phoneIcon5597");
			
		String phoneIcon = elementWithID.getAttribute("src");
		
		//System.out.println( phoneIcon );
		assertEquals("http://localhost/origin/images/phoneIcons/Z200_production.jpg", phoneIcon );
	}
	
	public void testListing_phoneIcons() throws MalformedURLException, IOException, SAXException{
		WebResponse response = client.getResponse("http://localhost/wxhtml/download1/download1_1?cc=SE&lc=sv&products=F500i");
			
		//System.out.println(" " + response.getText());
		HTMLElement elementWithID = response.getElementWithID("phoneIcon19987");
		String phoneIcon = elementWithID.getAttribute("src");
		
		//System.out.println( phoneIcon );
		assertEquals("http://localhost/origin/images/phoneIcons/F500i_production.jpg", phoneIcon );
	}
	
}
