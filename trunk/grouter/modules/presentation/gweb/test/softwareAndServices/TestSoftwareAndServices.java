/*
 * Created on Aug 19, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package softwareAndServices;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;

import javax.xml.transform.TransformerException;

import org.apache.xpath.XPathAPI;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.meterware.httpunit.WebLink;
import com.meterware.httpunit.WebResponse;
import com.meterware.servletunit.ServletUnitClient;

import common.HttpUnitTestCase;

/**
 * @author maya.retzlaff
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class TestSoftwareAndServices extends HttpUnitTestCase {

	public void testSoftwareAndServicesMwml () throws MalformedURLException, IOException, SAXException, TransformerException {
	
		ServletUnitClient client = this.getClient("SonyericssonT610");
		
		WebResponse response = client.getResponse("http://localhost/mwml/software?cc=GB&dn=T610&lc=en");
		Document dom = response.getDOM();
		
		System.out.println(response.getText());
		
		String XPATH = "/wml/card/p[2]/anchor";
		NodeList anchors = XPathAPI.selectNodeList(dom, XPATH);
		assertNotNull(anchors);
		
		Map map = new HashMap();
		
		for(int i=0; i<anchors.getLength(); i++){
			map.put(anchors.item(i).getChildNodes().item(0).getNodeValue(), anchors.item(i).getChildNodes());
		}
							
		assertNotNull(map.get("Wap configurator"));

		
		Node go = ((NodeList)map.get("Wap configurator")).item(1);			
		NamedNodeMap goAttributes = go.getAttributes();
		assertEquals("http://secure.mouse2mobile.com/clients/sonyericsson/?cc=GB&lc=en", goAttributes.item(0).getNodeValue());
	}
	
	public void testSoftwareAndServicesMxhtml() throws MalformedURLException, IOException, SAXException 
	{
		ServletUnitClient client = this.getClient("Mozilla");
		WebResponse response = client.getResponse("http://localhost/mxhtml/software?cc=GB&dn=K700i&lc=en");
		WebLink wap = response.getLinkWithID("wap_configurator");
		WebLink mms = response.getLinkWithID("mms_configurator");
		assertNotNull(wap);
		assertNotNull(mms);
	
		assertEquals("http://secure.mouse2mobile.com/clients/ericsson/tova/mms?cc=GB&lc=en", mms.getURLString());
		assertEquals("http://secure.mouse2mobile.com/clients/ericsson/tova/wap?cc=GB&lc=en", wap.getURLString());
	}
}
