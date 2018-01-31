package io.ermdev.cshop.rest.token;

import io.ermdev.cshop.commons.Error;
import io.ermdev.cshop.data.entity.User;
import io.ermdev.cshop.data.repository.TokenUserRepository;
import io.ermdev.cshop.data.service.TokenUserService;
import io.ermdev.cshop.exception.EntityException;
import io.ermdev.cshop.rest.user.UserDto;
import io.ermdev.cshop.rest.user.UserResourceLinks;
import io.ermdev.mapfierj.SimpleMapper;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Component
@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
@Path("user")
public class TokenUserResource {

    private TokenUserService tokenUserService;
    private SimpleMapper simpleMapper;

    public TokenUserResource(TokenUserService tokenUserService, SimpleMapper simpleMapper) {
        this.tokenUserService = tokenUserService;
        this.simpleMapper = simpleMapper;
    }

    @GET
    public Response findById(@PathParam("tokenId") Long tokenId) {
        try {
            UserDto userDto = simpleMapper.set(tokenUserService.findUserByTokenId(tokenId)).mapTo(UserDto.class);
            return Response.status(Response.Status.FOUND).entity(userDto).build();
        } catch (EntityException e) {
            Error error = new Error(e.getMessage());
            return Response.status(Response.Status.NOT_FOUND).entity(error).build();
        }
    }
}
