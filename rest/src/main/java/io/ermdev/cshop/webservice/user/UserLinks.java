package io.ermdev.cshop.webservice.user;

import io.ermdev.cshop.data.model.Link;

import javax.ws.rs.core.UriInfo;

public class UserLinks {

    private UserLinks(){}

    public static Link self(Long userId, UriInfo uriInfo) throws NullPointerException {
        if(userId == null)
            throw new NullPointerException("UserId is null");
        if(uriInfo == null)
            throw new NullPointerException("UriInfo is null");
        String rel = "self";
        String href = uriInfo.getBaseUriBuilder().path(UserApi.class).path(userId.toString()).build().toString();
        return new Link(rel, href);
    }
}
