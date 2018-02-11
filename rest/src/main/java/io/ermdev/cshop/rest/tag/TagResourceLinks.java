package io.ermdev.cshop.rest.tag;

import io.ermdev.cshop.commons.Link;

import javax.ws.rs.core.UriInfo;

public class TagResourceLinks {

    private UriInfo uriInfo;

    public TagResourceLinks(UriInfo uriInfo) {
        this.uriInfo = uriInfo;
    }

    public Link getSelf(Long tagId) {
        final String rel = "self";
        final String href = uriInfo.getBaseUriBuilder()
                .path(TagResource.class)
                .path(tagId.toString())
                .build()
                .toString();
        return new Link(rel, href);
    }
}
