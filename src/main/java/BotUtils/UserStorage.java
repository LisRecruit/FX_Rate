package BotUtils;

import java.util.HashMap;
import java.util.Map;

public class UserStorage {
    private static final Map<Long, User> users = new HashMap<>();

    public static User getUser(long chatId) {
        return users.getOrDefault(chatId, new User(chatId));
    }

    public static void saveUser(User user) {
        users.put(user.getChatId(), user);
    }
}
