package com.sonyericsson.blogger;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.UnknownHostException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.sonyericsson.blogger.exception.HostBloggerException;
import com.sonyericsson.blogger.exception.ProxyBloggerAuthenticationFailedException;
import com.sonyericsson.blogger.exception.ProxyBloggerMalformedURLException;
import com.sonyericsson.blogger.exception.ProxyBloggerProtocolException;
import com.sonyericsson.blogger.exception.ProxyBloggerRequestPathException;
import com.sonyericsson.blogger.exception.ProxyBloggerUnknownBloggerException;
import com.sonyericsson.blogger.exception.ProxyBloggerUnknownHostException;
import com.sonyericsson.blogger.exception.SystemException;


/**
 * Copyright 2005 Sony Ericsson Mobile Communications AB. All rights reserved.
 * @author The CIP 3.2 Project
 * @since $Date: 2006/05/15 09:40:26 $
 * @version $Revision: 1.3.10.6 $
 * 
 * Blogger sends a blog to a given AtomEnabled blogger host (ex. www.blogger.com)
 * It uses Atom xml in the @see {Blogger.addBlogs}
 * 
 */
public class Blogger {

	/** The username for the blog */
	private String username;
	/** The password credential for the blog */
	private String password;
	/** The shared secret between ME and F&D */
	private String sharedSecret;
	/** The mapping between bloggernames and their host urls */
	private Map bloggers = new HashMap();
	/** The Logger */
	private Logger logger = Logger.getLogger(Blogger.class); 

	
	/**
	 * Constructor which setsup the initial values
	 * 
	 * @param bloggers
	 * @param username
	 * @param password
	 * @param sharedSecret
	 */
	public Blogger(Map bloggers, String username, String password, String sharedSecret) {
		this.username = username;
		this.password = password;
		this.bloggers = bloggers;
		this.sharedSecret = sharedSecret;
	}

	/**
	 * Adds a new blog to specified bloggerhost 
	 * 
	 * @param blogger the BloggerHost to post to
	 * @param pathInfo the Atom path used for the host
	 * @param digest the hashed password
	 * @param deviceId the deviceId to match against the hashed password (digest)
	 * @param xml the Atom xml containing the post data 
	 * @return response from blogger.com
	 */
	public void addBlog(String blogger, String pathInfo, String digest, String deviceId, InputStream clientInput, OutputStream clientOutput) 
			throws IOException {

		if (clientInput == null) {
			throw new SystemException("No input stream");
		}		

		if (clientOutput == null) {
			throw new SystemException("No output stream");
		}		

		// Test Hash
		if (!testHash(digest, deviceId)) { 
			throw new ProxyBloggerAuthenticationFailedException("HashFailed");
		}
		
		blogger = (String)bloggers.get(blogger);
		if (blogger == null) {
			throw new ProxyBloggerUnknownBloggerException(blogger);
		}
		
		InputStream bloggerResponse = null;
		HttpURLConnection connection = null;
		String userPassword = username + ":" + password;
		String encoding = BloggerUtil.encodeBase64(userPassword.getBytes());

		try {

			// Create the connection
			String requestUrl = blogger+pathInfo; 
			URL url = new URL(requestUrl);
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-type", "application/atom+xml; charset=\"utf-8\"");
			connection.setRequestProperty("Authorization", "BASIC " + encoding);

			// Setup connection for both input and output
			connection.setDoOutput(true);
			connection.setDoInput(true);
			
			// Write blog to host
			DataOutputStream outwriter = new DataOutputStream(connection.getOutputStream());
			int b = 0;
			while ((b = clientInput.read()) != -1) {
				outwriter.write(b);
			}
			
			outwriter.flush();
			outwriter.close();

			// Get Response from host
			bloggerResponse = connection.getInputStream();
			if (bloggerResponse != null)  {
				// Return bloggerResponse to client
				while ((b = bloggerResponse.read()) != -1) {
					clientOutput.write(b);
				}
				bloggerResponse.close();
			}

		} catch (ProtocolException e) {
			logger.error(e);
			throw new ProxyBloggerProtocolException(e);
		} catch (MalformedURLException e) {
			logger.error(e);
			throw new ProxyBloggerMalformedURLException(e);
		} catch (UnknownHostException e) {
			logger.error(e);
			throw new ProxyBloggerUnknownHostException(e);
		} catch (IOException e) {
			if (connection != null) {
				logger.error(e);
				throw new HostBloggerException(connection.getResponseCode(), e);
			}
			throw e;
		}
		finally {
			if (connection != null)
				connection.disconnect();
			if (bloggerResponse != null) 
				bloggerResponse.close();
		}

	}

	/**
	 * Tests a digested password against the undigested deviceId
	 * 
	 * @param digest
	 * @param deviceId
	 * @return
	 */
	public boolean testHash(String digest, String deviceId){
		MessageDigest sha = null;
		try {
			sha = MessageDigest.getInstance("SHA-1");
		} catch (NoSuchAlgorithmException nsae) {
			throw new SystemException(nsae);
		}
		
		//sha.update(deviceId.getBytes());
		String digest2 = BloggerUtil.toHex(sha.digest((deviceId + sharedSecret).getBytes()));
		
		return digest.equalsIgnoreCase(digest2); 
	}
	
	/**
	 * Pulls the needed information from the request and adds a blog to 
	 * specified bloggerHost
	 * 
	 * @param request
	 * @return xml response from bloggerHost
	 * @throws IOException if inputstream fails
	 */
	public void addBlog(HttpServletRequest request, HttpServletResponse response) 
			throws IOException{
		
		String pathInfo = request.getPathInfo();
		StringTokenizer st = new StringTokenizer(pathInfo, "/");
		if (st.countTokens() != 3) {
			throw new ProxyBloggerRequestPathException("The incoming request is invalid "+ pathInfo);
		}

		String digest = request.getHeader("Authentication");
		// Get variables from path
		String bloggerHost = st.nextToken();
		String blogId = st.nextToken();
		String deviceId = st.nextToken();
		//this is a paramter unlike the input. This is according to the spec deliverd by e2e
		String operatorid = (String) request.getAttribute("operatorid");
		// reassemble requestpath for sending to blogger
		pathInfo = "/feeds/"+blogId+"/"+operatorid+"/"+deviceId+"/mposts/full"; 
		
		
		
		if (bloggerHost == null || blogId == null || digest == null) {
			throw new ProxyBloggerRequestPathException(pathInfo);
		}
		
		addBlog(bloggerHost, pathInfo, digest, deviceId, request.getInputStream(), response.getOutputStream());		
	}
	
	/**
	 * Deletes a blog from given blogger
	 * 
	 * @param request
	 * @return return message from the blogger host
	 */
	public StringBuffer deleteBlog(HttpServletRequest request) {
		throw new SystemException("Delete not implemented");
	}

//	/**
//	 * Retrieves the latest 15 blogs for the user given in the
//	 * constructor  
//  *	
//  *  ***********    THIS METHOD IS NOT TESTET VIA JUNIT    **********
//  *  ***********  So if U choose to use it make test first **********
//  *	
//	 * @param blogger AtomEnabled Blogger host from which to retrieve blogs
//	 * @return StringBuffer with blogger host response
//	 */
//	public StringBuffer getBlogs(String blogger, String blogId) {
//
//		StringBuffer sb = new StringBuffer();
//
//		blogger = (String)bloggers.get(blogger);
//		if (blogger == null) {
//			throw new SystemException("Unknown blogger!");
//		}
//		
//		InputStream in = null;
//		HttpURLConnection connection = null;
//		String userPassword = username + ":" + password;
//		String encoding = BloggerUtil.encodeBase64(userPassword.getBytes());
//
//		try {
//
//			URL url = new URL(blogger+blogId);
//			connection = (HttpURLConnection) url.openConnection();
//			connection.setRequestMethod("GET");
//			connection.setRequestProperty("Authorization", "BASIC " + encoding);
//			in = connection.getInputStream();
//
//			BufferedReader res = new BufferedReader(new InputStreamReader(in, "UTF-8"));
//			String inputLine;
//			while ((inputLine = res.readLine()) != null) {
//				sb.append(inputLine + "\n");
//			}
//			res.close();
//		} catch (Exception e) {
//			if (connection != null) {
//				in = ((HttpURLConnection) connection).getErrorStream();
//			}
//		} finally  {
//			try {
//				//Save response in StringBuffer 
//				BufferedReader res = new BufferedReader(new InputStreamReader(in,"UTF-8"));
//				String inputLine;
//				while ((inputLine = res.readLine()) != null) {
//					sb.append(inputLine + "\n");
//				}
//				res.close();
//			} catch (Exception e) {}
//		}
//
//		return sb;
//	}

}