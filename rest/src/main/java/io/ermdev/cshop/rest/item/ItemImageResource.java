package io.ermdev.cshop.rest.item;

import io.ermdev.cshop.commons.Error;
import io.ermdev.cshop.data.entity.Image;
import io.ermdev.cshop.data.service.ItemImageService;
import io.ermdev.cshop.exception.EntityException;
import io.ermdev.cshop.rest.image.ImageDto;
import io.ermdev.cshop.rest.image.ImageResourceLinks;
import mapfierj.SimpleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;

@Component
@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
@Path("images")
public class ItemImageResource {

    private ItemImageService itemImageService;
    private SimpleMapper simpleMapper;
    private UriInfo uriInfo;

    @Autowired
    public ItemImageResource(ItemImageService itemImageService, SimpleMapper simpleMapper) {
        this.itemImageService = itemImageService;
        this.simpleMapper = simpleMapper;
    }

    @GET
    @Path("{imageId}")
    public Response getById(@PathParam("itemId") Long itemId, @PathParam("imageId") Long imageId) {
        try {
            Image image = itemImageService.findImageByItemIdAndImageId(itemId, imageId);
            ImageDto imageDto = simpleMapper.set(image).mapTo(ImageDto.class);
            ImageResourceLinks imageResourceLinks = new ImageResourceLinks(uriInfo);
            imageDto.getLinks().add(imageResourceLinks.getSelf(imageId));
            return Response.status(Response.Status.FOUND).entity(imageDto).build();
        } catch (EntityException e) {
            Error error = new Error(e.getMessage());
            return Response.status(Response.Status.NOT_FOUND).entity(error).build();
        }
    }

    @GET
    public Response getAll(@PathParam("itemId") Long itemId) {
        try {
            List<Image> images = itemImageService.findImagesByItemId(itemId);
            List<ImageDto> imageDtos = simpleMapper.set(images).mapToList(ImageDto.class);
            ImageResourceLinks imageResourceLinks = new ImageResourceLinks(uriInfo);
            imageDtos.parallelStream().forEach(imageDto -> {
                imageDto.getLinks().add(imageResourceLinks.getSelf(imageDto.getId()));
            });
            return Response.status(Response.Status.FOUND).entity(imageDtos).build();
        } catch (EntityException e) {
            Error error = new Error(e.getMessage());
            return Response.status(Response.Status.NOT_FOUND).entity(error).build();
        }
    }

    @POST
    @Path("{imageId}")
    public Response addImage(@PathParam("itemId") Long itemId, @PathParam("imageId") Long imageId) {
        try {
            Image image = itemImageService.addImageToItem(itemId, imageId);
            ImageDto imageDto = simpleMapper.set(image).mapTo(ImageDto.class);
            ImageResourceLinks imageResourceLinks = new ImageResourceLinks(uriInfo);
            imageDto.getLinks().add(imageResourceLinks.getSelf(imageId));
            return Response.status(Response.Status.FOUND).entity(imageDto).build();
        } catch (EntityException e) {
            Error error = new Error(e.getMessage());
            return Response.status(Response.Status.NOT_FOUND).entity(error).build();
        }
    }

    @DELETE
    @Path("{imageId}")
    public Response deleteImage(@PathParam("itemId") Long itemId, @PathParam("imageId") Long imageId) {
        try {
            Image image = itemImageService.deleteImageFromItem(itemId, imageId);
            ImageDto imageDto = simpleMapper.set(image).mapTo(ImageDto.class);
            ImageResourceLinks imageResourceLinks = new ImageResourceLinks(uriInfo);
            imageDto.getLinks().add(imageResourceLinks.getSelf(imageId));
            return Response.status(Response.Status.FOUND).entity(imageDto).build();
        } catch (EntityException e) {
            Error error = new Error(e.getMessage());
            return Response.status(Response.Status.NOT_FOUND).entity(error).build();
        }
    }


    public void setUriInfo(UriInfo uriInfo) {
        this.uriInfo = uriInfo;
    }
}
