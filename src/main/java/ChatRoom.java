import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ChatRoom {
    private String title;
    private List<ChatUser> chatUsers;
    private String password;
    private boolean isPass;

    public ChatRoom(String title, ChatUser chatUser, String password, boolean isPass) {
        this.title = title;
        chatUsers = Collections.synchronizedList(new ArrayList<>());
        chatUsers.add(chatUser);
        this.password = password;
        this.isPass = isPass;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<ChatUser> getChatUsers() {
        return chatUsers;
    }

    public void setChatUsers(List<ChatUser> chatUsers) {
        this.chatUsers = chatUsers;
    }

    public boolean existUser(ChatUser chatUser) {
        return chatUsers.contains(chatUser);
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isPass() {
        return isPass;
    }

    public void setPass(boolean pass) {
        isPass = pass;
    }

    public void addChatUser(ChatUser chatUser) {
        chatUsers.add(chatUser);
    }
    
    public int cntUser(){
        return chatUsers.size();
    }

    public void delete(ChatUser chatUser) {
        if (chatUsers.contains(chatUser)) {
            chatUsers.remove(chatUser);
        }
    }

}
