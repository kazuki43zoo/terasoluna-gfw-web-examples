package org.terasoluna.gfw.examples.rest.api.common.resource.hateoas;

import java.io.Serializable;
import java.net.URI;

public class Link implements Serializable {
    private static final long serialVersionUID = 1L;
    private final String rel;
    private final URI href;

    public Link(String rel, URI href) {
        this.rel = rel;
        this.href = href;
    }

    public String getRel() {
        return rel;
    }

    public URI getHref() {
        return href;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((rel == null) ? 0 : rel.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Link other = (Link) obj;
        if (rel == null) {
            if (other.rel != null)
                return false;
        } else if (!rel.equals(other.rel))
            return false;
        return true;
    }

}
