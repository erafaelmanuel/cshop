package io.ermdev.cshop.rest.token;

import io.ermdev.cshop.commons.Link;

import javax.ws.rs.core.UriInfo;

public class TokenResourceLinks {

    private UriInfo uriInfo;

    public TokenResourceLinks(UriInfo uriInfo) {
        this.uriInfo = uriInfo;
    }

    public Link getSelf(Long tokenId) {
        final String rel = "self";
        final String href = uriInfo.getBaseUriBuilder()
                .path(TokenResource.class)
                .path(tokenId.toString())
                .build()
                .toString();
        return new Link(rel, href);
    }

    public Link getUser(Long tokenId) {
        final String rel = "user";
        final String href = uriInfo.getBaseUriBuilder()
                .path(TokenResource.class)
                .path(tokenId.toString())
                .path(TokenUserResource.class)
                .build()
                .toString();
        return new Link(rel, href);
    }
}
