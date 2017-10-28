package io.ermdev.ecloth.webservice.user;

import io.ermdev.ecloth.data.exception.EntityNotFoundException;
import io.ermdev.ecloth.data.service.UserService;
import io.ermdev.ecloth.model.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
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
@Path("user")
public class UserResource {

    @Context
    private UriInfo uriInfo;

    private UserService userService;

    @Autowired
    public UserResource(UserService userService) {
        this.userService = userService;
    }

    @Path("{userId}")
    @GET
    public Response getById(@PathParam("userId") long userId) {
        try {
            User user = userService.findById(userId);
            user.getLinks().add(UserLinks.self(userId, uriInfo));
            return Response.status(Response.Status.FOUND).entity(user).build();
        } catch (EntityNotFoundException e) {
            e.printStackTrace();
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @GET
    public Response getAll() {
        try {
            List<User> userList = userService.findAll();
            userList.forEach(user -> user.getLinks().add(UserLinks.self(user.getId(), uriInfo)));
            return Response.status(Response.Status.FOUND).entity(userList).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.NOT_FOUND).entity(new ArrayList<User>()).build();
        }
    }

    @POST
    public Response add(User user) {
        try {
            user = userService.add(user);
            user.getLinks().add(UserLinks.self(user.getId(), uriInfo));
            return Response.status(Response.Status.OK).entity(user).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @Path("{userId}")
    @PUT
    public Response updateById(@PathParam("userId") Long userId, User user) {
        try {
            user=userService.updateById(userId, user);
            user.getLinks().add(UserLinks.self(user.getId(), uriInfo));
            return Response.status(Response.Status.OK).entity(user).build();
        } catch (EntityNotFoundException e) {
            e.printStackTrace();
            return Response.status(Response.Status.NOT_FOUND).build();
        }catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @Path("{userId}")
    @DELETE
    public Response deleteById(@PathParam("userId") final Long userId) {
        try {
            User user = userService.deleteById(userId);
            user.getLinks().add(UserLinks.self(user.getId(), uriInfo));
            return Response.status(Response.Status.OK).entity(user).build();
        } catch (EntityNotFoundException e) {
            e.printStackTrace();
            return Response.status(Response.Status.NOT_FOUND).build();
        }catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }
}
