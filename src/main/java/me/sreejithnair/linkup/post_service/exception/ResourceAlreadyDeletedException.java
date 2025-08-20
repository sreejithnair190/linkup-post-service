package me.sreejithnair.linkup.post_service.exception;

public class ResourceAlreadyDeletedException extends RuntimeException {
    public ResourceAlreadyDeletedException(String message) {
        super(message);
    }
}
