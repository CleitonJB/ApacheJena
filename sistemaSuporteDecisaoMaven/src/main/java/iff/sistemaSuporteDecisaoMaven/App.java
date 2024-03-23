package iff.sistemaSuporteDecisaoMaven;

import org.apache.jena.datatypes.xsd.XSDDatatype;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;

/**
 * Hello world!
 *
 */
public class App 
{
	static private String URI = "http://example.org/test/";

    public static void main(String args[])
    {
    	Model model = ModelFactory.createDefaultModel();
    	
    	Resource subject = model.createResource(URI + "message");
    	
    	Property property = model.createProperty(URI + "says");
    	
    	subject.addProperty(property, "Hello World!", XSDDatatype.XSDstring);
    	
        model.write(System.out);
    }
}