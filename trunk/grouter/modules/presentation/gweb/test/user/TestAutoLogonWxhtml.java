package user;

import java.io.IOException;
import java.net.MalformedURLException;

import org.apache.commons.lang.StringUtils;
import org.xml.sax.SAXException;

import com.meterware.httpunit.WebLink;
import com.meterware.httpunit.WebResponse;
import com.meterware.servletunit.ServletUnitClient;
import common.HttpUnitTestCase;

public class TestAutoLogonWxhtml extends HttpUnitTestCase {
	private ServletUnitClient client;

	private WebResponse response;

	private final String username = "mareunuv";
	private final String password = "joltcola";

	/**
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
		client = this.getClient("Mozilla");
	}

	/**
	 */
	public void testAutoLogin() throws MalformedURLException, IOException, SAXException {
		client.putCookie("AUTOLOGON-COOKIE",username+":"+password);

		response = get$CharacterAssertedResponse(client, "http://localhost/wxhtml/overview1?cc=SE&lc=sv"); 
		writeResponseToFile(response, "c:\\tmp\\testAutoLogonWithCookie.html");

		WebLink manageDataLink = response.getLinkWithID("editProfile");
		assertNotNull("Check if loggedIn", manageDataLink);
	}

	/**
	 */
	public void testAutoLoginNoCookie() throws MalformedURLException, IOException, SAXException {
		//client.putCookie("AUTOLOGON-COOKIE",username+":"+password);

		response = get$CharacterAssertedResponse(client, "http://localhost/wxhtml/mycontent?cc=SE&lc=sv"); 
		writeResponseToFile(response, "c:\\tmp\\testAutoLogonNoCookie.html");

		WebLink signUpLink = response.getLinkWithID("signUpLink");
		assertNotNull("Check if loggedIn", signUpLink);
	}

	/**
	 */
	public void testLogoutRemoveCookie() throws MalformedURLException, IOException, SAXException {
		client.putCookie("AUTOLOGON-COOKIE",username+":"+password);

		response = get$CharacterAssertedResponse(client, "http://localhost/wxhtml/mycontent?cc=SE&lc=sv"); 

		WebLink manageDataLink = response.getLinkWithID("editProfile");
		assertNotNull("Check if loggedIn", manageDataLink);

		response = get$CharacterAssertedResponse(client, "http://localhost/wxhtml/mycontent?action=logout&cc=SE&lc=sv"); 
		response = get$CharacterAssertedResponse(client, "http://localhost/wxhtml/mycontent?cc=SE&lc=sv"); 
		writeResponseToFile(response, "c:\\tmp\\testLogoutRemoveCookie.html");

		WebLink signUpLink = response.getLinkWithID("signUpLink");
		assertNotNull("Check if loggedIn", signUpLink);
		
		
	}
	
	
}
