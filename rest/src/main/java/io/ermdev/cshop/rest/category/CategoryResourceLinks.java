package io.ermdev.cshop.rest.category;

import io.ermdev.cshop.commons.Link;

import javax.ws.rs.core.UriInfo;

public class CategoryResourceLinks {

    private UriInfo uriInfo;

    public CategoryResourceLinks(UriInfo uriInfo) {
        this.uriInfo = uriInfo;
    }

    public Link getSelf(Long categoryId) {
        final String rel = "self";
        final String href = uriInfo.getBaseUriBuilder()
                .path(CategoryResource.class)
                .path(categoryId.toString())
                .build()
                .toString();
        return new Link(rel, href);
    }
}
