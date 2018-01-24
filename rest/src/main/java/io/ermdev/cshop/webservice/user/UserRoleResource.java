package io.ermdev.cshop.webservice.user;

import io.ermdev.cshop.data.entity.Role;
import io.ermdev.cshop.data.exception.EntityException;
import io.ermdev.cshop.data.model.Error;
import io.ermdev.cshop.data.service.UserRoleService;
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
@Path("role")
public class UserRoleResource {

    private UserRoleService userRoleService;

    public UserRoleResource(UserRoleService userRoleService) {
        this.userRoleService = userRoleService;
    }

    @GET
    @Path("{roleId}")
    public Response getById(@PathParam("userId") Long userId, @PathParam("roleId") Long roleId, @Context UriInfo info) {
        try {
            Role role = userRoleService.findUserRoleById(userId, roleId);
            return Response.status(Response.Status.FOUND).entity(role).build();
        } catch (EntityException e) {
            Error error = new Error(e.getMessage());
            return Response.status(Response.Status.NOT_FOUND).entity(error).build();
        }
    }

    @GET
    @Path("all")
    public Response getAll(@PathParam("userId") Long userId) {
        try {
            List<Role> roles = userRoleService.findRolesByUserId(userId);
            return Response.status(Response.Status.FOUND).entity(roles).build();
        } catch (EntityException e) {
            Error error = new Error(e.getMessage());
            return Response.status(Response.Status.NOT_FOUND).entity(error).build();
        }
    }
}
