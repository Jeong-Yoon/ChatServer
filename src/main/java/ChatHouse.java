import java.util.ArrayList;
import java.util.List;

public class ChatHouse {
    private List<ChatUser> chatUsers;
    private List<ChatRoom> chatRooms;

    public void addChatUser(ChatUser chatUser) {
        chatUsers.add(chatUser);
    }

    public void createRoom(String title, ChatUser chatUser) {
    }

    public List<ChatRoom> getChatRooms() {
        return chatRooms;
    }

    public void joinRoom(int roomNum, ChatUser chatUser) {
    }

    public List<ChatUser> getUser(ChatUser chatUser) {
        for (ChatRoom cr : chatRooms) {
            if (cr.existUser(chatUser)) {
                return cr.getChatUsers();
            }
        }
        return new ArrayList<>();
    }

    public void quit(ChatUser chatUser) {
        if (chatUsers.contains(chatUser)) {
            chatUsers.remove(chatUser);
        }
    }
}
