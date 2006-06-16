package com.sonyericsson.fun.http.common;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.util.HashSet;
import java.util.Set;

import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import junit.framework.AssertionFailedError;
import junit.framework.TestCase;

import org.apache.commons.lang.StringUtils;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.XMLReader;

import com.meterware.httpunit.HTMLElement;
import com.meterware.httpunit.WebForm;
import com.meterware.httpunit.WebLink;
import com.meterware.httpunit.WebResponse;
import com.meterware.servletunit.ServletRunner;
import com.meterware.servletunit.ServletUnitClient;

/**
 * Copyright 2005 Sony Ericsson Mobile Communications AB. All rights reserved.
 * 
 * @author The Platform Upgrade Project
 * @since $Date: 2006/04/11 13:24:09 $
 * @version $Revision: 1.3.2.2 $
 * 
 */
public abstract class HttpUnitTestCase extends TestCase implements ErrorHandler {

	private ServletRunner servletRunner;

	/**
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
		System.setProperty("java.naming.factory.initial", "com.sonyericsson.fun.http.common.MockInitialContextFactory");

		// Make sure to only load the spring config once.
		if (servletRunner == null) {
			InputStream webXml = new FileInputStream("src/main/webapp/WEB-INF/web.xml");
			servletRunner = new ServletRunner(webXml);
		}

	}

	protected ServletUnitClient getClient(String userAgent) {
		ServletUnitClient client = servletRunner.newClient();
		client.getClientProperties().setUserAgent(userAgent);
		return client;
	}

	/**
	 * Prints all the links to the console
	 * 
	 * @param allLinks
	 */
	protected void printLinks(WebLink[] allLinks) {
		for (int i = 0; i < allLinks.length; i++) {
			System.out.println(allLinks[i].getText());
		}
	}

	/**
	 * Writes the specified response:s text (response.getText()) to the
	 * specified file.
	 * 
	 * @param response
	 *            The response to write
	 * @param fileNameWithFullPath
	 *            The file to write the response text to
	 * @throws IOException
	 */
	protected void writeResponseToFile(WebResponse response, String fileNameWithFullPath) throws IOException {
		try {
			BufferedWriter out = new BufferedWriter(new FileWriter(fileNameWithFullPath));
			out.write(response.getText());
			out.close();
			System.out.println("File " + fileNameWithFullPath + " now written containing response output.");
		} catch (Exception e) {
			// Probably in running in the unix environment...igonore
			System.out.println("Could not create the file " + fileNameWithFullPath);
		}
	}

	/**
	 * Asserts that a inputfield with the specified inputfieldName is present
	 * and visible in the output of the specified response.
	 * 
	 * @param response
	 * @param inputfieldName
	 * @throws SAXException
	 */
	protected void assertInputfieldIsPresentAndVisible(WebResponse response, String inputfieldName) throws SAXException {
		HTMLElement inputField = getElementAssertExist(response, inputfieldName);
		assertInputfieldIsVisible(inputField);
	}

	/**
	 * Asserts that a inputfield with the specified inputfieldName is present,
	 * visible and have the specified expected value in the output of the
	 * specified response.
	 * 
	 * @param response
	 * @param inputfieldName
	 * @param extectedValue
	 *            The value that is expected to exist in the inputfields value
	 *            attribute.
	 * @throws SAXException
	 */
	protected void assertInputfieldIsPresentAndVisibleWithValue(WebResponse response, String inputfieldName, String expectedValue)
			throws SAXException {
		HTMLElement inputField = getElementAssertExist(response, inputfieldName);
		assertInputfieldIsVisible(inputField);
		assertInputfieldHasValue(inputField, expectedValue);
	}

	/**
	 * Asserts that the specified inputfield is visible.
	 * 
	 * @param inputfield
	 */
	protected void assertInputfieldIsVisible(HTMLElement inputfield) {
		if ("hidden".equalsIgnoreCase(inputfield.getAttribute("type"))) {
			fail("The element " + inputfield.getName() + " is hidden which was not expected!");
		}
	}

	/**
	 * Asserts that the specified inputfield is hidden.
	 * 
	 * @param inputfield
	 */
	protected void assertInputfieldIsHidden(HTMLElement inputfield) {
		if ("hidden".equalsIgnoreCase(inputfield.getAttribute("type"))) {
			// ok
		} else {
			fail("The element " + inputfield.getName() + " is visible which was not expected!");
		}
	}

	/**
	 * Asserts that a inputfield with the specified inputfieldName is present
	 * and hidden in the output of the specified response.
	 * 
	 * @param response
	 * @param inputfieldName
	 * @throws SAXException
	 */
	protected void assertInputfieldIsPresentAndHidden(WebResponse response, String inputfieldName) throws SAXException {
		HTMLElement inputField = getElementAssertExist(response, inputfieldName);
		assertInputfieldIsHidden(inputField);
	}

	/**
	 * Asserts that a inputfield with the specified inputfieldName is present,
	 * hidden and have the specified value in the output of the specified
	 * response.
	 * 
	 * @param response
	 * @param inputfieldName
	 * @throws SAXException
	 */
	protected void assertInputfieldIsPresentAndHiddenWithValue(WebResponse response, String inputfieldName, String expectedValue)
			throws SAXException {
		assertInputfieldIsPresentAndHidden(response, inputfieldName);
		HTMLElement[] elementsWithName = response.getElementsWithName(inputfieldName);
		assertInputfieldHasValue(elementsWithName[0], expectedValue);
	}

	/**
	 * @param response
	 * @param inputfieldName
	 * @param expectedValue
	 * @throws SAXException
	 */
	protected void assertInputfieldHasValue(HTMLElement inputField, String expectedValue) throws SAXException {
		String actualValue = inputField.getAttribute("value");
		assertEquals(inputField.getName(), expectedValue, actualValue);
	}

	/**
	 * Gets a HTMLElement with the specified name from the specified response.
	 * Will fail if the element was not found or there were mote than one
	 * element with the specified name found.
	 * 
	 * @param response
	 * @param elementName
	 * @return
	 * @throws SAXException
	 */
	protected HTMLElement getElementAssertExist(WebResponse response, String elementName) throws SAXException {
		HTMLElement[] elementsWithName = response.getElementsWithName(elementName);
		if (elementsWithName == null) {
			fail("The element " + elementName + " do not exist.");
		}
		if (elementsWithName.length > 1) {
			fail("The element " + elementName + " exist more than once. Exist in another form?");
		}
		if (elementsWithName.length == 0) {
			fail("The are no element with name " + elementName);
		}
		return elementsWithName[0];
	}

	/**
	 * Asserts that a inputfield with the specified inputfieldName is NOT
	 * present in the output of the specified response.
	 * 
	 * @param response
	 * @param inputfieldName
	 * @throws SAXException
	 */
	protected void assertInputfieldIsNotPresent(WebResponse response, String inputfieldName) throws SAXException {
		HTMLElement[] elementsWithName = response.getElementsWithName(inputfieldName);
		if (elementsWithName.length > 0) {
			fail("The element " + inputfieldName + " exist which was not expected!");
		}
	}

	/**
	 * Asserts that the output in the specified response do not contain any $
	 * characters. If it does it presents the errors in the failing message and
	 * writes the output to the file @{java.io.tmpdir}/$errors.html.
	 * 
	 * @param responseToCheck
	 * @throws IOException
	 * @throws SAXException
	 */
	protected void assertDollarSignsInResponse(WebResponse responseToCheck) throws IOException, SAXException {
		int index = responseToCheck.getText().indexOf("$");
		if (index != -1) {

			// Contains $
			Set collected$Errors = new HashSet();
			WebForm[] forms = responseToCheck.getForms();
			for (int i = 0; i < forms.length; i++) {
				// forms[i].get
				String[] parameterNames = forms[i].getParameterNames();
				for (int j = 0; j < parameterNames.length; j++) {
					HTMLElement[] elementsWithName = responseToCheck.getElementsWithName(parameterNames[j]);
					for (int k = 0; k < elementsWithName.length; k++) {
						String attributeValue = elementsWithName[k].getAttribute("value");
						if (attributeValue != null) {
							if (attributeValue.indexOf("$") != -1) {
								collected$Errors.add(attributeValue);
							}
						}
					}
				}
			}

			// TODO add checks in links.

			String filePath = System.getProperty("java.io.tmpdir")+"/$errors.html";
			writeResponseToFile(responseToCheck, filePath);

			fail("$ values exist: " + collected$Errors + ", See " + filePath);
		}
	}

	/**
	 * Asserts that all the parameters in the specified WebForm is supported by
	 * the specified Class. That means that the Class have a matching
	 * setMethod() for all the parameternames in the form.
	 * 
	 * @param formToCheck
	 * @param actionToCheck
	 */
	protected void assertActionSupportFormParameters(WebForm formToCheck, Class actionToCheck) {

		// Get all the writeable properties in the actionToCheck class.
		PropertyDescriptor[] propertyDescriptors;

		try {
			propertyDescriptors = Introspector.getBeanInfo(actionToCheck).getPropertyDescriptors();
		} catch (IntrospectionException e) {
			throw new AssertionFailedError(e.getMessage());
		}

		Set writeableProperties = new HashSet();
		for (int j = 0; j < propertyDescriptors.length; j++) {
			if (propertyDescriptors[j].getWriteMethod() != null) {
				writeableProperties.add(propertyDescriptors[j].getName().toLowerCase());
			}
		}
		String[] parameterNames = formToCheck.getParameterNames();
		for (int i = 0; i < parameterNames.length; i++) {
			if (!writeableProperties.contains(parameterNames[i].toLowerCase())) {
				System.out.println("The parameter " + parameterNames[i] + " has no mathing set method in the "
						+ actionToCheck.getClass().getName());
				fail("The parameter " + parameterNames[i] + " is not one of : " + writeableProperties + " in the "
						+ actionToCheck.getClass().getName());
			}
		}
	}

	/**
	 * Gets a response (from the specified URL and client) that is verified
	 * (asserted) to not contain any $ characters in its output.
	 * 
	 * @param client
	 *            The ServletUnitClient to use for getting the response.
	 * @param URL
	 *            The URL target when getting the response.
	 * @return A WebResponse that is asserted to not contain any $ characters in
	 *         its output.
	 * @throws MalformedURLException
	 * @throws IOException
	 * @throws SAXException
	 */
	protected WebResponse get$CharacterAssertedResponse(ServletUnitClient client, String URL) throws MalformedURLException,
			IOException, SAXException {
		WebResponse responseToCheck = client.getResponse(URL);
		assertDollarSignsInResponse(responseToCheck);
		return responseToCheck;
	}

	/**
	 * Gets a response (from the specified URL and client) that is verified
	 * (asserted) to not contain any $ characters in its output.
	 * 
	 * @param client
	 *            The ServletUnitClient to use for getting the response.
	 * @param URL
	 *            The URL target when getting the response.
	 * @return A WebResponse that is asserted to not contain any $ characters in
	 *         its output.
	 * @throws MalformedURLException
	 * @throws IOException
	 * @throws SAXException
	 */
	protected WebResponse get$CharacterAssertedResponseFromFormSubmit(WebForm formToSubmit) throws MalformedURLException,
			IOException, SAXException {
		WebResponse responseToCheck = formToSubmit.submit();

		assertDollarSignsInResponse(responseToCheck);
		return responseToCheck;
	}

	/**
	 * Asserts that a link with the specified text does not exist in the
	 * response
	 * 
	 * @param links
	 */
	protected void assertLinkDoesNotExist(WebResponse webResponse, String linkText) throws SAXException {
		assertNull(webResponse.getLinkWith(linkText));
	}

	/**
	 * Asserts that a link with the specified text does exist in the response
	 */
	protected void assertLinkDoesExist(WebResponse webResponse, String linkText) throws SAXException {
		assertNotNull(webResponse.getLinkWith(linkText));
	}

	/**
	 * Asserts that a link with the specified text exist with the specified URL
	 * string.
	 */
	protected void assertLinkExistAndHaveURLString(WebResponse webResponse, String linkText, String urlString)
			throws SAXException {
		WebLink webLink = webResponse.getLinkWith(linkText);
		assertEquals(urlString, webLink.getURLString());
	}

	/**
	 * This attaches the action login and the username and parameters to the url
	 * of a request going to software and service. Note currently we don't have
	 * working javascript support in httpunit so logging in via the form is not
	 * possible. Note this is dependant on keeping a session. If a session is
	 * lost, f.ex after a tear down/setup cycle you are no longer logged in.
	 * 
	 * @param client
	 * @param username
	 *            should be url encoded.
	 * @param password
	 *            should be url encoded.
	 * @throws MalformedURLException
	 * @throws IOException
	 * @throws SAXException
	 */
	protected void login(ServletUnitClient client, String username, String password) throws MalformedURLException, IOException,
			SAXException {

		WebResponse response = client.getResponse("http://localhost/wxhtml/software1?action=login&cc=GB&lc=en&username="
				+ username + "&password=" + password);
		String loginText = response.getText();
		assertTrue(StringUtils.contains(loginText, username));
		assertNull(response.getFormWithName("loginForm"));
		System.out.println("Welcome in " + username);

	}

	/**
	 * This attaches the action login and the username and parameters to the url
	 * of a request going to software and service. Note currently we don't have
	 * working javascript support in httpunit so logging in via the form is not
	 * possible. Note this is dependant on keeping a session. If a session is
	 * lost, f.ex after a tear down/setup cycle you are no longer logged in.
	 * 
	 * @param client
	 * @param username
	 *            should be url encoded.
	 * @param password
	 *            should be url encoded.
	 * @param url
	 *            the url to which you wish to log into. Including the local
	 * @throws MalformedURLException
	 * @throws IOException
	 * @throws SAXException
	 */

	protected void login(ServletUnitClient client, String username, String password, String url) throws MalformedURLException,
			IOException, SAXException {

		WebResponse response = client.getResponse(url + "&action=login" + "&username=" + username + "&password=" + password);

		assertTrue(StringUtils.contains(response.getText(), username));
		assertNull(response.getFormWithName("loginForm"));

	}

	protected WebResponse loginAndGetResponse(ServletUnitClient client, String username, String password, String url)
			throws MalformedURLException, IOException, SAXException {
		WebResponse response = client.getResponse(url + "&action=login" + "&username=" + username + "&password=" + password);

		assertTrue(StringUtils.contains(response.getText(), username));
		assertNull(response.getFormWithName("loginForm"));
		return response;
	}

	protected WebResponse loginAndGetResponseWithoutValidation(ServletUnitClient client, String username, String password,
			String url) throws MalformedURLException, IOException, SAXException {
		WebResponse response = client.getResponse(url + "&action=login" + "&username=" + username + "&password=" + password);
		return response;
	}

	protected void assertValidXMLSchema(InputStream is, String targetNamespace, String schema) throws ParserConfigurationException, FactoryConfigurationError, IOException, SAXException {
		String jaxpProperty = "http://apache.org/xml/properties/schema/external-schemaLocation";

		SAXParserFactory factory = SAXParserFactory.newInstance();

		factory.setValidating(true);
		factory.setNamespaceAware(true);

		SAXParser parser = factory.newSAXParser();
		parser.setProperty(jaxpProperty, targetNamespace + " " + schema);

		XMLReader reader = parser.getXMLReader();

		reader.setErrorHandler(this);
		reader.parse(new InputSource(is));
	}

	protected void assertValidXMLNoSchema(InputStream is) throws ParserConfigurationException, FactoryConfigurationError, IOException, SAXException {

		SAXParserFactory factory = SAXParserFactory.newInstance();

		factory.setValidating(true);
		factory.setNamespaceAware(true);

		SAXParser parser = factory.newSAXParser();

		XMLReader reader = parser.getXMLReader();

		reader.setErrorHandler(this);
		reader.parse(new InputSource(is));
	}

	public void error(SAXParseException e) throws SAXException {
		String errormsg = "At line: " + e.getLineNumber() + ". Source:" + e.toString();
		fail(errormsg);

	}

	public void fatalError(SAXParseException e) throws SAXException {
		String errormsg = "At line: " + e.getLineNumber() + ". Source:" + e.toString();
		fail(errormsg);

	}

	public void warning(SAXParseException e) throws SAXException {
		// TODO Auto-generated method stub

	}

}

