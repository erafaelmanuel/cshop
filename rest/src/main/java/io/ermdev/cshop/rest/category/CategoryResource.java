package io.ermdev.cshop.rest.category;

import io.ermdev.cshop.commons.Error;
import io.ermdev.cshop.data.entity.Category;
import io.ermdev.cshop.data.service.CategoryService;
import io.ermdev.cshop.exception.EntityException;
import mapfierj.xyz.Mapper;
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
@Path("categories")
public class CategoryResource {

    private CategoryService categoryService;
    private Mapper mapper;

    @Autowired
    public CategoryResource(CategoryService categoryService, Mapper mapper) {
        this.categoryService = categoryService;
        this.mapper = mapper;
    }

    @GET
    @Path("{categoryId}")
    public Response getById(@PathParam("categoryId") long categoryId, @Context UriInfo uriInfo) {
        try {
            CategoryDto categoryDto = mapper.set(categoryService.findById(categoryId)).mapTo(CategoryDto.class);
            CategoryResourceLinks categoryResourceLinks = new CategoryResourceLinks(uriInfo);
            categoryDto.getLinks().add(categoryResourceLinks.getSelf(categoryId));
            return Response.status(Response.Status.OK).entity(categoryDto).build();
        } catch (EntityException e) {
            Error error = new Error(e.getMessage());
            return Response.status(Response.Status.NOT_FOUND).entity(error).build();
        }
    }

    @GET
    public Response getAll(@Context UriInfo uriInfo) {
        try {
            List<CategoryDto> categoryDtos = mapper.set(categoryService.findAll())
                    .transactional()
                    .mapToList(CategoryDto.class);
            CategoryResourceLinks categoryResourceLinks = new CategoryResourceLinks(uriInfo);
            categoryDtos.parallelStream().forEach(categoryDto -> {
                categoryDto.getLinks().add(categoryResourceLinks.getSelf(categoryDto.getId()));
            });
            return Response.status(Response.Status.OK).entity(categoryDtos).build();
        } catch (EntityException e) {
            Error error = new Error(e.getMessage());
            return Response.status(Response.Status.NOT_FOUND).entity(error).build();
        }
    }

    @POST
    public Response add(Category category, @Context UriInfo uriInfo) {
        try {
            CategoryDto categoryDto = mapper.set(categoryService.save(category)).mapTo(CategoryDto.class);
            CategoryResourceLinks categoryResourceLinks = new CategoryResourceLinks(uriInfo);
            categoryDto.getLinks().add(categoryResourceLinks.getSelf(categoryDto.getId()));
            return Response.status(Response.Status.CREATED).entity(categoryDto).build();
        } catch (EntityException e) {
            Error error = new Error(e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity(error).build();
        }
    }

    @PUT
    @Path("{categoryId}")
    public Response update(@PathParam("categoryId") Long categoryId, Category category, @Context UriInfo uriInfo) {
        try {
            category.setId(categoryId);
            CategoryDto categoryDto = mapper.set(categoryService.save(category)).mapTo(CategoryDto.class);
            CategoryResourceLinks categoryResourceLinks = new CategoryResourceLinks(uriInfo);
            categoryDto.getLinks().add(categoryResourceLinks.getSelf(categoryDto.getId()));
            return Response.status(Response.Status.OK).entity(categoryDto).build();
        } catch (EntityException e) {
            Error error = new Error(e.getMessage());
            return Response.status(Response.Status.NOT_FOUND).entity(error).build();
        }
    }

    @DELETE
    @Path("{categoryId}")
    public Response delete(@PathParam("categoryId") Long categoryId, @Context UriInfo uriInfo) {
        try {
            CategoryDto categoryDto = mapper.set(categoryService.delete(categoryId)).mapTo(CategoryDto.class);
            CategoryResourceLinks categoryResourceLinks = new CategoryResourceLinks(uriInfo);
            categoryDto.getLinks().add(categoryResourceLinks.getSelf(categoryDto.getId()));
            return Response.status(Response.Status.OK).entity(categoryDto).build();
        } catch (EntityException e) {
            Error error = new Error(e.getMessage());
            return Response.status(Response.Status.NOT_FOUND).entity(error).build();
        }
    }
}
