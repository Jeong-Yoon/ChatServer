import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ChatServer {
    private int port;
    private ChatHouse chatHouse;

    public ChatServer(int port) {
        this.port = port;
        chatHouse = new ChatHouse();
    }

    public void run(){
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(port);
            while (true){
                Socket socket = serverSocket.accept();
                ChatServerHandler chatServerHandler = new ChatServerHandler(socket,chatHouse);
                chatServerHandler.start();
            }
        } catch (IOException e) {
            System.out.println("오류가 발생하였습니다.");
        }finally {
            try {
                serverSocket.close();
            }catch (Exception ignore){}
        }
    }
}
