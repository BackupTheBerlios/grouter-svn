package billing;


import java.io.IOException;
import java.net.MalformedURLException;

import org.apache.commons.lang.StringUtils;
import org.xml.sax.SAXException;

import com.meterware.httpunit.HTMLElement;
import com.meterware.httpunit.WebForm;
import com.meterware.httpunit.WebLink;
import com.meterware.httpunit.WebResponse;
import com.meterware.servletunit.ServletUnitClient;
import common.HttpUnitTestCase;

/**
 * Copyright 2005 Sony Ericsson Mobile Communications AB. All rights reserved.
 * @author The Platform Upgrade Project
 * @since $Date: 2006/03/15 12:02:33 $
 * @version $Revision: 1.1.10.1 $
 * 
 */
public class TestWxhtmlPurchaseWizard extends HttpUnitTestCase {
	
	private ServletUnitClient client;
	
	
	
	
	public void testMoreThanOnePhoneForItem () throws MalformedURLException, IOException, SAXException{
		WebResponse response = client.getResponse("http://localhost/wxhtml/download1/purchasephone?cc=SE&contentId=content11&lc=sv&page=0&products=S700i");
		this.assertPurchaseDeviceNameS700i(response);
		WebResponse response2 = client.getResponse("http://localhost/wxhtml/download1/purchasephone?cc=SE&contentId=content11&lc=sv&page=0&products=F500i");
		this.assertPurchaseDeviceNameF500i(response2);
	}
	public void testCcbPurchase() throws MalformedURLException, IOException, SAXException {
		WebResponse response = client.getResponse("http://localhost/wxhtml/download1/purchaseccb?cc=SE&contentId=4045&lc=sv&page=0");
		this.writeResponseToFile(response, "c:/temp/purchaseWizard_ccb.html");
		
		this.assertPurchaseThumbnailT610(response);
		this.assertPurchasePriceSEK30(response);
		this.assertPurchasePhoneIcon(response);
		this.purchaseMediaTypeIcon(response);
	}
	
	public void testIVRPurchase() throws MalformedURLException, IOException, SAXException
	{
		WebResponse response = client.getResponse("http://localhost/wxhtml/download1/purchaseivr?cc=SE&contentId=4045&lc=sv&page=0");
		this.writeResponseToFile(response, "c:/temp/purchaseWizard_ivr.html");
		this.purchaseMediaTypeIcon(response);
		
		this.assertPurchaseThumbnailT610(response);
		this.assertPurchasePriceSEK30(response);
		this.assertPurchasePhoneIcon(response);
	}
			
	public void testPremiumPackageItem () throws MalformedURLException, IOException, SAXException{
		WebResponse response = client.getResponse("http://localhost/wxhtml/download1/downloadDetails?cc=SE&contentId=content42&lc=sv&page=0&products=P900");
		this.writeResponseToFile(response, "c:/temp/packageItem.html");
		HTMLElement packageLink = response.getElementWithID("packageLinkcontent42");
		assertEquals("/wxhtml/download1/downloadDetails?cc=SE&contentId=package1&lc=sv&page=0&products=P900", packageLink.getAttribute("href"));
	}
	
	
	public void testPhone_operator() throws MalformedURLException, IOException, SAXException {
		WebResponse response = client.getResponse("http://localhost/wxhtml/download1/purchasephone?cc=SE&contentId=4045&lc=sv&page=0");
		this.writeResponseToFile(response, "c:/temp/purchaseWizard_base.html");
		this.assertPurchasePriceSEK30(response);
		this.assertPurchaseThumbnailT610(response);
		this.assertPurchasePhoneIcon(response);
		this.purchaseMediaTypeIcon(response);
		
	}
	
	
	
	
	
	private void purchaseMediaTypeIcon(WebResponse response) throws MalformedURLException, IOException, SAXException {
		HTMLElement elementWithID = response.getElementWithID("contentItemIcon");
		assertEquals("http://localhost/origin/images/downloads/game_icon.gif", elementWithID.getAttribute("src"));
		
	}
	
	
	/**
	 * @param url TODO
	 * @throws MalformedURLException
	 * @throws IOException
	 * @throws SAXException
	 */
	private void assertPurchasePhoneIcon(WebResponse response) throws MalformedURLException, IOException, SAXException {
		
		
		HTMLElement element = response.getElementWithID("phoneIcon");
		
		assertNotNull( element );
		assertEquals("http://localhost/origin/images/phoneIcons/T610_production.jpg", element.getAttribute("src"));
	}
	
	/**
	 * @param url TODO
	 * @throws MalformedURLException
	 * @throws IOException
	 * @throws SAXException
	 */
	private void assertPurchasePriceSEK30(WebResponse response) throws MalformedURLException, IOException, SAXException {
		
		assertTrue(StringUtils.contains(response.getText(), "SEK 30.0"));
	}
	
	
	/**
	 * @param url TODO
	 * @throws MalformedURLException
	 * @throws IOException
	 * @throws SAXException
	 */
	private void assertPurchaseThumbnailT610(WebResponse response) throws MalformedURLException, IOException, SAXException {
		
		HTMLElement elementWithID = response.getElementWithID("thumbnail");
		assertNotNull(elementWithID);
		assertEquals("http://localhost/origin/images/thumbnails/aceT610.jpg", elementWithID.getAttribute("src"));
		
	}
	private void assertPurchaseDeviceNameS700i (WebResponse response) throws SAXException{
		HTMLElement elementWithID = response.getElementWithID("phonedescriptor");
		assertNotNull(elementWithID);
		assertEquals("S700i",elementWithID.getText());
	}
	
	private void assertPurchaseDeviceNameF500i (WebResponse response) throws SAXException{
		HTMLElement elementWithID = response.getElementWithID("phonedescriptor");
		assertNotNull(elementWithID);
		assertEquals("F500i",elementWithID.getText());
	}

	
	/**
	 * @see HttpUnitTestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
		client = getClient("Mozilla");
	}
	
	
}


