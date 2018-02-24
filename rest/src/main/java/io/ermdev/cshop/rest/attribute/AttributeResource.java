package io.ermdev.cshop.rest.attribute;

import io.ermdev.cshop.commons.Error;
import io.ermdev.cshop.data.entity.Attribute;
import io.ermdev.cshop.data.service.AttributeService;
import io.ermdev.cshop.exception.EntityException;
import mapfierj.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.ArrayList;
import java.util.List;

@Component
@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
@Path("attributes")
public class AttributeResource {

    private AttributeService attributeService;
    private Mapper mapper;

    @Autowired
    public AttributeResource(AttributeService attributeService, Mapper mapper) {
        this.attributeService = attributeService;
        this.mapper = mapper;
    }

    @GET
    @Path("{attributeId}")
    public Response getById(@PathParam("attributeId") Long attributeId, @Context UriInfo uriInfo) {
        try {
            Attribute attribute = attributeService.findById(attributeId);
            AttributeDto attributeDto = mapper.set(attribute).mapTo(AttributeDto.class);
            AttributeResourceLinks attributeResourceLinks = new AttributeResourceLinks(uriInfo);
            attributeDto.getLinks().add(attributeResourceLinks.getSelf(attributeId));
            return Response.status(Response.Status.OK).entity(attributeDto).build();
        } catch (EntityException e) {
            Error error = new Error(e.getMessage());
            return Response.status(Response.Status.NOT_FOUND).entity(error).build();
        }
    }

    @GET
    public Response getAll(@Context UriInfo uriInfo) {
        try {
            List<AttributeDto> attributeDtos = new ArrayList<>();
            attributeService.findAll().parallelStream().forEach(attribute -> {
                AttributeDto attributeDto = mapper.set(attribute).mapTo(AttributeDto.class);
                AttributeResourceLinks attributeResourceLinks = new AttributeResourceLinks(uriInfo);
                attributeDto.getLinks().add(attributeResourceLinks.getSelf(attributeDto.getId()));
                attributeDtos.add(attributeDto);
            });
            return Response.status(Response.Status.OK).entity(attributeDtos).build();
        } catch (EntityException e) {
            Error error = new Error(e.getMessage());
            return Response.status(Response.Status.NOT_FOUND).entity(error).build();
        }
    }
}
