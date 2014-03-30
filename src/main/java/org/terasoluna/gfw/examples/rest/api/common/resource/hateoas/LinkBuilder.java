package org.terasoluna.gfw.examples.rest.api.common.resource.hateoas;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Component
public class LinkBuilder {
    public static enum RelNames {
        SELF, CHILDREN, PARENT, NEXT_PAGE("next"), FIRST_PAGE("first"), LAST_PAGE("last");
        private String relName;

        private RelNames() {
            this.relName = name().toLowerCase();
        }

        private RelNames(String relName) {
            this.relName = relName;
        }

        public String getRelName() {
            return relName;
        }
    }

    public Link self() {
        return new Link(RelNames.SELF.getRelName(), ServletUriComponentsBuilder
                .fromCurrentRequest().build().toUri());
    }

    public Link self(String path) {
        return children(RelNames.SELF.getRelName(), path);
    }

    
    public Link created(String id) {
        return children(RelNames.SELF.getRelName(), id);
    }

    public Link children(String rel, String path) {
        return new Link(rel, ServletUriComponentsBuilder.fromCurrentRequestUri().path("/")
                .path(String.valueOf(path)).build().toUri());
    }

    public Link children(String path) {
        return new Link(RelNames.CHILDREN.getRelName(), ServletUriComponentsBuilder
                .fromCurrentRequestUri().path("/").path(String.valueOf(path)).build().toUri());
    }

}
