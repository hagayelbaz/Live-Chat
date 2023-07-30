package hac.Models.Response;

import hac.Models.ChatRoom;

import java.util.HashSet;
import java.util.Set;

/**
 * <h1>ChatRoomResponse</h1>
 * <p>
 *     This class is used to send chat room data to the client.
 * </p>
 * @see ChatRoom
 */
public class ChatRoomResponse {
    public Integer id;
    public String name;

    public Set<UserResponse> users;

    /**
     * This method is used to load chat room data from the database.
     * @param chatRoom Chat room data from the database.
     */
    public void loadChatRoomResponse(ChatRoom chatRoom) {
        this.id = chatRoom.getId();
        this.name = chatRoom.getName();
        this.users = new HashSet<>();
        chatRoom.getUsers().forEach(user -> {
            UserResponse userResponse = new UserResponse();
            userResponse.loadUserResponse(user);
            this.users.add(userResponse);
        });
    }

    /**
     * This method is used to create a chat room response from chat room data.
     * u can use this methode like this:
     * <pre>
     *     {@code
     *     ChatRoomResponse chatRoomResponse = ChatRoomResponse.getFromChatRoom(chatRoom);
     *     }
     * </pre>
     * @param chatRoom Chat room data from the database.
     * @return Chat room response.
     */
    public static ChatRoomResponse getFromChatRoom(ChatRoom chatRoom) {
        ChatRoomResponse chatRoomResponse = new ChatRoomResponse();
        chatRoomResponse.loadChatRoomResponse(chatRoom);
        return chatRoomResponse;
    }
}
