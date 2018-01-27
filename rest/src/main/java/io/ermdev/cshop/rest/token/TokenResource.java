package io.ermdev.cshop.rest.token;

import io.ermdev.cshop.commons.Error;
import io.ermdev.cshop.data.service.TokenService;
import io.ermdev.cshop.exception.EntityException;
import io.ermdev.mapfierj.SimpleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

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
    private SimpleMapper simpleMapper;

    @Autowired
    public TokenResource(TokenService tokenService, SimpleMapper simpleMapper) {
        this.tokenService = tokenService;
        this.simpleMapper = simpleMapper;
    }

    @GetMapping
    @Path("{tokenId}")
    public Response getById(@PathParam("tokenId") Long tokenId, @Context UriInfo uriInfo) {
        try {
            TokenDto tokenDto = simpleMapper.set(tokenService.findById(tokenId)).mapTo(TokenDto.class);
            return Response.status(Response.Status.FOUND).entity(tokenDto).build();
        } catch (EntityException e) {
            Error error = new Error(e.getMessage());
            return Response.status(Response.Status.NOT_FOUND).entity(error).build();
        }
    }

    @GetMapping
    public Response getAll(@QueryParam("userId") Long userId, @Context UriInfo uriInfo) {
        try {
            if (userId == null) {
                List<TokenDto> tokenDto = simpleMapper.set(tokenService.findAll()).mapToList(TokenDto.class);
                return Response.status(Response.Status.FOUND).entity(tokenDto).build();
            } else {
                TokenDto tokenDto = simpleMapper.set(tokenService.findByUserId(userId)).mapTo(TokenDto.class);
                return Response.status(Response.Status.FOUND).entity(tokenDto).build();
            }
        } catch (EntityException e) {
            Error error = new Error(e.getMessage());
            return Response.status(Response.Status.NOT_FOUND).entity(error).build();
        }
    }
}
