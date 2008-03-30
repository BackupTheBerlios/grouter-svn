package org.grouter.core.validator;

import org.grouter.domain.entities.Node;
import org.grouter.domain.entities.EndPoint;
import org.springframework.validation.Validator;
import org.springframework.validation.BindException;
import junit.framework.TestCase;

/**
 *
 * @author Georges Polyzois
 */
public class NodeConfigurationValidatorTest extends TestCase
{
    public void testEmptyPersonValidation() throws Exception
    {
        EndPoint endPoint = new EndPoint();
        endPoint.setUri( "invalidurl" );
        Node node = new Node();
        node.setInBound( endPoint );
        Validator validator = new NodeConfigurationValidator();
        BindException errors = new BindException(node, "node");
        validator.validate(node, errors);

        assertEquals("node.inbound.uri.notvalidpath", errors.getFieldError("inBound.uri").getCode());
    }
}