import java.util.List;

public class ChatRoomManager {
    ChatHouse chatHouse = new ChatHouse();


    public void chattingEachOther(ChatUser chatUser, String message, List<ChatUser> chatUsers) {
        for (ChatUser cu : chatUsers) {
            cu.write(chatUser.getNickname() + " : " + message);
        }
    }

    public void listInRoomPeople(ChatUser chatUser, List<ChatUser> chatUsers) {
        chatUser.write("채팅중인 사람 목록");
        for (ChatUser cu : chatUsers) {
            chatUser.write(cu.getNickname());
        }
    }

    public boolean exitTheRoom(ChatUser chatUser) {
        chatHouse.exit(chatUser);
        chatUser.write("방을 나갑니다.");
        return false;
    }
}