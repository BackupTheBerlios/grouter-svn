package org.grouter.core.validator;

import org.grouter.domain.entities.Node;
import org.grouter.domain.entities.EndPoint;
import org.grouter.domain.entities.Router;
import org.springframework.validation.Validator;
import org.springframework.validation.BindException;
import junit.framework.TestCase;

import java.util.Set;
import java.util.HashSet;

/**
 * Created by IntelliJ IDEA.
 * User: georgespolyzois
 * Date: Mar 22, 2008
 * Time: 10:41:32 AM
 * To change this template use File | Settings | File Templates.
 */
public class RouterConfigurationValidatorTest extends TestCase
{
    public void testEmptyHomepatValidation() throws Exception
    {
        EndPoint endPoint = new EndPoint();
        endPoint.setUri("ssdf");
        Node node = new Node();
        node.setInBound(endPoint);
        Router router = new Router();
        Set<Node> nodes = new HashSet<Node>();
        nodes.add( node );
        router.setNodes(nodes);

        Validator validator = new RouterConfigurationValidator();
        BindException errors = new BindException(router, "router");
        validator.validate(router, errors);

        assertEquals("router.homePath.empty", errors.getFieldError("homePath").getCode());
    //    assertEquals("node.inbound.uri.notvalidpath", errors.getFieldError("node.inBound.uri").getCode());

    }
}