package io.ermdev.cshop.webservice.user;

import io.ermdev.cshop.data.entity.User;
import io.ermdev.cshop.data.exception.EntityException;
import io.ermdev.cshop.data.model.Error;
import io.ermdev.cshop.data.service.UserService;
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
@Path("user")
public class UserResource {

    @Context
    private UriInfo uriInfo;
    private UserService userService;
    private SimpleMapper mapper;

    @Autowired
    public UserResource(UserService userService, SimpleMapper mapper) {
        this.userService = userService;
        this.mapper = mapper;
    }

    @GET
    @Path("{userId}")
    public Response getById(@PathParam("userId") long userId) {
        try {
            User user = userService.findById(userId);
            user.getLinks().add(UserLinks.self(userId, uriInfo));
            return Response.status(Response.Status.FOUND).entity(user).build();
        } catch (EntityException e) {
            Error error = new Error(e.getMessage());
            return Response.status(Response.Status.NOT_FOUND).entity(error).build();
        }
    }

    @GET
    @Path("all")
    public Response getAll() {
        try {
            List<User> userList = userService.findAll();
            userList.forEach(user -> user.getLinks().add(UserLinks.self(user.getId(), uriInfo)));
            return Response.status(Response.Status.FOUND).entity(userList).build();
        } catch (Exception e) {
            Error error = new Error(e.getMessage());
            return Response.status(Response.Status.NOT_FOUND).entity(error).build();
        }
    }

    @POST
    public Response add(UserDto userDto) {
        try {
            User user = mapper.set(userDto).mapTo(User.class);
            user = userService.save(user);
            user.getLinks().add(UserLinks.self(user.getId(), uriInfo));
            return Response.status(Response.Status.OK).entity(user).build();
        } catch (EntityException e) {
            Error error = new Error(e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity(error).build();
        }
    }

    @PUT
    @Path("{userId}")
    public Response update(@PathParam("userId") Long userId, UserDto userDto) {
        try {
            User user = mapper.set(userDto).mapTo(User.class);
            user = userService.save(user);
            user.getLinks().add(UserLinks.self(user.getId(), uriInfo));
            return Response.status(Response.Status.OK).entity(user).build();
        } catch (EntityException e) {
            Error error = new Error(e.getMessage());
            return Response.status(Response.Status.NOT_FOUND).entity(error).build();
        }
    }

    @DELETE
    @Path("{userId}")
    public Response delete(@PathParam("userId") final Long userId) {
        try {
            User user = userService.delete(userId);
            user.getLinks().add(UserLinks.self(user.getId(), uriInfo));
            return Response.status(Response.Status.OK).entity(user).build();
        } catch (Exception e) {
            Error error = new Error(e.getMessage());
            return Response.status(Response.Status.NOT_FOUND).entity(error).build();
        }
    }
}
