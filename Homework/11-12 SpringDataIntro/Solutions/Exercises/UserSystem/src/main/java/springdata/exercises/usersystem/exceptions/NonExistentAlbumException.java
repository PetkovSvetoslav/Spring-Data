package springdata.exercises.usersystem.exceptions;

public class NonExistentAlbumException extends RuntimeException {
    public NonExistentAlbumException() {
        super();
    }

    public NonExistentAlbumException(long albumId) {
        super("There is no album with such id: " + albumId);
    }

    public NonExistentAlbumException(String message, Throwable cause) {
        super(message, cause);
    }

    public NonExistentAlbumException(Throwable cause) {
        super(cause);
    }
}
