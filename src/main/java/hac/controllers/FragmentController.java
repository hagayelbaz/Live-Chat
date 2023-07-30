package hac.controllers;


import hac.Models.User.User;
import hac.Services.ChatRoomService;
import hac.Services.FragmentService;
import hac.Services.MessagesService;
import hac.Services.UserService;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;

/**
 * <h1>FragmentController</h1>
 * <p>
 *     This class is responsible for handling all the requests that are related
 *     to the fragments.
 * </p>
 * <p>
 *     This class is annotated with @RestController which means that all the
 *     methods in this class will return a string that contains the html code
 *     of the fragment.
 * </p>
 */
@RestController
@RequestMapping("/api/fragments")
public class FragmentController {
    @Resource(name = "fragmentService")
    private FragmentService fragmentService;

    @Resource(name = "userService")
    private UserService userService;

    @Resource(name = "chatRoomService")
    private ChatRoomService chatRoomService;




    //==========================================================================
    //============================ person-menu =================================
    //==========================================================================

    /**
     * This method is responsible for getting all the users in the database and
     * mapping them to the person-menu fragment.
     * @return a string that contains the html code of the person-menu fragment.
     */
    @GetMapping("/person-menu")
    public String getAllPersonMenu() {
        var users = userService.getAllUsers();
        return fragmentService.mapUsersToPersonMenu(users);
    }

    /**
     * This method is responsible for getting a user by his id and mapping him
     * to the person-menu fragment.
     * @param userId the id of the user.
     * @return a string that contains the html code of the person-menu fragment.
     */
    @GetMapping("/person-menu/{userId}")
    public String getPersonMenuById(@PathVariable Integer userId) {
        var user = userService.getUserById(userId);
        Set<User> userSet = new HashSet<>();
        userSet.add(user);
        return fragmentService.mapUsersToPersonMenu(userSet);
    }

    /**
     * This method is responsible for getting all the users in the database that
     * are not friends with the user with the given id and mapping them to the
     * person-menu fragment.
     * @param userId the id of the user.
     * @return a string that contains the html code of the person-menu fragment.
     */
    @GetMapping("/person-menu/filter/not-friends/{userId}")
    public String getAllPersonMenuNotInChatRoomWithUserId(@PathVariable Integer userId){
        var user = userService.getUserById(userId);
        var users = chatRoomService.getAllUsersNotInChatRoomWithUserId(user);
        return fragmentService.mapUsersToPersonMenu(users);
    }

    /**
     * This method is responsible for getting all the users in the database that
     * are not in the chat-room with the given id and mapping them to the
     * person-menu fragment.
     * @param chatRoomId the id of the chat-room.
     * @return a string that contains the html code of the person-menu fragment.
     */
    @GetMapping("/person-menu/filter/not-friends/chatroom/{chatRoomId}")
    public String getAllUsersNotInChatRoomWithChatRoomId(@PathVariable Integer chatRoomId){
        var users = chatRoomService.getAllUsersNotInChatRoomWithChatRoomId(chatRoomId);
        return fragmentService.mapUsersToPersonMenu(users);
    }

    /**
     * This method is responsible for getting all the users in the database that
     * are friends with the user with the given id and mapping them to the
     * person-menu fragment.
     * @param userId the id of the user.
     * @return a string that contains the html code of the person-menu fragment.
     */
    @GetMapping("/person-menu/filter/friends/{userId}")
    public String getAllPersonMenuInChatRoomWithUserId(@PathVariable Integer userId){
        var user = userService.getUserById(userId);
        var users = chatRoomService.getAllUsersInChatRoomWithUserId(user);
        return fragmentService.mapUsersToPersonMenu(users);
    }

    //==========================================================================
    //============================ chat-room ===================================
    //==========================================================================

    /**
     * This method is responsible for getting all the chat-rooms in the database
     * and mapping them to the chat-room fragment.
     * @param userId the id of the user.
     * @return a string that contains the html code of the chat-room fragment.
     */
    @GetMapping("/chat-room/{userId}")
    public String getAllChatRoomByUserId(@PathVariable Integer userId) {
        var user = userService.getUserById(userId);
        return fragmentService.mapAllUserChatRooms(user);
    }


    /**
     * handles all the exceptions that are thrown by the controller.
     * @param e the exception that is thrown.
     * @return a string that contains the message of the exception.
     */
    @ExceptionHandler(Exception.class)
    public String handleException(Exception e) {
        return e.getMessage();
    }
}
