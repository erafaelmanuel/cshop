package io.ermdev.cshop.rest.attribute;

import io.ermdev.cshop.commons.Link;

import javax.ws.rs.core.UriInfo;

public class AttributeResourceLinks {

    private UriInfo uriInfo;

    public AttributeResourceLinks(UriInfo uriInfo) {
        this.uriInfo = uriInfo;
    }

    public Link getSelf(Long attributeId) {
        final String rel = "self";
        final String href = uriInfo.getBaseUriBuilder()
                .path(AttributeResource.class)
                .path(attributeId.toString())
                .build()
                .toString();
        return new Link(rel, href);
    }
}
