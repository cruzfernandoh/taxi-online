package org.taxionline.core.handler;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import org.taxionline.core.exception.ResourceNotFoundException;

@Provider
public class ResourceNotFoundExceptionMapper implements ExceptionMapper<ResourceNotFoundException> {

    @Override
    public Response toResponse( ResourceNotFoundException e ) {
        return Response
                .status( Response.Status.NOT_FOUND )
                .entity( e.getMessage( ) )
                .build( );
    }
}
