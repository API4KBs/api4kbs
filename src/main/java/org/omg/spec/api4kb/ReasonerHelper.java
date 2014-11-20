package org.omg.spec.api4kb;

import org.semanticweb.HermiT.Reasoner;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.reasoner.ConsoleProgressMonitor;
import org.semanticweb.owlapi.reasoner.InferenceType;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.semanticweb.owlapi.reasoner.OWLReasonerConfiguration;
import org.semanticweb.owlapi.reasoner.OWLReasonerFactory;
import org.semanticweb.owlapi.reasoner.SimpleConfiguration;
import org.semanticweb.owlapi.util.InferredAxiomGenerator;
import org.semanticweb.owlapi.util.InferredClassAssertionAxiomGenerator;
import org.semanticweb.owlapi.util.InferredDataPropertyCharacteristicAxiomGenerator;
import org.semanticweb.owlapi.util.InferredEquivalentClassAxiomGenerator;
import org.semanticweb.owlapi.util.InferredEquivalentDataPropertiesAxiomGenerator;
import org.semanticweb.owlapi.util.InferredEquivalentObjectPropertyAxiomGenerator;
import org.semanticweb.owlapi.util.InferredInverseObjectPropertiesAxiomGenerator;
import org.semanticweb.owlapi.util.InferredOntologyGenerator;
import org.semanticweb.owlapi.util.InferredPropertyAssertionGenerator;
import org.semanticweb.owlapi.util.InferredSubClassAxiomGenerator;
import org.semanticweb.owlapi.util.InferredSubDataPropertyAxiomGenerator;
import org.semanticweb.owlapi.util.InferredSubObjectPropertyAxiomGenerator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ReasonerHelper {

    public static final List<InferredAxiomGenerator<? extends OWLAxiom>> defaultAxiomGenerators = Collections.unmodifiableList(
            new ArrayList<InferredAxiomGenerator<? extends OWLAxiom>>(
                    Arrays.asList(
                            new InferredClassAssertionAxiomGenerator(),
                            new InferredDataPropertyCharacteristicAxiomGenerator(),
                            new InferredEquivalentClassAxiomGenerator(),
                            new InferredEquivalentDataPropertiesAxiomGenerator(),
                            new InferredEquivalentObjectPropertyAxiomGenerator(),
                            new InferredInverseObjectPropertiesAxiomGenerator(),
                            new InferredPropertyAssertionGenerator(),
                            new InferredSubClassAxiomGenerator(),
                            new InferredSubDataPropertyAxiomGenerator(),
                            new InferredSubObjectPropertyAxiomGenerator()
                    ) ) );


    public void makeInferences( OWLOntology ontology ) {
        InferredOntologyGenerator reasoner = new InferredOntologyGenerator( initReasoner( ontology ), defaultAxiomGenerators );
        reasoner.fillOntology( ontology.getOWLOntologyManager(), ontology );
    }

    protected OWLReasoner initReasoner( OWLOntology ontoDescr ) {

        ConsoleProgressMonitor progressMonitor = new ConsoleProgressMonitor();
        OWLReasonerConfiguration config = new SimpleConfiguration(progressMonitor);

        OWLReasonerFactory reasonerFactory = new Reasoner.ReasonerFactory();
        OWLReasoner owler = reasonerFactory.createReasoner( ontoDescr, config );
        owler.precomputeInferences(
                InferenceType.CLASS_HIERARCHY,
                InferenceType.CLASS_ASSERTIONS,

                InferenceType.OBJECT_PROPERTY_ASSERTIONS,
                InferenceType.DATA_PROPERTY_ASSERTIONS,

                InferenceType.DIFFERENT_INDIVIDUALS,
                InferenceType.SAME_INDIVIDUAL,

                InferenceType.DISJOINT_CLASSES,

                InferenceType.OBJECT_PROPERTY_HIERARCHY
        );


        if ( ! owler.isConsistent() ) {
            throw new RuntimeException( "Inconsistent ontology " );
        }

        return owler;
    }


}
