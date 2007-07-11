package org.grouter.core.config;

import org.apache.log4j.Logger;
import org.apache.commons.lang.Validate;
import org.grouter.domain.entities.*;
import org.grouter.config.GrouterDocument;
import org.grouter.config.Context;
import org.grouter.core.util.file.FileUtils;

import java.util.Set;
import java.util.HashSet;
import java.util.Map;
import java.util.HashMap;

/**
 * Use this factory (or assemblee) to create Node entities from corresponding xml binding type of object.
 * NodeType.
 * <p/>
 * This factory will also do some basic validation or configuration parameters - like valid paths etc.
 *
 * @author Georges Polyzois
 */
public class ConfigFactory
{
    private static Logger logger = Logger.getLogger(ConfigFactory.class);

    /**
     * Create a NodeConfig config object from a NodeType object(xml binding representation
     * object).
     *
     * @param grouterconfig root element in configuration of the grouter
     * @return Router instance to use within the Grouter domain
     * @throws IllegalArgumentException if failure to validate configuration
     */
    public static Router createRouter(GrouterDocument.Grouter grouterconfig)
    {
        Validate.notNull(grouterconfig, "Global config section is null, or you are lacking a valid grouter home element in your config file!");

        Router router = new Router();
        router.setName(grouterconfig.getName());
        router.setId(grouterconfig.getId());
        router.setRmiRegistryPort(grouterconfig.getRmiRegistryPort());
        router.setRmiServicePort(grouterconfig.getRmiServicePort());
        //router.setStartedOn( grouterconfig.getId() );


        String uri = grouterconfig.getHome();
        if ( uri.startsWith("file://"))
        {
            uri = grouterconfig.getHome().replace("file://", "/");
        }
        if (!FileUtils.isValidDir(uri))
        {
            throw new IllegalArgumentException("Invalid uri path to a grouter home folder. File path should begin with file://  . Path was :" + uri);
        }

        router.setHomePath(uri);


        router.setNodes(getNodes(grouterconfig, router));

        //   logger.info("Router config : " + router);
        return router;
    }


    private static Set<Node> getNodes(GrouterDocument.Grouter grouterconfig, Router router) throws IllegalArgumentException
    {
        Set<Node> result = new HashSet<Node>();

        org.grouter.config.Node[] nodes = grouterconfig.getNodeArray();

        for (org.grouter.config.Node configNode : nodes)
        {
            Node nodeEntity = new Node();
            nodeEntity.setName(configNode.getName());
            nodeEntity.setId(configNode.getId().getStringValue());
            nodeEntity.setReceiverStatic(configNode.getReceiverStatic());
            nodeEntity.setSenderStatic(configNode.getSenderStatic());

            EndPoint inbound = getEndPoint(configNode.getInBoundEndPoint());
            nodeEntity.setInBound(inbound);

            EndPoint outbound = getEndPoint(configNode.getOutBoundEndPoint());
            nodeEntity.setOutBound(outbound);


            nodeEntity.setRouter(router);

            result.add(nodeEntity);
        }
        return result;
    }

    private static EndPoint getEndPoint(org.grouter.config.EndPoint endPoint) throws IllegalArgumentException
    {
        EndPoint point = new EndPoint();
        point.setClazzName(endPoint.getClazzname());

        String uriValid = null;
        if (endPoint.getEndPointType().equals(org.grouter.config.EndPointType.FILE_READER) ||
                endPoint.getEndPointType().equals(org.grouter.config.EndPointType.FILE_WRITER))
        {
            uriValid = endPoint.getUri().replace("file://", "/");
            if (!FileUtils.isValidDir(uriValid))
            {
                throw new IllegalArgumentException("Invalid uriValid path to a file type of endpoint. Path was :" + uriValid);
            }
        }

        if (endPoint.getEndPointType().equals(org.grouter.config.EndPointType.FTP_READER) ||
                endPoint.getEndPointType().equals(org.grouter.config.EndPointType.FTP_WRITER))
        {
            String urlpath = new String( endPoint.getUri() );
            urlpath = urlpath.replace("ftp://", "");
            uriValid = urlpath.replaceAll( "/" , "");
            endPoint.setUri(uriValid);

        }


        Context[] context = endPoint.getContextparamArray();
        Map endPointContextMap = new HashMap();
        if( context != null && context.length > 0 )
        {
            for (Context context1 : context)
            {
                EndPointContext endPointContext = new EndPointContext( context1.getName(), context1.getValue(), point );
                endPointContextMap.put( context1.getName(), context1.getValue()  );
            }
        }


        point.setUri(uriValid);
        point.setId(endPoint.getId());
        point.setScheduleCron(endPoint.getCron());
        point.setEndPointContext( endPointContextMap );

        EndPointType type = EndPointType.valueOf(new Long(endPoint.getEndPointType().intValue()));
        point.setEndPointType(type);

        return point;
    }


}
