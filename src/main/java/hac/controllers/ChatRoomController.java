package hac.controllers;

import hac.Models.Message;
import hac.Models.Response.UserResponse;
import hac.Models.User.User;
import hac.Services.UserService;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.logging.Logger;

/**
 * <h1>ChatRoomController</h1>
 * <p>
 *     This class is responsible for handling the chat room page
 * </p>
 * @see hac.Models.ChatRoom ChatRoom
 * @see hac.Services.ChatRoomService ChatRoomService
 */
@Controller
@RequestMapping("/chat-room")
public class ChatRoomController {
    @Resource(name = "userService")
    private UserService userService;


    /**
     * getChatRoom is a method that returns the chat room page
     * @param principal the principal of the user
     * @param model the model of the page
     * @return the chat room page
     */
    @GetMapping("")
    public String getChatRoom(Principal principal, Model model) {
        User user = userService.getUserByEmail(principal.getName());
        model.addAttribute("user", user);
        return "shared/chat-room";
    }

    /**
     * handle the exception
     * @param e the exception
     * @return redirect to home with error message
     */
    @ExceptionHandler({Exception.class})
    public String handleException(Exception e){
        return "redirect:/?error=Some Error Happened";
    }
}
