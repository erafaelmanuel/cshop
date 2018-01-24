package io.ermdev.cshop.webservice.user;

import io.ermdev.cshop.data.entity.Role;
import io.ermdev.cshop.data.exception.EntityException;
import io.ermdev.cshop.data.model.Error;
import io.ermdev.cshop.data.service.UserRoleService;
import io.ermdev.cshop.webservice.role.RoleDto;
import io.ermdev.mapfierj.SimpleMapper;
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
@Path("role")
public class UserRoleResource {

    private UserRoleService userRoleService;
    private SimpleMapper mapper;

    private List<Long> roleIds;
    private UriInfo uriInfo;

    @Autowired
    public UserRoleResource(UserRoleService userRoleService, SimpleMapper mapper) {
        this.userRoleService = userRoleService;
        this.mapper = mapper;
    }

    public void setRoleIds(List<Long> roleIds) {
        this.roleIds = roleIds;
    }

    public void setUriInfo(UriInfo uriInfo) {
        this.uriInfo = uriInfo;
    }

    @GET
    @Path("{roleId}")
    public Response getById(@PathParam("userId") Long userId, @PathParam("roleId") Long roleId) {
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

    @POST
    public Response addRole(@PathParam("userId") Long userId) {
        try {
            List<RoleDto> roles = new ArrayList<>();
            if(roleIds != null && roleIds.size() >= 1) {
                for(Long roleId : roleIds) {
                    Role role = userRoleService.addRoleToUser(userId, roleId);
                    roles.add(mapper.set(role).mapTo(RoleDto.class));
                }
                if(roles.size() == 1) {
                    return Response.status(Response.Status.FOUND).entity(roles.get(0)).build();
                } else {
                    return Response.status(Response.Status.FOUND).entity(roles).build();
                }
            } else {
                throw new EntityException("roleId is required");
            }
        } catch (EntityException e) {
            Error error = new Error(e.getMessage());
            return Response.status(Response.Status.NOT_FOUND).entity(error).build();
        }
    }

    @DELETE
    public Response deleteRole(@PathParam("userId") Long userId) {
        try {
            List<RoleDto> roles = new ArrayList<>();
            if(roleIds != null && roleIds.size() >= 1) {
                for(Long roleId : roleIds) {
                    Role role = userRoleService.deleteRoleFromUser(userId, roleId);
                    roles.add(mapper.set(role).mapTo(RoleDto.class));
                }
                if(roles.size() == 1) {
                    return Response.status(Response.Status.FOUND).entity(roles.get(0)).build();
                } else {
                    return Response.status(Response.Status.FOUND).entity(roles).build();
                }
            } else {
                throw new EntityException("roleId is required");
            }
        } catch (EntityException e) {
            Error error = new Error(e.getMessage());
            return Response.status(Response.Status.NOT_FOUND).entity(error).build();
        }
    }
}
