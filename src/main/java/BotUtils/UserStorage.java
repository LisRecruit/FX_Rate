package BotUtils;

import java.util.HashMap;
import java.util.Map;

public class UserStorage {
    private static Map<Long, Users> users = new HashMap<>();


    public static Users getUser(long chatId) {
        return users.getOrDefault(chatId, new Users(chatId));
    }

    public static void saveUser(Users user) {
        users.put(user.getChatId(), user);
    }
    public static boolean containsUser (long chatId){
        if (users.containsKey(chatId)){
            return true;
        }  return false;
    }


}
