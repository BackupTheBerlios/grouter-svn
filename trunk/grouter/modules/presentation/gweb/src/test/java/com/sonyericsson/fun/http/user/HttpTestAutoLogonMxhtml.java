package com.sonyericsson.fun.http.user;

import java.io.IOException;
import java.net.MalformedURLException;

import org.xml.sax.SAXException;

import com.meterware.httpunit.WebLink;
import com.meterware.httpunit.WebResponse;
import com.meterware.servletunit.ServletUnitClient;
import com.sonyericsson.fun.http.common.HttpUnitTestCase;

public class HttpTestAutoLogonMxhtml extends HttpUnitTestCase {
	private ServletUnitClient client;

	private WebResponse response;

	private final String username = "torben.hc@cybercomgroup.com";
	private final String password = "dominos";

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

		response = client.getResponse("http://localhost/mxhtml/portallogon?cc=GB&lc=en");
//		response = get$CharacterAssertedResponse(client, "http://localhost/mxhtml/logon?cc=GB&lc=en"); 
		writeResponseToFile(response, System.getProperty("java.io.tmpdir")+"/httpUnitTest/testMxhtmlAutoLogonWithCookie.html");

		WebLink manageDataLink = response.getLinkWith("Manage account");
		assertNotNull("Check if loggedIn", manageDataLink);
	}

	/**
	 */
	public void testAutoLoginNoCookie() throws MalformedURLException, IOException, SAXException {
		//client.putCookie("AUTOLOGON-COOKIE",username+":"+password);

		response = client.getResponse("http://localhost/mxhtml/logon?cc=GB&lc=en");
//		response = get$CharacterAssertedResponse(client, "http://localhost/mxhtml/mycontent?cc=GB&lc=en"); 
		writeResponseToFile(response, System.getProperty("java.io.tmpdir")+"/httpUnitTest/testMxhtmlAutoLogonNoCookie.html");

		WebLink manageDataLink = response.getLinkWith("Login");
		assertNotNull("Check if loggedIn", manageDataLink);
	}

	/**
	 */
	public void testLogoutRemoveCookie() throws MalformedURLException, IOException, SAXException {
		client.putCookie("AUTOLOGON-COOKIE",username+":"+password);

		response = client.getResponse("http://localhost/mxhtml/logon?cc=GB&lc=en");
//		response = get$CharacterAssertedResponse(client, "http://localhost/mxhtml/mycontent?cc=GB&lc=en"); 
		writeResponseToFile(response, System.getProperty("java.io.tmpdir")+"/httpUnitTest/testMxhtmlLogoutRemoveCookie.html");

		WebLink manageDataLink = response.getLinkWith("Manage account");
		assertNotNull("Check if loggedIn", manageDataLink);

		response = client.getResponse("http://localhost/mxhtml/home?action=logout&cc=GB&lc=en");
//		response = get$CharacterAssertedResponse(client, "http://localhost/mxhtml/home?action=logout&cc=GB&lc=en"); 
		response = client.getResponse("http://localhost/mxhtml/mycontent?cc=GB&lc=en");
//		response = get$CharacterAssertedResponse(client, "http://localhost/mxhtml/mycontent?cc=GB&lc=en"); 
		writeResponseToFile(response, System.getProperty("java.io.tmpdir")+"/httpUnitTest/testMxhtmlLogoutRemoveCookie.html");
		manageDataLink = response.getLinkWith("Log in");
		
		assertNotNull("Check if not loggedIn", manageDataLink);
		
		response = client.getResponse("http://localhost/mxhtml/logon?cc=GB&lc=en");
//		response = get$CharacterAssertedResponse(client, "http://localhost/mxhtml/logon?cc=GB&lc=en"); 
		writeResponseToFile(response, System.getProperty("java.io.tmpdir")+"/httpUnitTest/testMxhtmlLogon.html");

		
	}
	
	
}
