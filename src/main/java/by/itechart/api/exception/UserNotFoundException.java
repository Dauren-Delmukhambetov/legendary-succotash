package by.itechart.api.exception;

import static java.lang.String.format;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(Long id) {
        super(format("User with ID %d is not found", id));
    }

}
