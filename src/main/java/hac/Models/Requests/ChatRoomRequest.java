package hac.Models.Requests;


import lombok.Builder;

/**
 * <h1>ChatRoomRequest</h1>
 * <p>
 *     This class is used to create a ChatRoomRequest object.
 * </p>
 *
 * @see hac.Models.ChatRoom
 */
public class ChatRoomRequest {
    public String name;
    public UserRequest [] users;
}
