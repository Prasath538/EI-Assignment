import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserSession implements Runnable {
    private final ProtocolAdapter adapter;
    private final ChatRoomManager manager = ChatRoomManager.getInstance();
    private final Logger logger = Logger.getLogger("ChatApp");
    private final AtomicBoolean running = new AtomicBoolean(true);
    private String userName;
    private ChatRoom currentRoom;

    public UserSession(ProtocolAdapter adapter) {
        this.adapter = adapter;
    }

    public String getUserName() {
        return userName;
    }

    public void sendRaw(String line) {
        try {
            adapter.sendLine(line);
        } catch (IOException e) {
            logger.log(Level.WARNING, "Failed to send to " + userName, e);
            shutdown();
        }
    }

    private void shutdown() {
        running.set(false);
        try {
            if (currentRoom != null) {
                currentRoom.leave(this);
            }
            adapter.close();
        } catch (Exception e) {
            logger.log(Level.FINER, "Error during shutdown", e);
        }
    }

    @Override
    public void run() {
        try {
            adapter.sendLine("Welcome. Enter username:")
            ;
            String name = adapter.readLine();
            if (name == null || name.trim().isEmpty()) {
                adapter.sendLine("Invalid username. Connection closing.");
                shutdown();
                return;
            }
            userName = name.trim();
            adapter.sendLine("Type /create <roomId> or /join <roomId>. Type /help for commands.");
            while (running.get()) {
                String line;
                try {
                    line = adapter.readLine();
                } catch (IOException e) {
                    break;
                }
                if (line == null) {
                    break;
                }
                String trimmed = line.trim();
                if (trimmed.isEmpty()) {
                    continue;
                }
                if (trimmed.startsWith("/create ")) {
    String[] parts = trimmed.split("\\s+", 2);
    if (parts.length < 2) {
        adapter.sendLine("Usage: /create <roomId>");
        continue;
    }
    String roomId = parts[1].trim();
    try {
        ChatRoom newRoom = manager.create(roomId);
        joinRoom(newRoom);
    } catch (IllegalStateException e) {
        adapter.sendLine("Room " + roomId + " already exists");
    }
    continue;
}

if (trimmed.startsWith("/join ")) {
    String[] parts = trimmed.split("\\s+", 2);
    if (parts.length < 2) {
        adapter.sendLine("Usage: /join <roomId>");
        continue;
    }
    String roomId = parts[1].trim();
    ChatRoom existing = manager.get(roomId);
    if (existing == null) {
        adapter.sendLine("Room " + roomId + " does not exist. Use /create to create it.");
    } else {
        joinRoom(existing);
    }
    continue;
}

                if (trimmed.equals("/rooms")) {
                    adapter.sendLine("Rooms: " + String.join(", ", manager.roomIds()));
                    continue;
                }
                if (trimmed.equals("/users")) {
                    if (currentRoom == null) {
                        adapter.sendLine("Not in any room");
                    } else {
                        adapter.sendLine("Active users: " + currentRoom.activeUsers());
                    }
                    continue;
                }
                if (trimmed.startsWith("/pm ")) {
                    if (currentRoom == null) {
                        adapter.sendLine("Join a room first");
                        continue;
                    }
                    String[] parts = trimmed.split("\\s+", 3);
                    if (parts.length < 3) {
                        adapter.sendLine("Usage: /pm <user> <message>");
                        continue;
                    }
                    String target = parts[1].trim();
                    String body = parts[2].trim();
                    Message m = new Message(userName, body, true, target);
                    currentRoom.addMessage(m);
                    continue;
                }
                if (trimmed.equals("/help")) {
                    adapter.sendLine("/create <roomId>");
                    adapter.sendLine("/join <roomId>");
                    adapter.sendLine("/rooms");
                    adapter.sendLine("/users");
                    adapter.sendLine("/pm <user> <message>");
                    adapter.sendLine("/quit");
                    continue;
                }
                if (trimmed.equals("/quit")) {
                    adapter.sendLine("Goodbye");
                    break;
                }
                if (currentRoom == null) {
                    adapter.sendLine("Join a room first with /join or /create");
                    continue;
                }
                Message m = new Message(userName, trimmed);
                currentRoom.addMessage(m);
            }
        } catch (IOException e) {
            logger.log(Level.INFO, "IO error in user session", e);
        } finally {
            shutdown();
        }
    }

    private void joinRoom(ChatRoom room) {
        if (room == null) {
            return;
        }
        if (currentRoom != null && currentRoom.getId().equals(room.getId())) {
            try {
                adapter.sendLine("Already in room " + room.getId());
            } catch (IOException e) {
            }
            return;
        }
        if (currentRoom != null) {
            currentRoom.leave(this);
        }
        currentRoom = room;
        currentRoom.join(this);
        try {
            adapter.sendLine("Joined room " + room.getId());
        } catch (IOException e) {
        }
    }
}
