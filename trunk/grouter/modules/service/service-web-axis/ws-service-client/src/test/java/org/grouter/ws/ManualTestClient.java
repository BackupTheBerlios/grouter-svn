package org.grouter.ws;

import junit.framework.TestCase;
import junit.framework.AssertionFailedError;
import org.grouter.ws.grouterservice.*;
import org.apache.axis.client.Call;

import javax.xml.rpc.ServiceFactory;
import javax.xml.rpc.Service;
import javax.xml.rpc.ServiceException;
import javax.xml.namespace.QName;
import java.net.URL;


/**
 * Unit test the servic eoperations manually. Originally created from axis.
 */
public class ManualTestClient extends TestCase
{
    private String grouterserviceEndpoint = "http://localhost:8080/grouter/services/GRouterService";

    public ManualTestClient(java.lang.String name)
    {
        super(name);
    }

    public void testGRouterServiceWSDL() throws Exception
    {
        ServiceFactory serviceFactory = ServiceFactory.newInstance();
        URL url = new URL(grouterserviceEndpoint + "?WSDL");
        QName qName = new QName("http://grouter.org/ws/grouterservice", "GRouterService");
        Service service = serviceFactory.createService(url, qName);
        assertTrue(service != null);
    }



    /**
     * Dynamic invocation interface
     */
    public void tesCallDII()
    {
        try {
            org.apache.axis.client.Service service = new org.apache.axis.client.Service();
            Call call = (Call) service.createCall();

            call.setTargetEndpointAddress(grouterserviceEndpoint );
            call.setOperationName(new QName("GRouterService", "getServicesNoException") );
            Object ret = call.invoke( new Object[] { "in param" } );
            //System.out.println("Object = " + ret.getClass().getName());
            System.out.println("Number Returned : " + ret.toString());
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }


    public void test3()  throws Exception
    {
        //QName serviceName = new QName("urn:attachment.tip","AttachmentService");
        //URL wsdlLocation = new URL("http://localhost:9080/attachment/services/AttachmentTip?wsdl");
        javax.xml.namespace.QName qName = new javax.xml.namespace.QName("http://grouter.org/ws/grouterservice", "GRouterService");
        URL wsdlLocation = new URL(grouterserviceEndpoint + "?wsdl");

        javax.xml.rpc.ServiceFactory factory = javax.xml.rpc.ServiceFactory.newInstance();
        javax.xml.rpc.Service service = factory.createService(wsdlLocation, qName);

        /* javax.xml.rpc.Call call = service.createCall();
       call.setTargetEndpointAddress(grouterserviceEndpoint );
       call.setOperationName(new javax.xml.namespace.QName("GRouterService", "getServicesNoException") );
       Object ret = call.invoke( new Object[] { "in param" } );
       System.out.println("Number Returned : " + ret.toString());
        */
        //QName portName = new QName("", "AttachmentTip");
        //service.
        //return (AttachmentTip) service.getPort(portName,AttachmentTip.class);
    }



    public void test2GRouterServiceGetServicesNoException() throws Exception
    {
        GRouterServiceSoapBindingStub binding;
        GRouterService_ServiceLocator locator = new GRouterService_ServiceLocator();
        locator.setEndpointAddress("GRouterService",grouterserviceEndpoint);
        try {
            binding = (GRouterServiceSoapBindingStub)locator.getGRouterService();
        }
        catch (javax.xml.rpc.ServiceException jre) {
            if(jre.getLinkedCause()!=null)
                jre.getLinkedCause().printStackTrace();
            throw new junit.framework.AssertionFailedError("JAX-RPC ServiceException caught: " + jre);
        }
        assertNotNull("binding is null", binding);

        // Time out after a minute
        binding.setTimeout(60000);

        // Test operation
        String value = binding.getServicesNoException(new String());
        System.out.println("value : " + value);
        value = binding.getServices();
        System.out.println("value : " + value);

    }
}
