package com.sonyericsson.fun.http.portal;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import org.xml.sax.SAXException;

import com.meterware.httpunit.WebLink;
import com.meterware.httpunit.WebResponse;
import com.meterware.servletunit.ServletUnitClient;
import com.sonyericsson.fun.http.common.HttpUnitTestCase;

public class HttpTestWxhtmlPortalWithCookie extends HttpUnitTestCase {
	private ServletUnitClient client;

	private WebResponse response;

	/**
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
//		client = this.getClient("SonyEricssonK700i");
		client = this.getClient("Mozilla");
	}

	/**
	 */
	public void testPortalWithCookie() throws MalformedURLException, IOException, SAXException {
		final String preferredLocale = "sv_SE";
		//final String preferredLocale = "zh_CN";
		
		client.putCookie("PREFERRED-LOCALE",preferredLocale);

		//response = get$CharacterAssertedResponse(client, "http://localhost/mxhtml/portal?dn=S700i");
		response = get$CharacterAssertedResponse(client, "http://localhost/wxhtml/portal"); 
		writeResponseToFile(response, "c:\\tmp\\testPortalWithCookie.html");

		//printLinks(response.getLinks());
		WebLink[] links = response.getLinks();
		
		WebLink[] linksWithPreferredLocale = getLinksWithPreferredLocale(preferredLocale, links);
		int with = linksWithPreferredLocale.length;
		assertTrue("Should be more than a couple of links with the preferred locale set. Please check, because only "+with+" out of "+links.length+" was found", with>=3);

		WebLink ringTones = response.getLinkWith("ringtones");
		assertNotNull("With preferred locale in cookie, page should contain ringtones (overview1)", ringTones);
	}

	/**
	 */
	public void testPortalWithCookieChangePreferredLocale() throws MalformedURLException, IOException, SAXException {
		final String preferredLocale = "sv_SE";
		
		client.putCookie("PREFERRED-LOCALE",preferredLocale);

		//response = get$CharacterAssertedResponse(client, "http://localhost/mxhtml/portal?dn=S700i");
		response = get$CharacterAssertedResponse(client, "http://localhost/wxhtml/portal"); 
		writeResponseToFile(response, "c:\\tmp\\testPortalWithCookie.html");

		WebLink ringTones = response.getLinkWith("ringtones");
		assertNotNull("With preferred locale in cookie, page should contain ringtones (overview1)", ringTones);
		
		//printLinks(response.getLinks());
		WebLink[] links = response.getLinks();
		for (int n=0; n<links.length; n++) {
			WebLink link = links[n];
			if (link.getURLString().endsWith("/portalChangeLocale")) {
				System.out.println("url="+link.getRequest().getURL());
				response = get$CharacterAssertedResponse(client, link.getRequest().getURL().toString());
				writeResponseToFile(response, "c:\\tmp\\testPortalWithCookiePortal.html");
				
				ringTones = response.getLinkWith("ringtones");
				assertNull("When changing locale, page should NOT contain ringtones (portalChangeLocale)", ringTones);
				
				return;
			}
		}
		assertTrue("Link to changing locale not found.", false);
	}
	
	
	public void testPortalWithoutCookie() throws MalformedURLException, IOException, SAXException {
//		client.putCookie("PREFERRED-LOCALE","sv_SE");

		//response = get$CharacterAssertedResponse(client, "http://localhost/mxhtml/portal?dn=S700i");
		response = get$CharacterAssertedResponse(client, "http://localhost/wxhtml/portal"); 
		writeResponseToFile(response, "c:\\tmp\\testPortalWithoutCookie.html");

		//printLinks(response.getLinks());

		WebLink[] links = response.getLinks();
		
		WebLink[] linksWithPreferredLocale = getLinksWithPreferredLocale(null, links);
		int with = linksWithPreferredLocale.length;
		assertTrue("No links with preferred locale should be set. Please check, because "+with+" out of "+links.length+" was found", with>0);

		WebLink ringTones = response.getLinkWith("ringtones");
		assertNull("When no preferred locale is set in cookie, page should NOT contain ringtones (portalChangeLocale)", ringTones);
	}
	
	
	/**
	 * Returns the links with cc/lc matching the given preferredLocale. 
	 * If preferredLocale null is given, all links containing parameters cc and lc are returned.
	 * @param preferredLocale
	 * @param links
	 * @return
	 */
	private WebLink[] getLinksWithPreferredLocale(String preferredLocale, WebLink[] links) {
		List linksWithPreferredLocale = new ArrayList();
		List linksWithoutPreferredLocale = new ArrayList();
		for (int n=0; n<links.length; n++) {
			WebLink link = links[n];
			String[] cc = link.getParameterValues("cc");
			String[] lc = link.getParameterValues("lc");
			if (lc.length==1 && cc.length==1) {
				String locale = lc[0]+"_"+cc[0];
				if (preferredLocale==null || preferredLocale.equals(locale)) {
					linksWithPreferredLocale.add(link);
				} else {
					linksWithoutPreferredLocale.add(link);
				}
			} else {
				linksWithoutPreferredLocale.add(link);
			}
		}
		int with = linksWithPreferredLocale.size();
		int without = linksWithoutPreferredLocale.size();
		return (WebLink[])linksWithPreferredLocale.toArray(new WebLink[linksWithPreferredLocale.size()]);
	}
}
