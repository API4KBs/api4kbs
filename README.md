# API4KP - API for Knowledge Platforms

[![Build Status](https://travis-ci.com/API4KBs/api4kbs.svg?branch=master)](https://travis-ci.com/API4KBs/api4kbs)
[ ![Download](https://api.bintray.com/packages/api4kbs/API4KP-Mvn-Repo/api4kbs/images/download.svg) ](https://bintray.com/api4kbs/API4KP-Mvn-Repo/api4kbs/_latestVersion)

This repository contains the technical documents that constitute the response to the OMG API4KP standard RFP.

The API for Knowledge Platforms (API4KP) standard is based on a model-driven architecture that combines [UML](http://uml.org/) models, [OWL](https://www.w3.org/OWL/) ontologies and OpenAPI specifications to define a platform, technology, and vendor independent way to build **Knowledge Based Systems**.

## Getting Started

- [GitHub Pages](https://api4kbs.github.io/)
- [API4KB Wiki](http://www.omgwiki.org/API4KB/doku.php?id=start)

[![Join the chat at https://gitter.im/API4KBs/api4kbs](https://badges.gitter.im/Join%20Chat.svg)](https://gitter.im/API4KBs/api4kbs?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge)

## API4KP Docs

This directory contains UML architecture diagrams as well as PowerPoints of specification diagrams.

## API4KP Ontologies

This directory contains normative and informative OWL/RDF documents that define the API4KP concepts.

Recommended: Open with [Protege 5.x](https://protege.stanford.edu/products.php)

See official [README](./ontologies/README.md)

- API4KP Latest RDF/OWL documents for defining API4KP concepts
- OMG Standard RDF/OWL documents
- W3C RDF/OWL documents
- Other RDF/Owl documents

## API4KP OpenAPI Specifications

This directory contains the OpenAPI / Swagger v2 specifications of the API4KP

Recommended: Open with [SwaggerHub](https://swagger.io/tools/swaggerhub/)

See official [README](./openapi/v3/org/omg/spec/api4kp/README.md)

OpenAPI specifications include:
- Inference
- Knowledge Artifact Repository
- Knowledge Asset Repository
- KnowledgeBase
- Knowledge Transrepresentation
- Terminology

## API4KP Publications

This directory contains previous publications on API for Knowledge Platforms.

## API4KP Source Resources

This directory contains the API4KP Registries of:
- Languages
- Profiles
- Serialization
- Lexicon
- Format
- Prefixes

## API4KP UML

This directory contains UML models that describe the core specification, including the information objects exchanged by the APIs

Recommended: Open with [UMLDesigner 9](http://www.umldesigner.org/download/)

In UMLDesigner 9, follow these steps to get started:
- Create a new personal workspace
- Select: File/Import/General/Projects from folder or archive
- Pick "Dir" - {uml-source}/src/main/resources/uml
- Import
- Right click on Project / Configure / Add modeling project nature

UML models include:
- Core API4KP model
- API4KP UML Profiles
- Vocab UML
