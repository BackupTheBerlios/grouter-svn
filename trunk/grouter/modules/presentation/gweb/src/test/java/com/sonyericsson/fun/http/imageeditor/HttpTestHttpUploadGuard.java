package com.sonyericsson.fun.http.imageeditor;

import java.io.IOException;

import org.xml.sax.SAXException;

import com.meterware.httpunit.WebLink;
import com.meterware.httpunit.WebResponse;
import com.meterware.servletunit.ServletUnitClient;
import com.sonyericsson.fun.http.common.HttpUnitTestCase;



/**
 * Copyright 2005 Sony Ericsson Mobile Communications AB. All rights reserved.
 * @author The Platform Upgrade Project
 * @since $Date: 2006/03/15 12:02:33 $
 * @version $Revision: 1.1.8.1 $
 * 
 */
public class HttpTestHttpUploadGuard extends HttpUnitTestCase {
	
	private final String username = "mareunuv";
	private final String password = "joltcola";

	public void testHttpUploadCapable() throws IOException, SAXException {
		ServletUnitClient client = this.getClient("SonyEricssonK700i");
		WebResponse response = get$CharacterAssertedResponse(client, "http://localhost/mxhtml/home?action=login&username="+username+"&password="+password+"&cc=GB&lc=en"); 
		WebLink manageDataLink = response.getLinkWith("Manage account");
		assertNotNull("Check if loggedIn", manageDataLink);
		
		response = get$CharacterAssertedResponse(client, "http://localhost/mxhtml/mycontent?cc=GB&lc=en"); 
		manageDataLink = response.getLinkWith("Upload video");
		assertNotNull("Check if Upload Video exists", manageDataLink);
		
		manageDataLink = response.getLinkWith("Upload image");
		assertNotNull("Check if Upload Image exists", manageDataLink);
	}

	public void testNotHttpUploadCapable() throws IOException, SAXException {
		ServletUnitClient client = this.getClient("SonyEricssonS700i");
		WebResponse response = get$CharacterAssertedResponse(client, "http://localhost/mxhtml/home?action=login&username="+username+"&password="+password+"&cc=GB&lc=en"); 
		WebLink manageDataLink = response.getLinkWith("Manage account");
		assertNotNull("Check if loggedIn", manageDataLink);
		
		response = get$CharacterAssertedResponse(client, "http://localhost/mxhtml/mycontent?cc=GB&lc=en"); 
		manageDataLink = response.getLinkWith("Upload video");
		assertNull("Check if Upload Video exists", manageDataLink);
		
		manageDataLink = response.getLinkWith("Upload image");
		assertNull("Check if Upload Image exists", manageDataLink);
	}
	
}