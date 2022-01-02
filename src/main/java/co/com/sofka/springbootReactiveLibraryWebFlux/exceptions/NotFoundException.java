package co.com.sofka.springbootReactiveLibraryWebFlux.exceptions;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String message) {
        super(message);
    }

}
