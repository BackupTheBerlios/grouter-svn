
package user;

import java.io.FileInputStream;
import java.io.InputStream;

import org.apache.commons.lang.StringUtils;
import org.apache.xpath.XPathAPI;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.meterware.httpunit.WebLink;
import com.meterware.httpunit.WebResponse;
import com.meterware.servletunit.ServletRunner;
import com.meterware.servletunit.ServletUnitClient;
import com.sun.rsasign.an;

import common.HttpUnitTestCase;

public class TestSignupLinkWml extends HttpUnitTestCase {

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
	    client.getClientProperties().setUserAgent("SonyEricssonT610");       
	}
	
	public void testChineseSignupLinkNotLoggedIn() throws Exception {
		response = client.getResponse("http://localhost/mwml/home?cc=CN&dn=T618&lc=zh");
		assertDollarSignsInResponse(response);
		
		/*This can be tested more but will present difficulties since
		* XPath needs to be used to find a node and verify it's value and
		* the structure of <p> and <go> tags is not consistent before and
		* after login
		* 
		* Probably good enough for now
		*/
		assertFalse(StringUtils.contains(response.getText(), "/mwml/signup?cc=CN&dn=T610&lc=zh"));
	}
	
	public void testNormalSignupLink() throws Exception {
		response = client.getResponse("http://localhost/mwml/home?cc=GB&dn=T610&lc=en");
		assertDollarSignsInResponse(response);
		writeResponseToFile(response, "c:\\temp\\mwmlTestNormalSignupLink.html");
		
		assertTrue(StringUtils.contains(response.getText(), "Sign up"));
		
		Document dom = response.getDOM();
		String XPATH = "/wml/card/p[7]/anchor";
		NodeList anchors = XPathAPI.selectNodeList(dom, XPATH);
		assertNotNull(anchors);
		
		Node go = anchors.item(0).getChildNodes().item(1);			
		NamedNodeMap goAttributes = go.getAttributes();
		assertEquals("/mwml/signup?cc=GB&dn=T610&lc=en", goAttributes.item(0).getNodeValue());
		
	}
	
	public void testChineseSignupLinkLoggedIn() throws Exception {
		response = loginAndGetResponseWithoutValidation(client, "mareunuv", "joltcola", "http://localhost/mwml/logon?cc=CN&dn=T618&lc=zh&checklogin=1");
		
		/*This can be tested more but will present difficulties since
		* XPath needs to be used to find a node and verify it's value and
		* the structure of <p> and <go> tags is not consistent before and
		* after login
		* 
		* Probably good enough for now
		*/
		assertFalse(StringUtils.contains(response.getText(), "/mwml/signup?cc=CN&dn=T618&lc=zh"));
		//writeResponseToFile(response, "c:\\temp\\mwmlTestChineseSignupLinkLoggedIn.html");
	}
}
