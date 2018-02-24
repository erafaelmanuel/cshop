package io.ermdev.cshop.rest.token;

import io.ermdev.cshop.commons.Error;
import io.ermdev.cshop.data.entity.Token;
import io.ermdev.cshop.data.service.TokenService;
import io.ermdev.cshop.exception.EntityException;
import mapfierj.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.ArrayList;
import java.util.List;

@Component
@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
@Path("tokens")
public class TokenResource {

    private TokenService tokenService;
    private TokenUserResource tokenUserResource;
    private Mapper mapper;

    @Autowired
    public TokenResource(TokenService tokenService, TokenUserResource tokenUserResource, Mapper mapper) {
        this.tokenService = tokenService;
        this.tokenUserResource = tokenUserResource;
        this.mapper = mapper;
    }

    @GET
    @Path("{tokenId}")
    public Response getById(@PathParam("tokenId") Long tokenId, @Context UriInfo uriInfo) {
        try {
            TokenDto tokenDto = mapper.set(tokenService.findById(tokenId)).mapTo(TokenDto.class);
            TokenResourceLinks tokenResourceLinks = new TokenResourceLinks(uriInfo);
            tokenDto.getLinks().add(tokenResourceLinks.getSelf(tokenId));
            tokenDto.getLinks().add(tokenResourceLinks.getUser(tokenId));
            return Response.status(Response.Status.FOUND).entity(tokenDto).build();
        } catch (EntityException e) {
            Error error = new Error(e.getMessage());
            return Response.status(Response.Status.NOT_FOUND).entity(error).build();
        }
    }

    @GET
    public Response getAll(@QueryParam("key") String key, @Context UriInfo uriInfo) {
        try {
            if (key != null) {
                TokenDto tokenDto = mapper.set(tokenService.findByKey(key)).mapTo(TokenDto.class);
                TokenResourceLinks tokenResourceLinks = new TokenResourceLinks(uriInfo);
                tokenDto.getLinks().add(tokenResourceLinks.getSelf(tokenDto.getId()));
                tokenDto.getLinks().add(tokenResourceLinks.getUser(tokenDto.getId()));
                return Response.status(Response.Status.FOUND).entity(tokenDto).build();
            } else {
                List<TokenDto> tokenDtos = new ArrayList<>();
                tokenService.findAll().forEach(token -> {
                    TokenDto tokenDto = mapper.set(token).mapTo(TokenDto.class);
                    ;
                    TokenResourceLinks tokenResourceLinks = new TokenResourceLinks(uriInfo);
                    tokenDto.getLinks().add(tokenResourceLinks.getSelf(tokenDto.getId()));
                    tokenDto.getLinks().add(tokenResourceLinks.getUser(tokenDto.getId()));
                    tokenDtos.add(tokenDto);
                });
                return Response.status(Response.Status.FOUND).entity(tokenDtos).build();
            }
        } catch (EntityException e) {
            Error error = new Error(e.getMessage());
            return Response.status(Response.Status.NOT_FOUND).entity(error).build();
        }
    }

    @POST
    public Response add(TokenDto tokenDto, @Context UriInfo uriInfo) {
        try {
            Token token = tokenService.save(mapper.set(tokenDto).mapTo(Token.class));
            TokenResourceLinks tokenResourceLinks = new TokenResourceLinks(uriInfo);
            tokenDto.setId(token.getId());
            tokenDto.getLinks().add(tokenResourceLinks.getSelf(token.getId()));
            tokenDto.getLinks().add(tokenResourceLinks.getUser(token.getId()));
            return Response.status(Response.Status.FOUND).entity(tokenDto).build();
        } catch (EntityException e) {
            Error error = new Error(e.getMessage());
            return Response.status(Response.Status.NOT_FOUND).entity(error).build();
        }
    }

    @PUT
    @Path("{tokenId}")
    public Response update(@PathParam("tokenId") Long tokenId, TokenDto tokenDto, @Context UriInfo uriInfo) {
        try {
            tokenDto.setId(tokenId);
            Token token = tokenService.save(mapper.set(tokenDto).mapTo(Token.class));
            TokenResourceLinks tokenResourceLinks = new TokenResourceLinks(uriInfo);
            tokenDto = mapper.set(token).mapTo(TokenDto.class);
            tokenDto.getLinks().add(tokenResourceLinks.getSelf(token.getId()));
            tokenDto.getLinks().add(tokenResourceLinks.getUser(token.getId()));
            return Response.status(Response.Status.FOUND).entity(tokenDto).build();
        } catch (EntityException e) {
            Error error = new Error(e.getMessage());
            return Response.status(Response.Status.NOT_FOUND).entity(error).build();
        }
    }

    @DELETE
    @Path("{tokenId}")
    public Response delete(@PathParam("tokenId") Long tokenId, @Context UriInfo uriInfo) {
        try {
            TokenDto tokenDto = mapper.set(tokenService.delete(tokenId)).mapTo(TokenDto.class);
            TokenResourceLinks tokenResourceLinks = new TokenResourceLinks(uriInfo);
            tokenDto.getLinks().add(tokenResourceLinks.getSelf(tokenId));
            tokenDto.getLinks().add(tokenResourceLinks.getUser(tokenId));
            return Response.status(Response.Status.FOUND).entity(tokenDto).build();
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
