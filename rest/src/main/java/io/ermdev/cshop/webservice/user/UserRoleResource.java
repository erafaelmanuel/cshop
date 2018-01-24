package io.ermdev.cshop.webservice.user;

import io.ermdev.cshop.data.model.Error;
import io.ermdev.cshop.data.service.UserRoleService;
import io.ermdev.cshop.exception.EntityException;
import io.ermdev.cshop.exception.ResourceException;
import io.ermdev.cshop.webservice.role.RoleDto;
import io.ermdev.cshop.webservice.role.RoleResourceLinks;
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
            RoleDto roleDto = mapper.set(userRoleService.findUserRoleById(userId, roleId)).mapTo(RoleDto.class);
            RoleResourceLinks roleResourceLinks = new RoleResourceLinks(uriInfo);
            roleDto.getLinks().add(roleResourceLinks.getSelf(roleId));
            return Response.status(Response.Status.FOUND).entity(roleDto).build();
        } catch (EntityException e) {
            Error error = new Error(e.getMessage());
            return Response.status(Response.Status.NOT_FOUND).entity(error).build();
        }
    }

    @GET
    @Path("all")
    public Response getAll(@PathParam("userId") Long userId) {
        try {
            List<RoleDto> roleDtos = mapper.set(userRoleService.findRolesByUserId(userId)).mapToList(RoleDto.class);
            roleDtos.parallelStream().forEach(roleDto -> {
                RoleResourceLinks roleResourceLinks = new RoleResourceLinks(uriInfo);
                roleDto.getLinks().add(roleResourceLinks.getSelf(roleDto.getId()));
            });
            return Response.status(Response.Status.FOUND).entity(roleDtos).build();
        } catch (EntityException e) {
            Error error = new Error(e.getMessage());
            return Response.status(Response.Status.NOT_FOUND).entity(error).build();
        }
    }

    @POST
    public Response addRole(@PathParam("userId") Long userId) {
        try {
            List<RoleDto> roleDtos = new ArrayList<>();
            if (roleIds != null && roleIds.size() >= 1) {
                for (Long roleId : roleIds) {
                    RoleDto roleDto = mapper.set(userRoleService.addRoleToUser(userId, roleId)).mapTo(RoleDto.class);
                    RoleResourceLinks roleResourceLinks = new RoleResourceLinks(uriInfo);
                    roleDto.getLinks().add(roleResourceLinks.getSelf(roleDto.getId()));
                    roleDtos.add(roleDto);
                }
                if (roleDtos.size() == 1) {
                    return Response.status(Response.Status.FOUND).entity(roleDtos.get(0)).build();
                } else {
                    return Response.status(Response.Status.FOUND).entity(roleDtos).build();
                }
            } else {
                throw new ResourceException("roleId is required");
            }
        } catch (EntityException | ResourceException e) {
            Error error = new Error(e.getMessage());
            return Response.status(Response.Status.NOT_FOUND).entity(error).build();
        }
    }

    @DELETE
    public Response deleteRole(@PathParam("userId") Long userId) {
        try {
            List<RoleDto> roleDtos = new ArrayList<>();
            if (roleIds != null && roleIds.size() >= 1) {
                for (Long roleId : roleIds) {
                    RoleDto roleDto = mapper.set(userRoleService.addRoleToUser(userId, roleId)).mapTo(RoleDto.class);
                    RoleResourceLinks roleResourceLinks = new RoleResourceLinks(uriInfo);
                    roleDto.getLinks().add(roleResourceLinks.getSelf(roleDto.getId()));
                    roleDtos.add(roleDto);
                }
                if (roleDtos.size() == 1) {
                    return Response.status(Response.Status.FOUND).entity(roleDtos.get(0)).build();
                } else {
                    return Response.status(Response.Status.FOUND).entity(roleDtos).build();
                }
            } else {
                throw new ResourceException("roleId is required");
            }
        } catch (EntityException | ResourceException e) {
            Error error = new Error(e.getMessage());
            return Response.status(Response.Status.NOT_FOUND).entity(error).build();
        }
    }
}
