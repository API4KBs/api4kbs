<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:xs="http://www.w3.org/2001/XMLSchema" exclude-result-prefixes="xs xd dc rdfs swrl owl2xml owl xsd swrlb rdf f dcterms"
                xmlns:xd="http://www.oxygenxml.com/ns/doc/xsl" version="2.0"
                xmlns:dc="http://purl.org/dc/elements/1.1/"
                xmlns:rdfs="http://www.w3.org/2000/01/rdf-schema#"
                xmlns:swrl="http://www.w3.org/2003/11/swrl#"
                xmlns:owl2xml="http://www.w3.org/2006/12/owl2-xml#"
                xmlns:owl="http://www.w3.org/2002/07/owl#"
                xmlns:xsd="http://www.w3.org/2001/XMLSchema#"
                xmlns:swrlb="http://www.w3.org/2003/11/swrlb#"
                xmlns:rdf="http://www.w3.org/1999/02/22-rdf-syntax-ns#"
                xmlns:f="http://www.essepuntato.it/xslt/function"
                xmlns:dcterms="http://purl.org/dc/terms/"
                xmlns:test5="http://foo.test/test5#"
                xmlns:obo="http://purl.obolibrary.org/obo/"
                xmlns:skos="http://www.w3.org/2004/02/skos/core#"
                xmlns="http://www.w3.org/1999/xhtml">

    <xsl:include href="extraction.xsl" />

    <xsl:template match="owl:Class" priority="0.01">
        <div id="{generate-id()}" class="entity">
            <xsl:call-template name="get.entity.name">
                <xsl:with-param name="toc" select="'classes'" tunnel="yes" as="xs:string" />
                <xsl:with-param name="toc.string" select="f:getDescriptionLabel('classtoc')" tunnel="yes" as="xs:string" />
            </xsl:call-template>
            <xsl:call-template name="get.entity.metadata" />
            <xsl:call-template name="get.entity.defs" />
            <xsl:call-template name="get.class.description" />
            <xsl:call-template name="get.entity.info" />
            <xsl:call-template name="get.entity.examples" />
        </div>
    </xsl:template>

    <xsl:template match="owl:NamedIndividual" priority="0.01">
        <div id="{generate-id()}" class="entity">
            <xsl:call-template name="get.entity.name">
                <xsl:with-param name="toc" select="'namedindividuals'" tunnel="yes" as="xs:string" />
                <xsl:with-param name="toc.string" select="f:getDescriptionLabel('namedindividualtoc')" tunnel="yes" as="xs:string" />
            </xsl:call-template>
            <xsl:call-template name="get.entity.metadata" />
            <xsl:call-template name="get.entity.defs" />
            <xsl:call-template name="get.individual.description" />
            <xsl:call-template name="get.entity.info" />
            <xsl:call-template name="get.entity.examples" />
        </div>
    </xsl:template>

    <xsl:template match="owl:ObjectProperty | owl:DatatypeProperty | owl:AnnotationProperty" priority="0.01">
        <div id="{generate-id()}" class="entity">
            <xsl:call-template name="get.entity.name">
                <xsl:with-param name="toc" select="if (self::owl:ObjectProperty) then 'objectproperties' else if (self::owl:AnnotationProperty) then 'annotationproperties' else 'dataproperties'" tunnel="yes" as="xs:string" />
                <xsl:with-param name="toc.string" select="if (self::owl:ObjectProperty) then f:getDescriptionLabel('objectpropertytoc') else if (self::owl:AnnotationProperty) then f:getDescriptionLabel('annotationpropertytoc') else f:getDescriptionLabel('datapropertytoc')" tunnel="yes" as="xs:string" />
            </xsl:call-template>
            <xsl:call-template name="get.entity.metadata" />
            <xsl:call-template name="get.entity.defs" />
            <xsl:call-template name="get.property.description" />
            <xsl:call-template name="get.entity.info" />
            <xsl:call-template name="get.entity.examples" />
        </div>
    </xsl:template>


    <xsl:template name="get.entity.defs">
        <xsl:apply-templates select="rdfs:comment" />
        <xsl:apply-templates select="obo:IAO_0000116" />
        <xsl:apply-templates select="skos:definition" />
    </xsl:template>

    <xsl:template name="get.entity.info">
        <xsl:apply-templates select="dc:description[normalize-space() != ''] , dc:description[@*:resource]" />
        <xsl:apply-templates select="obo:IAO_0000600" />
        <xsl:apply-templates select="skos:note" />
    </xsl:template>

    <xsl:template name="get.entity.examples">
        <xsl:apply-templates select="obo:IAO_0000112" />
        <xsl:apply-templates select="skos:example" />
    </xsl:template>



    <xsl:template match="obo:IAO_0000116">
        <div class="comment">
            <xsl:call-template name="get.content" />
        </div>
    </xsl:template>
    <xsl:template match="skos:definition">
        <div class="comment">
            <xsl:call-template name="get.content" />
        </div>
    </xsl:template>


    <xsl:template match="obo:IAO_0000600">
        <div class="comment">
            <xsl:call-template name="get.content" />
        </div>
    </xsl:template>
    <xsl:template match="skos:note">
        <div class="comment">
            <xsl:call-template name="get.content" />
        </div>
    </xsl:template>


    <xsl:template match="obo:IAO_0000112">
        <div class="literal">
            <xsl:call-template name="get.content" />
        </div>
    </xsl:template>
    <xsl:template match="skos:example">
        <div class="literal">
            <xsl:call-template name="get.content" />
        </div>
    </xsl:template>


</xsl:stylesheet>