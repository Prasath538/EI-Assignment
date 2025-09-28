# Chatting Application

This is a multi-user chat application built in Java, consisting of a server and a client. It allows users to connect to a chat server, join or create chat rooms, send public messages to the room, and exchange private messages with other users. The application supports multiple concurrent users and maintains a history of messages in each room.

## Features

- **User Authentication**: Users connect and provide a username to start chatting.
- **Chat Rooms**: Users can create new chat rooms or join existing ones. Each room is identified by a unique ID.
- **Public Messaging**: Send messages to all users in the current room.
- **Private Messaging**: Send direct messages to specific users in the same room.
- **Message History**: Each room keeps a history of recent messages, which is sent to new users when they join.
- **Room Management**: List available rooms and active users in the current room.
- **Commands**: A set of commands for navigation and actions, such as /join, /create, /rooms, /users, /pm, /help, /quit.

## Design Patterns Used

This application incorporates the following design patterns:

- **Singleton Pattern**: The `ChatRoomManager` class uses the singleton pattern to ensure a single instance manages all chat rooms across the application.
- **Adapter Pattern**: The `ProtocolAdapter` interface and `SocketAdapter` class adapt socket communication to a simple line-based protocol.
- **Observer Pattern**: (Implicitly through room participants) Users in a room are notified of new messages and events.

## How to Run

1. Ensure you have Java installed on your system.
2. Navigate to the directory containing the Java files.
3. Compile the application:
   ```
   javac *.java
   ```
4. Start the server in one terminal:
   ```
   java Server
   ```
   The server will start listening on port 12345.
5. Start one or more clients in separate terminals:
   ```
   java Client
   ```
   Each client will connect to the server at localhost:12345.

## Usage

### Server
The server runs continuously, accepting client connections and managing chat rooms. It uses a thread pool to handle multiple clients concurrently. The server can be stopped with Ctrl+C, which triggers a shutdown hook for graceful termination.

### Client
When you start the client, it connects to the server and prompts for a username. After entering a valid username, you can use the following commands:

- **/create <roomId>**: Create a new chat room with the given ID.
- **/join <roomId>**: Join an existing chat room.
- **/rooms**: List all available chat rooms.
- **/users**: List active users in the current room.
- **/pm <user> <message>**: Send a private message to a specific user.
- **/help**: Display the list of available commands.
- **/quit**: Disconnect from the server.

Once in a room, any text you type (not starting with /) will be sent as a public message to all users in the room.

### Example Session
1. Start the server.
2. Start a client, enter username "Alice".
3. Type "/create myroom" to create a room.
4. Start another client, enter username "Bob".
5. Type "/join myroom" to join the room.
6. Alice and Bob can now chat by typing messages.
7. Alice can send "/pm Bob Hello privately" for a private message.

## Notes

- The server uses a cached thread pool for handling client connections.
- Message history is limited to the most recent 200 messages per room.
- Private messages are indicated with [PM] prefix.
- The application uses Java's built-in logging for server-side information.
- Connections are handled gracefully, with automatic cleanup on disconnection.
- The client uses a daemon thread for reading incoming messages, allowing the main thread to handle user input.

This application serves as an example of building a concurrent, multi-user network application in Java using sockets and threads.
