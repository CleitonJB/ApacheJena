package iff.sistemaSuporteDecisaoMaven;

//import org.apache.jena.datatypes.xsd.XSDDatatype;
//import org.apache.jena.rdf.model.Model;
//import org.apache.jena.rdf.model.ModelFactory;
//import org.apache.jena.rdf.model.Property;
//import org.apache.jena.rdf.model.Resource;

import org.apache.jena.rdf.model.*;

import java.io.FileOutputStream;
import java.io.OutputStream;

import org.apache.jena.riot.RDFDataMgr;

import iff.sistemaSuporteDecisaoMaven.impl.SemanticCrawlerImpl;

import java.io.File;

public class Main
{
    public static void main(String[] args) {
        String resourceURI = "http://dbpedia.org/resource/Zico";
        
        Model graph = ModelFactory.createDefaultModel();
        graph.read(resourceURI);
        
        SemanticCrawlerImpl crawler = new SemanticCrawlerImpl();
        crawler.search(graph, resourceURI);
       
        System.out.printf("\n\n[*] Grafo resultante: \n\n");
        
        graph.write(System.out);
    }
}