package io.ermdev.cshop.rest.attribute;

import io.ermdev.cshop.data.exception.UnsatisfiedEntityException;
import io.ermdev.cshop.data.service.AttributeService;
import io.ermdev.cshop.data.entity.Attribute;
import io.ermdev.cshop.commons.Error;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;

@Component
@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
@Path("attribute")
public class AttributeResource {

    @Context
    private UriInfo uriInfo;

    private AttributeService attributeService;

    @Autowired
    public AttributeResource(AttributeService attributeService) {
        this.attributeService = attributeService;
    }

    @Path("{attributeId}")
    @GET
    public Response getById(@PathParam("attributeId") Long attributeId) {
        try {
            Attribute attribute = attributeService.findById(attributeId);
            attribute.getLinks().add(AttributeLinks.self(attributeId, uriInfo));
            return Response.status(Response.Status.FOUND).entity(attribute).build();
        } catch (EntityNotFoundException e) {
            Error error = new Error(e.getMessage());
            return Response.status(Response.Status.NOT_FOUND).entity(error).build();
        } catch (NullPointerException e) {
            Error error = new Error(e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(error).build();
        }
    }

    @GET
    public Response getAll() {
        try {
            List<Attribute> attributes = attributeService.findAll();
            attributes.forEach(attribute -> attribute.getLinks().add(AttributeLinks.self(attribute.getId(), uriInfo)));
            return Response.status(Response.Status.FOUND).entity(attributes).build();
        } catch (EntityNotFoundException e) {
            Error error = new Error(e.getMessage());
            return Response.status(Response.Status.NOT_FOUND).entity(error).build();
        } catch (NullPointerException e) {
            Error error = new Error(e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(error).build();
        }
    }

    @POST
    public Response add(Attribute attribute) {
        try {
            attribute = attributeService.add(attribute);
            attribute.getLinks().add(AttributeLinks.self(attribute.getId(), uriInfo));
            return Response.status(Response.Status.OK).entity(attribute).build();
        } catch (UnsatisfiedEntityException e) {
            Error error = new Error(e.getMessage());
            return Response.status(Response.Status.NOT_FOUND).entity(error).build();
        } catch (NullPointerException e) {
            Error error = new Error(e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(error).build();
        }
    }

    @Path("{attributeId}")
    @POST
    public Response updateById(@PathParam("attributeId") Long attributeId, Attribute attribute) {
        try {
            attribute = attributeService.updateById(attributeId, attribute);
            attribute.getLinks().add(AttributeLinks.self(attributeId, uriInfo));
            return Response.status(Response.Status.OK).entity(attribute).build();
        } catch (EntityNotFoundException e) {
            Error error = new Error(e.getMessage());
            return Response.status(Response.Status.NOT_FOUND).entity(error).build();
        } catch (NullPointerException e) {
            Error error = new Error(e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(error).build();
        }
    }

    @Path("{attributeId}")
    @POST
    public Response deleteById(@PathParam("attributeId") Long attributeId) {
        try {
            Attribute attribute = attributeService.findById(attributeId);
            attribute.getLinks().add(AttributeLinks.self(attributeId, uriInfo));
            return Response.status(Response.Status.OK).entity(attribute).build();
        } catch (EntityNotFoundException e) {
            Error error = new Error(e.getMessage());
            return Response.status(Response.Status.NOT_FOUND).entity(error).build();
        } catch (NullPointerException e) {
            Error error = new Error(e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(error).build();
        }
    }
}
