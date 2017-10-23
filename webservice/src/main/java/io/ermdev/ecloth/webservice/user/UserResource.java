package io.ermdev.ecloth.webservice.user;

import io.ermdev.ecloth.data.exception.EntityNotFoundException;
import io.ermdev.ecloth.data.service.UserService;
import io.ermdev.ecloth.model.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@Component
@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
@Path("user")
public class UserResource {

    private UserService userService;

    @Autowired
    public UserResource(UserService userService) {
        this.userService = userService;
    }

    @Path("{userId}")
    @GET
    public Response getById(@PathParam("userId") long userId) {
        try {
            User user = userService.getById(userId);
            return Response.status(Response.Status.FOUND).entity(user).build();
        } catch (EntityNotFoundException e) {
            e.printStackTrace();
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @GET
    public Response getAll() {
        List<User> userList = new ArrayList<>();
        try {
            userList.addAll(userService.getAll());
            return Response.status(Response.Status.FOUND).entity(userList).build();
        } catch (EntityNotFoundException e) {
            e.printStackTrace();
            return Response.status(Response.Status.NOT_FOUND).entity(userList).build();
        }
    }

    @POST
    public Response add(User user) {
        try {
            user = userService.add(user);
            return Response.status(Response.Status.OK).entity(user).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @Path("{userId}")
    @PUT
    public Response updateById(@PathParam("userId") final long userId, final User nUser) {
        try {
            User user = userService.updateById(userId, nUser);
            if(user != null)
                return Response.status(Response.Status.OK).entity(user).build();
            else
                throw new Exception();
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
    public Response deleteById(@PathParam("userId") final long userId) {
        try {
            User user = userService.deleteById(userId);
            if(user != null)
                return Response.status(Response.Status.OK).entity(user).build();
            else
                throw new Exception();
        } catch (EntityNotFoundException e) {
            e.printStackTrace();
            return Response.status(Response.Status.NOT_FOUND).build();
        }catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }
}
