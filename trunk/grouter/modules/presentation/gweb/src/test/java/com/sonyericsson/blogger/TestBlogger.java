package com.sonyericsson.blogger;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jmock.RecorderTestCase;

import com.sonyericsson.blogger.exception.ProxyBloggerAuthenticationFailedException;
import com.sonyericsson.blogger.exception.ProxyBloggerRequestPathException;
import com.sonyericsson.blogger.exception.ProxyBloggerUnknownBloggerException;
import com.sonyericsson.blogger.exception.SystemException;

public class TestBlogger extends RecorderTestCase {
	
	ByteArrayOutputStream outputStream;

	public void setUp() {
		outputStream = new ByteArrayOutputStream();
	}
	
	private String getXml() throws FileNotFoundException, IOException {
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat format2 = new SimpleDateFormat("hh:mm:ss");
		Date now = new Date();
		String issued = format1.format(now) + "T" + format2.format(now) + "Z";
		String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"  standalone=\"yes\"?>"
				+ "<entry xmlns=\"http://purl.org/atom/ns#\">"
				+ "<title mode=\"escaped\" type=\"text/plain\">Test: "
				+ issued
				+ "</title>"
				+ "<issued>"
				+ issued
				+ "</issued>"
				+ "<generator url=\"http://www.sonyreicsson.com/\" version=\"1.0\"></generator>"
				+ "<content type=\"application/xhtml+xml\">"
				+ "<div xmlns=\"http://www.w3.org/1999/xhtml\">"
				+ "<object type=\"image/gif\" data=\"data:image/gif;base64,R0lGODlhDwAPAKECAAAAzMzM///// " 
				+ "wAAACwAAAAADwAPAAACIISPeQHsrZ5ModrLlN48CXF8m2iQ3YmmKqVlRtW4ML "
				+ "wWACH+H09wdGltaXplZCBieSBVbGVhZCBTbWFydFNhdmVyIQAAOw==\" ></object>"
				+ "</div>"
				+ "</content>"
				+ "</entry>";

		return xml;
	}

	private String imei = "35400700943543609";
	private String imsi = "051462115469403";
	private String sharedSecret = "fc1707420ee3c2395bd0";
	private String deviceHash;
	private String passwordHash;

	private void setUp(String sharedSecret) throws NoSuchAlgorithmException {
		// Get algorithm
		MessageDigest sha = MessageDigest.getInstance("SHA-1");

		// Digest deviceId
		sha.update(imei.getBytes());
		byte[] deviceDigest = sha.digest(imsi.getBytes());
		deviceHash = BloggerUtil.encodeBase64(deviceDigest);
		deviceHash = deviceHash.replace('/', '-');
		deviceHash = deviceHash.replace('+', '_');
		
		deviceHash = "Cs_hWuAMaz0IiIkKf2Nxr7AoTvI=";
		// Digest password � after replacement of + and /
		sha.update(deviceHash.getBytes());
		byte[] passwordDigest = sha.digest(sharedSecret.getBytes());
		passwordHash = BloggerUtil.toHex(passwordDigest);
	}
	

	public void testHashFails() throws IOException, NoSuchAlgorithmException {
		setUp(sharedSecret);
		
		HashMap bloggers = new HashMap();
		bloggers.put("blogger.com", "http://beta.blogger.com");
		Blogger blogger = new Blogger(bloggers, "torcla", "dominos", sharedSecret+"error");
		
		String pathInfo = "/0/" + deviceHash + "/mposts/full";
		
		try {
			blogger.addBlog("blogger.com", pathInfo , passwordHash, deviceHash, new ByteArrayInputStream(getXml().getBytes()), outputStream);
			fail();
		} catch (ProxyBloggerAuthenticationFailedException e) {
			assertTrue("Hash Should fail: ", true);
		}
		
	}

	public void testHashUpperCase() throws IOException, NoSuchAlgorithmException {
		setUp(sharedSecret);
		
		HashMap bloggers = new HashMap();
		bloggers.put("blogger.com", "http://beta.blogger.com");
		Blogger blogger = new Blogger(bloggers, "torcla", "dominos", "fc1707420ee3c2395bd0");
		
		assertTrue(blogger.testHash(passwordHash.toUpperCase(), deviceHash));
	}

	public void testHashLowerCase() throws IOException, NoSuchAlgorithmException {
		setUp(sharedSecret);
		
		HashMap bloggers = new HashMap();
		bloggers.put("blogger.com", "http://beta.blogger.com");
		Blogger blogger = new Blogger(bloggers, "torcla", "dominos", "fc1707420ee3c2395bd0");
		
		assertTrue(blogger.testHash(passwordHash.toLowerCase(), deviceHash));
	}
	
	public void testUnknownBlogger() throws IOException, NoSuchAlgorithmException {
		setUp(sharedSecret);

		HashMap bloggers = new HashMap();
		bloggers.put("blogger.com", "http://beta.blogger.com");
		Blogger blogger = new Blogger(bloggers, "torcla", "dominos", sharedSecret);
		
		String pathInfo = "/0/" + deviceHash + "/mposts/full";
		
		try {
			blogger.addBlog("unknownblogger", pathInfo , passwordHash, deviceHash, new ByteArrayInputStream(getXml().getBytes()), outputStream);
			fail();
		} catch (ProxyBloggerUnknownBloggerException e) {
			assertTrue("UnknownHost: ", true);
		}
		
	}
	
	public void testRequest() throws IOException, NoSuchAlgorithmException {
		setUp(sharedSecret);

		HashMap bloggers = new HashMap();
		bloggers.put("blogger.com", "http://beta.blogger.com/feeds/");
		Blogger blogger = new Blogger(bloggers, "torcla", "dominos", sharedSecret+"error");
		
		// Start Recording
		HttpServletRequest request = (HttpServletRequest)newSource(HttpServletRequest.class); 
		HttpServletResponse response = (HttpServletResponse)newSource(HttpServletResponse.class);
		request.getPathInfo();
		String pathInfo = "www.blogger.com/0/Cs+hWuAMaz0IiIkKf2Nxr7AoTvI";//"blogger.com/0/" + deviceHash;
		modifyLast(request).returnsValue(pathInfo);
		
		request.getHeader("Authentication");
		modifyLast(request).returnsValue(passwordHash);
		request.getAttribute("operatorid");
		modifyLast(request).returnsValue("someid");
		
		request.getInputStream();
		modifyLast(request).returnsValue(null);
		
		response.getOutputStream();
		modifyLast(response).returnsValue(null);
		
		playBack();
		
	
		try {
			blogger.addBlog(request, response);
			fail();
		} catch (SystemException e) {
			System.out.println("some error " +e.getMessage());
			
			assertEquals("No input stream: ", "No input stream", e.getMessage());
		}
		
	}

	public void testRequestError() throws IOException, NoSuchAlgorithmException {
		setUp(sharedSecret);

		HashMap bloggers = new HashMap();
		bloggers.put("blogger.com", "http://beta.blogger.com/feeds/");
		Blogger blogger = new Blogger(bloggers, "torcla", "dominos", sharedSecret+"error");
		
		// Start Recording
		HttpServletRequest request = (HttpServletRequest)newSource(HttpServletRequest.class); 
		HttpServletResponse response = (HttpServletResponse)newSource(HttpServletResponse.class); 
		request.getPathInfo();
		String pathInfo = "/0/Cs+hWuAMaz0IiIkKf2Nxr7AoTvI=";
		modifyLast(request).returnsValue(pathInfo);
		
		playBack();
		
	
		try {
			blogger.addBlog(request, response);
			fail();
		} catch (ProxyBloggerRequestPathException e) {
			assertTrue(true);
		}
		
	}

}
