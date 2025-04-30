package org.taxionline.resource;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.AllArgsConstructor;
import org.taxionline.business.AccountBusiness;
import org.taxionline.dto.account.AccountDTO;
import org.taxionline.dto.account.CreateAccountDTO;

@AllArgsConstructor
@ApplicationScoped
@Produces( MediaType.APPLICATION_JSON )
@Consumes( MediaType.APPLICATION_JSON )
@Path( "/account" )
public class AccountResource {

    private final AccountBusiness business;

    @POST
    public Response createAccount( @Valid CreateAccountDTO dto ) {
        AccountDTO response = business.createAccount( dto );
        return Response.ok( response ).build( );
    }

    @GET
    @Path( "/{identifier}" )
    public Response getAccountByIdentifier( @PathParam( "identifier" ) String identifier ) {
        AccountDTO response = business.getAccountByIdentifier( identifier );
        return Response.ok( response ).build( );
    }
}
