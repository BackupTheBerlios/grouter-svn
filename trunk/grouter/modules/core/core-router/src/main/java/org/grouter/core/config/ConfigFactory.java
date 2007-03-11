package org.grouter.core.config;

import org.apache.log4j.Logger;
import org.apache.commons.lang.Validate;
import org.grouter.domain.entities.Router;
import org.grouter.domain.entities.Node;
import org.grouter.domain.entities.EndPoint;
import org.grouter.domain.entities.EndPointType;
import org.grouter.config.GrouterDocument;

import java.util.Set;
import java.util.HashSet;

/**
 * Use this factory (or assemblee) to create Node entities from corresponding xml binding type of object.
 * NodeType.
 *
 * @author Georges Polyzois
 */
public class ConfigFactory
{
    private static Logger logger = Logger.getLogger( ConfigFactory.class );

    /**
     * Create a NodeConfig config object from a NodeType object(xml binding representation
     * object).
     *
     * @param grouterconfig root element in configuration of the grouter
     * @return
     * @throws IllegalArgumentException if globalType == null
     */
    public static Router createRouter( GrouterDocument.Grouter grouterconfig)
    {
        Validate.notNull( grouterconfig , "Global config section is null, or you are lacking a valid grouter home element in your config file!");


        Router router = new Router();
        router.setName( grouterconfig.getName() );
   //     router.setId( grouterconfig.get );

        router.setNodes( getNodes( grouterconfig ) );

       return router;
    }


    private static Set<Node> getNodes( GrouterDocument.Grouter grouterconfig )
    {
        Set<Node> result = new HashSet<Node>();

        org.grouter.config.Node[] nodes = grouterconfig.getNodeArray();

        for (org.grouter.config.Node configNode : nodes)
        {
            Node nodeEntity = new Node();
            nodeEntity.setName( configNode.getName() );
            nodeEntity.setId( configNode.getId().getStringValue() );


            EndPoint inbound = getEndPoint( configNode.getInBoundEndPoint() );
            nodeEntity.setInBound( inbound );
            EndPoint outbound = getEndPoint( configNode.getOutBoundEndPoint() );
            nodeEntity.setOutBound( inbound );

            result.add( nodeEntity );
        }

        return result;
    }

    private static EndPoint getEndPoint(org.grouter.config.EndPoint inBoundEndPoint)
    {
        EndPoint inbound = new EndPoint();
        inbound.setClazzName( inBoundEndPoint.getClazzname() );
        inbound.setUri( inBoundEndPoint.getUri() );

        EndPointType type = EndPointType.valueOf( new Long(inBoundEndPoint.getEndPointType().intValue()) );
        inbound.setEndPointType( type );

        return inbound;
    }


}
