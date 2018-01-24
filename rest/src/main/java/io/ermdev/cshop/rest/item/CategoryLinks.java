package io.ermdev.cshop.rest.item;

import io.ermdev.cshop.data.model.Link;

import javax.ws.rs.core.UriInfo;

public class CategoryLinks {

    public static Link self(Long categoryId, UriInfo uriInfo) throws NullPointerException {
        if(categoryId == null)
            throw new NullPointerException("categoryId is null");
        if(uriInfo == null)
            throw new NullPointerException("UriInfo is null");

        final String rel="self";
        final String href=uriInfo.getBaseUriBuilder().path(CategoryResource.class)
                .path(categoryId.toString()).build().toString();
        return new Link(rel, href);
    }
}
