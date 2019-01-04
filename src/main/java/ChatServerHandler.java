import java.net.Socket;
import java.util.List;

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
        System.out.println("**서버** | 사용자: "+nickname+" | nickname: " + nickname);
        chatHouse.addChatUser(chatUser);

        while (true) {
//            String message = chatUser.read();
//            System.out.println("message: " + message);
            if (!inRoom) {
                chatUser.write("****************************************************");
                chatUser.write("명령어를 입력하세요: /create,   /list,  /join,  /quit");
                String message = chatUser.read();
                System.out.println("**서버** | 사용자: "+nickname+" | 명령어: " + message);
                if (message.equals("/create")) {
                    //
                    chatUser.write("방이름을 입력하세요: ");
                    String title = chatUser.read();
                    System.out.println("**서버** | 사용자: "+nickname+" | title: " + title);
                    chatUser.write("비밀번호를 입력 받으시겠습니까? (Y/N)");
                    String answer = chatUser.read();
                    String password;
                    boolean isPass;
                    if (answer.equalsIgnoreCase("Y")) {
                        System.out.println("**서버** | 방이름: "+title+" | 비밀번호 있음");
                        chatUser.write("비밀번호를 입력하세요");
                        password = chatUser.read();
                        System.out.println("**서버** | 사용자: "+nickname+" | 비밀번호: "+password);
                        isPass = true;
                    } else {
                        System.out.println("**서버** | 방이름: "+title+" | 비밀번호 없음");
                        password = null;
                        isPass = false;
                    }
                    chatHouse.createRoom(title, chatUser, isPass, password);
                    inRoom = true;
                    chatUser.write(chatHouse.getChatRooms().toString()+ "방에 입장하였습니다.");
                } else if (message.equals("/list")) {
                    List<ChatRoom> chatRooms = chatHouse.getChatRooms();
                    int roomNum = 0;
                    for (ChatRoom chatRoom : chatRooms) {
                        chatUser.write(roomNum + " : " + chatRoom.getTitle() + " - 현재인원: " + chatRoom.cntUser() + "/4");
                        roomNum++;
                    }
                    if(roomNum == 0){
                        chatUser.write("생성된 방이 없습니다.");
                    }

                } else if (message.equals("/join")) {
                    //TODO 바로 join을 눌렀을 경우
                    chatUser.write("들어갈 방 번호를 입력하세요: ");
                    String strRoomNum = chatUser.read();
                    //TODO 방번호 잘못입력하면 서버 죽음
                    System.out.println("**서버** | 사용자: " + nickname + " | 방번호: " + strRoomNum);
                    int roomNum = Integer.parseInt(strRoomNum);
                    if ((chatHouse.countChatUsers(roomNum)) == 4) {
                        chatUser.write("인원초과!!!");
                        continue;
                    } else {
                        String chk = chatHouse.chkRoom(roomNum);
                        if (chk == null) {
                            chatHouse.joinRoom(roomNum, chatUser);
                            inRoom = true;
                            chatUser.write(chatHouse.getChatRooms().toString() + "방에 입장하였습니다.");
                        } else {
                            chatUser.write("비밀번호를 입력해주세요.");
                            String password = chatUser.read();
                            if (chk.equals(password)) {
                                chatHouse.joinRoom(roomNum, chatUser);
                                inRoom = true;
                                chatUser.write(chatHouse.getChatRooms().toString() + "방에 입장하였습니다.");
                            } else {
                                chatUser.write("비밀번호가 틀렸습니다.");
                            }
                        }
                    }
                }else if (message.equals("/quit")) {
                    //ChatHouse에서 Lobby에 있는 사람 삭제하고, 컴퓨터 프로그램 종료
                    chatHouse.quit(chatUser);
                    chatUser.write("채팅프로그램을 종료합니다.");
                    System.exit(0);
                    //chatUser.close();
                }
            } else {
                String message = chatUser.read();

                List<ChatUser> chatUsers = chatHouse.getUser(chatUser);
                for (ChatUser cu : chatUsers) {
                    cu.write(chatUser.getNickname() + " : " + message);
                }

                if (message.equals("/list")) {
                    chatUser.write("채팅중인 사람 목록");
                    for (ChatUser cu : chatUsers) {
                        chatUser.write(cu.getNickname());
                    }
                }
                if (message.equals("/exit")) {
                    chatHouse.exit(chatUser);
                    chatUser.write("방을 나갑니다.");
                    inRoom = false;
                    //TODO 방을 나가면 바로 로비 명령어가 나오게끔 해야함
                }
            }

        }
    }
}