package io.ermdev.cshop.webservice.item;

import io.ermdev.cshop.data.exception.EntityNotFoundException;
import io.ermdev.cshop.data.exception.UnsatisfiedEntityException;
import io.ermdev.cshop.data.service.CategoryService;
import io.ermdev.cshop.model.entity.Category;
import io.ermdev.cshop.model.resource.Error;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.ArrayList;
import java.util.List;

@Component
@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
@Path("category")
public class CategoryResource {

    @Context
    private UriInfo uriInfo;

    @QueryParam("parentId")
    private Long parentId;

    private CategoryService categoryService;

    public CategoryResource(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Path("{categoryId}")
    @GET
    public Response getById(@PathParam("categoryId") Long categoryId) {
        try {
            Category category = categoryService.findById(categoryId);
            category.getLinks().add(CategoryLinks.self(categoryId, uriInfo));
            return Response.status(Response.Status.OK).entity(category).build();
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
            final List<Category> categories = new ArrayList<>();

            if(parentId == null) {
                categories.addAll(categoryService.findAll());
            } else {
                categories.addAll(categoryService.findByParent(parentId));
            }
            categories.forEach(category -> {
                category.getLinks().add(CategoryLinks.self(category.getId(), uriInfo));
            });
            return Response.status(Response.Status.OK).entity(categories).build();
        } catch (EntityNotFoundException e) {
            Error error = new Error(e.getMessage());
            return Response.status(Response.Status.NOT_FOUND).entity(error).build();
        } catch (NullPointerException e) {
            Error error = new Error(e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(error).build();
        }
    }

    @POST
    public Response add(Category category) {
        try {
            category = categoryService.add(category);
            category.getLinks().add(CategoryLinks.self(category.getId(), uriInfo));
            return Response.status(Response.Status.CREATED).entity(category).build();
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

    @Path("{categoryId}")
    @PUT
    public Response updateById(@PathParam("categoryId") Long categoryId, Category category) {
        try {
            category = categoryService.updateById(categoryId, category);
            category.getLinks().add(CategoryLinks.self(categoryId, uriInfo));
            return Response.status(Response.Status.OK).entity(category).build();
        } catch (EntityNotFoundException e) {
            Error error = new Error(e.getMessage());
            return Response.status(Response.Status.NOT_FOUND).entity(error).build();
        } catch (NullPointerException e) {
            Error error = new Error(e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(error).build();
        }
    }

    @Path("{categoryId}")
    @DELETE
    public Response deleteById(@PathParam("categoryId") Long categoryId) {
        try {
            final Category category = categoryService.deleteById(categoryId);
            category.getLinks().add(CategoryLinks.self(categoryId, uriInfo));
            return Response.status(Response.Status.OK).entity(category).build();
        } catch (EntityNotFoundException e) {
            Error error = new Error(e.getMessage());
            return Response.status(Response.Status.NOT_FOUND).entity(error).build();
        } catch (NullPointerException e) {
            Error error = new Error(e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(error).build();
        }
    }
}
