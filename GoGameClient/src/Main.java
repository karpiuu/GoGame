import Frames.LoginFrame.LoginFrame;

import java.util.Scanner;

public class Main {

    public static void main(String[] args){
        String x;

        SocketClient client = new SocketClient();
        client.listenSocket();
        Scanner scanner = new Scanner(System.in);
        while (true) {
            x = scanner.nextLine();
            client.out.println("Test " + x);
        }
        //LoginFrame loginFrame = new LoginFrame();
    }
}
