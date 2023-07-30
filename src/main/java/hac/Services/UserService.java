package hac.Services;

import hac.Classes.Utils;
import hac.Models.ChatRoom;
import hac.Models.Repositories.ChatRoomRepository;
import hac.Models.Repositories.MessageRepository;
import hac.Models.Repositories.UserRepository;
import hac.Models.Response.UserResponse;
import hac.Models.User.Role;
import hac.Models.User.User;
import jakarta.annotation.Resource;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.beans.Transient;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <h1>User Service</h1>
 * <p>
 *     This class is responsible for handling all the logic related to the User model.
 *     It is used by the {@link hac.controllers.Api.UsersApiController} class.
 * </p>
 *
 * @see hac.Models.Repositories.UserRepository
 */
@Service
public class UserService {

    @Resource(name = "userRepository")
    private UserRepository userRepository;

    @Resource(name = "chatRoomRepository")
    private ChatRoomRepository chatRoomRepository;

    @Resource(name = "messageRepository")
    private MessageRepository messageRepository;


    //==========================================================================
    //=============================== GET ======================================
    //==========================================================================

    /**
     * This method returns all the users in the database.
     * @return An {@link Iterable} of {@link User} objects.
     */
    public Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }

    /**
     * This method returns all the users in the database except the one with the given email.
     * @param email The id of the user to be excluded.
     * @return the user.
     */
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

    /**
     * This method returns all the users in the database except the one with the given id.
     * @param userId The id of the user to be excluded.
     * @return the User.
     */
    public User getUserById(Integer userId) {
        return userRepository.findById(userId).orElse(null);
    }

    //==========================================================================
    //============================== CREATE ====================================
    //==========================================================================

    /**
     * This method creates a new user in the database.
     * @param user The user to be created.
     * @return The created user.
     */
    public User save(User user) {
        return userRepository.save(user);
    }

    /**
     * this methode for add user to chat room.
     * @param user the user to be added.
     * @param chatRoom the chat room to be added to.
     * @return the chat room after adding the user.
     */
    @Transactional
    public ChatRoom addChatRoomToUser(User user, ChatRoom chatRoom) {
        user.addChatRoom(chatRoom);
        return chatRoomRepository.save(chatRoom);
    }

    //==========================================================================
    //============================== DELETE ====================================
    //==========================================================================

    /**
     * this methode for removing the user from chat room.
     * @param user the user to be removed.
     * @param chatRoom the chat room to be removed from.
     */
    @Transactional
    public void removeChatRoomFromUser(User user, ChatRoom chatRoom) {
        user.removeChatRoom(chatRoom);
        chatRoomRepository.save(chatRoom);
    }

    /**
     * this methode for deleting all users.
     */
    @Transactional
    public void deleteAllUsers() {
        List<User> users = userRepository.findAll();
        users.forEach(user -> deleteUserById(user.getId()));
    }


    /**
     * this methode for deleting user by email.
     * @param email the email of the user to be deleted.
     */
    @Transactional
    public void deleteUserByEmail(String email) {
        User user = getUserByEmail(email);
        if(isAdmin(user))
            return;
        deleteAssociated(user);
        userRepository.deleteByEmail(email);
    }

    /**
     * this methode for deleting admin by email.
     * it's a special case (when you turned off the app for example)
     * @param adminEmail the email of the admin to be deleted.
     */
    @Transactional
    public void deleteAdmin(String adminEmail) {
        User user = getUserByEmail(adminEmail);
        deleteAssociated(user);
        userRepository.deleteByEmail(adminEmail);
    }

    /**
     * this methode for deleting user by id.
     * @param userId the id of the user to be deleted.
     */
    @Transactional
    public void deleteUserById(Integer userId) {
        User user = getUserById(userId);
        if(isAdmin(user))
            return;

        deleteAssociated(user);
        userRepository.deleteById(userId);
    }

    //==========================================================================
    //============================== HELPER ====================================
    //==========================================================================

    /**
     * this methode make sure the associated data is deleted before deleting the user.
     * @param user the user to be deleted.
     */
    @Transactional
    public void deleteAssociated(User user) {
        Set<ChatRoom> chatRooms = new HashSet<>(user.getChatRooms());
        for(ChatRoom chatRoom : chatRooms) {
            removeChatRoomFromUser(user, chatRoom);
        }

        messageRepository.deleteAllByUser_Id(user.getId());
    }

    /**
     * check if the user is admin or not.
     * @param user the user to be checked.
     * @return Boolean if the user is admin or not.
     */
    private Boolean isAdmin(User user){
        return user.getRole() == Role.ADMIN;
    }

}
