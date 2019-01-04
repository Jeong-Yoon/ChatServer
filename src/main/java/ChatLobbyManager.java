import java.util.List;

public class ChatLobbyManager {
    private ChatHouse chatHouse;

    public ChatLobbyManager(ChatHouse chatHouse) {
        this.chatHouse = chatHouse;
    }

    public void quit(ChatUser chatUser) {
        //ChatHouse에서 Lobby에 있는 사람 삭제하고, 컴퓨터 프로그램 종료
        chatHouse.quit(chatUser);
        chatUser.write("채팅프로그램을 종료합니다.");
        chatUser.close();
    }

    public boolean joinRoom(ChatUser chatUser) {
        chatUser.write("들어갈 방 번호를 입력하세요: ");
        String strRoomNum = chatUser.read();
        //System.out.println("**서버** | 사용자: " + nickname + " | 방번호: " + strRoomNum);
        int roomNum = Integer.parseInt(strRoomNum);
        if (chatHouse.getChatRooms().size() <= roomNum) {
            chatUser.write("없는 방 번호 입니다.");
            return false;
        } else {
            if ((chatHouse.countChatUsers(roomNum)) == 4) {
                chatUser.write("인원초과!!!");
                return false;
            } else {
                String chk = chatHouse.chkRoom(roomNum);
                if (chk == null) {
                    chatHouse.joinRoom(roomNum, chatUser);
                    chatUser.write(chatHouse.getChatRooms().toString() + "방에 입장하였습니다.");
                    //inRoom = true;
                    return true;
                } else {
                    chatUser.write("비밀번호를 입력해주세요.");
                    String password = chatUser.read();
                    if (chk.equals(password)) {
                        chatHouse.joinRoom(roomNum, chatUser);
                        chatUser.write(chatHouse.getChatRooms().toString() + "방에 입장하였습니다.");
                        //inRoom = true;
                        return true;
                    } else {
                        chatUser.write("비밀번호가 틀렸습니다.");
                        return false;
                    }
                }
            }
        }
    }

    public void roomList(ChatUser chatUser) {
        List<ChatRoom> chatRooms = chatHouse.getChatRooms();
        int roomNum = 0;
        for (ChatRoom chatRoom : chatRooms) {
            chatUser.write(roomNum + " : " + chatRoom.getTitle() + " - 현재인원: " + chatRoom.cntUser() + "/4");
            roomNum++;
        }
        if(roomNum == 0){
            chatUser.write("생성된 방이 없습니다.");
        }
    }

    public boolean createRoom(ChatUser chatUser) {
        chatUser.write("방이름을 입력하세요: ");
        String title = chatUser.read();
        //System.out.println("**서버** | 사용자: "+nickname+" | title: " + title);
        chatUser.write("비밀번호를 입력 받으시겠습니까? (Y/N)");
        String answer = chatUser.read();
        String password;
        boolean isPass;
        if (answer.equalsIgnoreCase("Y")) {
            //System.out.println("**서버** | 방이름: "+title+" | 비밀번호 있음");
            chatUser.write("비밀번호를 입력하세요");
            password = chatUser.read();
            //System.out.println("**서버** | 사용자: "+nickname+" | 비밀번호: "+password);
            isPass = true;
        } else {
            //System.out.println("**서버** | 방이름: "+title+" | 비밀번호 없음");
            password = null;
            isPass = false;
        }
        chatHouse.createRoom(title, chatUser, isPass, password);
        chatUser.write(title+ "방에 입장하였습니다.");
        //inRoom = true;
        return true;
    }
}
