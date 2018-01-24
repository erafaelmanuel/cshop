package io.ermdev.cshop.rest.user;

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

    public Link getRoles(Long userId) {
        final String rel = "roles";
        final String href = uriInfo.getBaseUriBuilder()
                .path(UserResource.class)
                .path(userId.toString())
                .path(UserRoleResource.class)
                .path("all")
                .build()
                .toString();
        return new Link(rel, href);
    }
}
