package com.sonyericsson.fun.http.user;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;

import javax.xml.transform.TransformerException;

import org.apache.xpath.XPathAPI;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.meterware.httpunit.WebRequest;
import com.meterware.httpunit.WebResponse;
import com.meterware.servletunit.ServletUnitClient;
import com.sonyericsson.fun.http.common.HttpUnitTestCase;

public class HttpTestAutoLogonMwml extends HttpUnitTestCase {

	private final String username = "torben.hc@cybercomgroup.com";
	private final String password = "dominos";

	private ServletUnitClient client;
	private WebResponse response;

	/**
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
	    client = getClient("SonyEricssonT610");
	}

	/**
	 */
	public void testAutoLogin() throws MalformedURLException, IOException, SAXException, TransformerException {
		client.putCookie("AUTOLOGON-COOKIE",username+":"+password);
		response = client.getResponse("http://localhost/mwml/logon?cc=GB&dn=T610&lc=en"); 
		Document dom = response.getDOM();
		
		writeResponseToFile(response, System.getProperty("java.io.tmpdir")+"/httpUnitTest/testMwmlAutoLogonWithCookie.html");
		
		String XPATH = "/wml/card/p[3]/anchor";
		NodeList anchors = XPathAPI.selectNodeList(dom, XPATH);

		Map map = new HashMap();
		for(int i=0; i<anchors.getLength(); i++){
			map.put(anchors.item(i).getChildNodes().item(0).getNodeValue(), anchors.item(i).getChildNodes());
		}
							
		assertNotNull(map.get("My content"));
		
	}

	/**
	 */
	public void testAutoLoginNoCookie() throws MalformedURLException, IOException, SAXException, TransformerException {
		response = client.getResponse("http://localhost/mwml/home?cc=GB&dn=T610&lc=en"); 
		writeResponseToFile(response, System.getProperty("java.io.tmpdir")+"/httpUnitTest/testMwmlAutoLogonNoCookie.html");
		Document dom = response.getDOM();

		String XPATH = "/wml/card/p[8]/anchor";
		NodeList anchors = XPathAPI.selectNodeList(dom, XPATH);

		Map map = new HashMap();
		for(int i=0; i<anchors.getLength(); i++){
			map.put(anchors.item(i).getChildNodes().item(0).getNodeValue(), anchors.item(i).getChildNodes());
		}
							
		assertNotNull(map.get("Log in"));
	}

	/**
	 */
	public void testLogoutRemoveCookie() throws MalformedURLException, IOException, SAXException, TransformerException {
		client.putCookie("AUTOLOGON-COOKIE",username+":"+password);
		response = client.getResponse("http://localhost/mwml/logon?cc=GB&dn=T610&lc=en"); 
		Document dom = response.getDOM();

		String XPATH = "/wml/card/p[3]/anchor";
		NodeList anchors = XPathAPI.selectNodeList(dom, XPATH);

		Map map = new HashMap();
		for(int i=0; i<anchors.getLength(); i++){
			map.put(anchors.item(i).getChildNodes().item(0).getNodeValue(), anchors.item(i).getChildNodes());
		}
							
		assertNotNull(map.get("My content"));


		response = client.getResponse("http://localhost/mwml/home?action=logout&cc=GB&dn=T610&lc=en"); 
		response = client.getResponse("http://localhost/mwml/home?cc=GB&dn=T610&lc=en"); 
		writeResponseToFile(response, System.getProperty("java.io.tmpdir")+"/httpUnitTest/testMwmlLogoutRemoveCookie.html");
		dom = response.getDOM();
		
		XPATH = "/wml/card/p[8]/anchor";
		anchors = XPathAPI.selectNodeList(dom, XPATH);

		map = new HashMap();
		for(int i=0; i<anchors.getLength(); i++){
			map.put(anchors.item(i).getChildNodes().item(0).getNodeValue(), anchors.item(i).getChildNodes());
		}
							
		assertNotNull(map.get("Log in"));
		response = client.getResponse("http://localhost/mwml/logon?cc=GB&dn=T610&lc=en"); 
		writeResponseToFile(response, System.getProperty("java.io.tmpdir")+"/httpUnitTest/testMwmlLogon.html");
		
	}
	
	
}
