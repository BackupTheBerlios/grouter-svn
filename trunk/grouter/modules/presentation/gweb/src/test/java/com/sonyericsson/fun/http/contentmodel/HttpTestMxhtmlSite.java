package com.sonyericsson.fun.http.contentmodel;

import java.io.IOException;
import java.net.MalformedURLException;

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
 * @version $Revision: 1.2.2.1 $
 * 
 */
public class HttpTestMxhtmlSite extends HttpUnitTestCase{
	
	private ServletRunner sr;
	private ServletUnitClient client;
	private WebResponse response;

	/**
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
	    client = getClient("SonyEricssonS700i");
	}
	
	public void testHome() throws MalformedURLException, IOException, SAXException {
		response = client.getResponse("http://localhost/mxhtml/home?cc=SE&lc=sv");
		WebLink ringTones = response.getLinkWith("ringtones");
		WebLink themes = response.getLinkWith("Themes");
		WebLink browseByBrand = response.getLinkWith("Browse by brand");
		HTMLElement title = response.getElementWithID("bannerTitle");
		writeResponseToFile(response, "c:/temp/mxhtmlHomepage.html");		
		WebLink[] allLinks = response.getLinks();
		this.writeResponseToFile(response, "c:/logs/facetMxhtmlHome.html");
		
		System.out.println(response.getText());
		assertNotNull(ringTones);
		assertNotNull(themes);
		assertNotNull(browseByBrand);
		assertNotNull(title);
		
		HTMLElement iconRingtone = response.getElementWithID("icon.ringtones");
		HTMLElement iconTheme = response.getElementWithID("icon.themes");
		HTMLElement iconGame = response.getElementWithID("icon.games");	
		HTMLElement iconImage = response.getElementWithID("icon.images");
		HTMLElement iconValuePack = response.getElementWithID("icon_valuepack");
		assertEquals("",title);
		
		assertEquals("http://localhost/origin/images/mxhtml/shared/sh_ringtone_icon.gif",iconRingtone.getAttribute("src"));
		assertEquals("http://localhost/origin/images/mxhtml/shared/sh_theme_icon.gif",iconTheme.getAttribute("src"));
		assertEquals("http://localhost/origin/images/mxhtml/shared/sh_game_icon.gif",iconGame.getAttribute("src"));
		//No images in our test data 
		//assertEquals("http://localhost/origin/images/mxhtml/shared/sh_image_icon.gif",iconImage.getAttribute("src"));

		assertEquals("/mxhtml/download1_1?cc=SE&dn=S700i&files=4711&lc=sv&products=S700i", ringTones.getURLString());
		assertEquals("/mxhtml/download1_1?cc=SE&dn=S700i&files=4712&lc=sv&products=S700i", themes.getURLString());
		assertEquals("/mxhtml/download1_1?cc=SE&dn=S700i&lc=sv", browseByBrand.getURLString());
		assertEquals(14, allLinks.length);
	}

	

	public void testRingtonesContentCategoryListing() throws IOException, SAXException {
		response = client.getResponse("http://localhost/mxhtml/download1?cc=SE&lc=sv&files=4711");
		HTMLElement title = response.getElementWithID("bannerTitle");
		WebLink rt_hipHop = response.getLinkWith("hip hop");
		
		WebLink[] allRingtonesLinks = response.getLinks();

		this.writeResponseToFile(response,"c:/logs/ringtonesMxhtml.html");
		HTMLElement firstSmiley = response.getElementWithID("icon.hip hop"); 
		assertEquals("http://localhost/origin/images/mxhtml/shared/sh_ringtone_icon.gif", firstSmiley.getAttribute("src"));
		assertNotNull(rt_hipHop);
		assertEquals("/mxhtml/download1_1?cc=SE&dn=S700i&files=4711%2f4715&lc=sv&products=S700i", rt_hipHop.getURLString() );
		assertNotNull(title);
		assertEquals(8, allRingtonesLinks.length);
	}

	public void testRingtonesCategoryListing() throws IOException, SAXException {
		response = client.getResponse("http://localhost/mxhtml/download1?cc=GB&lc=en&files=559");
		HTMLElement title = response.getElementWithID("bannerTitle");
		WebLink rt_hipHop = response.getLinkWith("hip hop");
		
		WebLink[] allRingtonesLinks = response.getLinks();

		this.writeResponseToFile(response,"c:/temp/ringtonesCategoryMxhtml.html");
		assertNotNull(title);
		assertEquals(8, allRingtonesLinks.length);
	}

	/**
	 * @throws IOException
	 * @throws SAXException
	 */
	public void testAdditionalLinksOnListings() throws IOException, SAXException {
		client.setHeaderField("Accpet-language","zh");
		response = client.getResponse("http://localhost/mxhtml/download1?cc=CN&dn=S700c&files=559&lc=zh&products=S700c");
		HTMLElement title = response.getElementWithID("bannerTitle");
			
		HTMLElement category = response.getElementWithID("additionalRingtone");
		
		WebLink[] allRingtonesLinks = response.getLinks();
		this.writeResponseToFile(response, "c://temp/additionalLinks.html");
		//this.printLinks( allRingtonesLinks);
		
		assertNotNull(category);
		assertEquals("http://sonyericsson.fundownload.com.cn/wap/ringtongs/index.jsp", category.getAttribute("href"));
		
		assertEquals(8, allRingtonesLinks.length);
	}
	
	public void testRingtonesContentListing() throws IOException, SAXException {
		response = client.getResponse("http://localhost/mxhtml/download1_1?cc=SE&lc=sv&files=4711/4715");
		writeResponseToFile(response, "c:/temp/ringtonesContentListing.html");
		WebLink[] allLinks = response.getLinks();
		
		WebLink rt_Content11 = response.getLinkWith("content11");
		WebLink rt_Content12 = response.getLinkWith("content12");
		WebLink rt_Content13 = response.getLinkWith("content13");
		WebLink rt_Content14 = response.getLinkWith("content14");
		WebLink rt_Content15 = response.getLinkWith("content15");
		
		assertNotNull(rt_Content11);
		assertNotNull(rt_Content12);
		assertNotNull(rt_Content13);
		assertNotNull(rt_Content14);
		assertNotNull(rt_Content15);
				
		assertEquals("/mxhtml/download1_1_1_free?cc=SE&contentId=content12&dn=S700i&files=4711%2f4715&lc=sv&page=0&products=S700i", rt_Content12.getURLString());
		assertEquals("/mxhtml/download1_1_1_free?cc=SE&contentId=content13&dn=S700i&files=4711%2f4715&lc=sv&page=0&products=S700i", rt_Content13.getURLString());
		assertEquals("/mxhtml/download1_1_1_free?cc=SE&contentId=content11&dn=S700i&files=4711%2f4715&lc=sv&page=0&products=S700i", rt_Content11.getURLString());
		assertEquals("/mxhtml/download1_1_1_free?cc=SE&contentId=content14&dn=S700i&files=4711%2f4715&lc=sv&page=0&products=S700i", rt_Content14.getURLString());
		assertEquals("/mxhtml/download1_1_1_premium?cc=SE&contentId=content15&dn=S700i&files=4711%2f4715&lc=sv&page=0&products=S700i", rt_Content15.getURLString());

		assertEquals(10, allLinks.length);
	}
	
	public void testBrowseByBrand() throws Exception {
		response = client.getResponse("http://localhost/mxhtml/download1_brands?cc=SE&dn=S700i&lc=sv" );
		WebLink[] allLinks = response.getLinks();
		WebLink otherLink = response.getLinkWith("other");
		WebLink seLink = response.getLinkWith("sonyericsson");
		
		assertNotNull(otherLink);
		assertNotNull(seLink);
		
		assertEquals("/mxhtml/download1_1?brands=other&cc=SE&dn=S700i&lc=sv&products=S700i", otherLink.getURLString() );
		assertEquals("/mxhtml/download1_1?brands=sonyericsson&cc=SE&dn=S700i&lc=sv&products=S700i", seLink.getURLString() );
		assertEquals(7, allLinks.length);
	}
	
	public void testBrandsContentListing() throws Exception {
		response = client.getResponse("http://localhost/mxhtml/download1_1?cc=SE&dn=S700i&brands=sonyericsson&lc=sv");
		
		WebLink[] allLinks = response.getLinks();
		
		WebLink rt_Content11 = response.getLinkWith("content11");
		WebLink rt_Content12 = response.getLinkWith("content12");
		WebLink rt_Content13 = response.getLinkWith("content13");
		WebLink rt_Content14 = response.getLinkWith("content14");
		WebLink rt_Content15 = response.getLinkWith("content15");
		
		assertEquals(12, allLinks.length);
		assertNotNull(rt_Content11);
		assertNotNull(rt_Content12);
		assertNotNull(rt_Content13);
		assertNotNull(rt_Content14);
		assertNotNull(rt_Content15);
		
		assertEquals("/mxhtml/download1_1_1_free?brands=sonyericsson&cc=SE&contentId=content11&dn=S700i&lc=sv&page=0&products=S700i", rt_Content11.getURLString());
		assertEquals("/mxhtml/download1_1_1_free?brands=sonyericsson&cc=SE&contentId=content12&dn=S700i&lc=sv&page=0&products=S700i", rt_Content12.getURLString());
		assertEquals("/mxhtml/download1_1_1_free?brands=sonyericsson&cc=SE&contentId=content13&dn=S700i&lc=sv&page=0&products=S700i", rt_Content13.getURLString());
		assertEquals("/mxhtml/download1_1_1_free?brands=sonyericsson&cc=SE&contentId=content14&dn=S700i&lc=sv&page=0&products=S700i", rt_Content14.getURLString());
		assertEquals("/mxhtml/download1_1_1_premium?brands=sonyericsson&cc=SE&contentId=content15&dn=S700i&lc=sv&page=0&products=S700i", rt_Content15.getURLString());
	}

	public void testBrowseByBrandsShowContentIfOnlyOneCategory() throws Exception {
		response = getClient("SonyEricssonZ600").getResponse("http://localhost/mxhtml/download1_brands?cc=SE&lc=sv");
		
		WebLink morethan20 = response.getLinkWith("morethan20");
		
		printLinks(response.getLinks());
		System.out.println(response.getText());
		assertEquals("/mxhtml/download1?brands=morethan20&cc=SE&dn=Z600&lc=sv&products=Z600", morethan20.getURLString());
	}

	public void testFreeContentDetails() throws Exception {
		response = client.getResponse("http://localhost/mxhtml/download1_1_1_free?contentId=content11&cc=SE&dn=S700i&lc=sv&page=1");
		
		WebLink downloadLink = response.getLinkWith("Download");
		assertNotNull(downloadLink);
		assertEquals("http://localhost/origin/res/oma/sv_SE/downloadFileName11.dd", downloadLink.getURLString());
	
		HTMLElement contentDetailsElement = response.getElementWithID("cd_caption");
		assertEquals("content11", contentDetailsElement.getText());
		
		WebLink[] allLinks = response.getLinks();
		assertEquals( 6, allLinks.length);
		
	}
	
	
	public void testPremiumContentDetails() throws MalformedURLException, IOException, SAXException {
		response = client.getResponse("http://localhost/mxhtml/download1_1_1_premium?cc=SE&contentId=content15&dn=S700i&lc=sv&page=0");
		WebLink buyNowWBLink = response.getLinkWithID("BuyNowWapBilling");
		WebLink buyNowSMSLink = response.getLinkWithID("BuyNowSmsTo");
		
		printLinks(response.getLinks());
			
		String buyNowUrlWB = buyNowWBLink.getURLString();
		assertEquals("/mxhtml/purchase/purchasewb?cc=SE&contentId=content15&dn=S700i&lc=sv&page=0&products=S700i", buyNowUrlWB);
		
		
	}

	public void testPremiumContentDetailsVAT() throws MalformedURLException, IOException, SAXException {
		response = client.getResponse("http://localhost/mxhtml/download1_1_1_premium?cc=SE&contentId=content15&dn=K500i&lc=sv&page=0");
		
		WebLink buyNow = response.getLinkWithID("BuyNowWapBilling");
		writeResponseToFile(response, "c:/temp/premiumcontenMxhtml_vat.html");	
		assertFalse(StringUtils.contains(response.getText(), "+VAT"));
		
		assertEquals("/mxhtml/purchase/purchasewb?cc=SE&contentId=content15&dn=K500i&lc=sv&page=0&products=K500i", buyNow.getURLString());
				
	}

}


