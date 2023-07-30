package hac.Models.Repositories;

import hac.Models.User.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * <h1>UserRepository</h1>
 * <p>
 *     This interface is used to access the database and perform CRUD operations on the User table
 *     It extends JpaRepository which provides the basic CRUD operations for the User table.
 * </p>
 * @see JpaRepository
 * @see User
 */
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);//maybe findUserByEmail
    void deleteByEmail(String email);

}