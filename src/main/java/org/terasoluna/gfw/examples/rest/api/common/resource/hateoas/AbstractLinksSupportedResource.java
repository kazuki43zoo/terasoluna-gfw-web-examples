package org.terasoluna.gfw.examples.rest.api.common.resource.hateoas;

import java.io.Serializable;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

public abstract class AbstractLinksSupportedResource implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonSerialize(include = Inclusion.NON_EMPTY)
    private Set<Link> links = new LinkedHashSet<>();

    public AbstractLinksSupportedResource addLink(Link link) {
        this.links.add(link);
        return this;
    }

    public AbstractLinksSupportedResource addLinks(Link... links) {
        this.links.addAll(Arrays.asList(links));
        return this;
    }

    public Set<Link> getLinks() {
        return links;
    }

}
