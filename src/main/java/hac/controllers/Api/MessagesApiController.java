package hac.controllers.Api;

import hac.Models.Message;
import hac.Models.Requests.MessageRequest;
import hac.Models.Response.MessageResponse;
import hac.Services.MessagesService;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.stream.StreamSupport;

/**
 * <h1>MessagesApiController</h1>
 * <p>
 *     API controller for the messages, handles all the requests for the messages.
 * </p>
 * <p>
 *     <b>Note:</b> The controller is mapped to <code>/api/messages.</code>
 * </p>
 * @see hac.Models.Message The Message model.
 */
@RestController
@RequestMapping("/api/messages")
public class MessagesApiController {

    @Resource(name = "messagesService")
    private MessagesService messagesService;

    //==========================================================================
    //=============================== GET ======================================
    //==========================================================================

    /**
     * Returns all the messages in the database.
     * @return Iterable<MessageResponse>
     */
    @GetMapping("")
    public Iterable<MessageResponse> getAllMessages() {
        return StreamSupport.stream(messagesService.getAllMessages().spliterator(), false)
                .map(MessageResponse::getFromMessage)
                .toList();
    }

    /**
     * Returns all the messages that are in the chat room.
     * @param chatRoomId The id of the chat room.
     * @return Iterable<MessageResponse>
     */
    @GetMapping("/chatroom/{chatRoomId}")
    public Iterable<MessageResponse> getMessagesByChatRoomId(@PathVariable Integer chatRoomId) {
        return StreamSupport.stream(messagesService.getMessagesByChatRoomId(chatRoomId).spliterator(), false)
                .map(MessageResponse::getFromMessage)
                .toList();
    }

    /**
     * Returns the last message in the chat room.
     * @param chatRoomId The id of the chat room.
     * @return MessageResponse
     */
    @GetMapping("/chatroom/{chatRoomId}/last")
    public MessageResponse getLastMessageInChatRoom(@PathVariable Integer chatRoomId) {
        return MessageResponse.getFromMessage(messagesService.getLastMessageInChatRoom(chatRoomId));
    }

    //==========================================================================
    //=============================== POST =====================================
    //==========================================================================

    /**
     * Creates a new message.
     * @param message The message to be created.
     * @return MessageResponse
     */
    @PostMapping("")
    public MessageResponse createMessage(@RequestBody MessageRequest message) {
        return MessageResponse.getFromMessage(messagesService.createMessage(message));
    }

    //==========================================================================
    //============================== DELETE ====================================
    //==========================================================================

    /**
     * Deletes the message with the given id.
     * @param messageId The id of the message.
     * @return ResponseEntity<String>
     */
    @DeleteMapping("/{messageId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<String> deleteMessageById(@PathVariable Integer messageId) {
        messagesService.deleteMessageById(messageId);
        return ResponseEntity.ok("Message deleted successfully");
    }

    /**
     * Deletes all the messages in the chat room.
     * only the admin can delete the messages.
     * @param chatRoomId The id of the chat room.
     * @return ResponseEntity<String>
     */
    @DeleteMapping("/chatroom/{chatRoomId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<String> deleteMessagesByChatRoomId(@PathVariable Integer chatRoomId) {
        messagesService.deleteMessagesByChatRoomId(chatRoomId);
        return ResponseEntity.ok("Messages deleted successfully");
    }

    /**
     * handles all the exceptions.
     * @param e The exception.
     * @return ResponseEntity<String>
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception e) {
        return ResponseEntity.badRequest().body("Error: " + e.getMessage());
    }

}
