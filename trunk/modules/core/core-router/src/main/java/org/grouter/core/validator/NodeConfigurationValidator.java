package org.grouter.core.validator;

import org.grouter.core.util.file.FileUtils;
import org.grouter.domain.entities.Node;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * not used...
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