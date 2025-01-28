package recetasya.com.msvc_user.service.exception;

import lombok.Getter;

@Getter
public class UserException extends Exception {

    private final int statusCode;

    public UserException(String message, int statusCode) {
        super(message);
        this.statusCode = statusCode;
    }

}
