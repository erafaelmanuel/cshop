package io.ermdev.cshop.rest.tag;

import io.ermdev.cshop.commons.Error;
import io.ermdev.cshop.data.entity.Tag;
import io.ermdev.cshop.data.service.TagService;
import io.ermdev.cshop.exception.EntityException;
import mapfierj.SimpleMapper;
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
@Path("tags")
public class TagResource {

    private TagService tagService;
    private SimpleMapper simpleMapper;

    @Autowired
    public TagResource(TagService tagService, SimpleMapper simpleMapper) {
        this.tagService = tagService;
        this.simpleMapper = simpleMapper;
    }

    @GET
    @Path("{tagId}")
    public Response getById(@PathParam("tagId") Long tagId, @Context UriInfo uriInfo) {
        try {
            TagDto tagDto = simpleMapper.set(tagService.findById(tagId)).mapTo(TagDto.class);
            TagResourceLinks tagResourceLinks = new TagResourceLinks(uriInfo);
            tagDto.getLinks().add(tagResourceLinks.getSelf(tagId));
            return Response.status(Response.Status.FOUND).entity(tagDto).build();
        } catch (EntityException e) {
            Error error = new Error(e.getMessage());
            return Response.status(Response.Status.NOT_FOUND).entity(error).build();
        }
    }

    @GET
    public Response getAll(@Context UriInfo uriInfo) {
        try {
            List<TagDto> tagDtos = simpleMapper.set(tagService.findAll()).mapToList(TagDto.class);
            tagDtos.parallelStream().forEach(tagDto -> {
                TagResourceLinks tagResourceLinks = new TagResourceLinks(uriInfo);
                tagDto.getLinks().add(tagResourceLinks.getSelf(tagDto.getId()));
            });
            return Response.status(Response.Status.FOUND).entity(tagDtos).build();
        } catch (EntityException e) {
            Error error = new Error(e.getMessage());
            return Response.status(Response.Status.NOT_FOUND).entity(error).build();
        }
    }

    @POST
    public Response add(TagDto tagDto, @Context UriInfo uriInfo) {
        try {
            Tag tag = tagService.save(simpleMapper.set(tagDto).mapTo(Tag.class));
            TagResourceLinks tagResourceLinks = new TagResourceLinks(uriInfo);
            tagDto.setId(tag.getId());
            tagDto.getLinks().add(tagResourceLinks.getSelf(tagDto.getId()));
            return Response.status(Response.Status.OK).entity(tagDto).build();
        } catch (EntityException e) {
            Error error = new Error(e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity(error).build();
        }
    }

    @PUT
    @Path("{tagId}")
    public Response update(@PathParam("tagId") Long tagId, TagDto tagDto, @Context UriInfo uriInfo) {
        try {
            tagDto.setId(tagId);
            Tag tag = tagService.save(simpleMapper.set(tagDto).mapTo(Tag.class));
            TagResourceLinks tagResourceLinks = new TagResourceLinks(uriInfo);
            tagDto = simpleMapper.set(tag).mapTo(TagDto.class);
            tagDto.getLinks().add(tagResourceLinks.getSelf(tagDto.getId()));
            return Response.status(Response.Status.OK).entity(tagDto).build();
        } catch (EntityException e) {
            Error error = new Error(e.getMessage());
            return Response.status(Response.Status.NOT_FOUND).entity(error).build();
        }
    }

    @DELETE
    @Path("{tagId}")
    public Response delete(@PathParam("tagId") final Long tagId, @Context UriInfo uriInfo) {
        try {
            TagDto tagDto = simpleMapper.set(tagService.delete(tagId)).mapTo(TagDto.class);
            TagResourceLinks tagResourceLinks = new TagResourceLinks(uriInfo);
            tagDto.getLinks().add(tagResourceLinks.getSelf(tagDto.getId()));
            return Response.status(Response.Status.OK).entity(tagDto).build();
        } catch (Exception e) {
            Error error = new Error(e.getMessage());
            return Response.status(Response.Status.NOT_FOUND).entity(error).build();
        }
    }
}
