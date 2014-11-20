package org.omg.spec.api4kb;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;

import static org.junit.Assert.fail;

public class AbstractInferenceTest {

    protected OWLOntology loadOntology( String ontoIri, String path ) {
        OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
        try {
            API4KBOntologies.loadTheory( manager );
            manager.loadOntologyFromOntologyDocument( getClass().getResourceAsStream( path ) );
        } catch ( OWLOntologyCreationException e ) {
            fail( e.getMessage() );
        }
        return manager.getOntology( IRI.create( ontoIri ) );
    }


    protected AxiomTester testAxioms( OWLOntology ontology ) {
        return new AxiomTester( ontology );
    }


}
