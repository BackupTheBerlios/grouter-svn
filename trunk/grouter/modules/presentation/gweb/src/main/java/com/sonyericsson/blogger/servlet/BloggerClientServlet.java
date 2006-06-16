package com.sonyericsson.blogger.servlet;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.sonyericsson.blogger.BloggerUtil;

/**
 * Copyright 2004 Sony Ericsson Mobile Communications AB. All rights reserved.
 * @author The CIP 3.2 Project
 * @since 
 * @version
 *
 * This Servlet will simulate a mobile client by posting ATOM XML 
 * to the BloggerProxy servlet.
 *
 */
 public class BloggerClientServlet extends HttpServlet {

	/** The Logger */
	private Logger logger = Logger.getLogger(BloggerClientServlet.class); 

	/**
	 * Constructor of the object.
	 */
	public BloggerClientServlet() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	/**
	 * The doGet returns an interface where parameters for the bloggerServlet can be
	 * changed, thereby simulating different phone scenarios. Currently the following parameters
	 * are available:
	 * 
	 * <ul>
	 * 		<li>ProxyHostHeader - If an Akamai server is reached not using wap.se.com this must be set to the akamai host name</li>
	 * 		<li>ProxyHost - The SonyEricsson BloggerProxy to hit/test</li>
	 * 		<li>BloggerHost - The BloggerHost to send/relay the blogrequest to</li>
	 * 		<li>OperatorCustomisationId - Some operators might add a customization template</li>
	 * 		<li>BlogId - The blogId to use (always 0 when adding blogs)</li>
	 * 		<li>IMEI - the number of the mobile device</li>
	 * 		<li>IMSI - the SIM card number of the mobile device</li>
	 * 		<li>SharedSecret - the SonyEricsson sharedSecred between proxy and phone</li>
	 * 		<li>The ATOM XML blog request</li>
	 * </ul>
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		//tell akamai not to cache this page
		response.setHeader("X-Cache-Control","no-store");
		
		PrintWriter out = response.getWriter();
		out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
		out.println("<HTML>");
		out.println("  <HEAD><TITLE>SonyEricsson Blogger Test Client</TITLE></HEAD>");
		out.println("  <BODY>");
		out.println("  <center><h2>Test Client for the SonyEricsson Blogger interface</h2></center>");
		out.println("  <table align='center'> ");
		out.println("  <form action='bloggerclient' method='POST'> ");
		out.println("  <tr><td>ProxyHostHeader:&nbsp;</td><td><input type='text' name='proxyHostHeader' size='50' value=''/>&nbsp;Only use this if proxyHost is NOT wap.xxx</td></tr> ");
		out.println("  <tr><td>ProxyHost:&nbsp;</td><td><input type='text' name='proxyHost' size='50' value='http://wap.sonyericsson.com/client/blogger/'/>&nbsp;The Blogger Proxy URL</td></tr> ");
		out.println("  <tr><td>BloggerHost:&nbsp;</td><td><input type='text' name='bloggerHost' size='50' value='beta.blogger.com'/>&nbsp;The blogger to hit</td></tr> ");
		out.println("  <tr><td>OperatorCustomisationId:&nbsp;</td><td><input type='text' name='operatorid' size='50' value='0'/></td></tr> ");
		out.println("  <tr><td>BlogId:&nbsp;</td><td><input type='text' name='blogId' size='50' value='0'/>&nbsp; For posting always use 0</td></tr> ");
		out.println("  <tr><td>IMEI:&nbsp;</td><td><input type='text' name='imei' size='50' value='35400700943543609'/></td></tr> ");
		out.println("  <tr><td>IMSI:&nbsp;</td><td><input type='text' name='imsi' size='50' value='051462115469403'/>&nbsp; SIM Card number</td></tr> ");
		out.println("  <tr><td>SharedSecret:&nbsp;</td><td><input type='text' name='sharedSecret' size='50' value=''/></td></tr> ");
		out.println("  <!--tr><td>AtomXml:&nbsp;</td><td><input type='file' name='xml'/></td></tr--> ");
		out.println("  <tr><td>&nbsp;</td><td></td></tr> ");
		out.println("  </table> ");
		out.println("  <table align='center'> ");
		out.println("  <tr><td colspan='2'><textarea name='xml' rows='20' cols='100'>");
		out.println(getXml());
		out.println("  </textarea></td></tr> ");
		out.println("  <tr><td colspan='2'><input type='submit' value='Blog This!'></td></tr> ");
		out.println("  </form> ");
		out.println("  </table> ");
		out.println("  </BODY>");
		out.println("</HTML>");
		out.flush();
		out.close();
	}

	/**
	 * Creates a valid atom feed with a small gif for posting
	 *  
	 * @return a valid atom xml blog post
	 */
	private String getXml() {
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat format2 = new SimpleDateFormat("HH:mm:ss");
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

	/**
	 * This method will simulate the actual client call to the BloggerProxy. 
	 * It will also interpret the response to see if an error has ocourred. 
	 * 
	 * This method is called when a form has its tag value method equals to
	 * post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ArrayList errors = new ArrayList();

		String proxyHost = request.getParameter("proxyHost");
		String proxyHostHeader = request.getParameter("proxyHostHeader");
		String bloggerHost = request.getParameter("bloggerHost");
		String blogId = request.getParameter("blogId");
		String imei = request.getParameter("imei");
		String imsi = request.getParameter("imsi");
		String sharedSecret = request.getParameter("sharedSecret");
		String xml = request.getParameter("xml");
		String operatorid = request.getParameter("operatorid");

		StringBuffer proxyResponse = new StringBuffer();
		try {

			// Get algorithm
			MessageDigest sha = MessageDigest.getInstance("SHA-1");

			// Digest deviceId
			sha.update(imei.getBytes());
			byte[] deviceDigest = sha.digest(imsi.getBytes());
			String deviceHash = BloggerUtil.encodeBase64(deviceDigest);
			deviceHash = deviceHash.replace('/', '-');
			deviceHash = deviceHash.replace('+', '_');

			// Digest password after replacement of + and /
			sha.update(deviceHash.getBytes());
			byte[] passwordDigest = sha.digest(sharedSecret.getBytes());
			String passwordHash = BloggerUtil.toHex(passwordDigest);

			StringBuffer requestString = new StringBuffer(proxyHost);
			requestString.append("/").append(bloggerHost);
			requestString.append("/").append(blogId);
			requestString.append("/").append(deviceHash);
			requestString.append("?operatorid="+operatorid);

			// Send Request to bloggerProxy
			HttpURLConnection connection = null;
			InputStream in = null;
			try {

				URL url = new URL(requestString.toString());
				connection = (HttpURLConnection) url.openConnection();
				connection.setRequestMethod("POST");
				connection.setRequestProperty("Content-type", "application/xml");
				connection.setRequestProperty("Authentication", passwordHash);
				if (proxyHostHeader != null && !"".equals(proxyHostHeader))
					connection.setRequestProperty("Host", proxyHostHeader);

				connection.setDoOutput(true);
				connection.setDoInput(true);

				// Write blog to host
				DataOutputStream outwriter = new DataOutputStream(connection.getOutputStream());
				outwriter.writeBytes(xml);
				outwriter.flush();
				outwriter.close();

				in = connection.getInputStream();

			} catch (IOException e) {
				logger.error("IOException while writing to BloggerServlet", e);
				if (connection != null) {
					// get the errorstream instead of the inputstream
					in = ((HttpURLConnection) connection).getErrorStream();
				} else {
					throw e;
				}
			} finally {
				try {
					// Save response in StringBuffer
					if (in == null) {
						proxyResponse.append("inputstream is null <br/>\n");
						if (connection != null) {
							proxyResponse.append("ResponseCode: " + connection.getResponseCode() + "<br/>\n");
							proxyResponse.append("ResponseMessage: " + connection.getResponseMessage() + "<br/>\n");
							proxyResponse.append("URL path: " + connection.getURL().getPath() + "<br/>\n");
						}
					} else {
						//Read the InputStream/ErrorStream
						BufferedReader res = new BufferedReader(new InputStreamReader(in, "UTF-8"));
						String inputLine;
						while ((inputLine = res.readLine()) != null) {
							proxyResponse.append(inputLine + "\n");
						}
						res.close();  
					}
				} catch (IOException e) {
					logger.error("IOException while reading from BloggerServlet", e);
					throw e;
				}
			}

		} catch (NoSuchAlgorithmException ex) {
			errors.add(ex.getMessage());
		}
		//return BloggerHost response to BloggerClient
		response.setContentType("text/html");
		OutputStream out = response.getOutputStream();
		out.write(proxyResponse.toString().getBytes());
		out.flush();
		out.close();
	}

	/**
	 * Initialization of the servlet. <br>
	 * 
	 * @throws ServletException if an error occure
	 */
	public void init() throws ServletException {
		// Put your code here
	}

}
