package by.itechart.api.exception;

import static java.lang.String.format;

public class UserEmailDuplicationException extends RuntimeException {
    public UserEmailDuplicationException(String email) {
        super(format("User with %s email is already exists", email));
    }
}
