package com.sonyericsson.blogger;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jmock.RecorderTestCase;

import com.sonyericsson.blogger.exception.SystemException;
import com.sonyericsson.blogger.servlet.BloggerServlet;

public class TestBloggerServlet extends RecorderTestCase {

	
	private String imei = "35400700943543609";
	private String imsi = "051462115469403";
	private String sharedSecret = "mysecret";
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

		// Digest password � after replacement of + and /
		sha.update(deviceHash.getBytes());
		byte[] passwordDigest = sha.digest(sharedSecret.getBytes());
		passwordHash = BloggerUtil.toHex(passwordDigest);
	}

	public void testDoPostBlog() throws ServletException, IOException, NoSuchAlgorithmException {
		setUp(sharedSecret);
		
		ServletConfig config = (ServletConfig)newSource(ServletConfig.class);
		HttpServletResponse response = (HttpServletResponse)newSource(HttpServletResponse.class);
		HttpServletRequest request = (HttpServletRequest)newSource(HttpServletRequest.class); 

		response.setContentType("application/atom+xml");

		config.getInitParameter("username");
		modifyLast(config).returnsValue("torcla");
		config.getInitParameter("password");
		modifyLast(config).returnsValue("dominos");
		config.getInitParameter("sharedSecret");
		modifyLast(config).returnsValue("mysecret");

		response.setHeader("X-Cache-Control", "no-store");

		request.getHeader("X-HTTP-Method-Override");
		modifyLast(request).returnsValue(null);

		request.getAttribute("operatorid");
		modifyLast(request).returnsValue("someid");
		
		request.getPathInfo();
		String pathInfo = "blogger.com/0/anid" + deviceHash;
		modifyLast(request).returnsValue(pathInfo);
		
		request.getHeader("Authentication");
		modifyLast(request).returnsValue(passwordHash);
		
		request.getInputStream();
		modifyLast(request).returnsValue(null);

		response.getOutputStream();
		modifyLast(response).returnsValue(null);
		
		playBack();

		BloggerServlet bs = new BloggerServlet();
		bs.init(config);
		try {
			bs.doPost(request, response);
			assertTrue(false);
		} catch (IOException e) {
			assertTrue(false);
		} catch (SystemException e) {
			assertEquals("No Input Stream: ", "No input stream", e.getMessage());
		}
		
	}
	
	public void testDoDeleteBlog() throws ServletException, IOException, NoSuchAlgorithmException {
		setUp(sharedSecret);
		
		ServletConfig config = (ServletConfig)newSource(ServletConfig.class);
		HttpServletResponse response = (HttpServletResponse)newSource(HttpServletResponse.class);
		HttpServletRequest request = (HttpServletRequest)newSource(HttpServletRequest.class); 

		response.setContentType("application/atom+xml");

		config.getInitParameter("username");
		modifyLast(config).returnsValue("torcla");
		config.getInitParameter("password");
		modifyLast(config).returnsValue("dominos");
		config.getInitParameter("sharedSecret");
		modifyLast(config).returnsValue("mysecret");

		response.setHeader("X-Cache-Control", "no-store");
		

		request.getHeader("X-HTTP-Method-Override");
		modifyLast(request).returnsValue("DELETE");
		
		playBack();

		BloggerServlet bs = new BloggerServlet();
		bs.init(config);
		try {
			bs.doPost(request, response);
			assertTrue(false);
		} catch (SystemException e) {
			assertEquals("Delete not implemented: ", "Delete not implemented", e.getMessage());
		}
	}

}
