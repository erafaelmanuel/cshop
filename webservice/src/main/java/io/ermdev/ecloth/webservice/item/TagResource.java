package io.ermdev.ecloth.webservice.item;

import io.ermdev.ecloth.data.exception.EntityNotFoundException;
import io.ermdev.ecloth.data.exception.UnsatisfiedEntityException;
import io.ermdev.ecloth.data.service.TagService;
import io.ermdev.ecloth.model.entity.Tag;
import io.ermdev.ecloth.model.resource.Error;
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
@Path("tag")
public class TagResource {

    @Context
    private UriInfo uriInfo;

    private TagService tagService;

    @Autowired
    public TagResource(TagService tagService) {
        this.tagService = tagService;
    }

    @Path("{tagId}")
    @GET
    public Response getById(@PathParam("tagId") Long tagId) {
        try {
            Tag tag = tagService.findById(tagId);
            tag.getLinks().add(TagLinks.self(tagId, uriInfo));
            tag.getLinks().add(TagLinks.related(tagId, uriInfo));
            return Response.status(Response.Status.OK).entity(tag).build();
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
            List<Tag> tags = tagService.findAll();
            tags.forEach(tag -> {
                tag.getLinks().add(TagLinks.self(tag.getId(), uriInfo));
                tag.getLinks().add(TagLinks.related(tag.getId(), uriInfo));
            });
            return Response.status(Response.Status.FOUND).entity(tags).build();
        } catch (EntityNotFoundException e) {
            Error error = new Error(e.getMessage());
            return Response.status(Response.Status.NOT_FOUND).entity(error).build();
        } catch (NullPointerException e) {
            Error error = new Error(e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(error).build();
        }
    }

    @POST
    public Response add(Tag tag) {
        try {
            tag = tagService.add(tag);
            tag.getLinks().add(TagLinks.self(tag.getId(), uriInfo));
            tag.getLinks().add(TagLinks.related(tag.getId(), uriInfo));
            return Response.status(Response.Status.CREATED).entity(tag).build();
        } catch (EntityNotFoundException e) {
            Error error = new Error(e.getMessage());
            return Response.status(Response.Status.NOT_FOUND).entity(error).build();
        } catch (UnsatisfiedEntityException e) {
            Error error = new Error(e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity(error).build();
        } catch (NullPointerException e) {
            Error error = new Error(e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(error).build();
        }
    }

    @Path("{tagId}")
    @PUT
    public Response updateById(@PathParam("tagId") Long tagId, Tag tag) {
        try {
            tag = tagService.updateById(tagId, tag);
            tag.getLinks().add(TagLinks.self(tagId, uriInfo));
            tag.getLinks().add(TagLinks.related(tagId, uriInfo));
            return Response.status(Response.Status.OK).entity(tag).build();
        } catch (EntityNotFoundException e) {
            Error error = new Error(e.getMessage());
            return Response.status(Response.Status.NOT_FOUND).entity(error).build();
        } catch (NullPointerException e) {
            Error error = new Error(e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(error).build();
        }
    }

    @Path("{tagId}")
    @DELETE
    public Response deleteById(@PathParam("tagId") Long tagId) {
        try {
            final Tag tag = tagService.deleteById(tagId);
            tag.getLinks().add(TagLinks.self(tagId, uriInfo));
            tag.getLinks().add(TagLinks.related(tagId, uriInfo));
            return Response.status(Response.Status.OK).entity(tag).build();
        } catch (EntityNotFoundException e) {
            Error error = new Error(e.getMessage());
            return Response.status(Response.Status.NOT_FOUND).entity(error).build();
        } catch (NullPointerException e) {
            Error error = new Error(e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(error).build();
        }
    }

    @Path("{tagId}/related")
    public RelatedTagResource relatedTagResource() {
        return new RelatedTagResource(tagService, uriInfo);
    }
}
