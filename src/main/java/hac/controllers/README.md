# Controller package:

## Description

This package contains the controllers of the application. The controllers are responsible for handling the requests and
responses of the application. The controllers are the entry point of the application. The controllers are responsible
for calling the services and returning the responses to the client.

## Classes

### `AuthenticationController`

### `ChatRoomController`

### `FragmentController`

### `GlobalExceptionHandler`

### `MainController`

## The `AuthenticationController` class

This class is responsible for handling the authentication requests.
The class is responsible for calling the `AuthenticationService` 
class and returning the responses to the client.

this class provides the following options for the client:
- `GET` `/login` - the login page using thymeleaf.
- `POST` `/login` - the login request.
- `GET` `/register` - the register page using thymeleaf.
- `POST` `/register` - the register request using `AuthenticationService`
class to save the user, encrypt the password.


## The `ChatRoomController` class

This class is responsible for handling the chat room requests.
this class provides the following options for the client:


- `GET` `/chatroom` - the chat room page using thymeleaf.

## The `FragmentController` class

This class is responsible for handling the fragment requests.
this class provides the following options for the client:

- `GET` `/api/fragments/chatroom/person-menu` - get all the users in the chat room as person-menu template.
- `GET` `/api/fragments/chatroom/person-menu/{userId}` - get the user with the given id as person-menu template.
- `GET` `/api/fragments/chatroom/person-menu/filter/not-friends/{userId}` - get all the users that are not friends with the given user id as person-menu template.
- `GET` `/api/fragments/chatroom/person-menu/filter/friends/{userId}` - get all the users that are friends with the given user id as person-menu template.
- `GET` `/api/fragments/chatroom/person-menu/filter/person-menu/filter/not-friends/chatroom/{chatRoomId}` - get all the users that are not friends with the given user id and are in the given chat room as person-menu template.
- `GET` `/api/fragments/chatroom/{userId}` - get the chat room with the given id as chat-room template.
- `GET` `/api/fragments/chatroom/messages/{chatRoomId}` - get all the messages of the given chat room id as messages template.


## The `GlobalExceptionHandler` class

This class is responsible for handling the exceptions of the application.

## The `MainController` class

This class is responsible for handling the main requests.
this class provides the following options for the client:

- `GET` `/` - the home page using thymeleaf.
- `GET` `/admin` - the admin page using thymeleaf.
- `GET` `/accessDenied` - the access denied page using thymeleaf.


