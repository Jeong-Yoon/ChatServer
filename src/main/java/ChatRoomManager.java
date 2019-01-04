import java.util.List;

public class ChatRoomManager {
    private ChatHouse chatHouse;

    public ChatRoomManager(ChatHouse chatHouse) {
        this.chatHouse = chatHouse;
    }

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
        if (chatHouse.(r) < 1) { // 모든 인원이 다 방을 나갔다면
            RoomManager.removeRoom(this); // 이 방을 제거한다.
            return;
        }
    }

}