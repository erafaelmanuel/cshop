package io.ermdev.cshop.webservice.user;

import io.ermdev.cshop.data.model.Link;

import javax.ws.rs.core.UriInfo;

public class UserResourceLinks {

    private UriInfo uriInfo;

    public UserResourceLinks(UriInfo uriInfo) {
        this.uriInfo = uriInfo;
    }

    public Link getSelf(Long userId) {
        final String rel = "self";
        final String href = uriInfo.getBaseUriBuilder()
                .path(UserResource.class)
                .path(userId.toString())
                .build()
                .toString();
        return new Link(rel, href);
    }
}
