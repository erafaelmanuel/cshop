package io.ermdev.cshop.webservice.item;

import io.ermdev.cshop.data.exception.EntityNotFoundException;
import io.ermdev.cshop.data.exception.UnsatisfiedEntityException;
import io.ermdev.cshop.data.service.TagService;
import io.ermdev.cshop.model.entity.Tag;
import io.ermdev.cshop.model.resource.Error;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;

@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
@Path("related")
public class RelatedTagResource {

    private UriInfo uriInfo;
    private TagService tagService;

    public RelatedTagResource(TagService tagService, UriInfo uriInfo) {
        this.tagService = tagService;
        this.uriInfo = uriInfo;
    }

    @GET
    public Response getAll(@PathParam("tagId") Long tagId) {
        try {
            List<Tag> relatedTags = tagService.findRelatedTag(tagId);
            relatedTags.forEach(relatedTag -> {
                relatedTag.getLinks().add(TagLinks.self(relatedTag.getId(), uriInfo));
                relatedTag.getLinks().add(TagLinks.related(relatedTag.getId(), uriInfo));
                relatedTag.getLinks().add(TagLinks.removeRelated(tagId, relatedTag.getId(), uriInfo));
            });
            return Response.status(Response.Status.FOUND).entity(relatedTags).build();
        } catch (EntityNotFoundException e) {
            Error error = new Error(e.getMessage());
            return Response.status(Response.Status.NOT_FOUND).entity(error).build();
        } catch (NullPointerException e) {
            Error error = new Error(e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(error).build();
        }
    }

    @Path("{relatedTagId}")
    @POST
    public Response add(@PathParam("tagId") Long tagId, @PathParam("relatedTagId") Long relatedTagId) {
        try {
            Tag tag = tagService.addRelatedTag(tagId, relatedTagId);
            tag.getLinks().add(TagLinks.self(tagId, uriInfo));
            tag.getLinks().add(TagLinks.related(tagId, uriInfo));

            return Response.status(Response.Status.FOUND).entity(tag).build();
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

    @Path("{relatedTagId}")
    @DELETE
    public Response delete(@PathParam("tagId") Long tagId, @PathParam("relatedTagId") Long relatedTagId) {
        try {
            Tag tag = tagService.deleteRelatedTag(tagId, relatedTagId);
            tag.getLinks().add(TagLinks.self(tagId, uriInfo));
            tag.getLinks().add(TagLinks.related(tagId, uriInfo));
            return Response.status(Response.Status.FOUND).entity(tag).build();
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
}
