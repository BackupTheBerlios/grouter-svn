package com.sonyericsson.fun.http.ota;

import java.io.IOException;
import java.net.MalformedURLException;

import org.xml.sax.SAXException;

import com.meterware.httpunit.WebForm;
import com.meterware.httpunit.WebLink;
import com.meterware.httpunit.WebRequest;
import com.meterware.httpunit.WebResponse;
import com.meterware.servletunit.ServletUnitClient;
import com.sonyericsson.fun.http.common.HttpUnitTestCase;
import com.sonyericsson.service.highscore.impl.dbutil.URLEncoder;

public class HttpTestOtaSetupIntro extends HttpUnitTestCase {

	private ServletUnitClient client;
	private WebResponse response;
	private String tmpDir;
	private WebLink link;
	private WebForm form;

	/**
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
		client = this.getClient("SonyEricssonS700i");
		tmpDir = System.getProperty("java.io.tmpdir");
	}
	
	/***
	 * Used to build the links, parse a relative url, 
	 * and it will create a test path, eg. remove /fun/ and
	 * prefix http://localhost
	 * @param link The link
	 * @return The parsed link
	 */
	private String parseLink(String link) {
		StringBuffer buffer = new StringBuffer();
		buffer.append("http://localhost");
		buffer.append(link.substring(link.indexOf("/mxhtml/")));
		return buffer.toString();
	}
	
	/***
	 * Calls a url, writes the response to a file and fetches a link from the page
	 * @param url The url to submit request to
	 * @param filename The file to write the response to, starting with a "/"
	 * @param linkId The id of the link to fetch (like <a id=""></a>)
	 * @throws MalformedURLException
	 * @throws IOException
	 * @throws SAXException
	 */
	private void doCall(String url, String filename, String linkId) throws MalformedURLException, IOException, SAXException {
		response = client.getResponse(url);
		writeResponseToFile(response, tmpDir + filename);

		link = response.getLinkWithID(linkId);
	}
	
	/***
	 * Calls a url, writes the response to a file and fetches a form from the page
	 * @param url The url to submit request to
	 * @param filename The file to write the response to, starting with a "/"
	 * @param linkId The id of the link to fetch (like <a id=""></a>)
	 * @throws MalformedURLException
	 * @throws IOException
	 * @throws SAXException
	 */
	private void doFormCall(String url, String filename, String formId) throws MalformedURLException, IOException, SAXException {
		response = client.getResponse(url);
		writeResponseToFile(response, tmpDir + filename);
		
		form = response.getFormWithID(formId);
	}

	/***
	 * Calls a url, writes the response to a file and fetches a form from the page
	 * @param request the Request from the previous form
	 * @param filename The file to write the response to, starting with a "/"
	 * @param formId The id of the form to fetch (like <form id=""></form>)
	 * @throws MalformedURLException
	 * @throws IOException
	 * @throws SAXException
	 */
	private void doFormCall(String filename, String formId) throws MalformedURLException, IOException, SAXException {
		response = form.submit();
		writeResponseToFile(response, tmpDir + filename);
		
		form = response.getFormWithID(formId);
	}

	
	private void doMmsIntro(String pageName) throws MalformedURLException, IOException, SAXException {
		// Intro
		doCall("http://localhost/mxhtml/software/otasetup?cc=GB&dn=K700i&lc=en",
				pageName, "mmslink");
		assertNotNull("MMS link doesn't exist on Intro page!", link);
	}
	private void doWapIntro(String pageName) throws MalformedURLException, IOException, SAXException {
		// Intro
		doCall("http://localhost/mxhtml/software/otasetup?cc=GB&dn=K700i&lc=en",
				pageName, "waplink");
		assertNotNull("WAP link doesn't exist on Intro page!", link);
	}
	private void doEmailIntro(String pageName) throws MalformedURLException, IOException, SAXException {
		// Intro
		doCall("http://localhost/mxhtml/software/otasetup?cc=GB&dn=K700i&lc=en",
				pageName, "emaillink");
		assertNotNull("E-mail link doesn't exist on Intro page!", link);
	}
	
	private void doCombiIntro(String pageName) throws MalformedURLException, IOException, SAXException {
		// Intro
		doCall("http://localhost/mxhtml/software/otasetup?cc=GB&dn=K700i&lc=en",
				pageName, "twoormorelink");
		assertNotNull("Combi link doesn't exist on Intro page!", link);
	}
	
	private void doRegion(String pageName) throws MalformedURLException, IOException, SAXException {
		// Region
		doCall(parseLink(link.getURLString()), pageName, "region_EU4");
		assertNotNull("Europe R-Z link doesn't exist on Intro page!", link);
	}
	
	private void doCountry(String pageName) throws MalformedURLException, IOException, SAXException {
		// Country
		doCall(parseLink(link.getURLString()), pageName, "country_SE");
		assertNotNull("Europe R-Z link doesn't exist on Intro page!", link);
	}

	private void doServiceProvider(String pageName) throws MalformedURLException, IOException, SAXException {
		// Service Provider
		doCall(parseLink(link.getURLString()), pageName, "sp_10a6f0bf79c45f17");
		assertNotNull("Provider With ALL link doesn't exist on Intro page!", link);
	}

	private void doPhoneModel(String pageName) throws MalformedURLException, IOException, SAXException {
		// Phone Model
		doCall(parseLink(link.getURLString()), pageName, "phonemodel_F500i");
		assertNotNull("Phonemodel F500i link doesn't exist on Intro page!", link);
	}

	private void doCOPSummary(String pageName) throws MalformedURLException, IOException, SAXException {
		// COP Sumary
		doFormCall(parseLink(link.getURLString()), pageName, "summaryform");
		assertNotNull("No form exist to submit on COP Summary page", form);
	}
	
	private void doEmailProvider(String pageName) throws MalformedURLException, IOException, SAXException {
		// Email Provider
		doFormCall(pageName, "emailproviderform_2887+1");
		assertNotNull("No \"emailproviderform_2887+1\" form exist to submit on EmailProvider page!", form);
		form.setParameter("epId", "2887+1");
	}

	private void doEmailInput(String pageName) throws MalformedURLException, IOException, SAXException {
		// Email Input
		doFormCall(pageName, "emailinputform");
		assertNotNull("No emailinputform exist to submit on EmailInput page!", form);
		form.setParameter("ea", "jakob.maaloe@cybercomgroup.com");
		form.setParameter("eu", "testuser");
		form.setParameter("ep", "testpassword");
	}

	private void doCombiSelection(String pageName) throws MalformedURLException, IOException, SAXException {
		// Combi Selection
		doFormCall(pageName, "combiform");
		assertNotNull("No form exist to submit on Combi Selection page!", form);
		
		form.setParameter("wap", "wap");
		form.setParameter("mms", "mms");
		form.setParameter("email", "email");
	}

	private void doPhonenumber(String pageName) throws MalformedURLException, IOException, SAXException {
		// Phonenumber
		doFormCall(pageName, "phonenumberform");
		assertNotNull("No form exist to submit on Phonenumber page!", form);
		
		form.setParameter("phonenumber", "+4520300034");
	}

	private void doSetupTypeSummary(String pageName) throws MalformedURLException, IOException, SAXException {
		// Setup Type Summary
		doFormCall(pageName, "sendsettingform");
		assertNotNull("No form exist to submit on SetupType Summary page!", form);
	}

	private void doSendComplete(String pageName) throws MalformedURLException, IOException, SAXException {
		// Sending Complete
		response = client.getResponse(form.getRequest());
		writeResponseToFile(response, tmpDir + pageName);
		assertNotNull("Could not submit SettingsRequest to NABU GW properly, got back an error!", response.getElementWithID("done"));
	}

	public void testMxhtmlMmsFlow() throws MalformedURLException, IOException, SAXException {
		doMmsIntro("/testMxhtmlMmsFlowIntro.html");
		doRegion("/testMxhtmlMmsFlowRegion.html");
		doCountry("/testMxhtmlMmsFlowCountry.html");
		doServiceProvider("/testMxhtmlMmsFlowServiceProvider.html");
		doPhoneModel("/testMxhtmlMmsFlowPhoneModel.html");
		doCOPSummary("/testMxhtmlMmsFlowCOPSummary.html");
		doPhonenumber("/testMxhtmlMmsFlowPhonenumber.html");
		doSetupTypeSummary("/testMxhtmlMmsFlowSetupTypeSummary.html");
		doSendComplete("/testMxhtmlMmsFlowDone.html");
	}

	public void testMxhtmlWapFlow() throws MalformedURLException, IOException, SAXException {
		doWapIntro("/testMxhtmlWapFlowIntro.html");
		doRegion("/testMxhtmlWapFlowRegion.html");
		doCountry("/testMxhtmlWapFlowCountry.html");
		doServiceProvider("/testMxhtmlWapFlowServiceProvider.html");
		doPhoneModel("/testMxhtmlWapFlowPhoneModel.html");
		doCOPSummary("/testMxhtmlWapFlowCOPSummary.html");
		doPhonenumber("/testMxhtmlWapFlowPhonenumber.html");
		doSetupTypeSummary("/testMxhtmlWapFlowSetupTypeSummary.html");
		doSendComplete("/testMxhtmlWapFlowDone.html");
	}

	public void testMxhtmlEmailFlow() throws MalformedURLException, IOException, SAXException {
		doEmailIntro("/testMxhtmlEmailFlowIntro.html");
		doRegion("/testMxhtmlEmailFlowRegion.html");
		doCountry("/testMxhtmlEmailFlowCountry.html");
		doServiceProvider("/testMxhtmlEmailFlowServiceProvider.html");
		doPhoneModel("/testMxhtmlEmailFlowPhoneModel.html");
		doCOPSummary("/testMxhtmlEmailFlowCOPSummary.html");
		doEmailProvider("/testMxhtmlEmailFlowEmailProvider.html");
		doEmailInput("/testMxhtmlEmailFlowEmailInput.html");
		doPhonenumber("/testMxhtmlEmailFlowPhonenumber.html");
		doSetupTypeSummary("/testMxhtmlEmailFlowSetupTypeSummary.html");
		doSendComplete("/testMxhtmlEmailFlowDone.html");
	}

	public void testMxhtmlCombiFlow() throws MalformedURLException, IOException, SAXException {
		doCombiIntro("/testMxhtmlCombiFlowIntro.html");
		doRegion("/testMxhtmlCombiFlowRegion.html");
		doCountry("/testMxhtmlCombiFlowCountry.html");
		doServiceProvider("/testMxhtmlCombiFlowServiceProvider.html");
		doPhoneModel("/testMxhtmlCombiFlowPhoneModel.html");
		doCOPSummary("/testMxhtmlCombiFlowCOPSummary.html");
		doCombiSelection("/testMxhtmlCombiFlowCombiSelection.html");
		doPhonenumber("/testMxhtmlCombiFlowPhonenumber.html");
		doSetupTypeSummary("/testMxhtmlCombiFlowSetupTypeSummary.html");
		doSendComplete("/testMxhtmlCombiFlowDone.html");
	}
	
	public void testMxhtmlChangeStuffFlow() throws MalformedURLException, IOException, SAXException {
		doMmsIntro("/testMxhtmlChangeStuffFlowIntro.html");
		doRegion("/testMxhtmlChangeStuffFlowRegion.html");
		doCountry("/testMxhtmlChangeStuffFlowCountry.html");
		doServiceProvider("/testMxhtmlChangeStuffFlowServiceProvider.html");
		doPhoneModel("/testMxhtmlChangeStuffFlowPhoneModel.html");
		doCOPSummary("/testMxhtmlChangeStuffFlowCOPSummary.html");
	}
}
