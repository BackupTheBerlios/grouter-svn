package org.grouter.core.validator;

import org.springframework.validation.Validator;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.grouter.domain.entities.Router;
import org.grouter.domain.entities.Node;
import org.grouter.core.util.file.FileUtils;
import org.apache.commons.validator.EmailValidator;

import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: georgespolyzois
 * Date: Mar 20, 2008
 * Time: 6:39:34 PM
 * To change this template use File | Settings | File Templates.
 */
public class NodeConfigurationValidator implements Validator
{
    public boolean supports(Class clazz)
    {
        return Node.class.isAssignableFrom(clazz);
    }

    /**
     * Here we will check for paths and if create paths flag is set for a node or for the Router instance
     * we will create validate the paths and create paths that are missing.
     *
     * @param object a node entity instance
     * @param errors will contain all errors we found and could not fix
     */
    public void validate(Object object, Errors errors)
    {
        Node node = (Node) object;
        if (!FileUtils.isValidDir(node.getInBound().getUri()))
        {
            errors.rejectValue("inBound.uri", "node.inbound.uri.notvalidpath", "Nodes inobund URI not valid path.");
        }
    }
}