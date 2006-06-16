package contentmodel;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;

import org.jmock.RecorderTestCase;
import org.xml.sax.SAXException;

import com.meterware.httpunit.HTMLElement;
import com.meterware.httpunit.WebLink;
import com.meterware.httpunit.WebResponse;
import com.meterware.servletunit.ServletRunner;
import com.meterware.servletunit.ServletUnitClient;

import java.util.HashMap;
import java.util.Map;

/**
 * Copyright 2005 Sony Ericsson Mobile Communications AB. All rights reserved.
 * @author The Platform Upgrade Project
 * @since $Date: 2006/03/15 12:02:33 $
 * @version $Revision: 1.2.2.1 $
 * 
 */
public class TestWxhtmlFacetNavigation extends RecorderTestCase {
	private ServletUnitClient client;
	private ServletRunner sr;
	private WebLink t610Link;
	private WebLink s700Link;
	private WebLink p900Link;
	private WebLink wtaLink;
	private WebLink sonyericssonLink;
	private WebLink themesLink;
	private WebLink ringtonesLink;
	private WebLink hipHopLink;
	private WebLink rockAndRollLink;
	private WebLink gamesLink;
	private WebLink showAllPhonesLink;
	private WebLink showAllContentLink;
	private WebLink showAllBrandsLink;
	private WebLink showResetLink;
	private WebLink nextLink;
	private WebLink previousLink;
	
	
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
	    
		t610Link = null;
	    s700Link = null;		
	    p900Link = null;
	    wtaLink = null;
		sonyericssonLink = null;
		themesLink = null;
		gamesLink = null;
		ringtonesLink = null;
		hipHopLink = null;
		rockAndRollLink = null;
		showAllPhonesLink = null;
		showAllContentLink = null;
		showAllBrandsLink = null;
		showResetLink = null;
		nextLink = null;
		previousLink = null;

	}
	
	/**
	 * @see junit.framework.TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		super.tearDown();
	}
	

	/**
	 * @param response
	 * @throws SAXException
	 */
	private void getShowLinks(WebResponse response) throws SAXException {
		showAllPhonesLink = response.getLinkWith("Show all phones");
		showAllContentLink = response.getLinkWith("Show all content");
		showAllBrandsLink = response.getLinkWith("Show all brands");
		showResetLink = response.getLinkWith("reset");
	}
	
	/**
	 * @param response
	 * @throws SAXException
	 */
	private void getFileLinks(WebResponse response) throws SAXException {
		ringtonesLink = response.getLinkWith("ringtones");
	    themesLink = response.getLinkWith("themes");
	    gamesLink = response.getLinkWith("games");
	    hipHopLink = response.getLinkWith("hip hop");
	    rockAndRollLink = response.getLinkWith("rock & roll");
	}

	/**
	 * @param response
	 * @throws SAXException
	 */
	private void getBrandLinks(WebResponse response) throws SAXException {
		wtaLink = response.getLinkWith("wta");
	    sonyericssonLink = response.getLinkWith("sonyericsson");		
	}

	/**
	 * @param response
	 * @throws SAXException
	 */
	private void getPagingLinks(WebResponse response) throws SAXException {
		nextLink = response.getLinkWith("next");
	    previousLink = response.getLinkWith("previous");		
	}

	/**
	 * @param response
	 * @throws SAXException
	 * 
	 */
	private void getPhoneLinks(WebResponse response) throws SAXException {
		t610Link = response.getLinkWith("t610");
	    s700Link = response.getLinkWith("S700i");		
	    p900Link = response.getLinkWith("P900");
	}

	/**
	 * @param response
	 * @return
	 * @throws SAXException
	 * @throws IOException
	 */
	private WebResponse timeClick(WebLink link) throws IOException, SAXException {
		long start = System.currentTimeMillis();
		WebResponse response = link.click();
		long timeTaken = System.currentTimeMillis() - start;
		System.out.println("Click on <"+link.getText()+"> executed in <"+timeTaken+"ms>");
		return response;
	}
	
	public void testViewFacetsNoSelection() throws IOException, SAXException {
	    WebResponse response = client.getResponse("http://localhost/wxhtml/overview1?lc=sv&cc=SE");
	    getPhoneLinks(response);
	    getBrandLinks(response);
	    getFileLinks(response);
	    
	    assertNotNull(t610Link);
	    assertNotNull(p900Link);
	    assertNotNull(s700Link);
	    assertNotNull(wtaLink);
	    assertNotNull(sonyericssonLink);
	    assertNotNull(ringtonesLink);
	    assertNotNull(themesLink);
	    assertNotNull(gamesLink);
	    assertNull(hipHopLink);
	    assertNull(rockAndRollLink);
	}
	
	public void testViewSelectFacets() throws IOException, SAXException {
	    WebResponse response = client.getResponse("http://localhost/wxhtml/overview1?lc=sv&cc=SE");
	    getPhoneLinks(response);
	    getBrandLinks(response);
	    getFileLinks(response);
	    
	    assertNotNull(t610Link);
	    assertNotNull(p900Link);
	    assertNotNull(s700Link);
	    assertNotNull(wtaLink);
	    assertNotNull(sonyericssonLink);
	    assertNotNull(ringtonesLink);
	    assertNotNull(themesLink);
	    assertNotNull(gamesLink);
	    assertNull(hipHopLink);
	    assertNull(rockAndRollLink);
	    
	    HTMLElement itemCountElement = response.getElementWithID("headertext");
	    assertNull(itemCountElement);
	    
	    WebResponse t610Response = timeClick(t610Link);
	    
	    String responseText = t610Response.getText();
	    
	    writeResponseToFile(t610Response, "c:\\temp\\testViewSelectFacets.html");
	    
	    getPhoneLinks(t610Response);
	    getBrandLinks(t610Response);
	    getFileLinks(t610Response);
	    
	    assertNull(t610Link);
	    assertNull(p900Link);
	    assertNull(s700Link);
	    assertNull(wtaLink); // not in t610 selection
	    assertNotNull(sonyericssonLink);
	    assertNotNull(ringtonesLink);
	    assertNotNull(themesLink);
	    assertNotNull(gamesLink);
	    assertNull(hipHopLink);
	    assertNull(rockAndRollLink);
	  
	    itemCountElement = t610Response.getElementWithID("headertext");
	    assertNotNull(itemCountElement);
	    assertEquals("1 - 10 of 16", itemCountElement.getText());
	    
	    HTMLElement content1Elem = t610Response.getElementWithID("content_content1");
	    HTMLElement content10Elem = t610Response.getElementWithID("content_content10");
	    HTMLElement content11Elem = t610Response.getElementWithID("content_content11");
	    HTMLElement content12Elem = t610Response.getElementWithID("content_content12");
	    HTMLElement content13Elem = t610Response.getElementWithID("content_content13");
	    HTMLElement content14Elem = t610Response.getElementWithID("content_content14");
	    HTMLElement content15Elem = t610Response.getElementWithID("content_content15");
	    HTMLElement content2Elem = t610Response.getElementWithID("content_content2");
	    HTMLElement content3Elem = t610Response.getElementWithID("content_content3");
	    HTMLElement ACEElem = t610Response.getElementWithID("content_4045");
	    
	    assertNotNull(content1Elem);
	    assertNotNull(content10Elem);
	    assertNotNull(content11Elem);
	    assertNotNull(content12Elem);
	    assertNotNull(content13Elem);
	    assertNotNull(content14Elem);
	    assertNotNull(content15Elem);
	    assertNotNull(content2Elem);
	    assertNotNull(content3Elem);
	    assertNotNull(ACEElem);
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


	public void testHierarchialSelectFacets() throws IOException, SAXException {
	    WebResponse response = client.getResponse("http://localhost/wxhtml/overview1?lc=sv&cc=SE");
	    getPhoneLinks(response);
	    getBrandLinks(response);
	    getFileLinks(response);
	    
	    assertNotNull(t610Link);
	    assertNotNull(p900Link);
	    assertNotNull(s700Link);
	    assertNotNull(wtaLink);
	    assertNotNull(sonyericssonLink);
	    assertNotNull(ringtonesLink);
	    assertNotNull(themesLink);
	    assertNotNull(gamesLink);
	    assertNull(hipHopLink);
	    assertNull(rockAndRollLink);
	    
	    HTMLElement itemCountElement = response.getElementWithID("headertext");
	    assertNull(itemCountElement);
	    
	    WebResponse ringTonesResponse = timeClick(ringtonesLink);
	    getPhoneLinks(ringTonesResponse);
	    getBrandLinks(ringTonesResponse);
	    getFileLinks(ringTonesResponse);
	    assertNotNull(t610Link);
	    assertNull(p900Link);
	    assertNotNull(s700Link);
	    assertNull(wtaLink);
	    assertNotNull(sonyericssonLink);
	    assertNull(ringtonesLink);
	    assertNull(themesLink);
	    assertNull(gamesLink);
	    assertNotNull(hipHopLink);
	    assertNotNull(rockAndRollLink);
	    
	    itemCountElement = ringTonesResponse.getElementWithID("headertext");
	    assertNotNull(itemCountElement);
	    assertEquals("1 - 10 of 30", itemCountElement.getText());
	    
	    WebResponse rockAndRollResponse = timeClick(rockAndRollLink);
	    getPhoneLinks(rockAndRollResponse);
	    getBrandLinks(rockAndRollResponse);
	    getFileLinks(rockAndRollResponse);
	    assertNotNull(t610Link);
	    assertNull(p900Link);
	    assertNull(s700Link);
	    assertNull(wtaLink);
	    assertNull(sonyericssonLink);
	    assertNotNull(ringtonesLink);
	    assertNull(themesLink);
	    assertNull(gamesLink);
	    assertNull(hipHopLink);
	    assertNull(rockAndRollLink);
	    
	    itemCountElement = rockAndRollResponse.getElementWithID("headertext");
	    assertNotNull(itemCountElement);
	    assertEquals("1 - 9 of 9", itemCountElement.getText());
	}
	public void testPerformance() throws IOException, SAXException {
	    WebResponse response = client.getResponse("http://localhost/wxhtml/overview1?lc=sv&cc=SE");
	    
	    writeResponseToFile(response, "c:\\temp\\testPerformance.html");
	    WebLink linkRT = response.getLinkWith("ringtones");
	    assertNotNull(linkRT );
	    
	    WebResponse ringTonesResponse = timeClick(linkRT);
	    getFileLinks(ringTonesResponse);
	    
	    printLinks( ringTonesResponse.getLinks() );
	    WebLink pop = ringTonesResponse.getLinkWith("samba");
	    WebResponse sambaResponse = timeClick(pop);
	    getFileLinks(sambaResponse);
	    
	}

	
	public void testViewDetails() throws Exception {
		//This should return content 1-15, but only the first 10 will be displayed. 
		WebResponse response = client.getResponse("http://localhost/wxhtml/download1/download1_1?cc=SE&lc=sv&products=T610" );
		
		//System.out.println( response.getText() );
		//http://localhost/wxhtml/download1/downloadDetails?contentId=3037&products=T610&cc=SE&lc=sv&page=1&
		WebLink detailLinkContent1 = response.getLinkWithID("viewDetailsLinkcontent15");
		WebResponse detailResponse = detailLinkContent1.click();
		
		writeResponseToFile(detailResponse, "c:\\temp\\testViewDetails.html");
		//System.out.println( detailResponse.getText() );
		
		
		HTMLElement contentItemIcon = detailResponse.getElementWithID("contentItemIcon");
		HTMLElement contentItemLargeThumbnail = detailResponse.getElementWithID("contentItemLargeThumbnail");
		HTMLElement caption = detailResponse.getElementWithID("caption");
		HTMLElement category = detailResponse.getElementWithID("category");
		HTMLElement price = detailResponse.getElementWithID("price");
		HTMLElement contentItemSize = detailResponse.getElementWithID("contentItemSize");
		WebLink previewLink = detailResponse.getLinkWithID("previewLink");
		WebLink phoneBuyLink = detailResponse.getLinkWithID("phoneBuyLink");
		HTMLElement phoneIcon = detailResponse.getElementWithID("phoneIcon");
		HTMLElement phoneName = detailResponse.getElementWithID("phoneName");
		WebLink[] allLinks = detailResponse.getLinks();
		Map alsoAvailLinks = new HashMap();
		for (int i = 0; i < allLinks.length; i++) {
			String linkIdText = allLinks[i].getAttribute("id");
			if (linkIdText.startsWith("alsoAvailableFor")) {
				alsoAvailLinks.put(linkIdText, allLinks[i]);
			}
		}
		//TODO category, compatable devices
		assertNotNull(contentItemIcon);
		assertNotNull(contentItemLargeThumbnail.getText());
		assertNotNull(caption);
		assertNotNull(category);
		assertNotNull(price);
		assertNotNull(contentItemSize);
		assertNotNull(previewLink);
		assertNotNull(phoneIcon);
		assertNotNull(phoneName);
		assertNotNull(phoneBuyLink);
		
		assertEquals("http://localhost/origin/images/downloads/ringtone_icon.gif", contentItemIcon.getAttribute("src")); //TODO
		assertEquals("http://localhost/origin/images/thumbnails/largeThumbNailcontent15", contentItemLargeThumbnail.getAttribute("src"));
		assertEquals("content15", caption.getText());
		assertEquals("SEK30.00", price.getText());
		assertEquals("12 B", contentItemSize.getText());
		assertEquals("/wxhtml/download1/purchasephone?cc=SE&contentId=content15&lc=sv&page=0&products=T610", phoneBuyLink.getURLString());
		assertEquals("T610", phoneName.getText());
		WebLink backLink = detailResponse.getLinkWithID("backToLink");
		assertNotNull(backLink);
		assertEquals("ringtone", category.getText());
		assertEquals("javascript:openCenter(\"/wxhtml/view/ringtone?cc=SE&contentId=content15&lc=sv&page=0&products=T610\")", previewLink.getURLString());
		assertEquals("/wxhtml/download1/download1_1?cc=SE&contentId=content15&lc=sv&page=0&products=T610", backLink.getURLString());
		assertEquals("http://localhost/origin/images/phoneIcons/T610_production", phoneIcon.getAttribute("src"));
	}
	
	
	/**
	 * Asserts the content details for a content with specified id. 
	 * 
	 * 
	 * @param contentItemID The contents value in element id in file contentmodel_sv_SE.xml ( <content id="content40")..
	 * @param response
	 * @param isPremium If the content item is a premium content or not.
	 * @param The icon URL that should describe this content
	 * @throws SAXException
	 */
	private void assertContentListingDetails( String contentItemID, WebResponse response, boolean isPremium, String iconURL ) throws SAXException {
		System.out.println( "asserting contentitem " + contentItemID);
		String thumbNailID = "thumbnailURL"+ contentItemID;
		
		//thumbnail
		HTMLElement thumbnailElement = response.getElementWithID(thumbNailID);
		if ( thumbnailElement == null ) {
			fail( "No thumbnail element with id " + thumbNailID + " was found!" );
		}
		assertEquals("http://localhost/origin/images/thumbnails/smallThumbNail"+contentItemID, thumbnailElement.getAttribute("src"));

		//caption
		HTMLElement captionElement = response.getElementWithID("caption"+contentItemID);
		assertNotNull(captionElement);
		assertEquals(contentItemID, captionElement.getText());
		
		//verify that a price is being presented if the content is a premium.
		String priceTextElementID = "priceText"+contentItemID;
		HTMLElement priceTextElement = response.getElementWithID(priceTextElementID);
		if ( isPremium ) {
			if ( priceTextElement == null ) {
				fail( "No pricetext element with id " + priceTextElementID + " was found!" );
			}
			assertTrue( "SEK", priceTextElement.getText().startsWith("SEK"));
			assertTrue( priceTextElement.getText().length() > 3); //There should be something after "SEK"
		}else {
			//No price should be presented when content is not premium.
			assertNull( priceTextElement);
		}
		
		//icon
		String iconID = "icon"+contentItemID;
		HTMLElement iconElement = response.getElementWithID(iconID);
		if ( iconElement == null ) {
			fail( "No icon element with id " + iconID + " was found!" );
		}
		System.out.println( iconElement.getAttribute("src"));
		assertEquals("http://localhost"+iconURL, iconElement.getAttribute("src"));
		
	}
	
			
	/**
	 * @param allLinks
	 */
	private void printLinks(WebLink[] allLinks) {
		for (int i = 0; i < allLinks.length; i++) {
			System.out.println(allLinks[i].getText());
		}
	}

	public void testShowAllBrands() throws IOException, SAXException {
	    WebResponse response = client.getResponse("http://localhost/wxhtml/download1/download1_1?lc=sv&cc=SE&brands=sonyericsson");
	    getShowLinks(response);
	    assertNull(showAllContentLink);
	    assertNull(showAllPhonesLink);
	    assertNotNull(showAllBrandsLink);
	    assertEquals("/wxhtml/download1/download1_1?cc=SE&lc=sv", showAllBrandsLink.getURLString());
	    assertNotNull(response.getElementWithID("fcaption.sonyericsson"));
	    assertNull(response.getElementWithID("fcaption.Show all phones"));
	    
	}

	public void testShowAllContent() throws IOException, SAXException {
	    WebResponse response = client.getResponse("http://localhost/wxhtml/download1/download1_1?lc=sv&cc=SE&files=4711&brands=sonyericsson");
	    getShowLinks(response);
	    assertNotNull(showAllContentLink);
	    assertNull(showAllPhonesLink);
	    assertNotNull(showAllBrandsLink);
	    assertNotNull(showResetLink);
	    assertEquals("/wxhtml/download1/download1_1?brands=sonyericsson&cc=SE&lc=sv", showAllContentLink.getURLString());
	    assertNotNull(response.getElementWithID("fcaption.ringtones"));
	    assertNull(response.getElementWithID("fcaption.Show all phones"));
	}

	public void testShowAllPhones() throws IOException, SAXException {
	    WebResponse response = client.getResponse("http://localhost/wxhtml/download1/download1_1?lc=sv&cc=SE&products=S700i");
	    getShowLinks(response);
	    assertNull(showAllContentLink);
	    assertNull(showAllBrandsLink);
	    assertNotNull(showAllPhonesLink);
	    assertNotNull(showResetLink);
	}

	public void testReset() throws IOException, SAXException {
	    WebResponse response = client.getResponse("http://localhost/wxhtml/download1/download1_1?lc=sv&cc=SE?lc=sv&cc=SE&files=4711&brands=sonyericsson");
	    getShowLinks(response);
	    assertNotNull(showAllContentLink);
	    assertNotNull(showAllBrandsLink);
	    assertNull(showAllPhonesLink);
	    assertNotNull(showResetLink);
	    
	    assertEquals("/wxhtml/download1/download1_1?cc=SE&lc=sv", showResetLink.getURLString());
	}
	
	public void testNext() throws IOException, SAXException {
	    WebResponse response = client.getResponse("http://localhost/wxhtml/download1/download1_1?lc=sv&cc=SE?lc=sv&cc=SE&products=T610");
	    getPagingLinks(response);
	    assertNotNull(nextLink);
	    assertNull(previousLink);
	    assertEquals("/wxhtml/download1/download1_1?cc=SE&itemsPerPage=10&lc=sv&page=1&products=T610", nextLink.getURLString());
	}
	
	public void testPrevious() throws IOException, SAXException {
	    WebResponse response = client.getResponse("http://localhost/wxhtml/download1/download1_1?lc=sv&cc=SE?lc=sv&cc=SE&products=T610&page=1");
	    getPagingLinks(response);
	    assertNull(nextLink);
	    assertNotNull(previousLink);
	    assertEquals("/wxhtml/download1/download1_1?cc=SE&itemsPerPage=10&lc=sv&page=0&products=T610", previousLink.getURLString());
	}
	public void testContentListing() throws Exception {
		//This should return content 1-15, but only the first 10 will be displayed. 
		WebResponse response = client.getResponse("http://localhost/wxhtml/download1/download1_1?cc=SE&lc=sv&products=S700i&files=4712" );
		
		writeResponseToFile( response, "c:\\temp\\testContentListing.html");
		
		String soundsIconURL = "/origin/images/downloads/ringtone_icon.gif";
		
		assertContentListingDetails( "content11", response, false, soundsIconURL);
		
		HTMLElement phoneIconElement = response.getElementWithID("phoneIconcontent11");
		assertEquals("http://localhost/origin/images/static/downloads/multiplephones.gif", phoneIconElement.getAttribute("src"));
	}
	
}


