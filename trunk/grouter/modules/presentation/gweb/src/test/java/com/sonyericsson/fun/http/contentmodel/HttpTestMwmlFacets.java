	package com.sonyericsson.fun.http.contentmodel;

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
	public class HttpTestMwmlFacets extends HttpUnitTestCase {
		
//		private ServletRunner sr;
		private ServletUnitClient client;
		private WebResponse response;

		/**
		 * @see junit.framework.TestCase#setUp()
		 */
		protected void setUp() throws Exception {
			super.setUp();
			client = getClient("SonyEricssonT610");
/*			
			// Make sure to only load the spring config once.
			if ( sr == null) {
				InputStream webXml = new FileInputStream("web.xml");
				sr = new ServletRunner( webXml);     
			}
		    client = sr.newClient();
		    client.getClientProperties().setUserAgent("SonyEricssonT610");
*/		    
		}
		
		public void testHome() throws MalformedURLException, IOException, SAXException, TransformerException {
			response = client.getResponse("http://localhost/mwml/home?cc=SE&lc=sv&dn=T610");
			Document dom = response.getDOM();
			
			System.out.println(response.getText());
			
			String XPATH = "/wml/card/p[3]/anchor";
			NodeList anchors = XPathAPI.selectNodeList(dom, XPATH);
			assertNotNull(anchors);
			
			Map map = new HashMap();
			
			for(int i=0; i<anchors.getLength(); i++){
				map.put(anchors.item(i).getChildNodes().item(0).getNodeValue(), anchors.item(i).getChildNodes());
			}
								
			assertNotNull(map.get("ringtones"));
			assertNotNull(map.get("games"));
			assertNotNull(map.get("themes"));
			
			Node go = ((NodeList)map.get("ringtones")).item(1);			
			NamedNodeMap goAttributes = go.getAttributes();
			
			assertEquals("/mwml/download1_1?cc=SE&dn=T610&files=4711&lc=sv&products=T610", goAttributes.item(0).getNodeValue());			
	}
		public void testHomeBrands() throws MalformedURLException, IOException, SAXException, TransformerException {
			response = client.getResponse("http://localhost/mwml/home?cc=SE&lc=sv&dn=T610");
			Document dom = response.getDOM();
			
			System.out.println(response.getText());
			
			String XPATH = "/wml/card/p[4]/anchor";
			NodeList anchors = XPathAPI.selectNodeList(dom, XPATH);
			assertNotNull(anchors);
			
			Map map = new HashMap();
			
			for(int i=0; i<anchors.getLength(); i++){
				map.put(anchors.item(i).getChildNodes().item(0).getNodeValue(), anchors.item(i).getChildNodes());
			}
			
			assertNotNull(map.get("Browse by brand"));
			
			Node goBrands = ((NodeList)map.get("Browse by brand")).item(1);			
			NamedNodeMap goBrandsAttributes = goBrands.getAttributes();
			
			assertEquals("/mwml/download1_brands?cc=SE&dn=T610&lc=sv", goBrandsAttributes.item(0).getNodeValue());
		}
		
		public void testCategory() throws MalformedURLException, IOException, SAXException, TransformerException {
			response = client.getResponse("http://localhost/mwml/download1?cc=SE&dn=T610&files=4711&lc=sv&products=T610");
			//System.out.println(response.getText());
			Document dom = response.getDOM();
			
			
			String XPATH = "/wml/card/p[2]/anchor";
			NodeList anchors = XPathAPI.selectNodeList(dom, XPATH);
			assertNotNull(anchors);
			
			Map map = new HashMap();
			
			for(int i=0; i<anchors.getLength(); i++){
				map.put(anchors.item(i).getChildNodes().item(0).getNodeValue(), anchors.item(i).getChildNodes());
			}
			
			assertNotNull(map.get("rock & roll"));
			assertNotNull(map.get("hip hop"));
			assertNotNull(map.get("samba"));
			assertNotNull(map.get("country"));
			
			Node go = ((NodeList)map.get("hip hop")).item(1);			
			NamedNodeMap goAttributes = go.getAttributes();
			
			assertEquals("/mwml/download1_1?cc=SE&dn=T610&files=4711%2f4715&lc=sv&products=T610", goAttributes.item(0).getNodeValue());			
		}
		
		public void testAdditionalLinks() throws MalformedURLException, IOException, SAXException, TransformerException {
			response = client.getResponse("http://localhost/mwml/download1?cc=CN&dn=T618&files=559&lc=zh&products=T618");
			System.out.println(response.getText());
			Document dom = response.getDOM();
			
			
			String XPATH = "/wml/card/p[2]/anchor";
			NodeList anchors = XPathAPI.selectNodeList(dom, XPATH);
			assertNotNull(anchors);
					
			assertTrue(hasLink(anchors, "http://sonyericsson.fundownload.com.cn/wap/ringtongs/index.jsp"));
		
		}
		

				
		/**
		 * @param anchors
		 * @param httpUrl
		 * @return
		 */
		private boolean hasLink(NodeList anchors, String httpUrl) {
			boolean linkFound = false; 
			for(int i=0; i<anchors.getLength(); i++){
				
				//forevery anchor get the child notes
				
				 NodeList items = anchors.item(i).getChildNodes();
				 //get the first items of the children above. should be go
				 Node node= items.item(1);
				 //get the attributes of <go>, and find the first attribute of
				String href= node.getAttributes().item(0).getNodeValue();
				 		
				if (href.equals(httpUrl))
				{
					
					System.out.println("Found ringtones link");
					linkFound = true;
				}	
				System.out.println(node.getAttributes().item(0).getNodeValue());
			}
			return linkFound;
		}

		public void testCategoryListing() throws MalformedURLException, IOException, SAXException, TransformerException {
			response = client.getResponse("http://localhost//mwml/download1_1?cc=SE&dn=T610&files=4711%2f4715&lc=sv&products=T610");
			//System.out.println(response.getText());
			Document dom = response.getDOM();
			
			writeResponseToFile( response, "c:\\temp\\testCategoryListing.html");
			
			String XPATH = "/wml/card/p[3]/anchor";
			NodeList anchors = XPathAPI.selectNodeList(dom, XPATH);
			assertNotNull(anchors);
			
			Map map = new HashMap();
			
			for(int i=0; i<anchors.getLength(); i++){
			//	System.out.println("nodes "+ anchors.item(i).getChildNodes().item(0).getNodeValue());
				String key = anchors.item(i).getChildNodes().item(0).getNodeValue().trim();
				key = key.substring(0, 9); //only take the part that hold the contentid..
				map.put(key, anchors.item(i).getChildNodes());
			}
			
			assertNotNull(map.get("content10"));
			assertNotNull(map.get("content11"));
			assertNotNull(map.get("content12"));
			assertNotNull(map.get("content13"));
			assertNotNull(map.get("content14"));
			assertNotNull(map.get("content15"));
			
			Node go = ((NodeList)map.get("content10")).item(1);			
			NamedNodeMap goAttributes = go.getAttributes();
			
			assertEquals("/mwml/download1_1_1_free?cc=SE&contentId=content10&dn=T610&lc=sv", goAttributes.item(0).getNodeValue());			
		}	
		
		public void testContentDetailsFree() throws MalformedURLException, IOException, SAXException, TransformerException {
			response = client.getResponse("http://localhost/mwml/download1_1_1_free?contentId=3373&cc=SE&dn=T610&lc=sv&page=1");
			//System.out.println(response.getText());
			Document dom = response.getDOM();
			
			String XPATH = "/wml/card/p[2]/anchor";
			NodeList anchors = XPathAPI.selectNodeList(dom, XPATH);
			assertNotNull(anchors);
			
			Map map = new HashMap();
			
			for(int i=0; i<anchors.getLength(); i++){
				map.put(anchors.item(i).getChildNodes().item(0).getNodeValue(), anchors.item(i).getChildNodes());
			}
								
			assertNotNull(map.get("Download"));
			
			Node go = ((NodeList)map.get("Download")).item(1);			
			NamedNodeMap goAttributes = go.getAttributes();			
			assertEquals("http://localhost/downloads/Digirain.mid", goAttributes.item(0).getNodeValue());
			
		}
		public void testContentDetailsPremium() throws MalformedURLException, IOException, SAXException, TransformerException {
			response = client.getResponse("http://localhost/mwml/download1_1_1_premium?contentId=5487&cc=SE&dn=T610&lc=sv&page=0");
			//http://localhost/mxhtml/download1_1_1_premium?cc=SE&contentId=content15&dn=S700i&lc=sv&page=0");
//			System.out.println(response.getText());
			Document dom = response.getDOM();
			
			String XPATH = "/wml/card/p[3]/anchor";
			NodeList anchors = XPathAPI.selectNodeList(dom, XPATH);
			assertNotNull(anchors);
			
			Map map = new HashMap();
			
			for(int i=0; i<anchors.getLength(); i++){
				map.put(anchors.item(i).getChildNodes().item(0).getNodeValue().trim(), anchors.item(i).getChildNodes());
			}
								
			assertNotNull(map.get("Buy now"));
			
			Node go = ((NodeList)map.get("Buy now")).item(1);			
			NamedNodeMap goAttributes = go.getAttributes();
			
			assertEquals("/mwml/purchase/purchasewb?cc=SE&contentId=5487&dn=T610&lc=sv&page=0&products=T610", goAttributes.item(0).getNodeValue());
			
		}
	
		public void testBrowseByBrands() throws MalformedURLException, IOException, SAXException, TransformerException {
			response = client.getResponse("http://localhost/mwml/download1_brands?cc=SE&lc=SV");
			//System.out.println(response.getText());
			Document dom = response.getDOM();
			
			String XPATH = "/wml/card/p[2]/anchor";
			NodeList anchors = XPathAPI.selectNodeList(dom, XPATH);
			assertNotNull(anchors);
			
			Map map = new HashMap();
			
			for(int i=0; i<anchors.getLength(); i++){
				System.out.println("nodes "+ anchors.item(i).getChildNodes().item(0).getNodeValue());
				map.put(anchors.item(i).getChildNodes().item(0).getNodeValue().trim(), anchors.item(i).getChildNodes());
			}
		
			assertNotNull(map.get("Sony Ericsson"));
			
			Node go = ((NodeList)map.get("Sony Ericsson")).item(1);			
			NamedNodeMap goAttributes = go.getAttributes();
			
			assertEquals("/mwml/download1?brands=Sony%20Ericsson&cc=SE&dn=T610&lc=sv&products=T610", goAttributes.item(0).getNodeValue());
			//assertEquals("/mwml/download1_1?brands=sonyericsson&cc=SE&dn=T610&lc=sv&products=T610", goAttributes.item(0).getNodeValue());

		
		}
		
		/**
		 * Writes the specified response:s text (response.getText()) to the specified file.  
		 * @param response The response to write
		 * @param fileNameWithFullPath The file to write the response text to
		 * @throws IOException
		 */
/*
		private void writeResponseToFile(WebResponse response, String fileNameWithFullPath) throws IOException {
			
			try {
				BufferedWriter out = new BufferedWriter(new FileWriter(
						fileNameWithFullPath));
				out.write(response.getText());
				out.close();
				System.out.println("File " + fileNameWithFullPath
						+ " now written containing response output.");
			} catch (Exception e) {
				//Probably in running in the unix environment...igonore
				System.out.println("Could not create the file " + fileNameWithFullPath );
			}
		}
*/
	
	}