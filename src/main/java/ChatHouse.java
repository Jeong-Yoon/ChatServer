import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ChatHouse {
    private List<ChatUser> lobby;
    private List<ChatRoom> chatRooms;

    public ChatHouse(){
        lobby = Collections.synchronizedList(new ArrayList<>());
        chatRooms = Collections.synchronizedList(new ArrayList<>());
    }


    public void addChatUser(ChatUser chatUser) {
        lobby.add(chatUser);
    }

    public void createRoom(String title, ChatUser chatUser, boolean isPass, String password) {
        ChatRoom chatRoom = new ChatRoom(title,chatUser,password,isPass);
        chatRooms.add(chatRoom);
        System.out.println("========방에 입장하셨습니다.==========");
    }

    public List<ChatRoom> getChatRooms() {
        return chatRooms;
    }

    // 해당 리스트에서만 처리해주는 것이 아니라. 쓰레드에서 자주 사용하는 메서드이므로 synchronized를 적용하는 것이 좋아 보입니다.
    public void joinRoom(int roomNum, ChatUser chatUser) {
        ChatRoom chatRoom = chatRooms.get(roomNum);
        chatRoom.addChatUser(chatUser);
        System.out.println("========방에 입장하셨습니다.==========");
    }

    // 방안에서 유저리스트를 가지고 오면, 방을 접근해서 유저리스트를 가져오는 방식이 좋아 보입니다. 전체 방에서 순회하는 방식은 좋은 방식은 아닌것같습니다.
    public List<ChatUser> getUser(ChatUser chatUser) {
        for (ChatRoom cr : chatRooms) {
            if (cr.existUser(chatUser)) {
                return cr.getChatUsers();
            }
        }
        return new ArrayList<>();
    }

    public void quit(ChatUser chatUser) {
        if (lobby.contains(chatUser)) {
            lobby.remove(chatUser);
        }
    }

    public void exit(ChatUser chatUser){
        for (ChatRoom cr : chatRooms) {
            if (cr.existUser(chatUser)) {
                cr.delete(chatUser);
            }
        }
    }

    public String chkRoom(int roomNum) {
        ChatRoom chatRoom = chatRooms.get(roomNum);
        if (chatRoom.isHasPass()){
            return chatRoom.getPassword();
        }else {
            return null;
        }
    }

    public int countChatUsers(int roomNum){
        return chatRooms.get(roomNum).cntUser();
    }
}
