package org.siri.config;

import java.util.ArrayList;
import java.util.Iterator;
import java.io.File;
import java.io.IOException;

import org.apache.xmlbeans.*;


import org.siri.feeder.config.FeederConfigDocument;


/**
 * Validating XML against schema using features of the XMLBeans API.
 *
 * - Validating after changes by using the XmlObject.validate method.
 * This method is exposed by types generated by compiling schema. The
 * validate method validates instances against all aspects of schema.
 * Also, with this method you can specify a Collection instance to
 * capture errors that occur during validation.
 *
 */
public class ConfigHandler
{
    private static XmlOptions validationOptions;
    private static String filePath;
    private static ArrayList validationErrors = new ArrayList();

    public ConfigHandler(String filePath)
    {
        this.filePath = filePath;
    }

    /**
     */
    public static void main(String[] args)
    {
        ConfigHandler thisSample = new ConfigHandler("C:\\gepo\\siri\\modules\\feeder\\feederconfig\\src\\test-data\\feederconfig.xml");

        // Use the validate method to validate an instance after
        // updates.
        boolean isValidAfterChanges = thisSample.isValidAfterChanges(filePath);

        // Use the VALIDATE_ON_SET option to validate an instance
        // as updates are made.
        //boolean isValidOnTheFly = thisSample.isValidOnTheFly(args[0]);
    }

    /**
     * Illustrates use of the validate method by making changes to incoming
     * XML that invalidate the XML, then validating the instance and
     * printing resulting error messages.
     *
     * Because this code is designed to generate invalid XML, it
     * returns false when successful.
     *
     * @param xmlPath A path to the XML instance file.
     * @return <code>true if the XML is valid after changes;
     * otherwise, <code>false</code>.
     */
    public boolean isValidAfterChanges(String xmlPath)
    {
        System.out.println("Validating after changes: \n");
        // Set up the validation error listener.

        validationOptions = new XmlOptions();
        validationOptions.setErrorListener(validationErrors);
        FeederConfigDocument systemConfigDocument = (FeederConfigDocument)parseXml(xmlPath, null);

        // During validation, errors are added to the ArrayList for
        // retrieval and printing by the printErrors method.
        boolean isValid = systemConfigDocument.validate(validationOptions);

        if (!isValid)
        {
            printErrors(validationErrors);
        }
        return isValid;

    }

    /**
     * Receives the collection containing errors found during
     * validation and print the errors to the console.
     *
     * @param validationErrors The validation errors.
     */
    public void printErrors(ArrayList validationErrors)
    {
        System.out.println("Errors discovered during validation: \n");
        Iterator iter = validationErrors.iterator();
        while (iter.hasNext())
        {
            System.out.println(">> " + iter.next() + "\n");
        }
    }

    /**
     * <p>Creates a File from the XML path provided in main arguments, then
     * parses the file's contents into a type generated from schema.</p>
     * <p/>
     * <p>Note that this work might have been done in main. Isolating it here
     * makes the code separately available from outside this class.</p>
     *
     * @param xmlFilePath A path to XML based on the schema in inventory.xsd.
     * @return An instance of a generated schema type that contains the parsed
     *         XML.
     */
    public XmlObject parseXml(String xmlFilePath, XmlOptions validationOptions)
    {
        File xmlFile = new File(xmlFilePath);
        XmlObject xml = null;
        try
        {
            xml = XmlObject.Factory.parse(xmlFile, validationOptions);
        } catch (XmlException e)
        {
            e.printStackTrace();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        return xml;
    }
}
