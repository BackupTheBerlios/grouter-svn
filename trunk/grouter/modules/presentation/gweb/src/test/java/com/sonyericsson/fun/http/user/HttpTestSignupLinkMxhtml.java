
package com.sonyericsson.fun.http.user;

import java.io.FileInputStream;
import java.io.InputStream;

import com.meterware.httpunit.WebLink;
import com.meterware.httpunit.WebResponse;
import com.meterware.servletunit.ServletRunner;
import com.meterware.servletunit.ServletUnitClient;
import com.sonyericsson.fun.http.common.HttpUnitTestCase;

public class HttpTestSignupLinkMxhtml extends HttpUnitTestCase {

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
	    client.getClientProperties().setUserAgent("SonyEricssonK700c");
	    client.setHeaderField("accept-language", "en");         
	}
	
	public void testChineseSignupLink() throws Exception {
		response = get$CharacterAssertedResponse(client, "http://localhost/mxhtml/home?cc=CN&dn=K700c&lc=zh");
		assertDollarSignsInResponse(response);
		WebLink signupLink = response.getLinkWithID("signUpLink");
		writeResponseToFile(response, "c:\\temp\\mxhtmlTestChineseSignupLink.html");
		//If adding back china fix in site structure change this to assert null
		assertNotNull(signupLink);
	}
	
	public void testChineseSignupLinkLoggedIn() throws Exception {
		response = loginAndGetResponseWithoutValidation(client, "mareunuv", "joltcola", "http://localhost/mxhtml/logon?cc=CN&dn=K700c&lc=zh&checklogin=1");
		assertDollarSignsInResponse(response);
		WebLink signupLink = response.getLinkWithID("signUpLink");
		assertNull(signupLink);
		writeResponseToFile(response, "c:\\temp\\mxhtmlTestChineseSignupLinkLoggedIn.html");
	}
	
	public void testNonChineseSignupLink() throws Exception {
		response = get$CharacterAssertedResponse(client, "http://localhost/mxhtml/home?cc=GB&dn=K700i&lc=en");
		assertDollarSignsInResponse(response);
		WebLink signupLink = response.getLinkWithID("signUpLink");
		assertNotNull(signupLink);
		assertEquals("/mxhtml/signup?cc=GB&dn=K700i&lc=en", signupLink.getURLString());
	}
	
	public void testNonChineseSignupLinkLoggedIn() throws Exception {
		response = loginAndGetResponseWithoutValidation(client, "mareunuv", "joltcola", "http://localhost/mxhtml/logon?cc=GB&dn=K700i&lc=en&checklogin=1");
		writeResponseToFile(response, "c:\\temp\\mxhtmlTestNonChineseSignupLinkLoggedIn.html");
		assertDollarSignsInResponse(response);
		WebLink signupLink = response.getLinkWithID("signUpLink");
		assertNull(signupLink);
	}
}
