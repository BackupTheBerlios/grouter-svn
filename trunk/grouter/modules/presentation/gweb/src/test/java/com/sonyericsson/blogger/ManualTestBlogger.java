package com.sonyericsson.blogger;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import junit.framework.TestCase;

import com.sonyericsson.blogger.Blogger;
import com.sonyericsson.blogger.BloggerUtil;
import com.sonyericsson.blogger.exception.HostBloggerException;
import com.sonyericsson.blogger.exception.ProxyBloggerMalformedURLException;
import com.sonyericsson.blogger.exception.ProxyBloggerUnknownHostException;

public class ManualTestBlogger extends TestCase {

	private String getXml() throws FileNotFoundException, IOException {
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat format2 = new SimpleDateFormat("hh:mm:ss");
		Date now = new Date();
		String issued = format1.format(now) + "T" + format2.format(now) + "Z";
		String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"  standalone=\"yes\"?>\n"
					+ "<atom:entry xmlns:atom=\"http://www.w3.org/2005/Atom\" xml:lang=\"en-US\">\n"
					+ "  <atom:updated>"+issued+"</atom:updated>\n"
					+ "  <atom:category scheme=\"http://www.google.com/type\" term=\"blog.post\"/>\n"
					+ "  <atom:title type=\"text\">BloggerClientTest: "+issued+"</atom:title>\n"
					+ "  <atom:content type=\"xhtml\">\n"
					+ "	   <div xmlns=\"http://www.w3.org/1999/xhtml\">\n"
					+ "      <object type=\"image/gif\" data=\"data:image/gif;base64,R0lGODlhDwAPAKECAAAAzMzM///// wAAACwAAAAADwAPAAACIISPeQHsrZ5ModrLlN48CXF8m2iQ3YmmKqVlRtW4ML wWACH+H09wdGltaXplZCBieSBVbGVhZCBTbWFydFNhdmVyIQAAOw==\" ></object>\n"
					+ "    </div>\n"
					+ "  </atom:content>\n"
					+ "  <atom:author>\n"
					+ "	   <atom:name>Author Name</atom:name>\n"
					+ "	   <atom:uri>http://host.com/user/index.html</atom:uri>\n"
					+ "	   <atom:email>user@host.com</atom:email>\n"
					+ "  </atom:author>\n"
					+ "  <atom:devicename xmlns=\"http://sonyericsson.com/moblog/ns#\">\n"
					+ "	   Sony Ericsson W550i\n"
					+ "  </atom:devicename>\n"
					+ "</atom:entry>\n";

		return xml;
	}

	private String imei = "35400700943543609";
	private String imsi = "051462115469403";
	private String sharedSecret = "mysecret";
	private String deviceHash;
	private String passwordHash;
	private String operatorid = "vodafone.1";

	private void setUp(String sharedSecret) throws NoSuchAlgorithmException {
		// Get algorithm
		MessageDigest sha = MessageDigest.getInstance("SHA-1");

		// Digest deviceId
		sha.update(imei.getBytes());
		byte[] deviceDigest = sha.digest(imsi.getBytes());
		deviceHash = BloggerUtil.encodeBase64(deviceDigest);
		deviceHash = deviceHash.replace('/', '-');
		deviceHash = deviceHash.replace('+', '_');

		// Digest password after replacement of + and /
		sha.update(deviceHash.getBytes());
		byte[] passwordDigest = sha.digest(sharedSecret.getBytes());
		passwordHash = BloggerUtil.toHex(passwordDigest);
	}

	
	public void testAddBlog() throws FileNotFoundException, IOException, NoSuchAlgorithmException {
		HashMap bloggers = new HashMap();
		bloggers.put("blogger.com", "http://www.blogger.com");
		bloggers.put("beta.blogger.com", "http://beta.blogger.com");
		Blogger blogger = new Blogger(bloggers, "torcla", "dominos", sharedSecret);

		setUp(sharedSecret);
		
		String pathInfo = "/feeds/0/"+operatorid+"/" + deviceHash + "/mposts/full";
		
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		
		blogger.addBlog("blogger.com", pathInfo, passwordHash, deviceHash, new ByteArrayInputStream(getXml().getBytes()), outputStream);
		System.out.println(outputStream.toString());
		outputStream = new ByteArrayOutputStream();
		blogger.addBlog("beta.blogger.com", pathInfo, passwordHash, deviceHash, new ByteArrayInputStream(getXml().getBytes()), outputStream);
		System.out.println(outputStream.toString());
		
	}
	
	public void testIOErrors() throws FileNotFoundException, IOException, NoSuchAlgorithmException {
		HashMap bloggers = new HashMap();
		bloggers.put("unknownhost", "http://unknownhost");
		bloggers.put("malformedurl", "htt://unknownhost");
		bloggers.put("404", "http://localhost/404error");
		bloggers.put("connectionrefused", "http://localhost:7070/404error");
		Blogger blogger = new Blogger(bloggers, "torcla", "dominos", sharedSecret);
		
		setUp(sharedSecret);
		String pathInfo = "/feeds/0/" + deviceHash + "/mposts/full";
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		
		try {
			blogger.addBlog("unknownhost", pathInfo, passwordHash, deviceHash, new ByteArrayInputStream(getXml().getBytes()), outputStream);
			fail();
		} catch (ProxyBloggerUnknownHostException e) {
			assertTrue(true);
		}
		try {
			blogger.addBlog("malformedurl", pathInfo , passwordHash, deviceHash, new ByteArrayInputStream(getXml().getBytes()), outputStream);
			fail();
		} catch (ProxyBloggerMalformedURLException e) {
			assertTrue(true);
		}

		try {
			blogger.addBlog("connectionrefused", pathInfo , passwordHash, deviceHash, new ByteArrayInputStream(getXml().getBytes()),outputStream);
		} catch (IOException e) {
			assertTrue(true);
		}

		try {
			blogger.addBlog("404", pathInfo , passwordHash, deviceHash, new ByteArrayInputStream(getXml().getBytes()),outputStream);
		} catch (HostBloggerException e) {
			assertEquals(404, e.getResponseCode());
		}
		
	}

}
