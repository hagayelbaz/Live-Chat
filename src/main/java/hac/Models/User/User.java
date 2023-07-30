package hac.Models.User;

import com.fasterxml.jackson.annotation.JsonBackReference;
import hac.Models.ChatRoom;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import org.hibernate.Hibernate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

/**
 * <h1>User</h1>
 * <p>
 *     This class is used to represent a user in the database.
 *     It is used to store the user's information and the chat rooms they are in.
 *     It also implements the UserDetails interface to be used by Spring Security.
 *     The user's role is used to determine what they can do in the application.
 *     there is relation between user and chat room many to many
 *     user can be in many chat rooms and chat room can have many users.
 * </p>
 * <p>
 *     <b>Note:</b> the user's email is used as their username.
 *     <b>Note:</b> the user's role is set to USER by default.
 * </p>
 * <p>
 *     this class related to the following tables:
 *     <ul>
 *         <li>users</li>
 *         <li>user_chat_room</li>
 *     </ul>
 *
 *     so we have many to many relation between user and chat room.
 * </p>
 * <p>
 *     also this class connected to the following classes:
 *     <ul>
 *         <li>{@link hac.Services.UserService} provides the business logic for the user.</li>
 *         <li>{@link hac.Models.Repositories.UserRepository} JPA for database calls. </li>
 *         <li>{@link hac.Models.Response.UserResponse} class for what we want to give to the user. </li>
 *         <li>{@link hac.Models.Requests.UserRequest} the request we need from the user.</li>
 *     </ul>
 * </p>
 *
 * @see hac.Services.UserService
 * @see hac.Models.Repositories.UserRepository
 * @see hac.Models.Response.UserResponse
 * @see hac.Models.Requests.UserRequest
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "Please enter your name")
    private String firstname;

    @NotEmpty(message = "Please enter your name")
    private String lastname;

    @Email(message = "Please enter a valid user name")
    @NotEmpty(message = "Please enter your user name")
    @Column(unique = true)
    private String email;

    @NotEmpty(message = "Please enter your password")
    private String password;


    @Enumerated(EnumType.STRING)
    private Role role = Role.USER;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "user_chat_room",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "chat_room_id"))
    @JsonBackReference
    private Set<ChatRoom> chatRooms = new HashSet<>();

    /**
     * add user to chat room (or add chat room to user - same)
     * @param chatRoom chat room to be added
     */
    public void addChatRoom(ChatRoom chatRoom) {
        this.chatRooms.add(chatRoom);
        chatRoom.getUsers().add(this);
    }

    /**
     * remove user from chat room (or remove chat room from user - same)
     * @param chatRoom chat room to be removed
     */
    public void removeChatRoom(ChatRoom chatRoom) {
        this.chatRooms.remove(chatRoom);
        chatRoom.getUsers().remove(this);
    }

    /**
     * get user's full name
     * @return user's full name
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        User user = (User) o;
        return getId() != null && Objects.equals(getId(), user.getId());
    }

    /**
     * get user's hash code
     * @return user's hash code
     */
    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    /**
     * get user's authorities
     * @return user's authorities
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role == Role.ADMIN ? "ROLE_ADMIN" : "ROLE_USER"));
    }

    /**
     * <b> this related the email to user name </b>
     * get user's username (email)
     * @return user's username (email)
     */
    @Override
    public String getUsername() {
        return this.email;
    }

    /**
     * check if user's account is <b>Not</b> expired (always true, came from interface)
     * @return true
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * check if user's account is <b>Not</b> locked (always true, came from interface)
     * @return true
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * check if user's credentials are <b>Not</b> expired (always true, came from interface)
     * @return true
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * check if user is enabled (always true, came from interface)
     * @return true
     */
    @Override
    public boolean isEnabled() {
        return true;
    }
}