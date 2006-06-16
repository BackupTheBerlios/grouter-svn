/*
 * Created on 2005-jul-11
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.sonyericsson.fun.http.user;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;

import org.apache.commons.lang.StringUtils;
import org.xml.sax.SAXException;

import com.meterware.httpunit.WebForm;
import com.meterware.httpunit.WebLink;
import com.meterware.httpunit.WebResponse;
import com.meterware.servletunit.ServletRunner;
import com.meterware.servletunit.ServletUnitClient;
import com.sonyericsson.fun.http.common.HttpUnitTestCase;

/**
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class HttpTestMyContent extends HttpUnitTestCase {
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
	    client.setHeaderField("accept-language", "en");
	    
	      
	/*	
	   this.deleteAllContentItems("http://localhost/wxhtml/mycontent/mycontent/myimages?cc=CA&lc=en&action=login&username=mareunuv&password=joltcola");
	    this.deleteAllContentItems("http://localhost/wxhtml/mycontent/mythemes/mythemes?cc=CA&lc=en?cc=CA&lc=en");
	   this.deleteAllContentItems("http://localhost/wxhtml/mycontent/mycontent/myvideos?cc=CA&lc=en");
		*/       
	}

	public void testLogin() throws MalformedURLException, IOException, SAXException{
	
		response = client.getResponse("http://localhost/wxhtml/software1?action=login&cc=GB&lc=en&username=mareunuv&password=joltcola");
		assertTrue(StringUtils.contains(response.getText() ,"mareunuv"));
		
	}

	public void testCopyImageToMycontentLoggedin() throws MalformedURLException, IOException, SAXException{
		
		response = client.getResponse("http://localhost/wxhtml/download1/download1_1?cc=GB&files=582&lc=en&action=login&username=mareunuv&password=joltcola");
		assertTrue(StringUtils.contains(response.getText() ,"mareunuv"));
		WebResponse response2 = client.getResponse("http://localhost/wxhtml/mycontent/myimages/copyimageList?cc=CA&itemId=5762&lc=en&publicItem=true");
		assertTrue(StringUtils.contains(response2.getText() ,"mareunuv"));
		assertTrue(StringUtils.contains(response2.getText() ,"TunnelVision"));
	}
	
	public void testCopyThemeToMycontentLoggedin() throws MalformedURLException, IOException, SAXException{
		
		response = client.getResponse("http://localhost/wxhtml/download1/download1_1?cc=GB&files=582&lc=en&action=login&username=mareunuv&password=joltcola");
		assertTrue(StringUtils.contains(response.getText() ,"mareunuv"));
		WebResponse response2 = client.getResponse("http://localhost/wxhtml/mycontent/mythemes/mycontent.copytheme?cc=CA&itemId=6024&lc=en&publicItem=true");
		assertTrue(StringUtils.contains(response2.getText() ,"mareunuv"));
		assertTrue(StringUtils.contains(response2.getText() ,"Lines"));
			
	}	
	public void testCopyVideoToMycontentLoggedin() throws MalformedURLException, IOException, SAXException{
		
		response = client.getResponse("http://localhost/wxhtml/download1/download1_1?cc=GB&files=582&lc=en&action=login&username=mareunuv&password=joltcola");
		assertTrue(StringUtils.contains(response.getText() ,"mareunuv"));
		WebResponse response2 = client.getResponse("http://localhost/wxhtml/mycontent/myvideos/copyvideoList?cc=CA&itemId=6608&lc=en&publicItem=true");
		assertTrue(StringUtils.contains(response2.getText() ,"mareunuv"));
		assertTrue(StringUtils.contains(response2.getText() ,"Golf"));
	
	}	
	public void testCopyImageToMycontentNotLoggedin() throws MalformedURLException, IOException, SAXException{
		
		response = client.getResponse("http://localhost/wxhtml/download1/download1_1?cc=GB&files=582&lc=en");
		assertFalse(StringUtils.contains(response.getText() ,"mareunuv"));
		
		WebResponse copyToMyImages = client.getResponse("http://localhost/wxhtml/mycontent/myimages/copyimageList?cc=CA&itemId=5762&lc=en&publicItem=true");
		assertFalse(StringUtils.contains(copyToMyImages.getText() ,"mareunuv"));
		writeResponseToFile(copyToMyImages, "c:\\temp\\copyImages.html");
		
		assertTrue(StringUtils.contains(copyToMyImages.getText() ,"Please log in"));
		WebForm formWithName = copyToMyImages.getFormWithID("formLogin");
		String action = formWithName.getAction();
		assertEquals("/wxhtml/mycontent/myimages/copyimageList?action=login&cc=CA&itemId=5762&lc=en&publicItem=true",action);
		WebResponse loggedInToMyimages = client.getResponse("http://localhost/wxhtml/mycontent/myimages/copyimageList?action=login&cc=CA&itemId=5762&lc=en&publicItem=true&username=mareunuv&password=joltcola");	
		//login
		
		//check that the image is in my images
		assertTrue(StringUtils.contains(loggedInToMyimages.getText() ,"mareunuv"));
		assertTrue(StringUtils.contains(loggedInToMyimages.getText() ,"TunnelVision"));
		
	
	}

	public void testCopyVideoToMycontentNotLoggedin() throws MalformedURLException, IOException, SAXException{
		
		response = client.getResponse("http://localhost/wxhtml/download1/download1_1?cc=GB&files=582&lc=en");
		assertFalse(StringUtils.contains(response.getText() ,"mareunuv"));
		WebResponse copyToMyVideo = client.getResponse("http://localhost/wxhtml/mycontent/myvideos/copyvideoList?cc=CA&itemId=6608&lc=en&publicItem=true");
		assertFalse(StringUtils.contains(copyToMyVideo.getText() ,"mareunuv"));
		
		writeResponseToFile(copyToMyVideo, "c:\\temp\\copyVideo.html");
		assertTrue(StringUtils.contains(copyToMyVideo.getText() ,"Please log in"));
		WebForm formWithName = copyToMyVideo.getFormWithID("formLogin");
		String action = formWithName.getAction();
		assertEquals("/wxhtml/mycontent/myvideos/copyvideoList?action=login&cc=CA&itemId=6608&lc=en&publicItem=true",action);
		
		WebResponse loggedInToMyVideo = client.getResponse("http://localhost/wxhtml/mycontent/myvideos/copyvideoList?action=login&cc=CA&itemId=6608&lc=en&publicItem=true&username=mareunuv&password=joltcola");	
		writeResponseToFile( loggedInToMyVideo, "c:\\temp\\MyCopyVideo.html");
		
		assertFalse(StringUtils.contains(loggedInToMyVideo.getText() ,"Please log in"));
		assertTrue(StringUtils.contains(loggedInToMyVideo.getText() ,"Golf"));
		assertTrue(StringUtils.contains(loggedInToMyVideo.getText() ,"mareunuv"));
		
	}

	public void testCopyThemeToMycontentNotLoggedin() throws MalformedURLException, IOException, SAXException{
		
		response = client.getResponse("http://localhost/wxhtml/download1/download1_1?cc=GB&files=582&lc=en");
		assertFalse(StringUtils.contains(response.getText() ,"mareunuv"));
		WebResponse copyToMyThemes = client.getResponse("http://localhost/wxhtml/mycontent/mythemes/mycontent.copytheme?cc=CA&itemId=6024&lc=en&publicItem=true");
		assertFalse(StringUtils.contains(copyToMyThemes.getText() ,"mareunuv"));
		
		writeResponseToFile(copyToMyThemes, "c:\\temp\\copyThemes.html");
		assertTrue(StringUtils.contains(copyToMyThemes.getText() ,"Please log in"));
		WebForm formWithName = copyToMyThemes.getFormWithID("formLogin");
		String action = formWithName.getAction();
		assertEquals("/wxhtml/mycontent/mythemes/mycontent.copytheme?action=login&cc=CA&itemId=6024&lc=en&publicItem=true",action);
		
		WebResponse loggedInToMyThemes = client.getResponse("http://localhost/wxhtml/mycontent/mythemes/mycontent.copytheme?action=login&cc=CA&itemId=6024&lc=en&publicItem=true&username=mareunuv&password=joltcola");	
		writeResponseToFile( loggedInToMyThemes, "c:\\temp\\MyCopyThemes.html");
		
		assertFalse(StringUtils.contains(loggedInToMyThemes.getText() ,"Please log in"));
		assertTrue(StringUtils.contains(loggedInToMyThemes.getText() ,"Lines"));
		assertTrue(StringUtils.contains(loggedInToMyThemes.getText() ,"mareunuv"));
		
	}
	
	
	protected void tearDown() throws Exception {
		super.tearDown();
		
	/* Not deletion not working currently	
		this.deleteAllContentItems("http://localhost/wxhtml/mycontent/mycontent/myimages?cc=CA&lc=en&action=login&username=mareunuv&password=joltcola");
	   this.deleteAllContentItems("http://localhost/wxhtml/mycontent/mythemes/mythemes?cc=CA&lc=en?cc=CA&lc=en&action=login&username=mareunuv&password=joltcola");
	   this.deleteAllContentItems("http://localhost/wxhtml/mycontent/mycontent/myvideos?cc=CA&lc=en&action=login&username=mareunuv&password=joltcola");
	*/
	}
	
	
	private void deleteAllContentItems(String url) throws MalformedURLException, IOException, SAXException {
		//login
		WebResponse response0 = client.getResponse(url);
		System.out.println("goto mycontent and login "+ response0.getText());
		
		
		//check that we got logged in
		assertTrue(StringUtils.contains(response0.getText() ,"mareunuv"));
		WebLink link = response0.getLinkWithName("delete");
		while(link!=null)
		{
			WebResponse clickReply = link.click();
			link = clickReply.getLinkWithName("delete");
		}
	}
	
}
