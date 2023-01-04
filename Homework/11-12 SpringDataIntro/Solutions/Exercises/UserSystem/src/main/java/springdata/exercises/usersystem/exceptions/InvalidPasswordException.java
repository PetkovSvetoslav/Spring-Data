package springdata.exercises.usersystem.exceptions;

public class InvalidPasswordException extends RuntimeException {
    public InvalidPasswordException() {
        super("The password must contain at least:" + System.lineSeparator() +
                "   1 lowercase letter" + System.lineSeparator() +
                "   1 uppercase letter" + System.lineSeparator() +
                "   1 digit" + System.lineSeparator() +
                "   1 special symbol (!, @, #, $, %, ^, &, *, (, ), _, +, <, >, ?)");
    }

    public InvalidPasswordException(String message) {
        super(message);
    }

    public InvalidPasswordException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidPasswordException(Throwable cause) {
        super(cause);
    }
}
