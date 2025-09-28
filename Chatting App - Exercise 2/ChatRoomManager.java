import java.util.concurrent.ConcurrentHashMap;
import java.util.Set;

public class ChatRoomManager {
    private static volatile ChatRoomManager instance;
    private final ConcurrentHashMap<String, ChatRoom> rooms = new ConcurrentHashMap<>();

    private ChatRoomManager() {
    }

    public static ChatRoomManager getInstance() {
        if (instance == null) {
            synchronized (ChatRoomManager.class) {
                if (instance == null) {
                    instance = new ChatRoomManager();
                }
            }
        }
        return instance;
    }

    public ChatRoom create(String roomId) {
        return rooms.compute(roomId, (id, existing) -> {
            if (existing != null) {
                throw new IllegalStateException("Room already exists");
            }
            return new ChatRoom(id);
        });
    }

    public ChatRoom get(String roomId) {
        return rooms.get(roomId);
    }

    public Set<String> roomIds() {
        return rooms.keySet();
    }
}
