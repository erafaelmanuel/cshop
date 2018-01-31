package io.ermdev.cshop.rest.token;

import io.ermdev.cshop.commons.Error;
import io.ermdev.cshop.data.service.TokenService;
import io.ermdev.cshop.exception.EntityException;
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
@Path("tokens")
public class TokenResource {

    private TokenService tokenService;
    private TokenUserResource tokenUserResource;
    private SimpleMapper simpleMapper;

    @Autowired
    public TokenResource(TokenService tokenService, TokenUserResource tokenUserResource, SimpleMapper simpleMapper) {
        this.tokenService = tokenService;
        this.tokenUserResource = tokenUserResource;
        this.simpleMapper = simpleMapper;
    }

    @GET
    @Path("{tokenId}")
    public Response getById(@PathParam("tokenId") Long tokenId, @Context UriInfo uriInfo) {
        try {
            TokenDto tokenDto = simpleMapper.set(tokenService.findById(tokenId)).mapTo(TokenDto.class);
            TokenResourceLinks tokenResourceLinks = new TokenResourceLinks(uriInfo);
            tokenDto.getLinks().add(tokenResourceLinks.getSelf(tokenId));
            return Response.status(Response.Status.FOUND).entity(tokenDto).build();
        } catch (EntityException e) {
            Error error = new Error(e.getMessage());
            return Response.status(Response.Status.NOT_FOUND).entity(error).build();
        }
    }

    @GET
    public Response getAll(@QueryParam("key") String key, @Context UriInfo uriInfo) {
        try {
            if(key != null) {
                TokenDto tokenDto = simpleMapper.set(tokenService.findByKey(key)).mapTo(TokenDto.class);
                TokenResourceLinks tokenResourceLinks = new TokenResourceLinks(uriInfo);
                tokenDto.getLinks().add(tokenResourceLinks.getSelf(tokenDto.getId()));
                return Response.status(Response.Status.FOUND).entity(tokenDto).build();
            } else {
                List<TokenDto> tokenDtos = simpleMapper.set(tokenService.findAll()).mapToList(TokenDto.class);
                tokenDtos.parallelStream().forEach(tokenDto -> {
                    TokenResourceLinks tokenResourceLinks = new TokenResourceLinks(uriInfo);
                    tokenDto.getLinks().add(tokenResourceLinks.getSelf(tokenDto.getId()));
                });
                return Response.status(Response.Status.FOUND).entity(tokenDtos).build();
            }
        } catch (EntityException e) {
            Error error = new Error(e.getMessage());
            return Response.status(Response.Status.NOT_FOUND).entity(error).build();
        }
    }

    @Path("{tokenId}/user")
    public TokenUserResource tokenUserResource(@Context UriInfo uriInfo) {
        tokenUserResource.setUriInfo(uriInfo);
        return tokenUserResource;
    }
}
