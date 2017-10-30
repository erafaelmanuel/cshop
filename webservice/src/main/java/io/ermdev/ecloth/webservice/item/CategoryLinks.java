package io.ermdev.ecloth.webservice.item;

import io.ermdev.ecloth.model.resource.Link;

import javax.ws.rs.core.UriInfo;

public class CategoryLinks {

    public static Link self(Long categoryId, UriInfo uriInfo) throws NullPointerException {
        if(uriInfo == null)
            throw new NullPointerException("UriInfo is null");

        final String rel="self";
        final String href=uriInfo.getBaseUriBuilder().path(CategoryResource.class)
                .path(categoryId.toString()).build().toString();
        return new Link(rel, href);
    }
}
