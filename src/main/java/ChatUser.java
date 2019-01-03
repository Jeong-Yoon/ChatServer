import java.io.IOException;
import java.net.Socket;

public class ChatUser {
    private Socket socket;
    private String nickname;

    public ChatUser(Socket socket) {
        this.socket = socket;
    }

    public String read() {
        return null;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getNickname() {
        return nickname;
    }

    public void write(String s) {
    }
}
