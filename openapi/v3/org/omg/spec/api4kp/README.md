# API4KP OpenAPI Specifications

Based on latest 4.1.0 release, v2 of OpenAPI specification

## Inference 

Maturity - VERY LOW (highly subject to disruptive changes)

**Knowledge-Driven Reasoning (web) Service** that supports the (stateless) execution of
Inferences, Queries, and related information processing tasks on one or more Knowledge Bases
(aka 'Models').

## Knowledge Artifact Repository

Maturity - STABLE

API that expose the functional capabilities of a Knowledge Artifact Repository Service (KAR), as defined in the OMG API4KP specification. 
    
**Knowledge Artifact Repositories** allow to store and retrieve (copies of) Digital Knowledge Artifacts (KA). KARs treat KAs as black-box binary objects, so there is no limitation nor expectation on the nature of the content, or the requirements to consume it. However, identity and versioning must be supported. 

Identifiers must be universal, unique and opaque, so they MUST be UUID v4 compliant. Version tags can follow different patterns (semantic versioning, incremental numbering, date/time stamps, etc...).  

Special considerations involve the _deletion_ of an Artifact. For traceability and safety purposes, KARs SHOULD NOT allow Artifacts to be deleted in an unrecoverable way. Deletion itself is defined as making an Artifact no longer accessible to a client (i.e. status 404). A server SHOULD allow a deletion operation to be undone, e.g. using mechanisms conceptually similar to 'trash bins', and SHOULD at a minimum keep track of the IDs of Artifacts that were at some point managed in each Repository.
For this reason, a two phase deletion is recommended. Deleted Artifacts transition into a 'deleted' status in which they cannot be discovered nor retrieved, unless a dedicated flag is set. Once in a deleted state, Artifacts MAY be deleted permanently.  

The API also supports the (logical) federation of Repositories. A server instance MAY expose different repositories to a client, who should expect each repository to be independent. Whether these repositories map to actual physical repositories (e.g. different DBs), folder-like structures or logic tags/collections is left to the implementation. <br> The same artifact (as defined by having the same ID) COULD be stored in more than one repository, but all copies MUST be identical to each other 

With adequate rights, and if supported by the implementation, repositories can be _enabled_ or _disabled_. 
Enabled (resp. disabled) repositories are (resp. not) available to a client, regardless of whether the (de)allocation of actual resources is involved at the implementation level.

## Knowledge Asset Repository

Maturity - HIGH (possibly subject to extensions)

APIs for a Semantic Knowledge Asset Repository.

**Knowledge Assets**

Knowledge Assets are immutable, versioned works of knowledge (aka 'Piece of Knowledge', or 'Piece of Content'), which are expressible in any form that is fit for consumption by a designated audience.
Assets managed through a Knowledge Asset Catalog & Repository are usually, though not necessarily, _Enterprise_ Knowledge Assets, i.e. Assets whose content is endorsed by some Subject Matter Expert (party), and whose identification and life cycle is managed by an Authority.

**Knowledge Resources** - Assets vs Carriers vs Surrogates

Since Knowledge Assets themselves are immaterial entities, an Asset Repository can either store 'metadata' (Knowledge Asset Surrogates), or store/reference 'manifestations' (Knowledge Carriers). Both Surrogates and Carriers are actually Knowledge Artifacts, and differ in the role they play with respect to a given Asset.
The Asset Repository MIGHT be built on top of a Knowledge Artifact Repository, and internalize the storage of Carriers and Surrogates. An Asset Repository COULD _also_ be federated, and point to Carriers and Surrogates stored in external systems e.g. by means of URLs.
At a minimum, the Asset Repository MUST have direct access to at least one Surrogate, which is considered Canonical, and contains some core, mandatory information about the asset and its relationships to other assets and artifacts (including Carriers and other Surrogates), and some corollary information such as  pedigree/provenance, policies, applicability.
The Canonical Surrogate is a Resource that conforms to some standard adopted by the Repository, and is directly accessible through the Repository APIs.
The Canonical Surrogate is such that there cannot exist two distinct Surrogates of the same Asset that use the canonical metamodel (Knowledge Representation Language, or profile thereof) supported by the server.

Notice that all Carriers associated to an Asset MUST be iso-semantic, i.e. be _variant_ representation of the Asset knowledge content. Surrogates, on the other hand, can have different information content, as long as they refer to the same Asset, and are consistent with each other.

**Series**

All Resources are Versioned. Resources maintain a version-agnostic, (globally unique) ID, and a version tag which is implementation specific, even if semantic versioning is encouraged.
Versions in a series SHOULD be ordered by version tag, based on the natural order of the tags themselves (e.g. incremental numbers vs semantic versioning). Versions SHALL also be ordered by the timestamp each version was established on.
In particular, the LATEST version of a Resource SHALL be determined by tag ordering, and the timestamp will only be used to break any ties. (Implementations COULD use the timestamp itself as a version tag.)

**Knowledge Graph**

Collectively, the Canonical Surrogates constitute a Knowledge Base, of Graph-oriented nature, which is referre to as the Knowledge Asset Graph. The Graph, supports the indexing and querying of the Assets, and can be used to construct Knowledge Asset Catalogs.

**Search and Retrieval**

Assets (and their Carriers) MUST be discoverable through listing, and the individual Resources MUST be retrievable based on a combination of identifiers and version tags.
Filtering SHOULD be supported based on a limited category of metadata, namely types and semantic annotations.
Querying COULD be supported through the Knowledge Graph.
Text based searching MIGHT be supported, but is currently outside of the scope of this specification.

**Content Negotiation**

Given an Asset, its Carriers and Surrogates can also be selected through Content Negotiation. A client can use a formal MIME code (see extAccept later in the documentation) to choose between (i) the Carriers or (ii) the Surrogates of a given Asset, when multiple alternatives are present.
A client can also use Content Negotiation in combination with an Artifact identifier. In this case, the client declares their intent to access the information content of a specific Artifact (version), but asks that the Artifact be delivered in a certain format.
To support Content Negotiation, Asset Repositories SHOULD be able to provide Artifacts even when they are not materially stored/referenced, but can be derived on the fly at runtime using a Language service API (which internally could further delegate to KB/reasoning services for complex tasks such as assembling or semantic trans*tion).

Notice that the Asset Repository Content Negotiation is at a different level than the Content Negotiation provided by OpenAPI natively. (Formal) Content negotiation is applied _before_ a resulting Artifact (Carrier or Surrogate) is wrapped in a KnowledgeCarrier object and returned.
Standard OpenAPI content negotiation is applied to any API return type, including KnowledgeCarriers, at a later stage.

**Composites**

A Composite Resource (Asset or Artifact) is a resource that consists in components, which are either Composite Resources of the same type, or regular atomic Resources. Composites are also defined by the relationships between the parts. For this reason, each Composite has a special Component, a Struct(ure), which is a graph-oriented Artifact which establishes types, roles and relationships and is normally a subset of the Knowledge Asset Graph.
Composites are implicitly manifested through their identifier and Structure. The components can be resolved and assembled into a Composite Surrogate or into a Composite Artifact.
The Composite is distinct from each of its components, even when one of the components has a prominent role with respect to the others.
As such, Composites can have their own independent Canonical Surrogate, and have one or more variant Carriers.
Notice that, in general, a Composite Asset can be carried by an atomic Carrier, and an atomic Asset can be carried by a Composite Artifact.
Atomic Resources can be created from their Composite counterparts by means of _flattening_.

**Service Boundaries**

Unlike Knowledge Artifact Repositories, for which Artifacts are black boxes, Knowledge _Asset_ Repositories consider Assets and their Carriers as gray boxes.

- A Knowledge Asset Repository SHOULD delegate to a Language Service the ability to parse/serialize, validate and otherwise analyze the content of a Knowledge Carriers associated to an Asset. Language Trans*ion services SHOULD also be leveraged for Content Negotiation Purposes.
- A Knowledge Asset Repository SHOULD leverage a Knowledge Base Construction Service to manipulate artifacts, especially for the purpose of constructing, deconstructing, flattening and generating metadata.
- A Knowledge Asset Repository MIGHT also interact with a Reasoning Service, which SHOULD treat the Knowledge Asset Graph as a Knowledge Base, performing inferences and answering queries.
- A Knowledge Asset Repository SHALL NOT, instead, leverage a Reasoning Service to reason _with_ the Artifacts' content for any other reason that the extraction/validation of the Asset metadata. This choice is motivated by the different SLA constraints (e.g. security, traffic/load) involved when knowledge is 'in motion' rather than 'at rest'.

## Knowledge Base

The Knowledge Base (KB) Construction APIs allow to compose one or more Knowledge Assets into Knowledge Bases that are suitable for processing/reasoning.

The process, which is driven by metadata (surrogates), involves the retrieval, normalization,
and structuring of Knowledge Artifacts that manifest the components of the Knowledge Base itself.

As such, implementations of this API are likely to depend on the API4KP Knowledge Trans*ion APIs and, more loosely, on the API4KP Knowledge Asset Repository API.

This API, in turn, is designed to be a dependency of the API4PK Reasoning/Inference APIs.

The API is inspired by the State monad. Knowledge Bases are incubated within the server from their initialization, through their construction, until their finalization and retrieval.

As operations are applied to manipulate the KB, new versions are constructed ensuring reproducibility and traceabiilty.

Implementations, however, are not required nor guaranteed to be transactional.

## Knowledge Transrepresentation

Maturity - MEDIUM (subject to extensions)

This API defines "syntactic" manipulations of Knowledge Artifacts, based on the stratified representational aspects
of the Artifacts themselves (Language, profile, syntax/serialization, meta-format, encoding).

Supports both 'vertical' operations (parsing/serialization), which preserve the Asset and the Langauge,
and 'horizontal' operations (transrepresentations) which preserve the aspects up to a certain level,
but map across variants at the same level.

The API specializes the Knowledge Platform pattern. Components include one or more Operators, and each Operator
supports an API4KP Operation.

- The Operator is the primary entity. For a given Operation type (detect, translate, etc), the server is expected
to index one more operators. If multiple operators of the same kind exist, the server will play a broker role and
use content negotiation to select the operator that is best fitting to satisfy a client's request.
- The Operators can also be discovered, directly or through the Components. The APIs are overloaded so that
the client can interact with a specific Operator.

Syntactic Operations are curried to consume a Knowledge Artifact (wrapped in a Knowledge Carrier),
and return a wrapped KnowledgeCarrier, effectively behaving as functors in the functional programming sense.

The Operators can be addressed by identifier, or by pattern matching on the Syntactic Representations they consume or produce.
Patterns on the input will select operators that can process more equal or more general representations; patterns on the output will select operators that can produce equal or more specific representations than the pattern itself. Where necessary (e.g. as query/filter parameters), Syntactic Representations are encoded using formal MIME types.

## Terminology

This is a Terminology API built on top of the API4KP framework that provides access to Terminologies and Concepts.
    
In this context, Terminologies are loosely defined as sets of Concepts, e.g. in the sense of SKOS ConceptSchemes or Collections, or OWL Ontology Documents. Concepts are atomic units of knowledge that can be described/defined, can be related to each other, and can be evoked by means of linguistic terms.

Implementations of this API can be more formal in their definition, e.g. by enforcing the separation of Class and Concept.

This API is designed for Knowledge-Based implementations
