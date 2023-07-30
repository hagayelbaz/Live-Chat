package hac.Services;

import hac.Models.ChatRoom;
import hac.Models.Repositories.ChatRoomRepository;
import hac.Models.Repositories.MessageRepository;
import hac.Models.Repositories.UserRepository;
import hac.Models.Requests.ChatRoomRequest;
import hac.Models.User.User;
import jakarta.annotation.Resource;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.beans.Transient;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


/**
 * <h1>Chat Room Service</h1>
 * <p>
 *     This class is responsible for handling all the logic related to the Chat Room model.
 *     It is used by the {@link hac.controllers.Api.ChatRoomApiController} class.
 * </p>
 * @see hac.Models.Repositories.ChatRoomRepository
 */
@Service
public class ChatRoomService {
    @Resource(name = "chatRoomRepository")
    private ChatRoomRepository chatRoomRepository;

    @Resource(name = "userService")
    private UserService userService;

    @Resource(name = "messageRepository")
    private MessageRepository messageRepository;


    //==========================================================================
    //=============================== GET ======================================
    //==========================================================================

    /**
     * This method returns all the chat rooms in the database.
     * @return An {@link Iterable} of {@link ChatRoom} objects.
     */
    public Iterable<ChatRoom> getAllChatRooms() {
        return chatRoomRepository.findAll();
    }

    /**
     *  this methode returned all the chat-rooms that user is in
     * @param userId the id of the user
     * @return an {@link Iterable} of {@link ChatRoom} objects.
     */
    public Iterable<ChatRoom> getAllChatRoomsByUserId(Integer userId) {
        return chatRoomRepository.findByUsers_Id(userId);
    }

    /**
     * this methode return all the users in a chat-room
     * @param chatRoom the chat-room
     * @return an {@link Iterable} of {@link User} objects.
     */
    public Iterable<User> getAllUsers(ChatRoom chatRoom) {
        return chatRoom.getUsers();
    }

    /**
     * return chat-room by id, if not found return null
     * @param chatRoomId the id of the chat-room
     * @return the chat-room
     */
    public ChatRoom getChatRoomById(Integer chatRoomId) {
        return chatRoomRepository.findById(chatRoomId).orElse(null);
    }

    /**
     * return list of users are not in any chat-room with specific user
     * @param user the user
     * @return an {@link Iterable} of {@link User} objects.
     */
    @Transactional
    public Set<User> getAllUsersNotInChatRoomWithUserId(User user) {
        Set<User> users = convertIterableToSet(userService.getAllUsers());
        users.remove(user);

        Set<ChatRoom> chatRooms = user.getChatRooms();

        if (chatRooms != null) {
            Set<User> usersInChatRoom = new HashSet<>();
            chatRooms.forEach(chatRoom -> usersInChatRoom.addAll(chatRoom.getUsers()));
            users.removeAll(usersInChatRoom);
        }

        return users;
    }

    /**
     * return list of users are in any chat-room with specific user
     * @param user the user
     * @return an {@link Iterable} of {@link User} objects.
     */
    @Transactional
    public Set<User> getAllUsersInChatRoomWithUserId(User user) {
        Set<User> usersInChatRoomWithUser = new HashSet<>();
        Set<ChatRoom> chatRooms = user.getChatRooms();

        if (chatRooms != null) {
            chatRooms.forEach(chatRoom -> usersInChatRoomWithUser.addAll(chatRoom.getUsers()));
        }

        usersInChatRoomWithUser.remove(user);
        return usersInChatRoomWithUser;
    }

    /**
     * return list of users are not in specific chat-room
     * @param chatRoomId the chat-room
     * @return an {@link Iterable} of {@link User} objects.
     */
    @Transactional
    public Set<User> getAllUsersNotInChatRoomWithChatRoomId(Integer chatRoomId) {
        ChatRoom chatRoom = getChatRoomById(chatRoomId);
        Set<User> usersInChatRoom = chatRoom.getUsers();
        Set<User> allUsers = convertIterableToSet(userService.getAllUsers());
        allUsers.removeAll(usersInChatRoom);
        return allUsers;
    }

    //==========================================================================
    //============================== CREATE ====================================
    //==========================================================================

    /**
     * this methode create a new chat-room
     * @param chatRoom the chat-room
     * @return the chat-room
     */
    @Transactional
    public ChatRoom createChatRoom(ChatRoomRequest chatRoom) {
        ChatRoom newChatRoom = new ChatRoom();
        newChatRoom.setName(chatRoom.name);
        return chatRoomRepository.save(newChatRoom);
    }

    /**
     * this methode add a user to a chat-room
     * @param chatRoomId the id of the chat-room
     * @param userId the id of the user
     * @return the chat-room
     */
    @Transactional
    public ChatRoom addUserToChatRoom(Integer chatRoomId, Integer userId) {
        User user = userService.getUserById(userId);
        ChatRoom chatRoom = getChatRoomById(chatRoomId);
        return userService.addChatRoomToUser(user, chatRoom);
    }


    //==========================================================================
    //============================== DELETE ====================================
    //==========================================================================

    /**
     * delete all chat-rooms
     */
    @Transactional
    public void deleteAllChatRooms() {
        List<ChatRoom> chatRooms = chatRoomRepository.findAll();
        chatRooms.forEach(chatRoom -> deleteChatRoomById(chatRoom.getId()));
    }


    /**
     * delete a user from a chat-room
     * @param chatRoomId the id of the chat-room
     * @param userId the id of the user
     */
    @Transactional
    public void deleteUserFromChatRoom(Integer chatRoomId, Integer userId) {
        User user = userService.getUserById(userId);
        ChatRoom chatRoom = getChatRoomById(chatRoomId);
        userService.removeChatRoomFromUser(user, chatRoom);
    }

    /**
     * delete a chat-room by id
     * @param chatRoomId the id of the chat-room
     */
    @Transactional
    public void deleteChatRoomById(Integer chatRoomId) {
        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "ChatRoom not found"));

        List<Integer> userIds = chatRoom.getUsers().stream().map(User::getId).toList();
        userIds.forEach(userId -> deleteUserFromChatRoom(chatRoomId, userId));
        messageRepository.deleteByChatRoomId(chatRoomId);
        chatRoomRepository.deleteById(chatRoomId);
    }

    //==========================================================================
    //============================== HELPERS ====================================
    //==========================================================================

    private Set<User> convertIterableToSet(Iterable<User> usersIterable) {
        return StreamSupport.stream(usersIterable.spliterator(), false)
                .collect(Collectors.toSet());
    }

}
