package com.sonyericsson.fun.http.billing;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.lang.StringUtils;
import org.xml.sax.SAXException;

import com.meterware.httpunit.HTMLElement;
import com.meterware.httpunit.WebLink;
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
public class HttpTestWapBilling extends HttpUnitTestCase {
	
	public void testWapBillingEntryPoint() throws IOException, SAXException {
	    ServletUnitClient client = getClient("SonyEricssonK500i");
	    
	    WebResponse response = client.getResponse("http://localhost/mxhtml/purchase/purchasewb?contentId=content15&cc=SE&lc=sv&dn=K500i&products=K500i");
	    this.writeResponseToFile(response,"c:/temp/wapbilling.html");
	    
	    WebLink testTOCPage = response.getLinkWith("Confirm Purchase");
	    String url2 = testTOCPage.getURLString();
	    assertTrue(StringUtils.contains(response.getText(),"15.00"));    
	    assertTrue(StringUtils.contains(response.getText(),"SEK"));
	    assertTrue(StringUtils.contains(response.getText(),"Kb"));
	    
	    //some how the unittests removes lang= so look in the source, and look in the failour
	    assertEquals("http://test.mobilemedia.nu/cip3/mmw?cc=SE&lang=sv&ordercode=430668&backUrl=http%3A%2F%2Fdev2.sonyericsson.com%2Fmxhtml%2Fpurchase%2Fpurchasethanks%3Fcc%3DGB%26amp%3Bdn%3DK500i%26amp%3Blc%3Den", url2);

	}
	public void testSuccessFullPage() throws IOException, SAXException {
	    ServletUnitClient client = getClient("SonyEricssonK500i");
	    
	    WebResponse response = client.getResponse("http://localhost/mxhtml/purchase/purchasethanks?contentId=10337&cc=SE&lc=sv&dn=K500i");
	    
	     HTMLElement[] purchaseResult  = response.getElementsWithName("result");
	     //Get the text some how from the html element saying the status
	     
	    // /httpunit/mxhtml/purchase/purchaseWapbilling?ContId=10337&amp;cc=GB&amp;dn=K500i&amp;lc=en
	    assertEquals("mmpwbstatus=0",purchaseResult);
	        	    
	}

	
	public void testLegalPage() throws IOException, SAXException {
	    ServletUnitClient client = getClient("SonyEricssonK500i");

	    WebResponse response = client.getResponse("http://localhost/mxhtml/purchase/purchasewb?contentId=10337&cc=GB&lc=en&dn=K500i");
		
	     HTMLElement[] purchaseResult  = response.getElementsWithName("result");
	    WebLink link = response.getLinkWith("Terms and Conditions");
	    assertEquals("/mxhtml/purchase/purchaseLegal?cc=GB&dn=K500i&lc=en",link.getURLString());
	    
	    WebResponse response2 = link.click();
	    
	        	    
		}
	
	
	}
