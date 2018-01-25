package io.ermdev.cshop.rest.role;

import io.ermdev.cshop.commons.Link;

import javax.ws.rs.core.UriInfo;

public class RoleResourceLinks {

    private UriInfo uriInfo;

    public RoleResourceLinks(UriInfo uriInfo) {
        this.uriInfo = uriInfo;
    }

    public Link getSelf(Long roleId) throws NullPointerException {
        final String rel = "self";
        final String href = uriInfo.getBaseUriBuilder()
                .path(RoleResource.class)
                .path(roleId.toString())
                .build()
                .toString();
        return new Link(rel, href);
    }
}
