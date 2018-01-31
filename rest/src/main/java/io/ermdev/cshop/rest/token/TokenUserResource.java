package io.ermdev.cshop.rest.token;

import io.ermdev.cshop.commons.Error;
import io.ermdev.cshop.data.service.TokenUserService;
import io.ermdev.cshop.exception.EntityException;
import io.ermdev.cshop.rest.user.UserDto;
import io.ermdev.cshop.rest.user.UserResourceLinks;
import io.ermdev.mapfierj.SimpleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

@Component
@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
@Path("user")
public class TokenUserResource {

    private TokenUserService tokenUserService;
    private SimpleMapper simpleMapper;
    private UriInfo uriInfo;

    @Autowired
    public TokenUserResource(TokenUserService tokenUserService, SimpleMapper simpleMapper) {
        this.tokenUserService = tokenUserService;
        this.simpleMapper = simpleMapper;
    }

    @GET
    public Response findUserByTokenId(@PathParam("tokenId") Long tokenId) {
        try {
            UserDto userDto = simpleMapper.set(tokenUserService.findUserByTokenId(tokenId)).mapTo(UserDto.class);
            UserResourceLinks userResourceLinks = new UserResourceLinks(uriInfo);
            userDto.getLinks().add(userResourceLinks.getSelf(userDto.getId()));
            userDto.getLinks().add(userResourceLinks.getRoles(userDto.getId()));
            return Response.status(Response.Status.FOUND).entity(userDto).build();
        } catch (EntityException e) {
            Error error = new Error(e.getMessage());
            return Response.status(Response.Status.NOT_FOUND).entity(error).build();
        }
    }

    @POST
    @Path("{userId}")
    public Response addUserToToken(@PathParam("tokenId") Long tokenId, @PathParam("userId") Long userId) {
        try {
            UserDto userDto = simpleMapper.set(tokenUserService.addUserToToken(tokenId, userId)).mapTo(UserDto.class);
            UserResourceLinks userResourceLinks = new UserResourceLinks(uriInfo);
            userDto.getLinks().add(userResourceLinks.getSelf(userDto.getId()));
            userDto.getLinks().add(userResourceLinks.getRoles(userDto.getId()));
            return Response.status(Response.Status.FOUND).entity(userDto).build();
        } catch (EntityException e) {
            Error error = new Error(e.getMessage());
            return Response.status(Response.Status.NOT_FOUND).entity(error).build();
        }
    }

    @DELETE
    @Path("{userId}")
    public Response deleteUserFromToken(@PathParam("tokenId") Long tokenId) {
        try {
            UserDto userDto = simpleMapper.set(tokenUserService.deleteUserFromToken(tokenId)).mapTo(UserDto.class);
            UserResourceLinks userResourceLinks = new UserResourceLinks(uriInfo);
            userDto.getLinks().add(userResourceLinks.getSelf(userDto.getId()));
            userDto.getLinks().add(userResourceLinks.getRoles(userDto.getId()));
            return Response.status(Response.Status.FOUND).entity(userDto).build();
        } catch (EntityException e) {
            Error error = new Error(e.getMessage());
            return Response.status(Response.Status.NOT_FOUND).entity(error).build();
        }
    }

    public void setUriInfo(UriInfo uriInfo) {
        this.uriInfo = uriInfo;
    }
}
