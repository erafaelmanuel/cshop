package io.ermdev.ecloth.webservice.item;

import io.ermdev.ecloth.data.exception.EntityNotFoundException;
import io.ermdev.ecloth.data.service.AttributeService;
import io.ermdev.ecloth.model.entity.Attribute;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@Component
@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
@Path("attribute")
public class AttributeResource {

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
            return Response.status(Response.Status.FOUND).entity(attribute).build();
        } catch (EntityNotFoundException e) {
            e.printStackTrace();
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @GET
    public Response getAll() {
        try {
            List<Attribute> attributes = attributeService.findAll();
            return Response.status(Response.Status.FOUND).entity(attributes).build();
        } catch (EntityNotFoundException e) {
            e.printStackTrace();
            return Response.status(Response.Status.NOT_FOUND).entity(new ArrayList<Attribute>()).build();
        }
    }

    @POST
    public Response add(Attribute attribute) {
        try {
            attribute = attributeService.add(attribute);
            return Response.status(Response.Status.OK).entity(attribute).build();
        } catch (NullPointerException e) {
            e.printStackTrace();
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @Path("{attributeId}")
    @POST
    public Response updateById(@PathParam("attributeId") Long attributeId, Attribute attribute) {
        try {
            attribute = attributeService.updateById(attributeId, attribute);
            return Response.status(Response.Status.OK).entity(attribute).build();
        } catch (EntityNotFoundException e) {
            e.printStackTrace();
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @Path("{attributeId}")
    @POST
    public Response deleteById(@PathParam("attributeId") Long attributeId) {
        try {
            Attribute attribute = attributeService.findById(attributeId);
            return Response.status(Response.Status.OK).entity(attribute).build();
        } catch (EntityNotFoundException e) {
            e.printStackTrace();
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
}
