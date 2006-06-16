package validate;

import java.io.IOException;

import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import com.meterware.httpunit.WebResponse;
import com.meterware.servletunit.ServletUnitClient;
import common.HttpUnitTestCase;

public class TestValidateWML extends HttpUnitTestCase {
	private ServletUnitClient client;

	protected void setUp() throws Exception {
		super.setUp();
		client = this.getClient("SonyEricssonT610");
	}
/*The only dtd that I found for validaing wml 
 * 
 * 
 * 
 * */
	
	public void validatePortalPage() throws ParserConfigurationException, FactoryConfigurationError, IOException, SAXException {

		WebResponse response = client.getResponse("http://localhost/portal");

		writeResponseToFile(response, "c:/tmp/test.html");

		assertValidXMLNoSchema(response.getInputStream());

	}

	public void validatePortalLogon() throws ParserConfigurationException, FactoryConfigurationError, IOException, SAXException {

		WebResponse response = client.getResponse("http://localhost/mwml/portallogon");

		writeResponseToFile(response, "c:/tmp/test.html");

		assertValidXMLNoSchema(response.getInputStream());

	}

	public void testValidateHome() throws ParserConfigurationException, FactoryConfigurationError, IOException, SAXException {

		WebResponse response = client.getResponse("http://localhost/mwml/home?cc=SE&lc=sv");
		

		writeResponseToFile(response, "c:/tmp/test.html");

		assertValidXMLNoSchema(response.getInputStream());

	}
	
	public void testValidateDownload() throws ParserConfigurationException, FactoryConfigurationError, IOException, SAXException {

		WebResponse response = client.getResponse("http://localhost/mwml/download1_1?cc=SE&files=4711&lc=sv&products=T610");
		

		writeResponseToFile(response, "c:/tmp/test.html");

		assertValidXMLNoSchema(response.getInputStream());

	}
	

		
		public void testValidateSignUp() throws ParserConfigurationException, FactoryConfigurationError, IOException, SAXException {

		WebResponse response = client.getResponse("http://localhost/mwml/signup?cc=GB&lc=en");
		

		writeResponseToFile(response, "c:/tmp/test.html");

		assertValidXMLNoSchema(response.getInputStream());

	}
}
