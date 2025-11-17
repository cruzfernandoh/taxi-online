package org.taxionline.infra.exception;

public class DuplicateAttributeException extends RuntimeException {

    public DuplicateAttributeException( String message ) {
        super( message );
    }
}
