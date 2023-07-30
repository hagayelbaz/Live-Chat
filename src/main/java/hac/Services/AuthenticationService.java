package hac.Services;

import hac.Models.User.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * <h1>AuthenticationService</h1>
 * <p>
 *     This class is used to authenticate a user.
 *     It uses the AuthenticationManager to authenticate a user.
 *     It also uses the PasswordEncoder to encode the password.
 * </p>
 * @see AuthenticationManager
 */
@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;


    /**
     * This method is used to register a user with encoded password.
     * @param user User object
     * @return User object
     */
    public User register(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userService.save(user);
    }

    /**
     * This method is used to authenticate a user.
     * @param user User object
     */
    public void authenticate(User user) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        user.getEmail(),
                        user.getPassword()
                ));
    }
}
