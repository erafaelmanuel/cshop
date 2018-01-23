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
            UserDto userDto = mapper.set(userService.findById(userId)).mapTo(UserDto.class);
            userDto.getLinks().add(UserLinks.self(userId, uriInfo));
            return Response.status(Response.Status.FOUND).entity(userDto).build();
        } catch (EntityException e) {
            Error error = new Error(e.getMessage());
            return Response.status(Response.Status.NOT_FOUND).entity(error).build();
        }
    }

    @GET
    @Path("all")
    public Response getAll() {
        try {
            List<UserDto> userDtos = mapper.set(userService.findAll()).mapToList(UserDto.class);
            userDtos.stream().forEach(userDto -> userDto.getLinks().add(UserLinks.self(userDto.getId(), uriInfo)));
            return Response.status(Response.Status.FOUND).entity(userDtos).build();
        } catch (Exception e) {
            Error error = new Error(e.getMessage());
            return Response.status(Response.Status.NOT_FOUND).entity(error).build();
        }
    }

    @POST
    public Response add(UserDto userDto) {
        try {
            final User user = userService.save(mapper.set(userDto).mapTo(User.class));
            userDto.setId(user.getId());
            userDto.getLinks().add(UserLinks.self(user.getId(), uriInfo));
            return Response.status(Response.Status.OK).entity(userDto).build();
        } catch (EntityException e) {
            Error error = new Error(e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity(error).build();
        }
    }

    @PUT
    @Path("{userId}")
    public Response update(@PathParam("userId") Long userId, UserDto userDto) {
        try {
            final User user = userService.save(mapper.set(userDto).mapTo(User.class));
            userDto = mapper.set(user).mapTo(UserDto.class);
            userDto.getLinks().add(UserLinks.self(user.getId(), uriInfo));
            return Response.status(Response.Status.OK).entity(userDto).build();
        } catch (EntityException e) {
            Error error = new Error(e.getMessage());
            return Response.status(Response.Status.NOT_FOUND).entity(error).build();
        }
    }

    @DELETE
    @Path("{userId}")
    public Response delete(@PathParam("userId") final Long userId) {
        try {
            UserDto userDto = mapper.set(userService.delete(userId)).mapTo(UserDto.class);
            userDto.getLinks().add(UserLinks.self(userDto.getId(), uriInfo));
            return Response.status(Response.Status.OK).entity(userDto).build();
        } catch (Exception e) {
            Error error = new Error(e.getMessage());
            return Response.status(Response.Status.NOT_FOUND).entity(error).build();
        }
    }
}
