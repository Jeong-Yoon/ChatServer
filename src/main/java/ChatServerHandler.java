import java.net.Socket;
import java.util.List;

public class ChatServerHandler extends Thread{
    private Socket socket;
    private ChatHouse chatHouse;
    private boolean inRoom;

    public ChatServerHandler(Socket socket, ChatHouse chatHouse){
        this.socket = socket;
        this.chatHouse = chatHouse;
        inRoom = false;
    }

    public void run(){
        ChatUser chatUser = new ChatUser(socket);
        String nickname = chatUser.read();
        chatUser.setNickname(nickname);
        System.out.println("nickname: "+nickname);

        chatHouse.addChatUser(chatUser);

        while (true){
            String message = chatUser.read();
            System.out.println("message: "+ message);
            if (!inRoom){
                if (message.indexOf("/create") == 0){
                    //비밀번호 추가예정
                    String title = message.substring(message.indexOf(" ")+1);
                    chatHouse.createRoom(title, chatUser);
                    inRoom = true;
                }else if (message.indexOf("/list") == 0){
                    List<ChatRoom> chatRooms = chatHouse.getChatRooms();
                    int roomNum = 0;
                    for (ChatRoom chatRoom : chatRooms){
                        chatUser.write(roomNum+" : "+chatRoom.getTitle());
                        roomNum++;
                    }
                }else if (message.indexOf("/join") == 0){
                    String strRoomNum = message.substring(message.indexOf(" ") +1);
                    int roomNum = Integer.parseInt(strRoomNum);
                    chatHouse.joinRoom(roomNum,chatUser);
                    inRoom = true;
                }else if (message.indexOf("/quit") == 0){
                    //ChatHouse에서 Lobby에 있는 사람 삭제하고, 컴퓨터 프로그램 종료
                    chatHouse.quit(chatUser);
                    chatUser.close();
                }
            }else {
                List<ChatUser> chatUsers = chatHouse.getUser(chatUser);
                for (ChatUser cu : chatUsers){
                    cu.write(chatUser.getNickname()+" : "+message);
                }
            }
        }

    }

}
