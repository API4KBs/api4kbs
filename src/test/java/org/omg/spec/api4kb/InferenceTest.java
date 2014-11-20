package org.omg.spec.api4kb;

import org.junit.Test;
import org.semanticweb.owlapi.model.OWLOntology;

import static org.omg.spec.api4kb.API4KB.*;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

public class InferenceTest extends AbstractInferenceTest {

    @Test
    public void testSmall() {
        OWLOntology ontology = loadOntology( "http://www.omg.org/spec/API4KB/SmallTest/",
                                             "/ontologies/test/TerminologySmallTest.rdf" );
        new ReasonerHelper().makeInferences( ontology );

        assertNotNull( ontology );
    }

    @Test
    public void testFull() {
        String T = "http://www.omg.org/spec/API4KB/FullTest/";

        OWLOntology ontology = loadOntology( T, "/ontologies/test/TerminologyFullTest.rdf" );
        new ReasonerHelper().makeInferences( ontology );

        testAxioms( ontology )
                .checkRelationship( T + "RDFKPFactoryRole", TRM + "requires", T + "CreatingProficiency-KPforRDF" );
    }


}
