package hac.Models.Requests;

import java.time.LocalDateTime;

/**
 * <h1>MessageRequest</h1>
 * <p>
 * this class is used to create a message request object
 * for user to simply can fill this object and send it to the server.
 * </p>
 *
 * @see hac.Models.Message
 */
public class MessageRequest {
    public String text;
    public LocalDateTime timestamp;
    public Integer userId;
    public Integer chatRoomId;
}
