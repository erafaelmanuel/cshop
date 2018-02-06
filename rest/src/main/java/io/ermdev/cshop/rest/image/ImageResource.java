package io.ermdev.cshop.rest.image;

import io.ermdev.cshop.commons.Error;
import io.ermdev.cshop.data.service.ImageService;
import io.ermdev.cshop.exception.EntityException;
import io.ermdev.mapfierj.SimpleMapper;
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
@Path("images")
public class ImageResource {

    private ImageService imageService;
    private SimpleMapper simpleMapper;

    @Autowired
    public ImageResource(ImageService imageService, SimpleMapper simpleMapper) {
        this.imageService = imageService;
        this.simpleMapper = simpleMapper;
    }

    @GET
    @Path("{imageId}")
    public Response getById(@PathParam("imageId") long imageId, @Context UriInfo uriInfo) {
        try {
            ImageDto imageDto = simpleMapper.set(imageService.findById(imageId)).mapTo(ImageDto.class);
            ImageResourceLinks imageResourceLinks = new ImageResourceLinks(uriInfo);
            imageDto.getLinks().add(imageResourceLinks.getSelf(imageId));
            return Response.status(Response.Status.OK).entity(imageDto).build();
        } catch (EntityException e) {
            Error error = new Error(e.getMessage());
            return Response.status(Response.Status.NOT_FOUND).entity(error).build();
        }
    }

    @GET
    public Response getAll(@Context UriInfo uriInfo) {
        try {
            List<ImageDto> imageDtos = simpleMapper.set(imageService.findAll()).mapToList(ImageDto.class);
            ImageResourceLinks imageResourceLinks = new ImageResourceLinks(uriInfo);
            imageDtos.parallelStream().forEach(imageDto -> {
                imageDto.getLinks().add(imageResourceLinks.getSelf(imageDto.getId()));
            });
            return Response.status(Response.Status.OK).entity(imageDtos).build();
        } catch (EntityException e) {
            Error error = new Error(e.getMessage());
            return Response.status(Response.Status.NOT_FOUND).entity(error).build();
        }
    }
}
