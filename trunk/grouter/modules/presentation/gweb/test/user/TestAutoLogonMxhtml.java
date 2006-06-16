package user;

import java.io.IOException;
import java.net.MalformedURLException;

import org.apache.commons.lang.StringUtils;
import org.xml.sax.SAXException;

import com.meterware.httpunit.WebLink;
import com.meterware.httpunit.WebResponse;
import com.meterware.servletunit.ServletUnitClient;
import common.HttpUnitTestCase;

public class TestAutoLogonMxhtml extends HttpUnitTestCase {
	private ServletUnitClient client;

	private WebResponse response;

	private final String username = "mareunuv";
	private final String password = "joltcola";

	/**
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
		client = this.getClient("SonyEricssonS700i");
	}

	/**
	 */
	public void testAutoLogin() throws MalformedURLException, IOException, SAXException {
		client.putCookie("AUTOLOGON-COOKIE",username+":"+password);

		response = get$CharacterAssertedResponse(client, "http://localhost/mxhtml/logon?cc=GB&lc=en"); 
		writeResponseToFile(response, "c:\\tmp\\testMxhtmlAutoLogonWithCookie.html");

		WebLink manageDataLink = response.getLinkWith("Manage account");
		assertNotNull("Check if loggedIn", manageDataLink);
	}

	/**
	 */
	public void testAutoLoginNoCookie() throws MalformedURLException, IOException, SAXException {
		//client.putCookie("AUTOLOGON-COOKIE",username+":"+password);

		response = get$CharacterAssertedResponse(client, "http://localhost/mxhtml/mycontent?cc=GB&lc=en"); 
		writeResponseToFile(response, "c:\\tmp\\testMxhtmlAutoLogonNoCookie.html");

		WebLink manageDataLink = response.getLinkWith("Login");
		assertNotNull("Check if loggedIn", manageDataLink);
	}

	/**
	 */
	public void testLogoutRemoveCookie() throws MalformedURLException, IOException, SAXException {
		client.putCookie("AUTOLOGON-COOKIE",username+":"+password);

		response = get$CharacterAssertedResponse(client, "http://localhost/mxhtml/mycontent?cc=GB&lc=en"); 

		WebLink manageDataLink = response.getLinkWith("Manage account");
		assertNotNull("Check if loggedIn", manageDataLink);

		response = get$CharacterAssertedResponse(client, "http://localhost/mxhtml/home?action=logout&cc=GB&lc=en"); 
		response = get$CharacterAssertedResponse(client, "http://localhost/mxhtml/mycontent?cc=GB&lc=en"); 
		writeResponseToFile(response, "c:\\tmp\\testMxhtmlLogoutRemoveCookie.html");
		manageDataLink = response.getLinkWith("Login");
		
		assertNotNull("Check if not loggedIn", manageDataLink);
		
		response = get$CharacterAssertedResponse(client, "http://localhost/mxhtml/logon?cc=GB&lc=en"); 
		writeResponseToFile(response, "c:\\tmp\\testMxhtmlLogon.html");

		
	}
	
	
}
