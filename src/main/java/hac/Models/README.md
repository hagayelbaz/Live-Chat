# Package Models:


## Description

This package contains the models of the application. The models are the entities of the application. 
The models are responsible for representing the data of the application. 

## Classes

### `User` - this class represents the user of the application.
### `ChatRoom` - this class represents the chat room of the application.
### `Message` - this class represents the message of the application.

## Interfaces
- `Role` - this interface represents the role of the user of the application.

## Repositories
the repositories are the interfaces that are used to access the data of the application.
- `UserRepository` - this interface represents the repository of the user of the application.
- `ChatRoomRepository` - this interface represents the repository of the chat room of the application.
- `MessageRepository` - this interface represents the repository of the message of the application.

## Request Models
the request models are the models that are used to receive data from the client side.

- `UserRequest` - this class represents the request model of the user of the application.
- `ChatRoomRequest` - this class represents the request model of the chat room of the application.
- `MessageRequest` - this class represents the request model of the message of the application.

## Response Models

the response models are the models that are used to send data to the client side.

- `UserResponse` - this class represents the response model of the user of the application.
- `ChatRoomResponse` - this class represents the response model of the chat room of the application.
- `MessageResponse` - this class represents the response model of the message of the application.