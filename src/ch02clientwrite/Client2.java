package ch02clientwrite;

import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

public class Client2 {
    public static void main(String[] args) throws Exception {
        Socket clientSocket = new Socket();
        System.out.println("Connecting");
        clientSocket.connect(new InetSocketAddress("127.0.0.1", 8080));
        System.out.printf("Connected from port %d\n", clientSocket.getLocalPort());

        Scanner userInput = new Scanner(System.in);

        Scanner socketInput = new Scanner(clientSocket.getInputStream());

        new Thread(() -> {
            while(true) {
                System.out.println("waiting for response");
                String time = socketInput.nextLine();
                System.out.println(time);
            }
        });
        for (int i = 0; i < 10; i++) {
            String cmd = userInput.nextLine();
            clientSocket.getOutputStream().write((cmd + "\n").getBytes());
            clientSocket.getOutputStream().flush();

//            String time = socketInput.nextLine();
//            System.out.println(time);

            Thread.sleep(1000);
        }
//        clientSocket.close();
    }
}
