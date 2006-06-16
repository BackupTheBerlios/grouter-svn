package com.sonyericsson.fun.http.contentmodel;

import java.io.IOException;
import java.net.MalformedURLException;

import org.xml.sax.SAXException;

import com.meterware.httpunit.HTMLElement;
import com.meterware.httpunit.WebLink;
import com.meterware.httpunit.WebResponse;
import com.meterware.servletunit.ServletUnitClient;
import com.sonyericsson.fun.http.common.HttpUnitTestCase;

/**
 * Copyright 2006 Sony Ericsson Mobile Communications AB. All rights reserved.
 * @author The Platform Upgrade Project
 * @since $Date: 2006/03/15 12:02:33 $
 * @version $Revision: 1.2.2.1 $
 * 
 */
public class HttpTestVendorLink extends HttpUnitTestCase{
	private ServletUnitClient client;
		protected void setUp() throws Exception {
			super.setUp();
		    client = getClient("Mozilla");
		}
	public void testVendorLink() throws MalformedURLException, IOException, SAXException {
		
			WebResponse response = client.getResponse("http://localhost/wxhtml/download1/download1_1?cc=SE&lc=sv&products=P900&Page=0");
			
			this.writeResponseToFile(response, "c:/temp/3rdParty_content_listing.html");
			HTMLElement elementWithID = response.getElementWithID("vendorLink21244");
			String vendorLink = elementWithID.getAttribute("href");
			assertEquals("javascript:openVendorLink('http://www.handango.com/?cc=SE&dn=P900&lc=sv')", vendorLink );

			HTMLElement[] noPreview = response.getElementsWithName("noPpreview21244");
		    assertNotNull(noPreview);
		    WebLink link = response.getLinkWithID("copy21244");
		    assertNull(link);

			System.out.println( vendorLink);
	
		}


	public void testNoVendorLink() throws MalformedURLException, IOException, SAXException {
		
			WebResponse response = client.getResponse("http://localhost/wxhtml/download1/download1_1?cc=SE&lc=sv&products=K700i");
			this.writeResponseToFile(response, "c:/temp/no3rdParty_content_listing.html");
			HTMLElement elementWithID = response.getElementWithID("copy19857");
			String copyLink = elementWithID.getAttribute("href");
			
			assertEquals("/wxhtml/mycontent/myimages/copyimageList?cc=SE&itemId=19857&lc=sv&publicItem=true", copyLink );
			//test to ensure there is a space between the currency and the price.			
		}

	}
	
