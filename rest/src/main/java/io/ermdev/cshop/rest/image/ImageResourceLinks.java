package io.ermdev.cshop.rest.image;

import io.ermdev.cshop.commons.Link;

import javax.ws.rs.core.UriInfo;

public class ImageResourceLinks {

    private UriInfo uriInfo;

    public ImageResourceLinks(UriInfo uriInfo) {
        this.uriInfo = uriInfo;
    }

    public Link getSelf(Long imageId) {
        final String rel = "self";
        final String href = uriInfo.getBaseUriBuilder()
                .path(ImageResource.class)
                .path(imageId.toString())
                .build()
                .toString();
        return new Link(rel, href);
    }
}
