---
---


{% assign norm = site.collections | where: "label", "Normative" | first %}
{% assign info = site.collections | where: "label", "Informative" | first %}
{% assign brdg = site.collections | where: "label", "Bridges" | first %}
{% assign omg  = site.collections | where: "label", "OMG" | first %}
{% assign dol  = site.collections | where: "label", "DOL" | first %}

# API4KP
## {{ norm.label }}
{% for file in norm.files %}
   * [{{file.basename}}]( {{ site.baseurl }}{{ file.path | replace: "_", "/"}} ){:target="_blank"}
{% endfor %}

## {{ info.label }}
{% for file in info.files %}
   * [{{file.basename}}]( {{ site.baseurl }}{{ file.path | replace: "_", "/"}} ){:target="_blank"}
{% endfor %}

## {{ brdg.label }}
{% for file in brdg.files %}
   * [{{file.basename}}]( {{ site.baseurl }}{{ file.path | replace: "_", "/"}} ){:target="_blank"}
{% endfor %}

# Other OMG
## {{ dol.label }}
{% for file in dol.files %}
   * [{{file.basename}}]( {{ site.baseurl }}{{ file.path | replace: "_", "/"}} ){:target="_blank"}
{% endfor %}

## {{ omg.label }}
{% for file in omg.files %}
   * [{{file.basename}}]( {{ site.baseurl }}{{ file.path | replace: "_", "/"}} ){:target="_blank"}
{% endfor %}





---
[Credits](about.md)