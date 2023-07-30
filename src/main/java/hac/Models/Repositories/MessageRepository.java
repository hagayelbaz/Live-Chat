package hac.Models.Repositories;

import hac.Models.Message;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * <h1>MessageRepository</h1>
 * <p>
 *     This interface is used to access the database and perform CRUD operations on the Message table
 *     It extends JpaRepository which provides the basic CRUD operations for the Message table.
 * </p>
 * @see JpaRepository
 * @see Message
 */
public interface MessageRepository extends JpaRepository<Message, Integer>{
    Iterable<Message> findByChatRoomId(Integer chatRoomId);
    void deleteByChatRoomId(Integer chatRoomId);
    Iterable<Message> findByUserIdAndChatRoomId(Integer userId, Integer chatRoomId);
    void deleteByUserIdAndChatRoomId(Integer userId, Integer chatRoomId);
    public Message findFirstByChatRoomIdOrderByTimestampDesc(Integer chatRoomId);

    void deleteAllByUser_Id(Integer userId);
    void deleteAllByUser_IdAndChatRoom_Id(Integer userId, Integer chatRoomId);
}
