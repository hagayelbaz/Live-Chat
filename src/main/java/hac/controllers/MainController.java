package hac.controllers;

import hac.Models.User.User;
import hac.Services.UserService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

/**
 * <h1>MainController class</h1>
 * <p>
 *     This class is the main controller for the application.
 *     It handles the main page, the admin page, and the about page.
 *     It also handles exceptions.
 * </p>
 *
 * @see org.thymeleaf.Thymeleaf about Thymeleaf.
 */
@Controller
public class MainController {

    @Resource(name = "userService")
    private UserService userService;


    /**
     * This method handles the main page.
     * @param model The model for the page.
     * @return The index page.
     */
    @GetMapping("/")
    public String index(Model model){
        return "index";
    }

    /**
     * This method handles the admin page.
     * @param principal The principal of the user.
     * @param model The model for the page.
     * @return The admin page.
     */
    @GetMapping("/admin")
    public String admin(Principal principal, Model model){
        User admin = userService.getUserByEmail(principal.getName());
        model.addAttribute("admin", admin);
        return "admin/admin";
    }


    /**
     * This method handles the about page.
     * @return The about page.
     */
    @RequestMapping("/about")
    public String about(){
        return "about";
    }

    /**
     * This method handles exceptions.
     * @param e The exception.
     * @return The index page with an error message.
     */
    @ExceptionHandler(Exception.class)
    public String handleException(Exception e){
        return "redirect:/?error=" + e.getMessage();
    }
}
