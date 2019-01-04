import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;

public class ChatClient {
    private String ip;
    private int port;

    public ChatClient(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    public void run(){
        Socket socket = null;
        ChatUser chatUser = null;
        BufferedReader br = null;
        try{
            br = new BufferedReader(new InputStreamReader(System.in));
            socket = new Socket(ip, port); // 서버에 접속
            chatUser = new ChatUser(socket);

            System.out.println("닉네임을 입력하세요.");
            String nickName = br.readLine();
            chatUser.setNickname(nickName);
            chatUser.write(nickName);
            System.out.println("내이름은: "+chatUser.getNickname());
            ChatClientHandler chatClientHandler = new ChatClientHandler(chatUser);
            chatClientHandler.start();

            while(true){
                String line = br.readLine();
                chatUser.write(line);

                if(line.equals( "/quit") ) {
                    break;
                }
            }
            //System.exit(0);
        }catch (Exception ex){
            // 접속이 끊어지면 Exception;
            System.out.println("연결이 끊어졌습니다.");
        }finally {
            chatUser.close();
            System.exit(0);
        }
    }
}