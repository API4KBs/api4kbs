package org.omg.spec.api4kb;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLOntology;

import java.util.Set;

import static org.junit.Assert.assertTrue;

public class AxiomTester {

    private OWLOntology ontology;
    private OWLDataFactory odf;

    public AxiomTester( OWLOntology ontology ) {
        this.ontology = ontology;
        this.odf = ontology.getOWLOntologyManager().getOWLDataFactory();
    }

    public AxiomTester checkRelationship( String subject, String predicate, String object ) {

        OWLNamedIndividual sub = odf.getOWLNamedIndividual( IRI.create( subject ) );
        OWLNamedIndividual obj = odf.getOWLNamedIndividual( IRI.create( object ) );
        OWLObjectProperty prp = odf.getOWLObjectProperty( IRI.create( predicate ) );

        Set<OWLObjectPropertyAssertionAxiom> info = ontology.getObjectPropertyAssertionAxioms( sub );
        OWLObjectPropertyAssertionAxiom testX = odf.getOWLObjectPropertyAssertionAxiom( prp, sub, obj );

        assertTrue( info.contains( testX ) );

        return this;
    }


}
