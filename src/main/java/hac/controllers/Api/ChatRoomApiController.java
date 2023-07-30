package hac.controllers.Api;

import hac.Models.ChatRoom;
import hac.Models.Requests.ChatRoomRequest;
import hac.Models.Response.ChatRoomResponse;
import hac.Models.Response.UserResponse;
import hac.Models.User.User;
import hac.Services.ChatRoomService;
import hac.Services.UserService;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * <h1>ChatRoomController</h1>
 * <p>
 *     API class that handles all the requests related to the chat rooms.
 * </p>
 * <p>
 *     <b>Note:</b> The controller is mapped to <code>/api/chatroom.</code>
 * </p>
 * @see hac.Models.ChatRoom The ChatRoom model.
 */
@RestController
@RequestMapping("/api/chatroom")
public class ChatRoomApiController {

    @Resource(name = "chatRoomService")
    private ChatRoomService chatRoomService;

    @Resource(name = "userService")
    private UserService userService;


    //==========================================================================
    //=============================== GET ======================================
    //==========================================================================

    /**
     * Returns all the chat rooms in the database.
     * @return Iterable<ChatRoomResponse>
     */
    @GetMapping("")
    public Iterable<ChatRoomResponse> getAllChatRooms() {
        return StreamSupport.stream(chatRoomService.getAllChatRooms().spliterator(), false)
                .map(ChatRoomResponse::getFromChatRoom)
                .toList();
    }

    /**
     * Returns all the chat rooms that the user is in.
     * @param userId The id of the user.
     * @return Iterable<ChatRoomResponse>
     */
    @GetMapping("/user/{userId}")
    public Iterable<ChatRoomResponse> getAllChatRoomsByUserId(@PathVariable Integer userId) {
        return StreamSupport.stream(chatRoomService.getAllChatRoomsByUserId(userId).spliterator(), false)
                .map(ChatRoomResponse::getFromChatRoom)
                .toList();
    }

    /**
     * return one chat room by id
     * @param chatRoomId the id of the chat room
     * @return ChatRoomResponse
     */
    @GetMapping("/{chatRoomId}")
    public ChatRoomResponse getChatRoomById(@PathVariable Integer chatRoomId) {
        return ChatRoomResponse.getFromChatRoom(chatRoomService.getChatRoomById(chatRoomId));
    }

    /**
     * Returns all the users that are not in any chat room with the given user id.
     * @param userId The id of the user.
     * @return Set<UserResponse>
     */
    @GetMapping("/filter/not-friends/{userId}")
    public Set<UserResponse> getAllUsersNotInChatRoomWithUserId(@PathVariable Integer userId){
        return chatRoomService.getAllUsersNotInChatRoomWithUserId(userService.getUserById(userId)).stream()
                .map(UserResponse::getFromUser)
                .collect(Collectors.toSet());
    }

    /**
     * Returns all the users that are in any chat room with the given user id.
     * @param userId The id of the user.
     * @return Set<UserResponse>
     */
    @GetMapping("/filter/friends/{userId}")
    public Set<UserResponse> getAllUsersInChatRoomWithUserId(@PathVariable Integer userId){
        return chatRoomService.getAllUsersInChatRoomWithUserId(userService.getUserById(userId)).stream()
                .map(UserResponse::getFromUser)
                .collect(Collectors.toSet());
    }

    //==========================================================================
    //============================== POST ====================================
    //==========================================================================

    /**
     * Creates a new chat room.
     * @param chatRoomRequest The request body that contains the chat room data.
     * @return ChatRoomResponse
     */
    @PostMapping("")
    public ChatRoomResponse createChatRoom(@RequestBody ChatRoomRequest chatRoomRequest) {
        return ChatRoomResponse.getFromChatRoom(chatRoomService.createChatRoom(chatRoomRequest));
    }

    /**
     * Adds a user to a chat room.
     * @param chatRoomId The id of the chat room.
     * @param userId The id of the user.
     * @return ChatRoomResponse
     */
    @PostMapping("/{chatRoomId}/user/{userId}")
    public ChatRoomResponse addUserToChatRoom(@PathVariable Integer chatRoomId, @PathVariable Integer userId) {
        return ChatRoomResponse.getFromChatRoom(chatRoomService.addUserToChatRoom(chatRoomId, userId));
    }

    /**
     * Creates a new chat room and adds the users to it.
     * @param chatRoomRequest The request body that contains the chat room data.
     * @param principal The principal object that contains the user data.
     * @return ChatRoomResponse
     */
    @PostMapping("/create-add")
    public ChatRoomResponse createChatRoomAndAddUsers(@RequestBody ChatRoomRequest chatRoomRequest, Principal principal) {
        var chatRoom = chatRoomService.createChatRoom(chatRoomRequest);
        Arrays.stream(chatRoomRequest.users)
                .toList().forEach(user ->
                    chatRoomService.addUserToChatRoom(chatRoom.getId(),
                            userService.getUserByEmail(user.email).getId()));

        return ChatRoomResponse.getFromChatRoom(chatRoom);
    }


    //==========================================================================
    //============================== DELETE ====================================
    //==========================================================================

    /**
     * Deletes all the chat rooms in the database.
     * available only for admin.
     * @return ResponseEntity<String>
     */
    @DeleteMapping("")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<String> deleteAllChatRooms() {
        chatRoomService.deleteAllChatRooms();
        return ResponseEntity.ok("All chat rooms deleted");
    }

    /**
     * Deletes all the chat rooms that the user is in.
     * available only for admin.
     * @param chatRoomId The id of the chat room.
     * @param userId The id of the user.
     * @return ResponseEntity<String>
     */
    @DeleteMapping("/{chatRoomId}/user/{userId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<String> deleteUserFromChatRoom(@PathVariable Integer chatRoomId, @PathVariable Integer userId) {
        chatRoomService.deleteUserFromChatRoom(chatRoomId, userId);
        return ResponseEntity.ok("User deleted from chat room");
    }

    /**
     * Deletes a chat room by id.
     * available only for admin.
     * @param chatRoomId The id of the chat room.
     * @return ResponseEntity<String>
     */
    @DeleteMapping("/{chatRoomId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<String> deleteChatRoomById(@PathVariable Integer chatRoomId) {
        chatRoomService.deleteChatRoomById(chatRoomId);
        return ResponseEntity.ok("Chat room deleted");
    }

    /**
     * Handles all the exceptions that are thrown by the controller.
     * @param e The exception that was thrown.
     * @return ResponseEntity<String>
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception e) {
        return ResponseEntity.badRequest().body("Error: " + e.getMessage());
    }

}
