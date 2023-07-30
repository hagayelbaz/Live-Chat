# Api package:

## classes:

- [ ] [ChatRoomApiController](ChatRoomApiController.java)
- [ ] [MessagesApiController](MessagesApiController.java)
- [ ] [UsersApiController](UsersApiController.java)

## ChatRoomApiController:
this class handle the api for the chat room.

### the endpoints:

### GET
- [ ] [GET] /api/chatroom - get all the chat rooms objects.
- [ ] [GET] /api/chatroom/{id} - get the chat room by id.
- [ ] [GET] /api/chatroom/user/{userId} - get all the chat rooms that user is in.
- [ ] [GET] /api/chatroom/filter/not-friends/{userId} - get all the users that no in any chat room with the user.
- [ ] [GET] /api/chatroom/filter/friends/{userId} - get all the users that in any chat room with the user.

### POST
- [ ] [POST] /api/chatroom - create new chat room with `ChatRoomRequest` object.
- [ ] [POST] /api/chatroom/{chatRoomId}/user/{userId} - add user to chat room.
- [ ] [POST] /api/chatroom//create-add - create new chat room and add user to it with `ChatRoomRequest` object.

### DELETE
- [ ] [DELETE] /api/chatroom - delete chat room by id.
- [ ] [DELETE] /api/chatroom/{chatRoomId}/user/{userId} - remove user from chat room.
- [ ] [DELETE] /api/chatroom/{chatRoomId} - delete chat room by id.


## MessagesApiController:
this class handle the api for the messages.

### the endpoints:

### GET
- [ ] [GET] /api/messages - get all the messages objects.
- [ ] [GET] /chatroom/{chatRoomId} - get all the messages in the chat room.
- [ ] [GET] /api/messages//chatroom/{chatRoomId}/last - get the last message in the chat room.

### POST
- [ ] [POST] /api/messages - create new message with `MessageRequest` object.

### DELETE
- [ ] [DELETE] /api/messages/{messageId} - delete message by id.
- [ ] [DELETE] /api/messages/chatroom/{chatRoomId} - delete all the messages in the chat room.


## UsersApiController:
this class handle the api for the users.
there is no! create option, this handle by the `AuthController`.

### the endpoints:

### GET
- [ ] [GET] /api/users - get all the users objects.
- [ ] [GET] /api/users/email/{email} - get the user by email.
- [ ] [GET] /api/users/id/{id} - get the user by id.

### DELETE
- [ ] [DELETE] /api/users - delete all the users.
- [ ] [DELETE] /api/users/email/{email} - delete user by email.
- [ ] [DELETE] /api/users/id/{id} - delete user by id.











