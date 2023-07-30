package hac.Models.Response;

import hac.Models.User.User;

import java.util.Set;

/**
 * <h1>UserResponse</h1>
 * <p>
 *     This class is used to send user data to the client.
 * </p>
 * @see User
 */
public class UserResponse {
    public Integer id;
    public String firstname;
    public String lastname;
    public String email;

    public Set<ChatRoomResponse> chatRooms;

    /**
     * This method is used to load a User object into the UserResponse object.
     * @param user User object
     */
    public void loadUserResponse(User user) {
        this.id = user.getId();
        this.firstname = user.getFirstname();
        this.lastname = user.getLastname();
        this.email = user.getEmail();
    }

    /**
     *  This method is used to get a UserResponse object from a User object.
     *  u can simply use it like this:
     *  <pre>
     *      {@code
     *          UserResponse userResponse = UserResponse.getFromUser(user);
     *      }
     *  </pre>
     * @param user User object
     * @return UserResponse object
     */
    public static UserResponse getFromUser(User user) {
        UserResponse userResponse = new UserResponse();
        userResponse.loadUserResponse(user);
        return userResponse;
    }

}
