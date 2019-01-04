import java.net.Socket;
import java.util.List;


public class ChatServerHandler extends Thread {
    private Socket socket;
    private ChatHouse chatHouse;
    private boolean inRoom;


    public ChatServerHandler(Socket socket, ChatHouse chatHouse) {
        this.socket = socket;
        this.chatHouse = chatHouse;
        inRoom = false;
    }

    public void run() {
        ChatUser chatUser = new ChatUser(socket);
        String nickname = chatUser.read();
        chatUser.setNickname(nickname);
        //System.out.println("**서버** | 사용자: "+nickname+" | nickname: " + nickname);
        chatHouse.addChatUser(chatUser);
        try {
            while (true) {
//            String message = chatUser.read();
//            System.out.println("message: " + message);
                if (!inRoom) {
                    inLobby(chatUser);
                    continue;
                } else {
                    inTheRoom(chatUser);
                }
            }
        }catch (Exception e){
            chatHouse.exit(chatUser);
            chatHouse.quit(chatUser);
            chatUser.close();
            System.out.println(chatUser.getNickname()+"님이 종료하셨습니다.");
        }
    }

    public void inLobby(ChatUser chatUser) {
        ChatLobbyManager chatLobbyManager = new ChatLobbyManager(chatHouse);
        chatUser.write("****************************************************");
        chatUser.write("명령어를 입력하세요: /create,   /list,  /join,  /quit");
        String message = chatUser.read();
        //System.out.println("**서버** | 사용자: "+nickname+" | 명령어: " + message);
        if (message.equals("/create")) {
            inRoom = chatLobbyManager.createRoom(chatUser);
        } else if (message.equals("/list")) {
            chatLobbyManager.roomList(chatUser);
        } else if (message.equals("/join")) {
            inRoom = chatLobbyManager.joinRoom(chatUser);
            return;
        }else if (message.equals("/quit")) {
            chatLobbyManager.quit(chatUser);
        }
    }

    public void inTheRoom(ChatUser chatUser) {
        ChatRoomManager chatRoomManager = new ChatRoomManager(chatHouse);
        String message = chatUser.read();
        List<ChatUser> chatUsers = chatHouse.getUser(chatUser);

        chatRoomManager.chattingEachOther(chatUser, message, chatUsers);

        if (message.equals("/list")) {
            chatRoomManager.listInRoomPeople(chatUser, chatUsers);
        }
        if (message.equals("/exit")) {
            inRoom = chatRoomManager.exitTheRoom(chatUser);
        }
    }

}