
package com.sonyericsson.fun.http.user;

import java.io.FileInputStream;
import java.io.InputStream;

import com.meterware.httpunit.HTMLElement;
import com.meterware.httpunit.WebLink;
import com.meterware.httpunit.WebResponse;
import com.meterware.servletunit.ServletRunner;
import com.meterware.servletunit.ServletUnitClient;
import com.sonyericsson.fun.http.common.HttpUnitTestCase;

public class HttpTestSignupLinkXhtml extends HttpUnitTestCase {

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
	}
	
	public void testChineseSignupLink() throws Exception {
		response = get$CharacterAssertedResponse(client, "http://localhost/wxhtml/overview1?cc=CN&lc=zh");
		assertDollarSignsInResponse(response);
		WebLink signupLink = response.getLinkWithID("signUpLink");
		assertNotNull(signupLink);
		assertEquals("http://www.google.com", signupLink.getURLString());
	}
	
	public void testChineseSignupLinkLoggedIn() throws Exception {
		response = loginAndGetResponse(client, "mareunuv", "joltcola", "http://localhost/wxhtml/overview1?cc=CN&lc=zh");
		assertDollarSignsInResponse(response);
		WebLink signupLink = response.getLinkWithID("signUpLink");
		assertNull(signupLink);
		WebLink editProfile = response.getLinkWithID("editProfile");
		assertEquals("http://www.slashdot.org", editProfile.getURLString());
	
	}
	
	public void testNonChineseSignupLink() throws Exception {
		response = get$CharacterAssertedResponse(client, "http://localhost/wxhtml/overview1?cc=GB&lc=en");
		assertDollarSignsInResponse(response);
		WebLink signupLink = response.getLinkWithID("signUpLink");
		HTMLElement phoneIcon = response.getElementWithID("loginPhoneIcon");
		assertNull(phoneIcon);
		
		assertNotNull(signupLink);
		assertEquals("/wxhtml/registerfullmember?cc=GB&lc=en", signupLink.getURLString());
		
	}
	
	public void testNonChineseSignupLinkLoggedIn() throws Exception {
		response = loginAndGetResponse(client, "mareunuv", "joltcola", "http://localhost/wxhtml/overview1?cc=GB&lc=en");
		assertDollarSignsInResponse(response);
		WebLink signupLink = response.getLinkWithID("signUpLink");
		HTMLElement phoneIcon = response.getElementWithID("loginPhoneIcon");
		this.writeResponseToFile(response, "c:/logs/login.html");
		
		assertNull(signupLink);
		assertEquals("http://localhost/origin/images/phoneIcons/F500i_production.jpg", phoneIcon.getAttribute("src"));
		
		//This fails because the link contains a session id
		WebLink editProfile = response.getLinkWithID("editProfile");
		assertEquals("/wxhtml/managedata?cc=GB&lc=en", editProfile.getURLString());
	}
	
	public void testPhoneIconLoggedIn() throws Exception {
		response = loginAndGetResponse(client, "mareunuv", "joltcola", "http://localhost/wxhtml/overview1?cc=GB&lc=en");
		assertDollarSignsInResponse(response);
	
		HTMLElement phoneIcon = response.getElementWithID("loginPhoneIcon");
		assertEquals("http://localhost/origin/images/phoneIcons/F500i_production.jpg", phoneIcon.getAttribute("src"));
		this.writeResponseToFile(response, "c:/temp/login.html");
		WebResponse afterMovingToOtherPage = client.getResponse("http://localhost/wxhtml/software1?cc=GB&lc=en");
		
		HTMLElement phoneIcon2 = afterMovingToOtherPage.getElementWithID("loginPhoneIcon");
		this.writeResponseToFile(afterMovingToOtherPage, "c:/temp/software.html");
		assertEquals("http://localhost/origin/images/phoneIcons/F500i_production.jpg", phoneIcon2.getAttribute("src"));
	}
	public void testChineseTopNavSignupLink() throws Exception {
		response = get$CharacterAssertedResponse(client, "http://localhost/wxhtml/overview1?cc=CN&lc=zh");
		assertDollarSignsInResponse(response);
		this.writeResponseToFile(response, "c:/temp/chinese_singuplink.html");
		
		WebLink signupLink = response.getLinkWithID("MN_Link_signup");
		assertNotNull(signupLink);
		assertEquals("http://www.google.com", signupLink.getURLString());
	}
	
	public void testNonTopNavChineseSignupLink() throws Exception {
		response = get$CharacterAssertedResponse(client, "http://localhost/wxhtml/overview1?cc=GB&lc=en");
		assertDollarSignsInResponse(response);
		WebLink signupLink = response.getLinkWithID("MN_Link_signup");
		
		assertNotNull(signupLink);
		assertEquals("/wxhtml/registerfullmember?cc=GB&lc=en", signupLink.getURLString());
		
	}
}
