package hac.controllers.Api;

import hac.Models.Response.UserResponse;
import hac.Models.User.User;
import hac.Services.UserService;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.stream.Collectors;
import java.util.logging.Logger;
import java.util.Collections;
import java.util.stream.StreamSupport;


/**
 * <h1>UsersApiController</h1>
 * <p>
 *     API class that provides the endpoints for the users.
 * </p>
 * <p>
 *      <b>Note:</b> The controller is mapped to <code>/api/users</code>.
 * </p>
 * @see hac.Models.User.User the User model.
 */
@RestController
@RequestMapping("/api/users")
public class UsersApiController {


    @Resource(name = "userService")
    private UserService userService;

    //==========================================================================
    //=============================== GET ======================================
    //==========================================================================

    /**
     * This method is responsible for returning all the users in the database.
     * @return all the users in the database.
     */
    @GetMapping("")
    public Iterable<UserResponse> getAllUsers() {
        return StreamSupport.stream(userService.getAllUsers().spliterator(), false)
                .map(UserResponse::getFromUser)
                .collect(Collectors.toList());
    }

    /**
     * get user by email
     * @param email email of the user
     * @return user with the given email
     */
    @GetMapping("/email/{email}")
    public UserResponse getUserByEmail(@PathVariable String email) {
        return UserResponse.getFromUser(userService.getUserByEmail(email));
    }

    /**
     * get user by id
     * @param userId id of the user
     * @return user with the given id
     */
    @GetMapping("/id/{userId}")
    public UserResponse getUserById(@PathVariable Integer userId) {
        return UserResponse.getFromUser(userService.getUserById(userId));
    }


    //==========================================================================
    //============================== DELETE ====================================
    //==========================================================================

    /**
     * This method is responsible for deleting all the users in the database.
     * only the admin can do this.
     * @return ResponseEntity with the message "All users deleted".
     */
    @DeleteMapping("")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<String> deleteAllUsers() {
        userService.deleteAllUsers();
        return ResponseEntity.ok("All users deleted");
    }

    /**
     * This method is responsible for deleting the user with the given email.
     * only the admin can do this.
     * @param email email of the user to be deleted.
     * @return ResponseEntity with the message "User with email: {user email} deleted".
     */
    @DeleteMapping("/email/{email}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<String> deleteUserByEmail(@PathVariable String email) {
        userService.deleteUserByEmail(email);
        return ResponseEntity.ok("User with email: " + email + " deleted");
    }

    /**
     * This method is responsible for deleting the user with the given id.
     * only the admin can do this.
     * @param userId id of the user to be deleted.
     * @return ResponseEntity with the message "User with id: {user id} deleted".
     */
    @DeleteMapping("/id/{userId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<String> deleteUserById(@PathVariable Integer userId) {
        userService.deleteUserById(userId);
        return ResponseEntity.ok("User with id: " + userId + " deleted");
    }

    /**
     * handle the all the exceptions that can occur.
     * @param e exception
     * @return error message
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception e) {
        return ResponseEntity.badRequest().body("Error: " + e.getMessage());
    }
}
