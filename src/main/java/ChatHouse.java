import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ChatHouse {
    private List<ChatUser> lobby;
    private List<ChatRoom> chatRooms;

    public ChatHouse(){ //여기에 있는 synchronized는 굳이. 메소드들에 붙이는 게 좋다.
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

    public void joinRoom(int roomNum, ChatUser chatUser) {
        ChatRoom chatRoom = chatRooms.get(roomNum);
        chatRoom.addChatUser(chatUser);
        System.out.println("========방에 입장하셨습니다.==========");
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
        if (lobby.contains(chatUser)) {
            lobby.remove(chatUser);
        }
    }

    public void exit(ChatUser chatUser){
        for (ChatRoom cr : chatRooms) {
            if (cr.existUser(chatUser)) {
                cr.delete(chatUser);
                //TODO 방에서 나왔을 때 로비에 다시 추가.
                addChatUser(chatUser);
            }
        }
    }

    public String chkRoom(int roomNum) {
        ChatRoom chatRoom = chatRooms.get(roomNum);
        if (chatRoom.isPass()){
            return chatRoom.getPassword();
        }else {
            return null;
        }
    }

    public int countChatUsers(int roomNum){
        return chatRooms.get(roomNum).cntUser();
    }
}
