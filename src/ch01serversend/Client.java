package ch01serversend;

import java.net.InetSocketAddress;
import java.net.Socket;

public class Client {
    public static void main(String[] args) throws Exception {
        Socket clientSocket = new Socket();
        System.out.println("Connecting");
        clientSocket.connect(new InetSocketAddress("127.0.0.1", 8080));
        System.out.printf("Connected from port %d\n", clientSocket.getLocalPort());

        while (true) {
            int ch = clientSocket.getInputStream().read();
            if (ch == -1) { // end of stream
                System.out.println("Stream ended");
                break;
            }
            System.out.print((char) ch);
        }
        clientSocket.close();
    }
}
