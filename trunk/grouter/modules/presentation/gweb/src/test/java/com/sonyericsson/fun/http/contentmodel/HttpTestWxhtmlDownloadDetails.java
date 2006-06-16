package com.sonyericsson.fun.http.contentmodel;


import java.io.IOException;
import java.net.MalformedURLException;

import org.apache.commons.lang.StringUtils;
import org.xml.sax.SAXException;

import com.meterware.httpunit.HTMLElement;
import com.meterware.httpunit.WebLink;
import com.meterware.httpunit.WebResponse;
import com.meterware.servletunit.ServletUnitClient;
import com.sonyericsson.fun.http.common.HttpUnitTestCase;

/**
 * Copyright 2005 Sony Ericsson Mobile Communications AB. All rights reserved.
 * @author The Platform Upgrade Project
 * @since $Date: 2006/03/15 12:02:33 $
 * @version $Revision: 1.1.8.1 $
 * 
 */
public class HttpTestWxhtmlDownloadDetails extends HttpUnitTestCase {
	
	private ServletUnitClient client;

	/**
	 * @see HttpUnitTestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
		client = getClient("Mozilla");
	}
	
	public void testPreviewLink() throws MalformedURLException, IOException, SAXException {
		WebResponse response = client.getResponse("http://localhost/wxhtml/download1/downloadDetails?cc=CA&lc=en&contentId=508&page=0");
		
		//javascript:openCenter("/wxhtml/view/null?cc=SE&contentId=508&lc=sv&page=0"
		
		WebLink viewImageLink = response.getLinkWith("View wallpaper");
				
		WebLink[] allLinks = response.getLinks();
		printLinks(allLinks);
		
		assertNotNull(viewImageLink);
		
		assertEquals("javascript:openCenter(\"/wxhtml/view/image?cc=CA&contentId=508&lc=en&page=0\")", viewImageLink.getURLString());
	
	
	}
	
	public void testPreviewPage() throws MalformedURLException, IOException, SAXException {
		WebResponse response = client.getResponse("http://localhost/wxhtml/view/ringtone?cc=CA&lc=en&contentId=5597&page=0");
		
		writeResponseToFile(response, "c:/temp/preview_ringtone.html");
		assertTrue(StringUtils.contains(response.getText(), "http://localhost/downloads/Arabicnight_Z200.mmf"));
		
	}
	
	public void testPreviewPagePremium() throws MalformedURLException, IOException, SAXException {
		WebResponse response = client.getResponse("http://localhost/wxhtml/view/ringtone?cc=GB&lc=en&contentId=5165&page=0");
		
		writeResponseToFile(response, "c:/temp/preview_ringtone_premium.html");
		assertTrue(StringUtils.contains(response.getText(), "http://localhost/origin/images/thumbnails/RIN_PR_Flintstones.mid"));
		
	}
	
	
 public void testPrice() throws MalformedURLException, IOException, SAXException {
	WebResponse response = client.getResponse("http://localhost/wxhtml/download1/downloadDetails?cc=SE&lc=sv&contentId=4045&page=0");
	this.writeResponseToFile(response, "c:/temp/downloadDetails_price.html");
	assertTrue(StringUtils.contains(response.getText(), "SEK&nbsp;30.00"));
 
 }
	public void testPhoneIcon()throws MalformedURLException, IOException, SAXException {
		WebResponse response = client.getResponse("http://localhost/wxhtml/download1/downloadDetails?cc=CA&lc=en&contentId=2577&page=0&products=P900");
		
		WebLink[] allLinks = response.getLinks();
		printLinks(allLinks);
		
		HTMLElement element = response.getElementWithID("phoneIcon");
		
		assertNotNull( element );
		assertEquals("http://localhost/origin/images/phoneIcons/P900_production.jpg", element.getAttribute("src"));
	}
	
	public void testAvailableForLink()throws MalformedURLException, IOException, SAXException {
		WebResponse response = client.getResponse("http://localhost/wxhtml/download1/downloadDetails?cc=CA&lc=en&contentId=2577&page=0&products=P900");
		
		WebLink[] allLinks = response.getLinks();
		printLinks(allLinks);
		
		HTMLElement element = response.getLinkWithID("alsoAvailableForP800");
		
		String hRefAttribute = element.getAttribute("href");
		
		assertEquals("/wxhtml/download1/downloadDetails?cc=CA&contentId=2577&lc=en&products=P800", hRefAttribute);
		
	}
	
	public void testBackToLink()throws MalformedURLException, IOException, SAXException {
		WebResponse response = client.getResponse("http://localhost/wxhtml/download1/downloadDetails?cc=CA&lc=en&contentId=2577&page=0&products=P900");
		
		WebLink[] allLinks = response.getLinks();
		printLinks(allLinks);
		
		HTMLElement element = response.getLinkWithID("backToLink");
		
		String hRefAttribute = element.getAttribute("href");
		
		assertEquals("/wxhtml/download1/download1_1?cc=CA&contentId=2577&lc=en&page=0&products=P900", hRefAttribute);
		
	}
	
	public void testPremiumPackage() throws MalformedURLException, IOException, SAXException {
		WebResponse response = client.getResponse("http://localhost/wxhtml/download1/downloadDetails?cc=SE&lc=sv&contentId=package1&page=0&products=P900");
		this.writeResponseToFile(response,"c://temp/premiumPackage.html");
		
		HTMLElement packageItemImage = response.getElementWithID("packageItemImagecontent40");
		String packageItemImageUrl = packageItemImage.getAttribute("src");
		assertEquals("http://localhost/origin/images/thumbnails/smallThumbNailcontent40", packageItemImageUrl);
		
		HTMLElement packageItemLink = response.getElementWithID("packageItemLinkcontent40");
		String packageItemLinkUrl = packageItemLink.getAttribute("href");
		assertEquals("/wxhtml/download1/downloadDetails?cc=SE&contentId=content40&lc=sv&page=0&products=P900",packageItemLinkUrl);
		
		HTMLElement packageItemImage2 = response.getElementWithID("packageItemImagecontent41");
		String packageItemImageUrl2 = packageItemImage2.getAttribute("src");
		assertEquals("http://localhost/origin/images/thumbnails/smallThumbNailcontent41", packageItemImageUrl2);
		
		HTMLElement packageItemLink2 = response.getElementWithID("packageItemLinkcontent41");
		String packageItemLinkUrl2 = packageItemLink2.getAttribute("href");
		
		assertEquals("/wxhtml/download1/downloadDetails?cc=SE&contentId=content41&lc=sv&page=0&products=P900",packageItemLinkUrl2);

		HTMLElement packageItemImage3 = response.getElementWithID("packageItemImagecontent42");
		String packageItemImageUrl3 = packageItemImage3.getAttribute("src");
		assertEquals("http://localhost/origin/images/thumbnails/smallThumbNailcontent42", packageItemImageUrl3);
		
		HTMLElement packageItemLink3 = response.getElementWithID("packageItemLinkcontent42");
		String packageItemLinkUrl3 = packageItemLink3.getAttribute("href");
		
		assertEquals("/wxhtml/download1/downloadDetails?cc=SE&contentId=content42&lc=sv&page=0&products=P900",packageItemLinkUrl3);
	}
	
	public void testPremiumPackageItem () throws MalformedURLException, IOException, SAXException{
		WebResponse response = client.getResponse("http://localhost/wxhtml/download1/downloadDetails?cc=SE&contentId=content42&lc=sv&page=0&products=P900");
		this.writeResponseToFile(response, "c:/temp/packageItem.html");
		HTMLElement packageLink = response.getElementWithID("packageLinkcontent42");
		 assertEquals("/wxhtml/download1/downloadDetails?cc=SE&contentId=package1&lc=sv&page=0&products=P900", packageLink.getAttribute("href"));
	}
	
	public void testCategory() throws MalformedURLException, IOException, SAXException {
		WebResponse response = client.getResponse("http://localhost/wxhtml/download1/downloadDetails?cc=CA&lc=en&contentId=2577&page=0&products=P900");
		
		WebLink[] allLinks = response.getLinks();
		printLinks(allLinks);
		
		HTMLElement element = response.getElementWithID("category");
		assertEquals("Landscapes", element.getText());
	}	
	
	
	public void testEmulatorLink() throws MalformedURLException, IOException, SAXException {
		WebResponse response = client.getResponse("http://localhost/wxhtml/download1/downloadDetails?cc=SE&contentId=content82&lc=sv&page=1&products=Z600");
		
		HTMLElement element = response.getElementWithID("emulator");
		this.writeResponseToFile(response,"c:/temp/details_emulatorlink.html");
		assertNotNull(element);
		
		assertEquals("Try it!", element.getText());
	}	
	
	
}


