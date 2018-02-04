package io.ermdev.cshop.rest.item;

import io.ermdev.cshop.commons.Error;
import io.ermdev.cshop.data.entity.Item;
import io.ermdev.cshop.data.service.ItemService;
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
@Path("items")
public class ItemResource {

    private ItemService itemService;
    private SimpleMapper simpleMapper;

    @Autowired
    public ItemResource(ItemService itemService, SimpleMapper simpleMapper) {
        this.itemService = itemService;
        this.simpleMapper = simpleMapper;
    }

    @GET
    @Path("{itemId}")
    public Response getById(@PathParam("itemId") long itemId, @Context UriInfo uriInfo) {
        try {
            ItemDto itemDto = simpleMapper.set(itemService.findById(itemId)).mapTo(ItemDto.class);
            ItemResourceLinks itemResourceLinks = new ItemResourceLinks(uriInfo);
            itemDto.getLinks().add(itemResourceLinks.getSelf(itemId));
            return Response.status(Response.Status.OK).entity(itemDto).build();
        } catch (EntityException e) {
            Error error = new Error(e.getMessage());
            return Response.status(Response.Status.NOT_FOUND).entity(error).build();
        }
    }

    @GET
    public Response getAll(@Context UriInfo uriInfo) {
        try {
            List<ItemDto> itemDtos = simpleMapper.set(itemService.findAll()).mapToList(ItemDto.class);
            ItemResourceLinks itemResourceLinks = new ItemResourceLinks(uriInfo);
            itemDtos.parallelStream().forEach(itemDto -> {
                itemDto.getLinks().add(itemResourceLinks.getSelf(itemDto.getId()));
            });
            return Response.status(Response.Status.OK).entity(itemDtos).build();
        } catch (EntityException e) {
            Error error = new Error(e.getMessage());
            return Response.status(Response.Status.NOT_FOUND).entity(error).build();
        }
    }

    @POST
    public Response add(Item item, @Context UriInfo uriInfo) {
        try {
            ItemDto itemDto = simpleMapper.set(itemService.save(item)).mapTo(ItemDto.class);
            ItemResourceLinks itemResourceLinks = new ItemResourceLinks(uriInfo);
            itemDto.getLinks().add(itemResourceLinks.getSelf(item.getId()));
            return Response.status(Response.Status.CREATED).entity(itemDto).build();
        } catch (EntityException e) {
            Error error = new Error(e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity(error).build();
        }
    }

    @PUT
    @Path("{itemId}")
    public Response update(@PathParam("itemId") Long itemId, Item item, @Context UriInfo uriInfo) {
        try {
            item.setId(itemId);
            ItemDto itemDto = simpleMapper.set(itemService.save(item)).mapTo(ItemDto.class);
            ItemResourceLinks itemResourceLinks = new ItemResourceLinks(uriInfo);
            itemDto.getLinks().add(itemResourceLinks.getSelf(item.getId()));
            return Response.status(Response.Status.OK).entity(itemDto).build();
        } catch (EntityException e) {
            Error error = new Error(e.getMessage());
            return Response.status(Response.Status.NOT_FOUND).entity(error).build();
        }
    }

    @DELETE
    @Path("{itemId}")
    public Response delete(@PathParam("itemId") Long itemId, @Context UriInfo uriInfo) {
        try {
            ItemDto itemDto = simpleMapper.set(itemService.delete(itemId)).mapTo(ItemDto.class);
            ItemResourceLinks itemResourceLinks = new ItemResourceLinks(uriInfo);
            itemDto.getLinks().add(itemResourceLinks.getSelf(itemId));
            return Response.status(Response.Status.OK).entity(itemDto).build();
        } catch (EntityException e) {
            Error error = new Error(e.getMessage());
            return Response.status(Response.Status.NOT_FOUND).entity(error).build();
        }
    }
}
