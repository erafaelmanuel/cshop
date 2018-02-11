package io.ermdev.cshop.rest.tag;

import io.ermdev.cshop.commons.Error;
import io.ermdev.cshop.data.service.TagService;
import io.ermdev.cshop.exception.EntityException;
import io.ermdev.cshop.rest.token.TokenDto;
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
@Path("tokens")
public class TagResource {

    private TagService tagService;
    private SimpleMapper simpleMapper;

    @Autowired
    public TagResource(TagService tagService, SimpleMapper simpleMapper) {
        this.tagService = tagService;
        this.simpleMapper = simpleMapper;
    }

    @GET
    @Path("{tokenId}")
    public Response getById(@PathParam("tokenId") Long tokenId, @Context UriInfo uriInfo) {
        try {
            TagDto tagDto = simpleMapper.set(tagService.findById(tokenId)).mapTo(tagDto.class);
            TagResourceLinks tagResourceLinks = new TagResourceLinks(uriInfo);
            tagDto.getLinks().add(tagResourceLinks.getSelf(tokenId));
            return Response.status(Response.Status.FOUND).entity(tagDto).build();
        } catch (EntityException e) {
            Error error = new Error(e.getMessage());
            return Response.status(Response.Status.NOT_FOUND).entity(error).build();
        }
    }

    @GET
    public Response getAll(@Context UriInfo uriInfo) {
        try {
            List<TagDto> tagDtos = simpleMapper.set(tokenService.findAll()).mapToList(TokenDto.class);
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
}
