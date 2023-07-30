package hac.Services;

import hac.Models.Message;
import hac.Models.User.User;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

/**
 * <h1>FragmentService</h1>
 * <p>
 *     This class is used to map data to fragments.
 *     Fragments are reusable parts of html code.
 * </p>
 */
@Service
public class FragmentService {

    @Resource(name = "chatRoomService")
    private ChatRoomService chatRoomService;

    @Resource(name = "templateEngine")
    private TemplateEngine templateEngine;



    //==========================================================================
    //============================ person-menu =================================
    //==========================================================================

    /**
     * This method maps a user to a person-menu fragment.
     * @param users The users to map.
     * @return The mapped data.
     */
    public String mapUsersToPersonMenu(Iterable<User> users) {
        StringBuffer data = new StringBuffer();
        Context context = new Context();

        users.forEach(user -> {
            context.setVariable("person_name", user.getFirstname() + " " + user.getLastname());
            context.setVariable("email", user.getEmail());
            data.append(templateEngine.process("fragments/person-menu", context));
        });
        return data.toString();
    }


    /**
     * This method maps a user to a chat-room-menu fragment.
     * @param user The user to map.
     * @return The mapped data.
     */
    public String mapAllUserChatRooms(User user){
        StringBuffer data = new StringBuffer();
        Context context = new Context();

        var chatRooms = chatRoomService.getAllChatRoomsByUserId(user.getId());
        chatRooms.forEach(chatRoom -> {
            context.setVariable("chat_name", chatRoom.getName());
            context.setVariable("chat_id", chatRoom.getId());
            data.append(templateEngine.process("fragments/chat-room-menu", context));
        });
        return data.toString();
    }

}
