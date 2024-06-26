package no.cancerregistry.exception;

public class WrongVersionException extends RuntimeException {
    public WrongVersionException(String message) {
        super(message);
    }
}