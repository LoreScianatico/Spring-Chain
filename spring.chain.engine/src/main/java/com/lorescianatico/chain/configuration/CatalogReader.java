package com.lorescianatico.chain.configuration;

import com.lorescianatico.chain.configuration.model.Catalog;
import com.lorescianatico.chain.fault.InvalidCatalogException;
import lombok.Synchronized;
import lombok.extern.slf4j.Slf4j;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * The class for holding chain catalog
 */
@Slf4j
public final class CatalogReader {

    /**
     * Schema definition
     */
    private static final String SCHEMA = "/catalog.xsd";

    /**
     * Private constructor
     */
    private CatalogReader() {}

    /**
     * Get the catalog configuration
     *
     * @param sourceFile the source of the catalog configuration
     * @return the deserialized catalog object
     */
    @Synchronized
    public static Catalog getCatalog(String sourceFile){
        verifyCatalog(sourceFile);
        return loadCatalog(sourceFile);
    }

    /**
     * Verify that catalog configuration is compliant with the catalog schema
     * @param sourceFile the catalog to be verified
     */
    private static void verifyCatalog(String sourceFile) {
        logger.debug("Validating catalog file: " + sourceFile);
        try (InputStream xml = new FileInputStream(sourceFile)) {
            InputStream xsd = CatalogReader.class.getResourceAsStream(SCHEMA);
            SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = factory.newSchema(new StreamSource(xsd));
            Validator validator = schema.newValidator();
            validator.validate(new StreamSource(xml));
            logger.debug("Catalog file is valid.");
        } catch (SAXException | IOException e) {
            logger.error("Error while validating configuration: " + e.getMessage());
            throw new InvalidCatalogException("Error while validating configuration: " + e.getMessage());
        }

    }

    /**
     * Loads catalog from source fils
     * @param sourceFile the catalog source
     * @return Deserialized catalog
     */
    private static Catalog loadCatalog(String sourceFile) {
        logger.debug("Reading catalog: " + sourceFile);
        try (InputStream xml = new FileInputStream(sourceFile)){
            JAXBContext jaxbContext = JAXBContext.newInstance(Catalog.class.getPackage().getName());
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
            factory.setProperty("javax.xml.stream.supportDTD", false);
			factory.setProperty("javax.xml.stream.isReplacingEntityReferences", false);
			factory.setProperty("javax.xml.stream.isSupportingExternalEntities", false);
            XMLEventReader xmlEventReader = xmlInputFactory.createXMLEventReader(xml);
            JAXBElement<Catalog> catalogDeserialized = jaxbUnmarshaller.unmarshal(xmlEventReader, Catalog.class);
            return  catalogDeserialized.getValue();
        } catch (JAXBException | XMLStreamException e) {
            logger.error("Error while unmarshalling catalog: " + e.getMessage());
            throw new InvalidCatalogException("Error while unmarshalling catalog: " + e.getMessage());
        } catch (IOException e) {
            logger.error("Error while reading catalog file: " + e.getMessage());
            throw new InvalidCatalogException("Error while reading catalog file: : " + e.getMessage());
        }
    }

}
