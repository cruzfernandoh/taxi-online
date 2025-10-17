package org.taxionline.config.exception;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException( String message ) {
        super( message );
    }
}
