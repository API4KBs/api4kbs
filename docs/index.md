---
---
{% assign sortedColls = site.collections | sort: 'index' %}

{% for coll in sortedColls %}
{% if coll.label != "posts" %}
## {{ coll.label }} 
{% assign sorted = coll.docs | sort: 'index' %}
    {% for onto in sorted %}
* [{{ onto.name }}]( {{ site.baseurl }}{{ onto.lode }} ){:target="_blank"} ({% if onto.normative %} N {% else %} I {% endif %}) - {{ onto.title }}
    {% endfor %}
{% endif %}    
{% endfor %}

---
[Credits](about.md)