package billing;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.lang.StringUtils;
import org.jmock.RecorderTestCase;
import org.xml.sax.SAXException;

import com.meterware.httpunit.HTMLElement;
import com.meterware.httpunit.WebForm;
import com.meterware.httpunit.WebLink;
import com.meterware.httpunit.WebResponse;
import com.meterware.servletunit.ServletRunner;
import com.meterware.servletunit.ServletUnitClient;
import common.HttpUnitTestCase;



/**
 * Copyright 2005 Sony Ericsson Mobile Communications AB. All rights reserved.
 * @author The Platform Upgrade Project
 * @since $Date: 2006/03/15 12:02:33 $
 * @version $Revision: 1.2.2.1 $
 * 
 */
public class TestSmsTo extends HttpUnitTestCase {
	
	public void testSmsEntryPoint() throws IOException, SAXException {
		InputStream webXml = new FileInputStream("web.xml");
		
		ServletRunner sr = new ServletRunner( webXml, "/httpunit" );       // (1) use the web.xml file to define mappings
	    ServletUnitClient client = sr.newClient();               // (2) create a client to invoke the application
	    client.getClientProperties().setUserAgent("SonyEricssonK500i");
	    
	    WebResponse response = client.getResponse("http://localhost/httpunit/mxhtml/download1_1_1_premium?contentId=10337&cc=GB&dn=K500i&itemsPerPage=10&lc=en&page=1");
	    this.writeResponseToFile(response, "C:/logs/smsEntry.html");
	    //String strResponse = response.getText();
	   
	    //System.out.println(strResponse);
	    WebLink purchaseLinkWapBilling = response.getLinkWithID("BuyNowWapBilling");
	    // /httpunit/mxhtml/purchase/purchasephone?ContId=10337&amp;cc=GB&amp;dn=K500i&amp;lc=en
	    
	    assertNotNull(purchaseLinkWapBilling);

	    assertTrue(StringUtils.contains(response.getText(),"3.00"));    
	    assertTrue(StringUtils.contains(response.getText(),"GBP"));
	    assertFalse(StringUtils.contains(response.getText(),"VAT"));
	    assertEquals("/httpunit/mxhtml/purchase/purchasewb?cc=GB&contentId=10337&dn=K500i&lc=en&page=0&products=K500i", purchaseLinkWapBilling.getURLString());
	    
	}
	

	public void testSmsToLink() throws IOException, SAXException {
		InputStream webXml = new FileInputStream("web.xml");
		
		ServletRunner sr = new ServletRunner( webXml, "/httpunit" );       // (1) use the web.xml file to define mappings
	    ServletUnitClient client = sr.newClient();               // (2) create a client to invoke the application
	    client.getClientProperties().setUserAgent("SonyEricssonK500i");
	    
	    WebResponse response = client.getResponse("http://localhost/httpunit/mxhtml/purchase/purchasephone?contentId=4124&Dn=K500i&cc=GB&lc=en");
	    String strResponse = response.getText();
	    System.out.println(strResponse);
	    this.writeResponseToFile(response, "c:\\Temp\\operators.html");
	    assertTrue(StringUtils.contains(response.getText(),"1.50"));  
	    
	    assertTrue(StringUtils.contains(response.getText(),"KB"));
	    assertFalse(StringUtils.contains(response.getText(),"VAT"));	    
	
	    WebForm operatorForm = response.getFormWithID("payform");
	    operatorForm.setParameter("operatorid", "292");
	    
	    WebResponse response4 = operatorForm.submit();
	    writeResponseToFile(response4, "C:\\Temp\\sms-to_page.html");
	    String postSubmittText= response4.getText();
	    
	    
	    WebLink purchaseLink = response4.getLinkWith("Create premium text message 1");
	    this.writeResponseToFile(response4, "C:/logs/smsToLink.html");
	    String url = purchaseLink.getURLString();
	    assertTrue(StringUtils.contains(response4.getText(),"1.50"));    
	    assertTrue(StringUtils.contains(response4.getText(),"KB"));
	    assertFalse(StringUtils.contains(response4.getText(),"VAT"));
	    
	    assertEquals("smsto:87050?body=MXFT410510", url);
	
	}
	
	
	
	
	public void testSmsToMultipleSmsLinks() throws IOException, SAXException {
		InputStream webXml = new FileInputStream("web.xml");
		
		ServletRunner sr = new ServletRunner( webXml, "/httpunit" );       // (1) use the web.xml file to define mappings
	    ServletUnitClient client = sr.newClient();               // (2) create a client to invoke the application
	    client.getClientProperties().setUserAgent("SonyEricssonK500i");
	    
	    WebResponse response = client.getResponse("http://localhost/httpunit/mxhtml/purchase/purchasephone?contentId=9593&Dn=K500i&cc=GB&lc=en");
	    String strResponse = response.getText();
	    
	    WebForm operatorForm = response.getFormWithID("payform");
	    operatorForm.setParameter("operatorid", "292");
	    WebResponse response3 = operatorForm.submit();
	    
	    String postSubmitText= response3.getText();
	    WebLink purchaseLink1 = response3.getLinkWithID("smsToLink1");
	    String smsToUrl1 = purchaseLink1.getURLString();
	    WebLink purchaseLink2 = response3.getLinkWithID("smsToLink2");
	    String smsToUrl2 = purchaseLink2.getURLString();
	    
	    assertEquals("smsto:82666?body=MXFT430455", smsToUrl1);
	    assertEquals("smsto:87050?body=MXFT430455", smsToUrl2);
	        	    
	}

	
}


