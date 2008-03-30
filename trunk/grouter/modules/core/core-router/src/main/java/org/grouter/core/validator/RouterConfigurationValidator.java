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
 * A Router enity created from a configuration file should conform to config.xsd for grouters. This
 * enforces fields that must have been set that they are set.
 *
 * @authore Georges Polyzois
 */
public class RouterConfigurationValidator implements Validator
{
    Validator nodeValidator = new NodeConfigurationValidator();

    public boolean supports(Class clazz)
    {
        return Router.class.isAssignableFrom(clazz);
    }

    /**
     * Here we will check for paths and if create paths flag is set for a node or for the Router instance
     * we will create validate the paths and create paths that are missing.
     *
     * @param object a router instance
     * @param errors will contain all errors we found and could not fix
     */
    public void validate(Object object, Errors errors)
    {
        Router router = (Router) object;

        if (!FileUtils.isValidDir(router.getHomePath()))
        {
            errors.rejectValue("homePath", "router.homePath.empty", "Router has no homepath.");
        }

        /*
        Set<Node> nodes = router.getNodes();
        for (Node node : nodes)
        {
            nodeValidator.validate(node, errors);
        }      */
    }
}