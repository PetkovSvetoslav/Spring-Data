package springdata.exercises.usersystem.exceptions;

public class NonExistentUserException extends RuntimeException {
    public NonExistentUserException() {
        super();
    }

    public NonExistentUserException(long userId) {
        super("There is no user with such id: " + userId);
    }

    public NonExistentUserException(String message, Throwable cause) {
        super(message, cause);
    }

    public NonExistentUserException(Throwable cause) {
        super(cause);
    }
}
