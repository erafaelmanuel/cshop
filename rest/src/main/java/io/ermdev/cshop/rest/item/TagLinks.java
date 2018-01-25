package io.ermdev.cshop.rest.item;

import io.ermdev.cshop.commons.Link;

import javax.ws.rs.core.UriInfo;

public class TagLinks {

    private TagLinks() {}

    public static Link self(Long tagId, UriInfo uriInfo) throws NullPointerException {
        if(tagId == null)
            throw new NullPointerException("tagId is null");
        if(uriInfo == null)
            throw new NullPointerException("UriInfo is null");

        final String rel="self";
        final String href=uriInfo.getBaseUriBuilder().path(TagResource.class).path(tagId.toString()).build().toString();
        return new Link(rel, href);
    }

    public static Link related(Long tagId, UriInfo uriInfo) throws NullPointerException {
        if(tagId == null)
            throw new NullPointerException("tagId is null");
        if(uriInfo == null)
            throw new NullPointerException("UriInfo is null");

        final String rel="related";
        final String href=uriInfo.getBaseUriBuilder().path(TagResource.class)
                .path(tagId.toString()).path(RelatedTagResource.class).build().toString();
        return new Link(rel, href);
    }

    public static Link removeRelated(Long tagId, Long relatedTagId, UriInfo uriInfo) throws NullPointerException {
        if(tagId == null)
            throw new NullPointerException("tagId is null");
        if(uriInfo == null)
            throw new NullPointerException("UriInfo is null");

        final String rel="remove";
        final String href=uriInfo.getBaseUriBuilder().path(TagResource.class).path(tagId.toString())
                .path(RelatedTagResource.class).path(relatedTagId.toString()).build().toString();
        return new Link(rel, href);
    }
}
