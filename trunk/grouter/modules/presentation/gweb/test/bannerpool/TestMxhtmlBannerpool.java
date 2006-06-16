package bannerpool;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.lang.StringUtils;
import org.xml.sax.SAXException;

import com.meterware.httpunit.WebLink;
import com.meterware.httpunit.WebResponse;
import com.meterware.servletunit.ServletRunner;
import com.meterware.servletunit.ServletUnitClient;

import common.HttpUnitTestCase;


/**
 * @author christoffer.hellgren
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class TestMxhtmlBannerpool extends HttpUnitTestCase {
	public void testBannersInRingtonesGallery() throws IOException, SAXException {
		InputStream webXml = new FileInputStream("web.xml");
		
		ServletRunner sr = new ServletRunner( webXml, "/httpunit" );       // (1) use the web.xml file to define mappings
	    ServletUnitClient client = sr.newClient();               // (2) create a client to invoke the application
	    client.getClientProperties().setUserAgent("SonyEricssonK500i");
	    
	    WebResponse response = client.getResponse("http://localhost/httpunit/mxhtml/download1?cc=GB&dn=K500i&files=573&lc=en&products=K500i");
	    this.writeResponseToFile(response, "C:/temp/mxhtmlbannerpoolgamegallery.html");
	    WebLink externalBannerLink = response.getLinkWithID("bannerlink");
	    
	    
	    assertNotNull(externalBannerLink);

	    assertEquals("http://gamebanner1", externalBannerLink.getURLString());
	    
	}
	
	public void testBannersOnOverview() throws IOException, SAXException {
		InputStream webXml = new FileInputStream("web.xml");
		
		ServletRunner sr = new ServletRunner( webXml, "/httpunit" );       // (1) use the web.xml file to define mappings
	    ServletUnitClient client = sr.newClient();               // (2) create a client to invoke the application
	    client.getClientProperties().setUserAgent("SonyEricssonK500i");
	    
	    WebResponse response = client.getResponse("http://localhost/httpunit/mxhtml/home?cc=GB&dn=K500i&lc=en");
	    this.writeResponseToFile(response, "C:/temp/mxhtmlbannerpooloverview.html");
	    WebLink externalBannerLink = response.getLinkWithID("bannerlink");
	    
	    
	    assertNotNull(externalBannerLink);

	    assertEquals("http://gamebanner1", externalBannerLink.getURLString());
	    
	}
}
