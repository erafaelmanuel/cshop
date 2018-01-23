package io.ermdev.cshop.webservice.user;

import io.ermdev.cshop.data.repository.UserRoleRepository;
import org.springframework.stereotype.Component;

import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Component
@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
@Path("roles")
public class UsersRoleResource {

    private UserRoleRepository userRoleRepository;

    public UsersRoleResource(UserRoleRepository userRoleRepository) {
        this.userRoleRepository = userRoleRepository;
    }

    @Path("all")
    public Response getAll(@PathParam("userId") Long userId) {
        return null;
    }
}
