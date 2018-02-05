package io.ermdev.cshop.rest.item;

import io.ermdev.cshop.commons.Link;

import javax.ws.rs.core.UriInfo;

public class ItemResourceLinks {

    private UriInfo uriInfo;

    public ItemResourceLinks(UriInfo uriInfo) {
        this.uriInfo = uriInfo;
    }

    public Link getSelf(Long itemId) {
        final String rel = "self";
        final String href = uriInfo.getBaseUriBuilder()
                .path(ItemResource.class)
                .path(itemId.toString())
                .build()
                .toString();
        return new Link(rel, href);
    }

    public Link getImages(Long itemId) {
        final String rel = "images";
        final String href = uriInfo.getBaseUriBuilder()
                .path(ItemResource.class)
                .path(itemId.toString())
                .path(ItemImageResource.class)
                .build()
                .toString();
        return new Link(rel, href);
    }
}
