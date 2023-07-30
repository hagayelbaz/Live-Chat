package hac.Models.Response;

import hac.Models.Message;

import java.time.LocalDateTime;

/**
 * <h1>MessageResponse</h1>
 * <p>
 *     This class is used to send message data to the client.
 * </p>
 * @see Message
 */
public class MessageResponse {
    public Integer id;
    public UserResponse user;
    public ChatRoomResponse chatRoom;
    public String text;
    public LocalDateTime timestamp;

    /**
     * this method is used to load a Message object into the MessageResponse object.
     * @param message Message object
     */
    public void loadMessageResponse(Message message) {
        this.id = message.getId();
        this.user = new UserResponse();
        this.user.loadUserResponse(message.getUser());
        this.chatRoom = new ChatRoomResponse();
        this.chatRoom.loadChatRoomResponse(message.getChatRoom());
        this.text = message.getText();
        this.timestamp = message.getTimestamp();
    }

    /**
     *  this method is used to get a MessageResponse object from a Message object.
     *  u can simply use it like this:
     *  <pre>
     *      {@code
     *          MessageResponse messageResponse = MessageResponse.getFromMessage(message);
     *      }
     *  </pre>
     * @param message Message object
     * @return MessageResponse object
     */
    public static MessageResponse getFromMessage(Message message) {
        MessageResponse messageResponse = new MessageResponse();
        messageResponse.loadMessageResponse(message);
        return messageResponse;
    }
}
