package org.taxionline.core.handler;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import org.taxionline.core.exception.DuplicateAttributeException;

@Provider
public class DuplicateAttributeExceptionMapper implements ExceptionMapper<DuplicateAttributeException> {

    @Override
    public Response toResponse( DuplicateAttributeException e ) {
        return Response.status( Response.Status.CONFLICT )
                .entity( e.getMessage( ) )
                .build( );
    }
}
