package com.sonyericsson.blogger.servlet;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.HashMap;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.sonyericsson.blogger.Blogger;
import com.sonyericsson.blogger.exception.HostBloggerException;

public class BloggerServlet extends HttpServlet {

	private String username;
	private String password;
	private String sharedSecret;
	private HashMap bloggers = new HashMap();
	/** The Logger */
	private Logger logger = Logger.getLogger(BloggerServlet.class); 
	
	/**
	 * Constructor of the object.
	 */
	public BloggerServlet() {
		super();
	}

	/**
	 * Stores default values from the servlet configuration
	 * @param config the configuration
	 */
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		username = (String)config.getInitParameter("username");
		password = (String)config.getInitParameter("password");
		sharedSecret = (String)config.getInitParameter("sharedSecret");
		bloggers.put("beta.blogger.com", "http://beta.blogger.com.sonyericsson.com");
		bloggers.put("blogger.com","http://www.blogger.com.sonyericsson.com");
		bloggers.put("www.blogger.com","http://www.blogger.com.sonyericsson.com");
		bloggers.put("nosuffix.www.blogger.com","http://www.blogger.com");
		bloggers.put("nosuffix.beta.blogger.com","http://beta.blogger.com");
	}	
	
	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out
				.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
		out.println("<HTML>");
		out.println("  <HEAD><TITLE>A Servlet</TITLE></HEAD>");
		out.println("  <BODY>");
		out.print("    This is ");
		out.print(this.getClass());
		out.println(", using the GET method");
		out.println("  </BODY>");
		out.println("</HTML>");
		out.flush();
		out.close();
	}

	/**
	 * Passes the given request to the BloggerClass and sends the response from
	 * blogger to the client
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// Create blogger
		Blogger blogger = new Blogger(bloggers, username, password, sharedSecret);
		
		try {
			// Send response to client
			//Tells akamai not to cache this page
			response.setHeader("X-Cache-Control","no-store");
			response.setContentType("application/atom+xml");
			// Add Blog by passing request
			if ("DELETE".equals(request.getHeader("X-HTTP-Method-Override"))) {
				blogger.deleteBlog(request);
			} else {
				blogger.addBlog(request, response);
			}
			response.getOutputStream().flush();
			response.getOutputStream().close();
			
		} catch (HostBloggerException hbe) {
			logger.error("HostBloggerException", hbe);
			response.sendError(hbe.getResponseCode(), hbe.getMessage());
		} catch (IOException e) {
			logger.error("IOException", e);
			throw e;
		}
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
