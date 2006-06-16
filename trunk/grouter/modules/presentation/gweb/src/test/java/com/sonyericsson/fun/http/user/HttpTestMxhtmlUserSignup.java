/*
 * Created on 2005-jul-11
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.sonyericsson.fun.http.user;

import java.io.IOException;
import java.net.MalformedURLException;

import org.xml.sax.SAXException;

import com.meterware.httpunit.Button;
import com.meterware.httpunit.WebForm;
import com.meterware.httpunit.WebResponse;
import com.meterware.servletunit.ServletUnitClient;
import com.sonyericsson.fun.http.common.HttpUnitTestCase;

/**
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class HttpTestMxhtmlUserSignup extends HttpUnitTestCase {
	private ServletUnitClient client;
	private WebResponse response;

	/**
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
	    client = this.getClient("SonyEricssonK700i");
	}

	

	/**
	 * This test enters the normal "sonyericsson membership" page and verifies that all expected fields
	 * are present and visible.
	 *  
	 * @throws MalformedURLException
	 * @throws IOException
	 * @throws SAXException
	 */
	public void testViewSignup() throws MalformedURLException, IOException, SAXException{
		response = get$CharacterAssertedResponse( client, "http://localhost/mxhtml/signup?cc=SE&dn=S700i&lc=sv");
		writeResponseToFile( response, "c:\\logs\\testViewSignup.html");
		
		printLinks(response.getLinks());
		
		//Should be a register link
		WebForm registerForm = response.getFormWithName("signup");
		
		Button buttonWithID = registerForm.getButtonWithID("next");
		assertNotNull( buttonWithID);
		
		
		
		assertInputfieldIsPresentAndVisible( response, "emailAddress" );
		assertInputfieldIsPresentAndVisible( response, "registerPassword" );
		assertInputfieldIsPresentAndVisible( response , "enews");
		
	/*
	 assertInputfieldIsPresentAndVisible( response, "conEmailAddress" );
	 	assertInputfieldIsPresentAndVisible( response, "registerConPassword" );
		assertInputfieldIsPresentAndVisible( response, "firstName" );
		assertInputfieldIsPresentAndVisible( response, "lastName" );
		assertInputfieldIsPresentAndVisible( response, "gender" );
		assertInputfieldIsNotPresent( response, "address" );
		assertInputfieldIsNotPresent( response, "postcode" );
		assertInputfieldIsNotPresent( response, "city" );
		assertInputfieldIsPresentAndVisible( response, "brandID" );
		assertInputfieldIsNotPresent( response, "operatorID" );
		assertInputfieldIsNotPresent( response, "phonenumber" );
		assertInputfieldIsNotPresent( response, "purchasedatemonth" );
		assertInputfieldIsNotPresent( response, "purchasedateyear" );
		assertInputfieldIsPresentAndVisible( response, "agree" );
		assertInputfieldIsPresentAndVisible( response, "accessoryID" );
		assertInputfieldIsPresentAndVisible( response, "accessoryCategoryID" );
		assertInputfieldIsPresentAndVisible( response, "productID" );
		assertInputfieldIsPresentAndVisible( response, "newsletter" );
		assertInputfieldIsPresentAndVisible( response, "phoneupdates" );
		assertInputfieldIsPresentAndVisible( response, "productlaunches" );
		assertInputfieldIsNotPresent( response, "birthDay" );
		assertInputfieldIsNotPresent( response, "birthMonth" );
		assertInputfieldIsNotPresent( response, "birthYear" );
*/

	}
	
	public void testSubmitFirstSignupPage () throws MalformedURLException, IOException, SAXException
	{
		response = get$CharacterAssertedResponse( client, "http://localhost/mxhtml/signup?cc=SE&dn=S700i&lc=sv");
	
		//Should be a register link
		WebForm registerForm = response.getFormWithName("signup");
		registerForm.setParameter("emailAddress","maya@retzlaff.se");
		registerForm.setParameter("registerPassword", "joltcola");
		registerForm.setParameter("enews", "false");
		WebResponse page2 = registerForm.submit();
		writeResponseToFile(page2,"c:/logs/signupPage2.html");
		WebForm formWithName = page2.getFormWithName("signup");
		
		Button button = formWithName.getButtonWithID("next2");
		assertNotNull(button);
	}
	
	
	
}
