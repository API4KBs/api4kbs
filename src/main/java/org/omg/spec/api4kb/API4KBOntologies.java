package org.omg.spec.api4kb;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.coode.owlapi.manchesterowlsyntax.ManchesterOWLSyntaxOntologyFormat;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.io.OWLFunctionalSyntaxOntologyFormat;
import org.semanticweb.owlapi.io.OWLXMLOntologyFormat;
import org.semanticweb.owlapi.io.RDFXMLOntologyFormat;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyFormat;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import uk.ac.manchester.cs.owlapi.dlsyntax.DLSyntaxOntologyFormat;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;

public class API4KBOntologies {

    private static Logger logger = LogManager.getLogger( API4KBOntologies.class );

    public static OWLOntologyFormat inputFormat = new OWLFunctionalSyntaxOntologyFormat();

    public static OWLOntologyFormat outputFormat = new RDFXMLOntologyFormat();

    private static final String[] ontologies = new String[] {
            "omg/SpecificationMetadata",

            "ontoiop/OntoIOpTerminology",

            "api4kb/API4KBTerminologyEvent",
            "api4kb/API4KBTerminologyOperation",
            "api4kb/API4KBTerminologyTry",
            "api4kb/API4KBTerminologyProficiency",
            "api4kb/API4KBTerminology"
    };

    private static String path = "/ontologies/";


    public static void loadTheory( OWLOntologyManager manager ) throws OWLOntologyCreationException {
        for ( String onto : ontologies ) {
            if ( logger.isInfoEnabled() ) {
                logger.info( "Loading ontology ... " + onto );
            }
            manager.loadOntologyFromOntologyDocument( API4KBOntologies.class.getResourceAsStream( path + onto + getExtension( inputFormat ) ) );
            if ( logger.isInfoEnabled() ) {
                logger.info( "Ontology " + onto + " loaded " );
            }
        }
    }

    private static String getExtension( OWLOntologyFormat inputFormat ) {
        // TODO do a proper mapping
        if ( inputFormat instanceof RDFXMLOntologyFormat ) {
            return ".rdf";
        }
        if ( inputFormat instanceof OWLFunctionalSyntaxOntologyFormat ) {
            return ".ofn";
        }
        if ( inputFormat instanceof ManchesterOWLSyntaxOntologyFormat ) {
            return ".ms";
        }
        if ( inputFormat instanceof DLSyntaxOntologyFormat ) {
            return ".dl";
        }
        if ( inputFormat instanceof OWLXMLOntologyFormat ) {
            return ".owl";
        }
        return ".owl";
    }

    public static void rewriteOntologies( ) throws Exception {
        rewriteOntologies( inputFormat, outputFormat, path );
    }

    public static void rewriteOntologies( OWLOntologyFormat input, OWLOntologyFormat output, String targetPath ) throws Exception {
        OWLOntologyManager manager = OWLManager.createOWLOntologyManager();

        for ( String ontology : ontologies ) {
            String srcRelativePath = path + ontology + getExtension( input );
            String tgtRelativePath = targetPath + path + ontology + getExtension( output );

            URL res = API4KBOntologies.class.getResource( srcRelativePath );
            File src = new File( res.toURI() );

            File tgt = new File( tgtRelativePath );
            if ( ! tgt.getParentFile().exists() ) {
                tgt.getParentFile().mkdirs();
            }

            OWLOntology onto = manager.loadOntologyFromOntologyDocument( src );
            manager.saveOntology( onto, output, new FileOutputStream( tgt ) );
        }
    }


    public static void main( String... args ) {
        try {
            if ( args.length > 0 ) {
                String tgtPath = args[ 0 ];
                rewriteOntologies( inputFormat, outputFormat, tgtPath );
            } else {
                logger.error( "Unable to generate ontologies - no target path was provided" );
            }
        } catch ( Exception e ) {
            e.printStackTrace();
        }
    }
}
