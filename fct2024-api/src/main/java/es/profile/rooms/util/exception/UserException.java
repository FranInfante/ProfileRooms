package es.profile.rooms.util.exception;

import es.profile.rooms.util.MessageConstants;

public class UserException extends Exception {
    public UserException(String s) {
        super(s);
    }

    public static UserException userNameExistingException() {
        return new UserException(MessageConstants.EXISTING_USER);
    }

    public static UserException emailExistingException() {
        return new UserException(MessageConstants.EXISTING_MAIL);
    }

    public static UserException dniExistingException(String msg) {
        return new UserException(MessageConstants.DNI_NOT_VALID + msg);
    }

    public static UserException failedUpdate() {
        return new UserException(MessageConstants.MODIFY_USER_ERROR);
    }
}
