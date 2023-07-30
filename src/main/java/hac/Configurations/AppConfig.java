package hac.Configurations;

import hac.Models.Repositories.UserRepository;
import hac.Models.User.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.logging.Logger;

import static hac.Classes.Utils.combineArrays;
import static org.springframework.security.config.Customizer.withDefaults;

/**
 * <b>AppConfig</b>
 * <p>
 *     This class is used to configure the application,
 *     using Spring boot 3, Spring Security, and Spring Data JPA.
 * </p>
 * <p>
 *     handle the pages that available for each role,
 *     User, Admin, and Guest.
 * </p>
 * @see hac.Models.User.Role User Role
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class AppConfig {
    private static final String[] ALLOWED_ALL = {"/css/**", "/images/**"
            , "/script/**", "/", "fragments/**", "/about", "/access-denied", "/error-page"};
    private static final String[] ALLOWED_GUEST = {"/auth/**"};
    private static final String[] ALLOWED_USER = {"/user/**"};
    private static final String[] ALLOWED_ADMIN = {"/admin/**"};
    private static final String[] ALLOWED_SHARED = {"/shared/**", "/chat-room", "/api/**", "/chat-room/**"};

    private final UserRepository userRepository;

    /**
     * CORS configuration for the application to allow requests from any origin
     * @param http request
     * @return CorsConfigurationSource object
     * @throws Exception if any error occurs
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        var _guest = combineArrays(ALLOWED_ALL, ALLOWED_GUEST);
        var _user = combineArrays(ALLOWED_USER);
        var _admin = combineArrays(ALLOWED_ADMIN);
        var _shared = combineArrays(ALLOWED_SHARED);

        http
                .cors(withDefaults())
                .csrf(withDefaults())
                .authorizeHttpRequests((request) -> request
                        .requestMatchers(_guest).permitAll()
                        .requestMatchers(_user).hasRole(Role.USER.name())
                        .requestMatchers(_admin).hasRole(Role.ADMIN.name())
                        .requestMatchers(_shared).hasAnyRole(Role.USER.name(), Role.ADMIN.name())
                        .anyRequest().authenticated()
                )
                .formLogin((form) -> form
                        .loginPage("/auth/login")
                        .usernameParameter("email").passwordParameter("password")
                        .defaultSuccessUrl("/chat-room", true)
                        .failureUrl("/auth/login?error=true").permitAll()
                )
                .logout((logout) -> logout
                        .logoutUrl("/auth/logout")
                        .logoutSuccessUrl("/")
                        .logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler())
                        .permitAll()
                );//NO NEED HANDLE EXCEPTIONS!!!
        //in thymeleaf the error pages is auto by names (403,404) in error folder

        return http.build();
    }


    /**
     * for use, the encoded password must be stored in the database
     * @return PasswordEncoder object
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * UserDetailsService bean for authentication and authorization
     * @return UserDetailsService
     */
    @Bean
    public UserDetailsService userDetailsService() {
        return (email) ->
                userRepository.findByEmail(email)
                        .orElseThrow(() -> new UsernameNotFoundException("User: \"" + email + "\" not found"));
    }

    /**
     * AuthenticationManager bean for authentication and authorization
     * @param configuration AuthenticationConfiguration
     * @return AuthenticationManager
     * @throws Exception if any error occurs
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    /**
     * AuthenticationProvider bean for authentication and authorization
     * @return AuthenticationProvider
     */
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    /**
     * ignore favicon.ico request
     * @return WebSecurityCustomizer
     */
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers("/favicon.ico");
    }
}
