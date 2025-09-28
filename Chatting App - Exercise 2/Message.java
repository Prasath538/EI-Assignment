import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class Message {
    private final String sender;
    private final String body;
    private final long timestamp;
    private final boolean isPrivate;
    private final String targetUser;

    public Message(String sender, String body) {
        this(sender, body, false, null);
    }

    public Message(String sender, String body, boolean isPrivate, String targetUser) {
        this.sender = sender;
        this.body = body;
        this.timestamp = Instant.now().toEpochMilli();
        this.isPrivate = isPrivate;
        this.targetUser = targetUser;
    }

    public String getSender() {
        return sender;
    }

    public String getBody() {
        return body;
    }

    public boolean isPrivate() {
        return isPrivate;
    }

    public String getTargetUser() {
        return targetUser;
    }

    public String formatted() {
        DateTimeFormatter f = DateTimeFormatter.ofPattern("HH:mm:ss").withZone(ZoneId.systemDefault());
        return "[" + f.format(Instant.ofEpochMilli(timestamp)) + "] " + sender + ": " + body;
    }
}
