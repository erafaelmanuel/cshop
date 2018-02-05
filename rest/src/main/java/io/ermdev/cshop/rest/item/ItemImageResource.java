package io.ermdev.cshop.rest.item;

import io.ermdev.cshop.commons.Error;
import io.ermdev.cshop.data.service.ImageService;
import io.ermdev.cshop.exception.EntityException;
import io.ermdev.cshop.rest.image.ImageDto;
import io.ermdev.mapfierj.SimpleMapper;
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

    private ImageService imageService;
    private SimpleMapper simpleMapper;
    private UriInfo uriInfo;

    @Autowired
    public ItemImageResource(ImageService imageService, SimpleMapper simpleMapper) {
        this.imageService = imageService;
        this.simpleMapper = simpleMapper;
    }

    @GET
    public Response getAll(@PathParam("itemId") Long itemId) {
        try {
            List<ImageDto> imageDtos = simpleMapper.set(imageService.findByItemId(itemId)).mapToList(ImageDto.class);
            return Response.status(Response.Status.FOUND).entity(imageDtos).build();
        } catch (EntityException e) {
            Error error = new Error(e.getMessage());
            return Response.status(Response.Status.NOT_FOUND).entity(error).build();
        }
    }

    public void setUriInfo(UriInfo uriInfo) {
        this.uriInfo = uriInfo;
    }
}
