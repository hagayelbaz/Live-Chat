package hac.controllers;

import hac.Models.User.User;
import hac.Services.AuthenticationService;
import hac.controllers.Exception.UserAlreadyExistsException;
import jakarta.annotation.Resource;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.Principal;
import java.util.logging.Logger;

/**
 * <h1>AuthenticationController</h1>
 * <p>
 *     This class is responsible for the authentication of the user
 *     It handles the registration and the login of the user.
 * </p>
 * <p>
 *     check the user registration and login,.
 * </p>
 * @see hac.Services.AuthenticationService how it's done.
 */
@Controller
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    @Resource(name = "authenticationService")
    private AuthenticationService authenticationService;

    //====================================================================================================
    //============================== This is the register part of the code ================================
    //====================================================================================================

    /**
     * This method is responsible for the registration of the user
     * @param user the user that is going to be registered
     * @return the login page
     */
    @PostMapping("/register")
    public String register(@ModelAttribute User user) {
        try {
            authenticationService.register(user);
        } catch(Exception e) {
            throw new UserAlreadyExistsException(e.getMessage());
        }
        return "redirect:/auth/login";
    }


    /**
     * This method is responsible for the registration of the user
     * @param model the model that is going to be used
     * @return the register page
     */
    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }


    //====================================================================================================
    //============================== This is the login part of the code ==================================
    //====================================================================================================

    /**
     * This method is responsible for the login of the user
     * @param principal the principal of the user
     * @param model the model that is going to be used
     * @return the login page
     */
    @GetMapping("/login")
    public String login(Principal principal, Model model) {
        if(principal != null)
            return "redirect:/chat-room";

        model.addAttribute("user", new User());
        return "login";
    }

    /**
     * This method is responsible for the login of the user
     * @param user the user that is going to be logged in
     * @return the chat room page
     */
    @PostMapping("/login")
    public String login(@Payload User user) {
        try{
            authenticationService.authenticate(user);
        } catch (Exception e) {
            return "redirect:/auth/login?error=" + e.getMessage();
        }

        return "redirect:/chat-room";
    }

    /**
     * handles the exception
     * @param e the exception that is going to be handled
     * @return the login page
     */
    @ExceptionHandler({Exception.class})
    public String handleException(Exception e){
        return "redirect:/auth/login?error=Some Error Happened";
    }

    /**
     * handle specific exception for user already exists
     * @param e the exception that is going to be handled
     * @return the register page
     */
    @ExceptionHandler({UserAlreadyExistsException.class})
    public String handleUserAlreadyExistsException(UserAlreadyExistsException e){
        return "redirect:/auth/register?error=User already exists";
    }
}
