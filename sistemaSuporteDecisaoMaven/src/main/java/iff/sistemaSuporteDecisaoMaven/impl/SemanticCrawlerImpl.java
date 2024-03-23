package iff.sistemaSuporteDecisaoMaven.impl;

import java.util.HashSet;
import java.util.Set;

import org.apache.jena.rdf.model.Model;

import iff.sistemaSuporteDecisaoMaven.crawler.SemanticCrawler;

import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.Property;

import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.StmtIterator;
import org.apache.jena.rdf.model.Statement;

public class SemanticCrawlerImpl implements SemanticCrawler {
	@Override
	public void search(Model graph, String resourceURI) {
		Set<String> visitedURIs = new HashSet<>();

		crawl(graph, resourceURI, visitedURIs);
	}
	
	private void crawl(Model graph, String resourceURI, Set<String> visitedURIs) {
		if(visitedURIs.contains(resourceURI)) {
			System.out.printf("\n[*] A URI '%s' já foi verificada!", resourceURI);
			return;
		}
		
		CharsetEncoder enc = Charset.forName("ISO-8859-1").newEncoder();

		boolean isValid = enc.canEncode(resourceURI);
		
		if(isValid) {
			visitedURIs.add(resourceURI);
			
			//System.out.println("\n[*] array " + visitedURIs + "\n");
			
			Resource resource = graph.getResource(resourceURI);
			StmtIterator statements = resource.listProperties();
			
			//System.out.println("\n[*] statements " + statements + "\n");
			
			while(statements.hasNext()) {
				Statement statement = statements.next();
				graph.add(statement);
				
				RDFNode object = statement.getObject();
				if(object.isResource()) {
	                crawl(graph, object.asResource().getURI(), visitedURIs);
	            }
			}
			
			StmtIterator sameAsStatements = graph.listStatements(graph.createResource(resourceURI), graph.createProperty("owl:sameAs"), (RDFNode) null);
			
			//System.out.println("\n[*] sameAsStatements " + sameAsStatements + "\n");
			
			while(sameAsStatements.hasNext()) {
				Statement sameAsstatement = sameAsStatements.next();
				RDFNode object = sameAsstatement.getObject();

				if(object.isResource()) {
	                crawl(graph, object.asResource().getURI(), visitedURIs);
	            }
			}
		} else {
			System.out.printf("\n[*] A URI '%s' não pertence ao formato ISO!", resourceURI);
			return;
		}
	}
}