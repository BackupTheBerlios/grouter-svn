/*
 * Created on 2005-jul-11
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package user;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.xml.sax.SAXException;

import com.meterware.httpunit.HTMLElement;
import com.meterware.httpunit.WebForm;
import com.meterware.httpunit.WebLink;
import com.meterware.httpunit.WebResponse;
import com.meterware.servletunit.ServletUnitClient;
import common.HttpUnitTestCase;

/**
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class TestWxhtmlUserRegistration extends HttpUnitTestCase {
	private ServletUnitClient client;
	private WebResponse response1;

	/**
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
	    client = this.getClient("Mozilla");
	    client.getClientProperties().setAcceptCookies(true);
	}

	/**
	 * This test enters the first page in the registration flow and verifies that there
	 * are one link to the full sony ericsson membership, and one link to the enews membership.
	 * The URL:s on each link is also verified. 
	 * 
	 * @throws MalformedURLException
	 * @throws IOException
	 * @throws SAXException
	 */
	public void testEnterChooseMembershipLevelRegisterPage() throws MalformedURLException, IOException, SAXException{
		response1 = get$CharacterAssertedResponse( client, "http://localhost/wxhtml/registration?cc=SE&lc=sv");
		writeResponseToFile( response1, "c:\\temp\\testViewRegisterPage.html");
		
		WebLink fullMembershipLink = response1.getLinkWithID("sonyericssonmembership");
		assertNotNull( fullMembershipLink);
		assertEquals( "/wxhtml/registerfullmember?cc=SE&lc=sv", fullMembershipLink.getURLString());
		WebLink enewsMembershipLink = response1.getLinkWithID("enewsmembership");
		assertNotNull( enewsMembershipLink);
		assertEquals( "/wxhtml/registerenewsmember?cc=SE&lc=sv", enewsMembershipLink.getURLString());
	}
	
	/**
	 * This test enters the normal "enews membership" page and verifies that all expected fields
	 * are present and visible.
	 *  
	 * @throws MalformedURLException
	 * @throws IOException
	 * @throws SAXException
	 */
	public void testEnterEnewsMembershipPage() throws MalformedURLException, IOException, SAXException{
		response1 = get$CharacterAssertedResponse( client, "http://localhost/wxhtml/registerenewsmember?cc=SE&lc=sv");
		writeResponseToFile( response1, "c:\\temp\\testViewEnewsMembershipPage.html");
		
		//Should be a link to the sonyericsson membership
		WebLink fullMemberShipLink = response1.getLinkWithID("nextmembershiplevellink");
		assertNotNull( fullMemberShipLink);
		assertEquals( "/wxhtml/registerfullmember?cc=SE&lc=sv", fullMemberShipLink.getURLString());
		
		assertInputfieldIsPresentAndVisible( response1, "confirmationEmail.emailAddress" );
		assertInputfieldIsPresentAndVisible( response1, "confirmationEmail.checkerEmailAddress" );
		assertInputfieldIsNotPresent( response1, "confirmationPassword.password" );
		assertInputfieldIsNotPresent( response1, "confirmationPassword.checkerPassword" );
		assertInputfieldIsNotPresent( response1, "personalDetails.firstName" );
		assertInputfieldIsNotPresent( response1, "personalDetails.lastName" );
		assertInputfieldIsNotPresent( response1, "personalDetails.gender" );
		assertInputfieldIsNotPresent( response1, "personalDetails.streetAddress" );
		assertInputfieldIsNotPresent( response1, "personalDetails.postalCode" );
		assertInputfieldIsNotPresent( response1, "personalDetails.city" );
		assertInputfieldIsNotPresent( response1, "phoneDetails.brandID" );
		assertInputfieldIsNotPresent( response1, "phoneDetails.operatorID" );
		assertInputfieldIsNotPresent( response1, "phoneDetails.mobilePhoneNumber" );
		assertInputfieldIsNotPresent( response1, "phoneDetails.purchaseDate.month" );
		assertInputfieldIsNotPresent( response1, "phoneDetails.purchaseDate.year" );
		assertInputfieldIsPresentAndVisible( response1, "agree" );
		assertInputfieldIsNotPresent( response1, "phoneDetails.accessoryID" );
		assertInputfieldIsNotPresent( response1, "phoneDetails.accessoryCategoryID" );
		assertInputfieldIsNotPresent( response1, "phoneDetails.productID" );
		assertInputfieldIsPresentAndVisible( response1, "newsLetterSubscriptions.biMonthly" );
		assertInputfieldIsPresentAndVisible( response1, "newsLetterSubscriptions.phoneUpdates" );
		assertInputfieldIsPresentAndVisible( response1, "newsLetterSubscriptions.productLaunches" );
		assertInputfieldIsNotPresent( response1, "personalDetails.birthDate.day" );
		assertInputfieldIsNotPresent( response1, "personalDetails.birthDate.month" );
		assertInputfieldIsNotPresent( response1, "personalDetails.birthDate.year" );
	}


	/**
	 * This test enters the full "sonyericsson membership" page and verifies that all expected fields
	 * are present and visible.
	 *  
	 * @throws MalformedURLException
	 * @throws IOException
	 * @throws SAXException
	 */
	public void testEnterFullMembershipPage() throws MalformedURLException, IOException, SAXException{
		response1 = get$CharacterAssertedResponse( client, "http://localhost/wxhtml/registerfullmember?cc=SE&lc=sv");
		writeResponseToFile( response1, "c:\\temp\\testViewSonyEricssonMembershipPage.html");
		
		printLinks(response1.getLinks());
		
		//Should be a register link
		WebLink registerLink = response1.getLinkWithID("registerLink");
		assertNotNull( registerLink);
		
		//Should be a link to the extended membership
		WebLink extendedMemberShipLink = response1.getLinkWithID("nextmembershiplevellink");
		assertNotNull( extendedMemberShipLink);
		assertEquals( "/wxhtml/registerextendedmember?cc=SE&lc=sv", extendedMemberShipLink.getURLString());

		
		assertInputfieldIsPresentAndVisible( response1, "confirmationEmail.emailAddress" );
		assertInputfieldIsPresentAndVisible( response1, "confirmationEmail.checkerEmailAddress" );
		assertInputfieldIsPresentAndVisible( response1, "confirmationPassword.password" );
		assertInputfieldIsPresentAndVisible( response1, "confirmationPassword.checkerPassword" );
		assertInputfieldIsPresentAndVisible( response1, "personalDetails.firstName" );
		assertInputfieldIsPresentAndVisible( response1, "personalDetails.lastName" );
		assertInputfieldIsPresentAndVisible( response1, "personalDetails.gender" );
		assertInputfieldIsNotPresent( response1, "personalDetails.streetAddress" );
		assertInputfieldIsNotPresent( response1, "personalDetails.postalCode" );
		assertInputfieldIsNotPresent( response1, "personalDetails.city" );
		assertInputfieldIsPresentAndVisible( response1, "phoneDetails.brandID" );
		assertInputfieldIsNotPresent( response1, "phoneDetails.operatorID" );
		assertInputfieldIsNotPresent( response1, "phoneDetails.mobilePhoneNumber" );
		assertInputfieldIsNotPresent( response1, "phoneDetails.purchaseDate.month" );
		assertInputfieldIsNotPresent( response1, "phoneDetails.purchaseDate.year" );
		assertInputfieldIsPresentAndVisible( response1, "agree" );
		assertInputfieldIsPresentAndVisible( response1, "phoneDetails.accessoryID" );
		assertInputfieldIsPresentAndVisible( response1, "phoneDetails.accessoryCategoryID" );
		assertInputfieldIsPresentAndVisible( response1, "phoneDetails.productID" );
		assertInputfieldIsPresentAndVisible( response1, "newsLetterSubscriptions.biMonthly" );
		assertInputfieldIsPresentAndVisible( response1, "newsLetterSubscriptions.phoneUpdates" );
		assertInputfieldIsPresentAndVisible( response1, "newsLetterSubscriptions.productLaunches" );
		assertInputfieldIsPresentAndVisible( response1, "personalDetails.birthDate.day" );
		assertInputfieldIsPresentAndVisible( response1, "personalDetails.birthDate.month" );
		assertInputfieldIsPresentAndVisible( response1, "personalDetails.birthDate.year" );


	}
	/**
	 * This test enters the normal "sonyericsson extended membership" page and verifies that all expected fields
	 * are present and visible.
	 *  
	 * @throws MalformedURLException
	 * @throws IOException
	 * @throws SAXException
	 */
	public void testEnterExtendedMembershipPage() throws MalformedURLException, IOException, SAXException{
		response1 = get$CharacterAssertedResponse( client, "http://localhost/wxhtml/registerextendedmember?cc=SE&lc=sv");
		writeResponseToFile( response1, "c:\\temp\\testViewSonyEricssonExtendedMembershipPage.html");

		//Should not be a link to the extended membership
		WebLink extendedMemberShipLink = response1.getLinkWithID("nextmembershiplevellink");
		assertNull( extendedMemberShipLink);

		writeResponseToFile( response1, "c:\\temp\\testViewSonyEricssonExtendedMembershipPage.html");
		assertInputfieldIsPresentAndVisible( response1, "confirmationEmail.emailAddress" );
		assertInputfieldIsPresentAndVisible( response1, "confirmationEmail.checkerEmailAddress" );
		assertInputfieldIsPresentAndVisible( response1, "confirmationPassword.password" );
		assertInputfieldIsPresentAndVisible( response1, "confirmationPassword.checkerPassword" );
		assertInputfieldIsPresentAndVisible( response1, "personalDetails.firstName" );
		assertInputfieldIsPresentAndVisible( response1, "personalDetails.lastName" );
		assertInputfieldIsPresentAndVisible( response1, "personalDetails.gender" );
		assertInputfieldIsPresentAndVisible( response1, "personalDetails.streetAddress" );
		assertInputfieldIsPresentAndVisible( response1, "personalDetails.postalCode" );
		assertInputfieldIsPresentAndVisible( response1, "personalDetails.city" );
		assertInputfieldIsPresentAndVisible( response1, "phoneDetails.brandID" );
		assertInputfieldIsPresentAndVisible( response1, "phoneDetails.operatorID" );
		assertInputfieldIsPresentAndVisible( response1, "phoneDetails.imeiNumber" );
		assertInputfieldIsPresentAndVisible( response1, "phoneDetails.mobilePhoneNumber" );
		assertInputfieldIsPresentAndVisible( response1, "phoneDetails.purchaseDate.month" );
		assertInputfieldIsPresentAndVisible( response1, "phoneDetails.purchaseDate.year" );
		assertInputfieldIsPresentAndVisible( response1, "agree" );
		assertInputfieldIsPresentAndVisible( response1, "phoneDetails.accessoryID" );
		assertInputfieldIsPresentAndVisible( response1, "phoneDetails.accessoryCategoryID" );
		assertInputfieldIsPresentAndVisible( response1, "phoneDetails.productID" );
		assertInputfieldIsPresentAndVisible( response1, "newsLetterSubscriptions.biMonthly" );
		assertInputfieldIsPresentAndVisible( response1, "newsLetterSubscriptions.phoneUpdates" );
		assertInputfieldIsPresentAndVisible( response1, "newsLetterSubscriptions.productLaunches" );
		assertInputfieldIsPresentAndVisible( response1, "personalDetails.birthDate.day" );
		assertInputfieldIsPresentAndVisible( response1, "personalDetails.birthDate.month" );
		assertInputfieldIsPresentAndVisible( response1, "personalDetails.birthDate.year" );
		
	}
	
	/**
	 * This test enters the extended "sonyericsson extended membership" page and verifies that a state field
	 * is visible when running as a US user. After state is given and the form is submitted the
	 * test checks that the field now is hidden but contains the given state.  
	 * @throws MalformedURLException
	 * @throws IOException
	 * @throws SAXException
	 */
	public void testSubmitExtendedMembershipOnlyUSStateGiven() throws MalformedURLException, IOException, SAXException{
		response1 = get$CharacterAssertedResponse( client, "http://localhost/wxhtml/registerextendedmember?cc=US&lc=en");
		writeResponseToFile( response1, "c:\\temp\\testViewSonyEricssonExtendedMembershipPageUSStateGiven_beforeSubmit.html");
		assertInputfieldIsPresentAndVisible( response1, "personalDetails.state" );
		WebForm signUpForm = response1.getFormWithID("signupUserForm");
		signUpForm.setParameter("personalDetails.state", "NE");
		WebResponse response2 = this.get$CharacterAssertedResponseFromFormSubmit(signUpForm);
		writeResponseToFile( response2, "c:\\temp\\testViewSonyEricssonExtendedMembershipPageUSStateGiven_afterSubmit.html");
		assertInputfieldIsPresentAndHiddenWithValue(response2, "personalDetails.state", "NE");
	}

		
	/**
	 * This test enters the "sonyericsson full membership" page, fill in valid values and
	 * submits the form. Then checks that welcome text is being presented.  
	 *  
	 * @throws MalformedURLException
	 * @throws IOException
	 * @throws SAXException
	 */
	public void testRegisterFullMembershipSuccessfully() throws MalformedURLException, IOException, SAXException{
		
		response1 = get$CharacterAssertedResponse( client, "http://localhost/wxhtml/registerfullmember?cc=SE&lc=sv");
		writeResponseToFile(response1, "c:\\temp\\testRegisterFullMembershipSuccessfully_BeforeSubmit.html");
		
		WebForm signUpUserForm = response1.getFormWithID("signupUserForm");
		
		String emailUsed = getUniqueEmail();
		signUpUserForm.setParameter("confirmationPassword.password", "jonas");
		signUpUserForm.setParameter("confirmationPassword.checkerPassword", "jonas");
		signUpUserForm.setParameter("confirmationEmail.emailAddress", emailUsed);
		signUpUserForm.setParameter("confirmationEmail.checkerEmailAddress", emailUsed);
		
		signUpUserForm.setParameter("phoneDetails.brandID", "1");
		signUpUserForm.setParameter("phoneDetails.productID", "10172");
		signUpUserForm.setParameter("personalDetails.gender", "m");
		
		signUpUserForm.setParameter("newsLetterSubscriptions.biMonthly", "true");
		signUpUserForm.setParameter("newsLetterSubscriptions.phoneUpdates", "true");
		signUpUserForm.setParameter("newsLetterSubscriptions.productLaunches", "true");
		
		signUpUserForm.setParameter("personalDetails.firstName", "Jonas");
		signUpUserForm.setParameter("personalDetails.lastName", "Ahlgren" );
		signUpUserForm.setParameter("personalDetails.birthDate.year", "1971");
		signUpUserForm.setParameter("personalDetails.birthDate.month", "1");
		signUpUserForm.setParameter("personalDetails.birthDate.day", "13");

		signUpUserForm.setParameter("agree", "true");
				
		String action = signUpUserForm.getAction();
		System.out.println( "action = " + action);
		
		WebResponse response2 = get$CharacterAssertedResponseFromFormSubmit(signUpUserForm);
		
		writeResponseToFile( response2, "c:\\temp\\testRegisterFullMembershipSuccessfully_AfterSubmit.html");
		//Check if welcome text is being presented...
		assertTrue( StringUtils.contains(response2.getText(), "Thank you for registering with Sony Ericsson!"));
	}
	/**
	 * This test enters the "sonyericsson extended membership" page, fill in valid values and
	 * submits the form. Then checks that welcome text is being presented.  
	 *  
	 * @throws MalformedURLException
	 * @throws IOException
	 * @throws SAXException
	 */
	public void testRegisterExtendedMembershipSuccessfully() throws MalformedURLException, IOException, SAXException{
		
		response1 = get$CharacterAssertedResponse( client, "http://localhost/wxhtml/registerextendedmember?cc=SE&lc=sv");
		writeResponseToFile(response1, "c:\\temp\\testRegisterExtendedMembershipSuccessfully_BeforeSubmit.html");
		
		WebForm signUpUserForm = response1.getFormWithID("signupUserForm");
		
		String emailUsed = getUniqueEmail();
		signUpUserForm.setParameter("confirmationPassword.password", "jonas");
		signUpUserForm.setParameter("confirmationPassword.checkerPassword", "jonas");
		signUpUserForm.setParameter("confirmationEmail.emailAddress", emailUsed);
		signUpUserForm.setParameter("confirmationEmail.checkerEmailAddress", emailUsed);

		signUpUserForm.setParameter("personalDetails.firstName", "Jonas");
		signUpUserForm.setParameter("personalDetails.lastName", "Ahlgren" );
		signUpUserForm.setParameter("personalDetails.gender", "m");
		signUpUserForm.setParameter("personalDetails.streetAddress", "Fleminggatan 20");
		signUpUserForm.setParameter("personalDetails.postalCode", "11245");
		signUpUserForm.setParameter("personalDetails.city", "STOCKHOLM");

		signUpUserForm.setParameter("phoneDetails.brandID", "1");
		signUpUserForm.setParameter("phoneDetails.productID", "10172");
		signUpUserForm.setParameter("phoneDetails.operatorID", "294");
		signUpUserForm.setParameter("phoneDetails.mobilePhoneNumber", "+34234324324");
		signUpUserForm.setParameter("phoneDetails.purchaseDate.year", "2005");
		signUpUserForm.setParameter("phoneDetails.purchaseDate.month", "07");
		signUpUserForm.setParameter("phoneDetails.imeiNumber", "111111--11-111111-1");
		
		signUpUserForm.setParameter("personalDetails.birthDate.year", "1971");
		signUpUserForm.setParameter("personalDetails.birthDate.month", "1");
		signUpUserForm.setParameter("personalDetails.birthDate.day", "13");
		
		signUpUserForm.setParameter("newsLetterSubscriptions.biMonthly", "true");
		signUpUserForm.setParameter("newsLetterSubscriptions.phoneUpdates", "true");
		signUpUserForm.setParameter("newsLetterSubscriptions.productLaunches", "true");
		

		signUpUserForm.setParameter("agree", "true");
				
		String action = signUpUserForm.getAction();
		System.out.println( "action = " + action);
		
		WebResponse response2 = get$CharacterAssertedResponseFromFormSubmit(signUpUserForm);
		
		writeResponseToFile( response2, "c:\\temp\\testRegisterExtendedMembershipSuccessfully_AfterSubmit.html");
		//Check if welcome text is being presented...
		assertTrue( StringUtils.contains(response2.getText(), "Thank you for registering with Sony Ericsson!"));

	}


	
	
	/**
	 * This test enters the "Enews membership" page, fill in valid values and
	 * submits the form. Then checks that welcome text is being presented.  
	 *  
	 * @throws MalformedURLException
	 * @throws IOException
	 * @throws SAXException
	 */
	public void testRegisterEnewsMembershipSuccessfully() throws MalformedURLException, IOException, SAXException{
		
		response1 = get$CharacterAssertedResponse( client, "http://localhost/wxhtml/registerenewsmember?cc=SE&lc=sv");
		writeResponseToFile(response1, "c:\\temp\\testRegisterEnewsMembershipSuccessfully_BeforeSubmit.html");
		
		WebForm signUpUserForm = response1.getFormWithID("signupUserForm");
		
		String emailUsed = getUniqueEmail();
		signUpUserForm.setParameter("confirmationEmail.emailAddress", emailUsed);
		signUpUserForm.setParameter("confirmationEmail.checkerEmailAddress", emailUsed);		
		signUpUserForm.setParameter("newsLetterSubscriptions.biMonthly", "true");
		signUpUserForm.setParameter("newsLetterSubscriptions.phoneUpdates", "true");
		signUpUserForm.setParameter("newsLetterSubscriptions.productLaunches", "true");
		
		signUpUserForm.setParameter("agree", "true");
		
		String action = signUpUserForm.getAction();
		System.out.println( "action = " + action);
		
		WebResponse response2 = get$CharacterAssertedResponseFromFormSubmit(signUpUserForm);
		
		writeResponseToFile( response2, "c:\\temp\\testRegisterEnewsMembershipSuccessfully_AfterSubmit.html");
		//Check if welcome text is being presented...
		assertTrue( StringUtils.contains(response2.getText(), "Thank you for registering with Sony Ericsson!"));

	}
	
	/**
	 * This test enters the "Enews membership" page, not choose any newsletter subscription
	 * and submits the form. Then checks that the newsletter subscription checkboxes are shown.
	 *(user must at least choose one newsletter subscription).  
	 *  
	 * @throws MalformedURLException
	 * @throws IOException
	 * @throws SAXException
	 */
	public void testRegisterEnewsMembershipNoNewsLetterSubscriptionChoosen() throws MalformedURLException, IOException, SAXException{
		
		response1 = get$CharacterAssertedResponse( client, "http://localhost/wxhtml/registerenewsmember?cc=SE&lc=sv");
		writeResponseToFile(response1, "c:\\temp\\testRegisterEnewsMembershipNoNewsLetterSubscriptionChoosen_BeforeSubmit.html");
		
		WebForm signUpUserForm = response1.getFormWithID("signupUserForm");
		
		String emailUsed = getUniqueEmail();
		signUpUserForm.setParameter("confirmationEmail.emailAddress", emailUsed);
		signUpUserForm.setParameter("confirmationEmail.checkerEmailAddress", emailUsed);		
		
		signUpUserForm.setParameter("agree", "true");
		WebResponse response2 = get$CharacterAssertedResponseFromFormSubmit(signUpUserForm);
		writeResponseToFile( response2, "c:\\temp\\testRegisterEnewsMembershipNoNewsLetterSubscriptionChoosen_AfterSubmit.html");
		assertInputfieldIsPresentAndVisible( response2, "newsLetterSubscriptions.biMonthly");
		assertInputfieldIsPresentAndVisible( response2, "newsLetterSubscriptions.phoneUpdates");
		assertInputfieldIsPresentAndVisible( response2, "newsLetterSubscriptions.productLaunches");
	}
	
	/**
	 * This test enters the "Full membership" page, not choose any newsletter subscription
	 * and submits the form. Then checks that the newsletter subscription checkboxes are hidden.
	 *(newsletter subscription not mandatory).  
	 *  
	 * @throws MalformedURLException
	 * @throws IOException
	 * @throws SAXException
	 */
	public void testRegisterFullMembershipNoNewsLetterSubscriptionChoosen() throws MalformedURLException, IOException, SAXException{
		
		response1 = get$CharacterAssertedResponse( client, "http://localhost/wxhtml/registerfullmember?cc=SE&lc=sv");
		writeResponseToFile(response1, "c:\\temp\\testRegisterFullMembershipNoNewsLetterSubscriptionChoosen_BeforeSubmit.html");
		
		WebForm signUpUserForm = response1.getFormWithID("signupUserForm");
		
		WebResponse response2 = get$CharacterAssertedResponseFromFormSubmit(signUpUserForm);
		writeResponseToFile( response2, "c:\\temp\\testRegisterFullMembershipNoNewsLetterSubscriptionChoosen_AfterSubmit.html");
		assertInputfieldIsPresentAndHidden( response2, "newsLetterSubscriptions.biMonthly");
		assertInputfieldIsPresentAndHidden( response2, "newsLetterSubscriptions.phoneUpdates");
		assertInputfieldIsPresentAndHidden( response2, "newsLetterSubscriptions.productLaunches");
	}

	
	/**
	 * This test enters the extended "sonyericsson extended membership" page, fill in NO values and 
	 * submits the form. Then the test verifies that all the fields are visible so the user will be
	 * able to fill them in. 
	 *  
	 * @throws MalformedURLException
	 * @throws IOException
	 * @throws SAXException
	 */
	public void testSubmitExtendedMembershipNoValuesGiven() throws MalformedURLException, IOException, SAXException{
		
		response1 = get$CharacterAssertedResponse( client, "http://localhost/wxhtml/registerextendedmember?cc=SE&lc=sv");
		writeResponseToFile( response1, "c:\\temp\\testRegisterSonyEricssonExtendedShowAllErrors_BeforeSubmit.html");

		WebForm signUpUserForm = response1.getFormWithID("signupUserForm");
		//assertActionSupportFormParameters( signUpUserForm, SignupAction.class );
		
		assertDollarSignsInResponse( response1 );
		
		
		WebResponse response2 = get$CharacterAssertedResponseFromFormSubmit(signUpUserForm);
		writeResponseToFile( response2, "c:\\temp\\testRegisterSonyEricssonExtendedShowAllErrors_AfterSubmit.html");
		
		assertInputfieldIsPresentAndVisible( response2, "confirmationEmail.emailAddress" );
		assertInputfieldIsPresentAndVisible( response2, "confirmationEmail.checkerEmailAddress" );
		assertInputfieldIsPresentAndVisible( response2, "confirmationPassword.password" );
		assertInputfieldIsPresentAndVisible( response2, "confirmationPassword.checkerPassword" );
		assertInputfieldIsPresentAndVisible( response2, "personalDetails.firstName" );
		assertInputfieldIsPresentAndVisible( response2, "personalDetails.lastName" );
		assertInputfieldIsPresentAndVisible( response2, "personalDetails.gender" );
		assertInputfieldIsPresentAndVisible( response2, "personalDetails.streetAddress" );
		assertInputfieldIsPresentAndVisible( response2, "personalDetails.postalCode" );
		assertInputfieldIsPresentAndVisible( response2, "personalDetails.city" );
		assertInputfieldIsPresentAndVisible( response2, "phoneDetails.brandID" );
		assertInputfieldIsPresentAndVisible( response2, "phoneDetails.operatorID" );
		assertInputfieldIsPresentAndVisible( response2, "phoneDetails.mobilePhoneNumber" );
		assertInputfieldIsPresentAndVisible( response2, "phoneDetails.imeiNumber" );
		assertInputfieldIsPresentAndVisible( response2, "phoneDetails.purchaseDate.month" );
		assertInputfieldIsPresentAndVisible( response2, "phoneDetails.purchaseDate.year" );
		assertInputfieldIsPresentAndVisible( response2, "agree" );
		assertInputfieldIsPresentAndHiddenWithValue( response2, "phoneDetails.accessoryID", "-1" );
		assertInputfieldIsPresentAndHiddenWithValue( response2, "phoneDetails.accessoryCategoryID", "-1" );
		assertInputfieldIsPresentAndVisible( response2, "phoneDetails.productID" );
		assertInputfieldIsPresentAndVisible( response1, "newsLetterSubscriptions.biMonthly" );
		assertInputfieldIsPresentAndVisible( response1, "newsLetterSubscriptions.phoneUpdates" );
		assertInputfieldIsPresentAndVisible( response1, "newsLetterSubscriptions.productLaunches" );
	}

	

	/**
	 * This test enters the "sonyericsson extended membership" page, fill in email and confirmation email
	 * that do not match, and then submits the form. Then the test verifies that both 
	 * the email fields are shown.
	 *  
	 * @throws MalformedURLException
	 * @throws IOException
	 * @throws SAXException
	 */
	public void testSubmitExtendedMembershipNotEqualEmailsGiven() throws MalformedURLException, IOException, SAXException{
		response1 = get$CharacterAssertedResponse( client, "http://localhost/wxhtml/registerextendedmember?cc=SE&lc=sv");
		writeResponseToFile( response1, "c:\\temp\\testSubmitExtendedMembershipNotEqualEmailsGiven_BeforeSubmit.html");
		
		WebForm signUpUserForm = response1.getFormWithID("signupUserForm");
		
		signUpUserForm.setParameter("confirmationPassword.password", "hello");
		signUpUserForm.setParameter("confirmationPassword.checkerPassword", "hello");
		signUpUserForm.setParameter("confirmationEmail.emailAddress", "");
		signUpUserForm.setParameter("confirmationEmail.checkerEmailAddress", "jonasahlgren1@hotmail.com");
		signUpUserForm.setParameter("phoneDetails.brandID", "1");
		//signUpUserForm.setParameter("phoneDetails.accessoryCategoryID", "113");
		//signUpUserForm.setParameter("phoneDetails.accessoryID", "8811");
		signUpUserForm.setParameter("phoneDetails.productID", "10172");
		signUpUserForm.setParameter("personalDetails.gender", "m");
		signUpUserForm.setParameter("newsLetterSubscriptions.biMonthly", "true");
		signUpUserForm.setParameter("newsLetterSubscriptions.phoneUpdates", "true");
		signUpUserForm.setParameter("newsLetterSubscriptions.productLaunches", "true");
		signUpUserForm.setParameter("personalDetails.firstName", "Jonas");
		signUpUserForm.setParameter("personalDetails.lastName", "Ahlgren");
		signUpUserForm.setParameter("agree", "true");
		signUpUserForm.setParameter("personalDetails.streetAddress", "Fleminggatan20");
		signUpUserForm.setParameter("personalDetails.postalCode", "11245");
		signUpUserForm.setParameter("personalDetails.city", "STOCKHOLM");
		signUpUserForm.setParameter("phoneDetails.brandID", "1");
		signUpUserForm.setParameter("phoneDetails.operatorID", "294");
		signUpUserForm.setParameter("phoneDetails.mobilePhoneNumber", "+10101010");
		signUpUserForm.setParameter("phoneDetails.purchaseDate.month", "10");
		signUpUserForm.setParameter("phoneDetails.purchaseDate.year", "2005");
		signUpUserForm.setParameter("personalDetails.birthDate.year", "1971");
		signUpUserForm.setParameter("personalDetails.birthDate.month", "1");
		signUpUserForm.setParameter("personalDetails.birthDate.day", "13");
		
		
		WebResponse response2 = get$CharacterAssertedResponseFromFormSubmit(signUpUserForm );
		writeResponseToFile( response2, "c:\\temp\\testSubmitExtendedMembershipNotEqualEmailsGiven_AfterSubmit.html");
		
		assertInputfieldIsPresentAndVisibleWithValue( response2, "confirmationEmail.emailAddress", "" ); //Shown because of email validation error
		assertInputfieldIsPresentAndVisibleWithValue( response2, "confirmationEmail.checkerEmailAddress", "jonasahlgren1@hotmail.com" ); //Shown because of email validation error
		
		assertInputfieldIsPresentAndHiddenWithValue( response2, "confirmationPassword.checkerPassword", "hello" );
		assertInputfieldIsPresentAndHiddenWithValue( response2, "confirmationPassword.password", "hello" );
		assertInputfieldIsPresentAndHiddenWithValue( response2, "personalDetails.firstName", "Jonas" );
		assertInputfieldIsPresentAndHiddenWithValue( response2, "personalDetails.lastName", "Ahlgren" );
		assertInputfieldIsPresentAndHiddenWithValue( response2, "personalDetails.gender", "m" );
		assertInputfieldIsPresentAndHiddenWithValue( response2, "personalDetails.streetAddress", "Fleminggatan20");
		assertInputfieldIsPresentAndHiddenWithValue( response2, "personalDetails.postalCode", "11245" );
		assertInputfieldIsPresentAndHiddenWithValue( response2, "personalDetails.city", "STOCKHOLM" );
		assertInputfieldIsPresentAndHiddenWithValue( response2, "phoneDetails.brandID", "1" );
		assertInputfieldIsPresentAndHiddenWithValue( response2, "phoneDetails.operatorID", "294" );
		assertInputfieldIsPresentAndHiddenWithValue( response2, "phoneDetails.mobilePhoneNumber", "+10101010" );
		assertInputfieldIsPresentAndHiddenWithValue( response2, "phoneDetails.purchaseDate.month", "10" );
		assertInputfieldIsPresentAndHiddenWithValue( response2, "phoneDetails.purchaseDate.year", "2005" );
		assertInputfieldIsPresentAndHiddenWithValue( response2, "agree", "true" );
		assertInputfieldIsPresentAndHidden( response2, "phoneDetails.accessoryID");
		assertInputfieldIsPresentAndHidden( response2, "phoneDetails.accessoryCategoryID");
		assertInputfieldIsPresentAndHiddenWithValue( response2, "phoneDetails.productID", "10172" );
		assertInputfieldIsPresentAndHiddenWithValue( response2, "newsLetterSubscriptions.biMonthly", "true" );
		assertInputfieldIsPresentAndHiddenWithValue( response2, "newsLetterSubscriptions.phoneUpdates", "true" );
		assertInputfieldIsPresentAndHiddenWithValue( response2, "newsLetterSubscriptions.productLaunches", "true" );
		assertInputfieldIsPresentAndHiddenWithValue( response2, "personalDetails.birthDate.year", "1971");
		assertInputfieldIsPresentAndHiddenWithValue( response2, "personalDetails.birthDate.month", "01");
		assertInputfieldIsPresentAndHiddenWithValue( response2, "personalDetails.birthDate.day", "13" );

	}
	
	/**
	 * This test enters the "sonyericsson extended membership" page, fill in email that is not unique
	 * and then submits the form. Then the test verifies that the correct message are shown.
	 *  
	 * @throws MalformedURLException
	 * @throws IOException
	 * @throws SAXException
	 */
	public void testSubmitExtendedMembershipNotUniqueEmailGiven() throws MalformedURLException, IOException, SAXException{
		response1 = get$CharacterAssertedResponse( client, "http://localhost/wxhtml/registerfullmember?cc=SE&lc=sv");
		writeResponseToFile(response1, "c:\\temp\\testSubmitExtendedMembershipNotUniqueEmailGiven_BeforeSubmit.html");
		
		WebForm signUpUserForm = response1.getFormWithID("signupUserForm");
		
		signUpUserForm.setParameter("confirmationPassword.password", "jonas");
		signUpUserForm.setParameter("confirmationPassword.checkerPassword", "jonas");
		signUpUserForm.setParameter("confirmationEmail.emailAddress", "jonasahlgren@hotmail.com");
		signUpUserForm.setParameter("confirmationEmail.checkerEmailAddress", "jonasahlgren@hotmail.com");
		
		signUpUserForm.setParameter("phoneDetails.brandID", "1");
		signUpUserForm.setParameter("phoneDetails.productID", "10172");
		signUpUserForm.setParameter("personalDetails.gender", "m");
		
		signUpUserForm.setParameter("newsLetterSubscriptions.biMonthly", "true");
		signUpUserForm.setParameter("newsLetterSubscriptions.phoneUpdates", "true");
		signUpUserForm.setParameter("newsLetterSubscriptions.productLaunches", "true");
		
		signUpUserForm.setParameter("personalDetails.firstName", "Jonas");
		signUpUserForm.setParameter("personalDetails.lastName", "Ahlgren" );
		signUpUserForm.setParameter("personalDetails.birthDate.year", "1971");
		signUpUserForm.setParameter("personalDetails.birthDate.month", "1");
		signUpUserForm.setParameter("personalDetails.birthDate.day", "13");

		signUpUserForm.setParameter("agree", "true");
				
		String action = signUpUserForm.getAction();
		System.out.println( "action = " + action);
		
		WebResponse response2 = get$CharacterAssertedResponseFromFormSubmit(signUpUserForm);
		
		writeResponseToFile( response2, "c:\\temp\\testSubmitExtendedMembershipNotUniqueEmailGiven_AfterSubmit.html");
		//Check if welcome text is being presented...
		assertNotNull( response2.getElementWithID("email_address_exists"));
	}

	
	/**
	 * This test enters the "sonyericsson extended membership" page, fills in correct birthday,  
	 * submits and verifies that the birthday values exist in hidden fields.
	 *  
	 * @throws MalformedURLException
	 * @throws IOException
	 * @throws SAXException
	 */
	public void testSubmitExtendedMembershipOnlyBirthdayGiven() throws MalformedURLException, IOException, SAXException{
		response1 = get$CharacterAssertedResponse( client, "http://localhost/wxhtml/registerextendedmember?cc=SE&lc=sv");
		writeResponseToFile( response1, "c:\\temp\\testRegisterExtendedMembershipEmailpersonalDetails.firstNamepersonalDetails.lastNameError_BeforeSubmit.html");
		
		WebForm signUpUserForm1 = response1.getFormWithID("signupUserForm");
		//submit empty form
		WebResponse response2 = get$CharacterAssertedResponseFromFormSubmit(signUpUserForm1 );
		writeResponseToFile( response2, "c:\\temp\\testRegisterExtendedMembershipEmailpersonalDetails.firstNamepersonalDetails.lastNameError_AfterSubmit.html");

		WebForm signUpUserForm2 = response2.getFormWithID("signupUserForm");
		
		//Give birthday and submit
		signUpUserForm2.setParameter("personalDetails.birthDate.year", "1975");
		signUpUserForm2.setParameter("personalDetails.birthDate.month", "01");
		signUpUserForm2.setParameter("personalDetails.birthDate.day", "13");
		WebResponse response3 = get$CharacterAssertedResponseFromFormSubmit(signUpUserForm2 );
		writeResponseToFile( response3, "c:\\temp\\testRegisterExtendedMembershipEmailpersonalDetails.firstNamepersonalDetails.lastNameError_AfterSubmit.html");
		
		assertInputfieldIsPresentAndHiddenWithValue( response3, "personalDetails.birthDate.year", "1975");
		assertInputfieldIsPresentAndHiddenWithValue( response3, "personalDetails.birthDate.month", "01");
		assertInputfieldIsPresentAndHiddenWithValue( response3, "personalDetails.birthDate.day", "13" );

	}


	/**
	 * This test enters the "sonyericsson extended membership" page, fill in wrong email format, 
	 * submits and verifies that all expected fields with values exists.
	 *  
	 * @throws MalformedURLException
	 * @throws IOException
	 * @throws SAXException
	 */
	public void testSubmitExtendedMembershipIncorrectEmailFormatGiven() throws MalformedURLException, IOException, SAXException{
		response1 = get$CharacterAssertedResponse( client, "http://localhost/wxhtml/registerextendedmember?cc=SE&lc=sv");
		writeResponseToFile( response1, "c:\\temp\\testRegisterSonyEricssonEmailError_BeforeSubmit.html");
		
		WebForm signUpUserForm = response1.getFormWithID("signupUserForm");
		
		signUpUserForm.setParameter("confirmationPassword.password", "hello");
		signUpUserForm.setParameter("confirmationPassword.checkerPassword", "hello");
		signUpUserForm.setParameter("confirmationEmail.emailAddress", "jonasah lgren1@hotmail.com");
		signUpUserForm.setParameter("confirmationEmail.checkerEmailAddress", "jonasah lgfren1@hotmail.com");
		signUpUserForm.setParameter("phoneDetails.brandID", "1");
		//signUpUserForm.setParameter("phoneDetails.accessoryCategoryID", "113");
		//signUpUserForm.setParameter("phoneDetails.accessoryID", "8811");
		signUpUserForm.setParameter("phoneDetails.productID", "10172");
		signUpUserForm.setParameter("personalDetails.gender", "m");
		signUpUserForm.setParameter("newsLetterSubscriptions.biMonthly", "true");
		signUpUserForm.setParameter("newsLetterSubscriptions.phoneUpdates", "true");
		signUpUserForm.setParameter("newsLetterSubscriptions.productLaunches", "true");
		signUpUserForm.setParameter("personalDetails.firstName", "Jonas");
		signUpUserForm.setParameter("personalDetails.lastName", "Ahlgren");
		signUpUserForm.setParameter("agree", "true");
		signUpUserForm.setParameter("personalDetails.streetAddress", "Fleminggatan20");
		signUpUserForm.setParameter("personalDetails.postalCode", "11245");
		signUpUserForm.setParameter("personalDetails.city", "STOCKHOLM");
		signUpUserForm.setParameter("phoneDetails.brandID", "1");
		signUpUserForm.setParameter("phoneDetails.operatorID", "294");
		signUpUserForm.setParameter("phoneDetails.mobilePhoneNumber", "+10101010");
		signUpUserForm.setParameter("phoneDetails.purchaseDate.month", "10");
		signUpUserForm.setParameter("phoneDetails.purchaseDate.year", "2005");
		signUpUserForm.setParameter("personalDetails.birthDate.year", "1975");
		signUpUserForm.setParameter("personalDetails.birthDate.month", "1");
		signUpUserForm.setParameter("personalDetails.birthDate.day", "13");
		
		
		WebResponse response2 = get$CharacterAssertedResponseFromFormSubmit(signUpUserForm );
		writeResponseToFile( response2, "c:\\temp\\testRegisterSonyEricssonEmailError_AfterSubmit.html");
		
		assertInputfieldIsPresentAndVisible( response2, "confirmationEmail.emailAddress" ); //Shown because of email validation error
		assertInputfieldIsPresentAndVisible( response2, "confirmationEmail.checkerEmailAddress" ); //Shown because of email validation error
		
		assertInputfieldIsPresentAndHidden( response2, "confirmationPassword.checkerPassword" );
		assertInputfieldIsPresentAndHidden( response2, "confirmationPassword.password" );
		assertInputfieldIsPresentAndHiddenWithValue( response2, "personalDetails.firstName", "Jonas" );
		assertInputfieldIsPresentAndHiddenWithValue( response2, "personalDetails.lastName", "Ahlgren" );
		assertInputfieldIsPresentAndHiddenWithValue( response2, "personalDetails.gender", "m" );
		assertInputfieldIsPresentAndHiddenWithValue( response2, "personalDetails.streetAddress", "Fleminggatan20");
		assertInputfieldIsPresentAndHiddenWithValue( response2, "personalDetails.postalCode", "11245" );
		assertInputfieldIsPresentAndHiddenWithValue( response2, "personalDetails.city", "STOCKHOLM" );
		assertInputfieldIsPresentAndHiddenWithValue( response2, "phoneDetails.brandID", "1" );
		assertInputfieldIsPresentAndHiddenWithValue( response2, "phoneDetails.operatorID", "294" );
		assertInputfieldIsPresentAndHiddenWithValue( response2, "phoneDetails.mobilePhoneNumber", "+10101010" );
		assertInputfieldIsPresentAndHiddenWithValue( response2, "phoneDetails.purchaseDate.month", "10" );
		assertInputfieldIsPresentAndHiddenWithValue( response2, "phoneDetails.purchaseDate.year", "2005" );
		assertInputfieldIsPresentAndHiddenWithValue( response2, "agree", "true" );
		assertInputfieldIsPresentAndHiddenWithValue( response2, "phoneDetails.accessoryID", "-1" );
		assertInputfieldIsPresentAndHiddenWithValue( response2, "phoneDetails.accessoryCategoryID", "-1" );
		assertInputfieldIsPresentAndHiddenWithValue( response2, "phoneDetails.productID", "10172" );
		assertInputfieldIsPresentAndHiddenWithValue( response2, "newsLetterSubscriptions.biMonthly", "true" );
		assertInputfieldIsPresentAndHiddenWithValue( response2, "newsLetterSubscriptions.phoneUpdates", "true" );
		assertInputfieldIsPresentAndHiddenWithValue( response2, "newsLetterSubscriptions.productLaunches", "true" );
		assertInputfieldIsPresentAndHiddenWithValue( response2, "personalDetails.birthDate.year", "1975");
		assertInputfieldIsPresentAndHiddenWithValue( response2, "personalDetails.birthDate.month", "01");
		assertInputfieldIsPresentAndHiddenWithValue( response2, "personalDetails.birthDate.day", "13" );

	}
	
	/**
	 * Checks that bort purchase dates fields are shown when only error on purchase month.  
	 *  
	 * @throws MalformedURLException
	 * @throws IOException
	 * @throws SAXException
	 */
	public void testSubmitExtendedMembershipWrongPurchaseMonthGiven() throws MalformedURLException, IOException, SAXException{
		response1 = get$CharacterAssertedResponse( client, "http://localhost/wxhtml/registerextendedmember?cc=SE&lc=sv");
		WebForm signUpUserForm = response1.getFormWithID("signupUserForm");
		
		signUpUserForm.setParameter("phoneDetails.purchaseDate.month", "13");
		signUpUserForm.setParameter("phoneDetails.purchaseDate.year", "2005");
			
		WebResponse response2 = get$CharacterAssertedResponseFromFormSubmit(signUpUserForm );
		assertInputfieldIsPresentAndVisibleWithValue( response2, "phoneDetails.purchaseDate.month", "13" ); //Shown because of validation error
		assertInputfieldIsPresentAndVisibleWithValue( response2, "phoneDetails.purchaseDate.year", "2005" ); //Shown because of validation error
	}

	/**
	 * Checks that bort purchase dates fields are shown when only error on purchase year.  
	 *  
	 * @throws MalformedURLException
	 * @throws IOException
	 * @throws SAXException
	 */
	public void testSubmitExtendedMembershipWrongPurchaseYearGiven() throws MalformedURLException, IOException, SAXException{
		response1 = get$CharacterAssertedResponse( client, "http://localhost/wxhtml/registerextendedmember?cc=SE&lc=sv");
		WebForm signUpUserForm = response1.getFormWithID("signupUserForm");
		
		signUpUserForm.setParameter("phoneDetails.purchaseDate.month", "12");
		signUpUserForm.setParameter("phoneDetails.purchaseDate.year", "05");
			
		WebResponse response2 = get$CharacterAssertedResponseFromFormSubmit(signUpUserForm );
		writeResponseToFile( response2, "c:\\temp\\testSubmitExtendedMembershipWrongPurchaseYearGiven_AfterSubmit.html");
		assertInputfieldIsPresentAndVisibleWithValue( response2, "phoneDetails.purchaseDate.month", "12" ); //Shown because of validation error
		assertInputfieldIsPresentAndVisibleWithValue( response2, "phoneDetails.purchaseDate.year", "05" ); //Shown because of validation error
		
	}
	
	/**
	 * Checks that state are shown when not given for a US user.  
	 *  
	 * @throws MalformedURLException
	 * @throws IOException
	 * @throws SAXException
	 */
	public void testSubmitExtendedMembershipNoStateGivenForUSUser() throws MalformedURLException, IOException, SAXException{
		response1 = get$CharacterAssertedResponse( client, "http://localhost/wxhtml/registerextendedmember?cc=US&lc=en");
		WebForm signUpUserForm = response1.getFormWithID("signupUserForm");
		signUpUserForm.setParameter("personalDetails.firstName", "Pelle");
		signUpUserForm.setParameter("personalDetails.city", "Stockholm");
		signUpUserForm.setParameter("doRegister", "true");
		
		WebResponse response2 = get$CharacterAssertedResponseFromFormSubmit(signUpUserForm );
		writeResponseToFile( response2, "c:\\temp\\testSubmitExtendedMembershipNoStateGivenForUSUser_AfterSubmit.html");
		assertInputfieldIsPresentAndVisible( response2, "personalDetails.state"); //Shown because of validation error
	}

	/**
	 * Checks that state are hidden when given for a US user.  
	 *  
	 * @throws MalformedURLException
	 * @throws IOException
	 * @throws SAXException
	 */
	public void testSubmitExtendedMembershipStateGivenForUSUser() throws MalformedURLException, IOException, SAXException{
		response1 = get$CharacterAssertedResponse( client, "http://localhost/wxhtml/registerextendedmember?cc=US&lc=en");
		WebForm signUpUserForm = response1.getFormWithID("signupUserForm");
		signUpUserForm.setParameter("personalDetails.state", "NE");
		
		WebResponse response2 = get$CharacterAssertedResponseFromFormSubmit(signUpUserForm );
		writeResponseToFile( response2, "c:\\temp\\testSubmitExtendedMembershipStateGivenForUSUser_AfterSubmit.html");
		assertInputfieldIsPresentAndHiddenWithValue( response2, "personalDetails.state", "NE"); //Shown because of validation error
	}
	
	/**
	 * This test enters the extended "sonyericsson extended membership" page and verifies that a state field
	 * is not visible when running as a NON US user.  
	 * @throws MalformedURLException
	 * @throws IOException
	 * @throws SAXException
	 */
	public void testEnterExtendedMembershipUSStateNotShown() throws MalformedURLException, IOException, SAXException{
		response1 = get$CharacterAssertedResponse( client, "http://localhost/wxhtml/registerextendedmember?cc=SE&lc=sv");
		assertInputfieldIsNotPresent( response1, "personalDetails.state" );
	}
	/**
	 * This test enters the "sonyericsson full membership" page, fills in not equal values for 
	 * 'password' and 'verify password' fields and submits the form. Then the test verifies that 
	 * the 'password' and 'verify password' fields are visible so the user can correct the mistake. 
	 *  
	 * @throws MalformedURLException
	 * @throws IOException
	 * @throws SAXException
	 */
	public void testSubmitFullMembershopNotEqualPasswordsGiven() throws MalformedURLException, IOException, SAXException{
		
		response1 = get$CharacterAssertedResponse( client, "http://localhost/wxhtml/registerfullmember?cc=SE&lc=sv");
		writeResponseToFile( response1, "c:\\temp\\testRegisterSonyEricssonPasswordError_BeforeSubmit.html");
		
		WebForm signUpUserForm = response1.getFormWithID("signupUserForm");
		
		String[] parameterNames = signUpUserForm.getParameterNames();
		for (int i = 0; i < parameterNames.length; i++) {
			System.out.println( parameterNames[i]);
		}
		signUpUserForm.setParameter("confirmationPassword.password", "hello");
		signUpUserForm.setParameter("confirmationPassword.checkerPassword", "goodbye");
		
		WebResponse response2 = get$CharacterAssertedResponseFromFormSubmit(signUpUserForm);
		writeResponseToFile( response2, "c:\\temp\\testRegisterSonyEricssonPasswordError_AfterSubmit.html");

		assertInputfieldIsPresentAndVisibleWithValue( response2, "confirmationPassword.password", "" );
		assertInputfieldIsPresentAndVisibleWithValue( response2, "confirmationPassword.checkerPassword", "" );
	}
	

	/**
	 * This test enters the "sonyericsson extended membership" page, fills in the brand 'other' and submits the form. 
	 * Then the test verifies that the brand, product and imeinumber fields is hidden. 
	 *  
	 * @throws MalformedURLException
	 * @throws IOException
	 * @throws SAXException
	 */
	public void testSubmitExtendedMembershipOtherBrandGiven() throws MalformedURLException, IOException, SAXException{
		response1 = get$CharacterAssertedResponse( client, "http://localhost/wxhtml/registerextendedmember?cc=SE&lc=sv");
		writeResponseToFile( response1, "c:\\temp\\testSubmitExtendedMembershipOtherBrandGiven_BeforeSubmit.html");
		WebForm signUpUserForm = response1.getFormWithID("signupUserForm");
		String[] parameterNames = signUpUserForm.getParameterNames();
		signUpUserForm.setParameter("phoneDetails.brandID", "2");
		WebResponse response2 = get$CharacterAssertedResponseFromFormSubmit(signUpUserForm);
		writeResponseToFile( response2, "c:\\temp\\testSubmitExtendedMembershipOtherBrandGiven_AfterSubmit.html");
		assertInputfieldIsPresentAndHidden( response2, "phoneDetails.imeiNumber");
		assertInputfieldIsPresentAndHiddenWithValue( response2, "phoneDetails.brandID", "2" );
		assertInputfieldIsPresentAndHidden( response2, "phoneDetails.productID");
		assertInputfieldIsPresentAndHidden( response2, "phoneDetails.imeiNumber");
	}
	
	/**
	 * This test enters the "sonyericsson full membership" page, fills in the brand 'other' and submits the form. 
	 * Then the test verifies that the brand field is hidden. 
	 *  
	 * @throws MalformedURLException
	 * @throws IOException
	 * @throws SAXException
	 */
	public void testSubmitFullMembershipOtherBrandGiven() throws MalformedURLException, IOException, SAXException{
		response1 = get$CharacterAssertedResponse( client, "http://localhost/wxhtml/registerfullmember?cc=SE&lc=sv");
		writeResponseToFile( response1, "c:\\temp\\testSubmitFullMembershopOtherBrandGiven_BeforeSubmit.html");
		WebForm signUpUserForm = response1.getFormWithID("signupUserForm");
		String[] parameterNames = signUpUserForm.getParameterNames();
		signUpUserForm.setParameter("phoneDetails.brandID", "2");
		WebResponse response2 = get$CharacterAssertedResponseFromFormSubmit(signUpUserForm);
		writeResponseToFile( response2, "c:\\temp\\testSubmitFullMembershopOtherBrandGiven_AfterSubmit.html");
		assertInputfieldIsPresentAndHiddenWithValue( response2, "phoneDetails.brandID", "2" );
		assertInputfieldIsPresentAndHidden( response2, "phoneDetails.productID");
	}

	/**
	 * This test enters the "sonyericsson full membership" page, fills in the brand 'Sony Ericsson' and submits the form. 
	 * Then the test verifies that the brand field is shown. 
	 *  
	 * @throws MalformedURLException
	 * @throws IOException
	 * @throws SAXException
	 */
	public void testSubmitFullMembershipSonyEricssonBrandGiven() throws MalformedURLException, IOException, SAXException{
		response1 = get$CharacterAssertedResponse( client, "http://localhost/wxhtml/registerfullmember?cc=SE&lc=sv");
		writeResponseToFile( response1, "c:\\temp\\testSubmitFullMembershipSonyEricssonBrandGiven_BeforeSubmit.html");
		WebForm signUpUserForm = response1.getFormWithID("signupUserForm");
		String[] parameterNames = signUpUserForm.getParameterNames();
		signUpUserForm.setParameter("phoneDetails.brandID", "1");
		
		WebResponse response2 = get$CharacterAssertedResponseFromFormSubmit(signUpUserForm);
		writeResponseToFile( response2, "c:\\temp\\testSubmitFullMembershipSonyEricssonBrandGiven_AfterSubmit.html");
		assertInputfieldIsPresentAndVisible( response2, "phoneDetails.productID" );
	}
	/**
	 * This test enters the "sonyericsson extended membership" page, fills in the brand 'Sony Ericsson' and submits the form. 
	 * Then the test verifies that the brand field, imeiNumber is shown. 
	 *  
	 * @throws MalformedURLException
	 * @throws IOException
	 * @throws SAXException
	 */
	public void testSubmitExtendedMembershipSonyEricssonBrandGiven() throws MalformedURLException, IOException, SAXException{
		response1 = get$CharacterAssertedResponse( client, "http://localhost/wxhtml/registerextendedmember?cc=SE&lc=sv");
		writeResponseToFile( response1, "c:\\temp\\testSubmitExtendedMembershipSonyEricssonBrandGiven_BeforeSubmit.html");
		WebForm signUpUserForm = response1.getFormWithID("signupUserForm");
		String[] parameterNames = signUpUserForm.getParameterNames();
		signUpUserForm.setParameter("phoneDetails.brandID", "1");
		
		WebResponse response2 = get$CharacterAssertedResponseFromFormSubmit(signUpUserForm);
		writeResponseToFile( response2, "c:\\temp\\testSubmitExtendedMembershipSonyEricssonBrandGiven_AfterSubmit.html");
		assertInputfieldIsPresentAndVisible( response2, "phoneDetails.productID" );
		assertInputfieldIsPresentAndVisible( response2, "phoneDetails.imeiNumber" );

	}

	/**
	 * This test ensures that the SignupAction can collect all values that the 'SignupForm' (which exist in the register.vm
	 * template) sends.
	 * 
	 * @throws MalformedURLException
	 * @throws IOException
	 * @throws SAXException
	 */
	/*
	public void testSignupActionSupportsSignupForm() throws MalformedURLException, IOException, SAXException{
		response1 = get$CharacterAssertedResponse( client, "http://localhost/wxhtml/registerextendedmember?cc=SE&lc=sv");
		WebForm signUpUserForm = response1.getFormWithID("signupUserForm");
		assertActionSupportFormParameters( signUpUserForm, RegisterExtendedMemberAction.class );
	}*/
	

	
	/**
	 * Logins, goes to managedata (edit profile) and checks that the expected links are there.  
	 *   
	 * @throws MalformedURLException
	 * @throws IOException
	 * @throws SAXException
	 */
	public void testEnterManageAccountPage() throws MalformedURLException, IOException, SAXException{
		login(client, "axelssona12@hotmail.com", "annica12");
		
		response1 = get$CharacterAssertedResponse( client, "http://localhost/wxhtml/managedata?cc=SE&lc=sv");
		writeResponseToFile( response1, "c:\\temp\\testEnterManageAccountPage.html");
		
		assertLinkExistAndHaveURLString(response1, "Edit your profile", "/wxhtml/managedata/editprofile;jsessionid=1?cc=SE&lc=sv" );
		assertLinkExistAndHaveURLString(response1, "Change your email address", "/wxhtml/managedata/changeemail;jsessionid=1?cc=SE&lc=sv" );
		assertLinkExistAndHaveURLString(response1, "Change your password", "/wxhtml/managedata/changepassword;jsessionid=1?cc=SE&lc=sv" );
		assertLinkExistAndHaveURLString(response1, "Your eNews subscriptions", "/wxhtml/managedata/unsubscribe;jsessionid=1?cc=SE&lc=sv" );
		assertLinkExistAndHaveURLString(response1, "Cancel your membership", "/wxhtml/managedata/cancelmembership;jsessionid=1?cc=SE&lc=sv" );
	}
	
	/**
	 * Does the following: 
	 * - Registers a extended member.
	 * - Logins with that account.
	 * - Enters edit profile page and checks that all the registered values are there.  
	 * - Changes all of the values and submits.
	 * - Enters edit profile again and checks that all the changed values are there. 
	 *   
	 * @throws MalformedURLException
	 * @throws IOException
	 * @throws SAXException
	 */
	/* SHIT HOW TO KEEP STATE BETWEEEN CALLS IN HTTPUNIT....:(..
	public void testEditProfileChangeValuesSuccessfully() throws MalformedURLException, IOException, SAXException{
		String emailUsername = "axelssona12@hotmail.com";
		String password = "annica12";
		
		//login the user and checks if all the registered values is presented in the edit profile page.
		login(client, emailUsername, password); 
		WebResponse enterEditProfileResponse = get$CharacterAssertedResponse( client, "http://localhost/wxhtml/managedata/editprofile?cc=US&lc=en");
		writeResponseToFile( enterEditProfileResponse, "c:\\temp\\testEnterEditProfilePage.html");
		HTMLElement emailElement = enterEditProfileResponse.getElementWithID("emailaddress");
		assertNotNull(emailElement );
		assertEquals(emailUsername, emailElement.getText());
		HTMLElement usernameElement = enterEditProfileResponse.getElementWithID("username");
		assertNotNull(usernameElement);
		assertEquals(emailUsername, usernameElement.getText());
		HTMLElement locationNameElement = enterEditProfileResponse.getElementWithID("locationname");
		assertNotNull(locationNameElement);
		assertEquals("U.S.", locationNameElement.getText());
		
		//Change all the values and submit the form.
		WebForm editUserForm = enterEditProfileResponse.getFormWithID("editUserForm");	
		editUserForm.setParameter("personalDetails.firstName", "DonaldChanged");
		editUserForm.setParameter("personalDetails.lastName", "DuckChanged" );
		editUserForm.setParameter("personalDetails.gender", "f");
		editUserForm.setParameter("personalDetails.streetAddress", "St Eriks street 12");
		editUserForm.setParameter("personalDetails.postalCode", "33333");
		editUserForm.setParameter("personalDetails.city", "AMSTERDAM");
		editUserForm.setParameter("phoneDetails.brandID", "1");
		editUserForm.setParameter("phoneDetails.productID", "10176");
		editUserForm.setParameter("phoneDetails.operatorID", "295");
		editUserForm.setParameter("phoneDetails.mobilePhoneNumber", "+469999999999");
		editUserForm.setParameter("phoneDetails.purchaseDate.year", "2004");
		editUserForm.setParameter("phoneDetails.purchaseDate.month", "02");
		editUserForm.setParameter("phoneDetails.imeiNumber", "222222--22-222222-2");		
		editUserForm.setParameter("personalDetails.birthDate.year", "1972");
		editUserForm.setParameter("personalDetails.birthDate.month", "2");
		editUserForm.setParameter("personalDetails.birthDate.day", "14");		
		editUserForm.setParameter("newsLetterSubscriptions.biMonthly", "true");
		editUserForm.setParameter("newsLetterSubscriptions.phoneUpdates", "true");
		editUserForm.setParameter("newsLetterSubscriptions.productLaunches", "true");
		editUserForm.setParameter("imageSettingsEmails.email1", "imagesettingmail1@hotmail.com");
		editUserForm.setParameter("imageSettingsEmails.email2", "imagesettingmail2@hotmail.com");
		editUserForm.setParameter("imageSettingsEmails.email3", "imagesettingmail3@hotmail.com");
		editUserForm.setParameter("imageSettingsEmails.email4", "imagesettingmail4@hotmail.com");
		editUserForm.setParameter("imageSettingsEmails.email5", "imagesettingmail5@hotmail.com");
		editUserForm.setParameter("gamingSettings.alias", "one alias");
		editUserForm.setParameter("agree", "true");	
		System.out.println( "URL FOR FORM IS " + editUserForm.getAction() );
		WebResponse profileChangedResponse = get$CharacterAssertedResponseFromFormSubmit(editUserForm);
		
		//Enter the edit profile again and check that all the changes is there.
		WebResponse enterEditProfileAgainResponse = get$CharacterAssertedResponse( client, "http://localhost/wxhtml/managedata/editprofile?cc=US&lc=en");
		
		writeResponseToFile( enterEditProfileResponse, "c:\\temp\\testEnterEditProfileAgainPage.html");
		assertInputfieldIsPresentAndVisibleWithValue( enterEditProfileResponse, "personalDetails.firstName", "DonaldChanged" );
		assertInputfieldIsPresentAndVisibleWithValue( enterEditProfileResponse, "personalDetails.lastName", "DuckChanged" );
		assertInputfieldIsPresentAndVisibleWithValue( enterEditProfileResponse, "personalDetails.gender", "Female" );
		assertInputfieldIsPresentAndVisibleWithValue( enterEditProfileResponse, "personalDetails.streetAddress", "St Eriks street 12" );
		assertInputfieldIsPresentAndVisibleWithValue( enterEditProfileResponse, "personalDetails.postalCode", "33333" );
		assertInputfieldIsPresentAndVisibleWithValue( enterEditProfileResponse, "personalDetails.city", "AMSTERDAM" );
		assertInputfieldIsPresentAndVisibleWithValue( enterEditProfileResponse, "personalDetails.birthDate.day", "12" );
		assertInputfieldIsPresentAndVisibleWithValue( enterEditProfileResponse, "personalDetails.birthDate.month", "02" );
		assertInputfieldIsPresentAndVisibleWithValue( enterEditProfileResponse, "personalDetails.birthDate.year", "1972" );
		assertInputfieldIsPresentAndVisibleWithValue( enterEditProfileResponse, "phoneDetails.brandID", "1" );
		assertInputfieldIsPresentAndVisibleWithValue( enterEditProfileResponse, "phoneDetails.operatorID", "295" );
		assertInputfieldIsPresentAndVisibleWithValue( enterEditProfileResponse, "phoneDetails.imeiNumber", "222222--22-222222-2" );
		assertInputfieldIsPresentAndVisibleWithValue( enterEditProfileResponse, "phoneDetails.mobilePhoneNumber", "+469999999999" );
		assertInputfieldIsPresentAndVisibleWithValue( enterEditProfileResponse, "phoneDetails.purchaseDate.month", "02" );
		assertInputfieldIsPresentAndVisibleWithValue( enterEditProfileResponse, "phoneDetails.purchaseDate.year", "2004" );
		//assertInputfieldIsPresentAndVisible( enterEditProfileResponse, "phoneDetails.accessoryID" );
		assertInputfieldIsPresentAndVisibleWithValue( enterEditProfileResponse, "phoneDetails.accessoryCategoryID", "-1" );
		assertInputfieldIsPresentAndVisibleWithValue( enterEditProfileResponse, "phoneDetails.productID", "10173" );
		assertInputfieldIsPresentAndVisibleWithValue( enterEditProfileResponse, "newsLetterSubscriptions.biMonthly", "false" );
		assertInputfieldIsPresentAndVisibleWithValue( enterEditProfileResponse, "newsLetterSubscriptions.phoneUpdates", "false" );
		assertInputfieldIsPresentAndVisibleWithValue( enterEditProfileResponse, "newsLetterSubscriptions.productLaunches", "false" );
		assertInputfieldIsPresentAndVisibleWithValue( enterEditProfileResponse, "imageSettingsEmails.email1", "imagesettingmail1@hotmail.com" );
		assertInputfieldIsPresentAndVisibleWithValue( enterEditProfileResponse, "imageSettingsEmails.email2", "imagesettingmail2@hotmail.com" );
		assertInputfieldIsPresentAndVisibleWithValue( enterEditProfileResponse, "imageSettingsEmails.email3", "imagesettingmail3@hotmail.com" );
		assertInputfieldIsPresentAndVisibleWithValue( enterEditProfileResponse, "imageSettingsEmails.email4", "imagesettingmail4@hotmail.com" );
		assertInputfieldIsPresentAndVisibleWithValue( enterEditProfileResponse, "imageSettingsEmails.email5", "imagesettingmail5@hotmail.com" );
		assertInputfieldIsPresentAndVisibleWithValue( enterEditProfileResponse, "gamingSettings.alias", "one alias" );
		assertInputfieldIsPresentAndVisibleWithValue( enterEditProfileResponse, "agree", "false" );
	}
	*/
	
	/**
	 * Does the following: 
	 * - Registers a extended member.
	 * - Logins with that account.
	 * - Enters edit profile page and checks that all the registered values are there.  
	 * - Changes all of the values and submits.
	 * - Enters edit profile again and checks that all the changed values are there. 
	 *   
	 * @throws MalformedURLException
	 * @throws IOException
	 * @throws SAXException
	 */
	public void testSubmitEditProfileNoValuesGiven() throws MalformedURLException, IOException, SAXException{
		String emailForNewUser = "axelssona12@hotmail.com";
		String password = "annica12";
		
		//login the user and checks if all the registered values is presented in the edit profile page.
		login(client, emailForNewUser, password); 
		WebResponse enterEditProfileResponse = get$CharacterAssertedResponse( client, "http://localhost/wxhtml/managedata/editprofile?cc=US&lc=en");
		writeResponseToFile( enterEditProfileResponse, "c:\\temp\\testSubmitEditProfileNoValuesGiven_beforeSubmit.html");
		WebForm editUserForm = enterEditProfileResponse.getFormWithID("editUserForm");
		writeResponseToFile( enterEditProfileResponse, "c:\\temp\\testSubmitEditProfileNoValuesGiven_afterSubmit.html");
	}

	/**
	 * Registers a extended user with specified email and password. The rest of the values are hardcoded. 
	 *  
	 * @throws MalformedURLException
	 * @throws IOException
	 * @throws SAXException
	 */
	private void registerExtendedMembership(String email, String password) throws MalformedURLException, IOException, SAXException{
		response1 = get$CharacterAssertedResponse( client, "http://localhost/wxhtml/registerextendedmember?cc=SE&lc=sv");
		WebForm signUpUserForm = response1.getFormWithID("signupUserForm");
		
		signUpUserForm.setParameter("confirmationPassword.password", password);
		signUpUserForm.setParameter("confirmationPassword.checkerPassword", password);
		signUpUserForm.setParameter("confirmationEmail.emailAddress", email);
		signUpUserForm.setParameter("confirmationEmail.checkerEmailAddress", email);

		signUpUserForm.setParameter("personalDetails.firstName", "Donald");
		signUpUserForm.setParameter("personalDetails.lastName", "Duck" );
		signUpUserForm.setParameter("personalDetails.gender", "m");
		signUpUserForm.setParameter("personalDetails.streetAddress", "Fleminggatan 20");
		signUpUserForm.setParameter("personalDetails.postalCode", "11245");
		signUpUserForm.setParameter("personalDetails.city", "STOCKHOLM");

		signUpUserForm.setParameter("phoneDetails.brandID", "1");
		signUpUserForm.setParameter("phoneDetails.productID", "10172");
		signUpUserForm.setParameter("phoneDetails.operatorID", "294");
		signUpUserForm.setParameter("phoneDetails.mobilePhoneNumber", "+46034234324324");
		signUpUserForm.setParameter("phoneDetails.purchaseDate.year", "2005");
		signUpUserForm.setParameter("phoneDetails.purchaseDate.month", "07");
		signUpUserForm.setParameter("phoneDetails.imeiNumber", "111111--11-111111-1");
		
		signUpUserForm.setParameter("personalDetails.birthDate.year", "1971");
		signUpUserForm.setParameter("personalDetails.birthDate.month", "1");
		signUpUserForm.setParameter("personalDetails.birthDate.day", "13");
		
		signUpUserForm.setParameter("newsLetterSubscriptions.biMonthly", "true");
		signUpUserForm.setParameter("newsLetterSubscriptions.phoneUpdates", "true");
		signUpUserForm.setParameter("newsLetterSubscriptions.productLaunches", "true");
		signUpUserForm.setParameter("agree", "true");
		
		WebResponse response2 = get$CharacterAssertedResponseFromFormSubmit(signUpUserForm);
		assertNotNull( response2.getElementWithID("registeredSuccessfullyText"));
	}

	
	/**
	 * Gets a most likely unique email adress.
	 * 
	 * @param cal
	 * @return
	 */
	private String getUniqueEmail() {
		//Generate most likely unique email
		Calendar cal = Calendar.getInstance();
		cal.setTime( new Date());
		String year = String.valueOf( cal.get(Calendar.YEAR));
		String month = String.valueOf( cal.get(Calendar.MONTH))+1;
		String day = String.valueOf( cal.get(Calendar.DAY_OF_MONTH));
		String hour = String.valueOf( cal.get(Calendar.HOUR));
		String minute = String.valueOf( cal.get(Calendar.MINUTE));
		String second = String.valueOf( cal.get(Calendar.SECOND));
		
		String emailUsed = "donaldduck"+year+month+day+hour+minute+second+"@hotmail.com";
		return emailUsed;
	}
	
	

	
}
