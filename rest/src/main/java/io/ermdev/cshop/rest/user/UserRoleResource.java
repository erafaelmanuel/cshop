package io.ermdev.cshop.rest.user;

import io.ermdev.cshop.commons.Error;
import io.ermdev.cshop.data.entity.Role;
import io.ermdev.cshop.data.service.UserRoleService;
import io.ermdev.cshop.exception.EntityException;
import io.ermdev.cshop.rest.role.RoleDto;
import io.ermdev.cshop.rest.role.RoleResourceLinks;
import mapfierj.xyz.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.ArrayList;
import java.util.List;

@Component
@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
@Path("roles")
public class UserRoleResource {

    private UserRoleService userRoleService;
    private Mapper mapper;

    private UriInfo uriInfo;

    @Autowired
    public UserRoleResource(UserRoleService userRoleService, Mapper mapper) {
        this.userRoleService = userRoleService;
        this.mapper = mapper;
    }

    public void setUriInfo(UriInfo uriInfo) {
        this.uriInfo = uriInfo;
    }

    @GET
    @Path("{roleId}")
    public Response getById(@PathParam("userId") Long userId, @PathParam("roleId") Long roleId) {
        try {
            Role role = userRoleService.findRoleByUserIdAndRoleId(userId, roleId);
            RoleDto roleDto = mapper.set(role).mapTo(RoleDto.class);
            RoleResourceLinks roleResourceLinks = new RoleResourceLinks(uriInfo);
            roleDto.getLinks().add(roleResourceLinks.getSelf(roleId));
            return Response.status(Response.Status.FOUND).entity(roleDto).build();
        } catch (EntityException e) {
            Error error = new Error(e.getMessage());
            return Response.status(Response.Status.NOT_FOUND).entity(error).build();
        }
    }

    @GET
    public Response getAll(@PathParam("userId") Long userId) {
        try {
            List<RoleDto> roleDtos = new ArrayList<>();
            userRoleService.findRolesByUserId(userId).forEach(role -> {
                RoleDto roleDto = mapper.set(role).mapTo(RoleDto.class);
                RoleResourceLinks roleResourceLinks = new RoleResourceLinks(uriInfo);
                roleDto.getLinks().add(roleResourceLinks.getSelf(roleDto.getId()));
                roleDtos.add(roleDto);
            });
            return Response.status(Response.Status.FOUND).entity(roleDtos).build();
        } catch (EntityException e) {
            Error error = new Error(e.getMessage());
            return Response.status(Response.Status.NOT_FOUND).entity(error).build();
        }
    }

    @POST
    @Path("{roleId}")
    public Response addRole(@PathParam("userId") Long userId, @PathParam("roleId") Long roleId) {
        try {
            Role role = userRoleService.addRoleToUser(userId, roleId);
            RoleDto roleDto = mapper.set(role).mapTo(RoleDto.class);
            RoleResourceLinks roleResourceLinks = new RoleResourceLinks(uriInfo);
            roleDto.getLinks().add(roleResourceLinks.getSelf(roleDto.getId()));
            return Response.status(Response.Status.FOUND).entity(roleDto).build();
        } catch (EntityException e) {
            Error error = new Error(e.getMessage());
            return Response.status(Response.Status.NOT_FOUND).entity(error).build();
        }
    }

    @DELETE
    @Path("{roleId}")
    public Response deleteRole(@PathParam("userId") Long userId, @PathParam("roleId") Long roleId) {
        try {
            Role role = userRoleService.deleteRoleFromUser(userId, roleId);
            RoleDto roleDto = mapper.set(role).mapTo(RoleDto.class);
            RoleResourceLinks roleResourceLinks = new RoleResourceLinks(uriInfo);
            roleDto.getLinks().add(roleResourceLinks.getSelf(roleDto.getId()));
            return Response.status(Response.Status.FOUND).entity(roleDto).build();
        } catch (EntityException e) {
            Error error = new Error(e.getMessage());
            return Response.status(Response.Status.NOT_FOUND).entity(error).build();
        }
    }
}
