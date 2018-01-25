package io.ermdev.cshop.rest.user;

import io.ermdev.cshop.data.entity.User;
import io.ermdev.cshop.exception.EntityException;
import io.ermdev.cshop.commons.Error;
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

    private UserService userService;
    private UserRoleResource userRoleResource;
    private SimpleMapper mapper;

    @Autowired
    public UserResource(UserService userService, UserRoleResource userRoleResource, SimpleMapper mapper) {
        this.userService = userService;
        this.userRoleResource = userRoleResource;
        this.mapper = mapper;
    }

    @GET
    @Path("{userId}")
    public Response getById(@PathParam("userId") long userId, @Context UriInfo uriInfo) {
        try {
            UserDto userDto = mapper.set(userService.findById(userId)).mapTo(UserDto.class);
            UserResourceLinks userResourceLinks = new UserResourceLinks(uriInfo);
            userDto.getLinks().add(userResourceLinks.getSelf(userId));
            userDto.getLinks().add(userResourceLinks.getRoles(userId));
            return Response.status(Response.Status.FOUND).entity(userDto).build();
        } catch (EntityException e) {
            Error error = new Error(e.getMessage());
            return Response.status(Response.Status.NOT_FOUND).entity(error).build();
        }
    }

    @GET
    @Path("all")
    public Response getAll(@Context UriInfo uriInfo) {
        try {
            List<UserDto> userDtos = mapper.set(userService.findAll()).mapToList(UserDto.class);
            userDtos.parallelStream().forEach(userDto -> {
                UserResourceLinks userResourceLinks = new UserResourceLinks(uriInfo);
                userDto.getLinks().add(userResourceLinks.getSelf(userDto.getId()));
                userDto.getLinks().add(userResourceLinks.getRoles(userDto.getId()));
            });
            return Response.status(Response.Status.FOUND).entity(userDtos).build();
        } catch (Exception e) {
            Error error = new Error(e.getMessage());
            return Response.status(Response.Status.NOT_FOUND).entity(error).build();
        }
    }

    @POST
    public Response add(UserDto userDto, @Context UriInfo uriInfo) {
        try {
            User user = userService.save(mapper.set(userDto).mapTo(User.class));
            UserResourceLinks userResourceLinks = new UserResourceLinks(uriInfo);
            userDto.setId(user.getId());
            userDto.getLinks().add(userResourceLinks.getSelf(userDto.getId()));
            userDto.getLinks().add(userResourceLinks.getRoles(userDto.getId()));
            return Response.status(Response.Status.OK).entity(userDto).build();
        } catch (EntityException e) {
            Error error = new Error(e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity(error).build();
        }
    }

    @PUT
    @Path("{userId}")
    public Response update(@PathParam("userId") Long userId, UserDto userDto, @Context UriInfo uriInfo) {
        try {
            userDto.setId(userId);
            User user = userService.save(mapper.set(userDto).mapTo(User.class));
            UserResourceLinks userResourceLinks = new UserResourceLinks(uriInfo);
            userDto = mapper.set(user).mapTo(UserDto.class);
            userDto.getLinks().add(userResourceLinks.getSelf(userDto.getId()));
            userDto.getLinks().add(userResourceLinks.getRoles(userDto.getId()));
            return Response.status(Response.Status.OK).entity(userDto).build();
        } catch (EntityException e) {
            Error error = new Error(e.getMessage());
            return Response.status(Response.Status.NOT_FOUND).entity(error).build();
        }
    }

    @DELETE
    @Path("{userId}")
    public Response delete(@PathParam("userId") final Long userId, @Context UriInfo uriInfo) {
        try {
            UserDto userDto = mapper.set(userService.delete(userId)).mapTo(UserDto.class);
            UserResourceLinks userResourceLinks = new UserResourceLinks(uriInfo);
            userDto.getLinks().add(userResourceLinks.getSelf(userDto.getId()));
            userDto.getLinks().add(userResourceLinks.getRoles(userDto.getId()));
            return Response.status(Response.Status.OK).entity(userDto).build();
        } catch (Exception e) {
            Error error = new Error(e.getMessage());
            return Response.status(Response.Status.NOT_FOUND).entity(error).build();
        }
    }

    @Path("{userId}/role")
    public UserRoleResource usersRoleResource(@QueryParam("roleIds") List<Long> roleIds, @Context UriInfo uriInfo) {
        userRoleResource.setRoleIds(roleIds);
        userRoleResource.setUriInfo(uriInfo);
        return userRoleResource;
    }
}
