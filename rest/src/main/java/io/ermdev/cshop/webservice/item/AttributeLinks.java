package io.ermdev.cshop.webservice.item;

import io.ermdev.cshop.data.model.Link;

import javax.ws.rs.core.UriInfo;

public class AttributeLinks {

    public static Link self(Long attributeId, UriInfo uriInfo) throws NullPointerException {
        if(attributeId == null)
            throw new NullPointerException("attributeId is null");
        if(uriInfo == null)
            throw new NullPointerException("uriInfo is null");

        final String rel="self";
        final String href=uriInfo.getBaseUriBuilder()
                .path(AttributeResource.class).path(attributeId.toString()).build().toString();
        return new Link(rel, href);
    }
}
