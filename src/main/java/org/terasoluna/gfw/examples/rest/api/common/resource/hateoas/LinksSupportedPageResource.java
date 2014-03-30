package org.terasoluna.gfw.examples.rest.api.common.resource.hateoas;

import org.codehaus.jackson.annotate.JsonUnwrapped;
import org.springframework.data.domain.Page;

public class LinksSupportedPageResource<T> extends AbstractLinksSupportedResource {

    private static final long serialVersionUID = 1L;

    @JsonUnwrapped
    private final Page<T> page;

    public LinksSupportedPageResource(Page<T> page, Link... links) {
        this.page = page;
        for (Link link : links) {
            addLink(link);
        }
    }

    public Page<T> getPage() {
        return page;
    }

}
