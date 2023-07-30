package hac.controllers.Exception;

/**
 * <h1>UserAlreadyExistsException</h1>
 * <p>
 *     simple class to throw an exception when a user already exists
 * </p>
 */
public class UserAlreadyExistsException extends RuntimeException{
    public UserAlreadyExistsException(String message) {
        super(message);
    }
}
