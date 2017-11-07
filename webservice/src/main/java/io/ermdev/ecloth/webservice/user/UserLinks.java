package io.ermdev.ecloth.webservice.user;

import io.ermdev.ecloth.model.resource.Link;

import javax.ws.rs.core.UriInfo;

public class UserLinks {

    private UserLinks(){}

    public static Link self(Long userId, UriInfo uriInfo) throws NullPointerException {
        if(userId == null)
            throw new NullPointerException("UserId is null");
        if(uriInfo == null)
            throw new NullPointerException("UriInfo is null");
        String rel = "self";
        String href = uriInfo.getBaseUriBuilder().path(UserResource.class).path(userId.toString()).build().toString();
        return new Link(rel, href);
    }
}
