package io.ermdev.ecloth.webservice.item;

import io.ermdev.ecloth.data.exception.EntityNotFoundException;
import io.ermdev.ecloth.data.service.TagService;
import io.ermdev.ecloth.model.entity.Tag;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Component
@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
@Path("tag")
public class TagResource {

    private TagService tagService;

    public TagResource(TagService tagService) {
        this.tagService = tagService;
    }

    @Path("{tagId}")
    @GET
    public Response getById(@PathParam("tagId") Long tagId) {
        try {
            Tag tag = tagService.findById(tagId);
            return Response.status(Response.Status.FOUND).entity(tag).build();
        } catch (EntityNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @GET
    public Response getAll() {
        try {
            List<Tag> tags = tagService.findAll();
            return Response.status(Response.Status.FOUND).entity(tags).build();
        } catch (EntityNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @POST
    public Response add(Tag tag) {
        try {
            tag = tagService.add(tag);
            return Response.status(Response.Status.FOUND).entity(tag).build();
        } catch (Exception e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @Path("{tagId}")
    @PUT
    public Response updateById(@PathParam("tagId") Long tagId, Tag tag) {
        try {
            tag = tagService.updateById(tagId, tag);
            return Response.status(Response.Status.FOUND).entity(tag).build();
        } catch (EntityNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @Path("{tagId}")
    @DELETE
    public Response deleteById(@PathParam("tagId") Long tagId) {
        try {
            final Tag tag = tagService.deleteById(tagId);
            return Response.status(Response.Status.FOUND).entity(tag).build();
        } catch (EntityNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
}
