import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.Deque;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.stream.Collectors;
import java.util.logging.Logger;

public class ChatRoom {
    private final String id;
    private final Set<UserSession> participants = new CopyOnWriteArraySet<>();
    private final Deque<Message> history = new ConcurrentLinkedDeque<>();
    private final int maxHistory = 200;
    private final Logger logger = Logger.getLogger("ChatApp");

    public ChatRoom(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void join(UserSession user) {
        participants.add(user);
        sendHistoryTo(user);
        broadcastSystem(user.getUserName() + " joined the room");
        logger.info("User " + user.getUserName() + " joined " + id);
    }

    public void leave(UserSession user) {
        participants.remove(user);
        broadcastSystem(user.getUserName() + " left the room");
        logger.info("User " + user.getUserName() + " left " + id);
    }

    private void sendHistoryTo(UserSession user) {
        for (Message m : history) {
            user.sendRaw(m.formatted());
        }
    }

    public void addMessage(Message message) {
        history.addFirst(message);
        while (history.size() > maxHistory) {
            history.removeLast();
        }
        if (message.isPrivate()) {
            sendPrivate(message.getTargetUser(), message);
        } else {
            broadcast(message.formatted());
        }
    }

    public void broadcast(String text) {
        for (UserSession u : participants) {
            u.sendRaw(text);
        }
    }

    public void broadcastSystem(String text) {
        Message m = new Message("System", text);
        addMessage(m);
    }

    public void sendPrivate(String targetUserName, Message message) {
        UserSession target = participants.stream().filter(u -> u.getUserName().equals(targetUserName)).findFirst().orElse(null);
        if (target != null) {
            target.sendRaw("[PM] " + message.formatted());
            UserSession sender = participants.stream().filter(u -> u.getUserName().equals(message.getSender())).findFirst().orElse(null);
            if (sender != null && sender != target) {
                sender.sendRaw("[PM to " + targetUserName + "] " + message.formatted());
            }
        } else {
            UserSession sender = participants.stream().filter(u -> u.getUserName().equals(message.getSender())).findFirst().orElse(null);
            if (sender != null) {
                sender.sendRaw("System: user " + targetUserName + " not found in room");
            }
        }
    }

    public String activeUsers() {
        return participants.stream().map(UserSession::getUserName).collect(Collectors.joining(", "));
    }
}
