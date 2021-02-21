package ch01serversend;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) throws Exception {
        ServerSocket serverSocket = new ServerSocket();
        serverSocket.bind(new InetSocketAddress(8080), 10000);
        System.out.println("Listening to 8080");
        while (true) {
            Socket clientSocket = serverSocket.accept();
            System.out.printf("ch01serversend.Client connected %s:%d in\n",
                    clientSocket.getInetAddress().getHostAddress(),
                    clientSocket.getPort());

            Thread t1 = new Thread(new ClientHandler(clientSocket));
            t1.start();
        }
    }

}

class ClientHandler implements Runnable {
    private Socket clientSocket;

    public ClientHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try {

            for (int i = 0; i < 10; i++) {
                String data = String.format("TIME:%d\n", System.currentTimeMillis());
                clientSocket.getOutputStream().write(data.getBytes());


                // make sure buffer send
                clientSocket.getOutputStream().flush();
                Thread.sleep(1000);
            }
            // read/write to client socket
        } catch (Exception e) {
            // do not thing
            e.printStackTrace();
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                // do nothing
            }
        }
    }
}