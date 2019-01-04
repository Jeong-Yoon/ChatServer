public class ChatServerMain {
    public static void main(String[] args){
        int port = 9999;
        if (args.length > 1){ // 프로그램 아규먼트
            try{
                port = Integer.parseInt(args[0]);
            }catch (NumberFormatException nfe){
                System.out.println("사용법 : ~");
                System.out.println("port 번호는 정수값이어야 합니다.");
                return;
            }
        }

        ChatServer chatServer = new ChatServer(port);
        chatServer.run();
    }
}

/**
 * doc 주석문 얘가 도대체 무슨 역할을 수행하는 지 적어주는 것.
 */

