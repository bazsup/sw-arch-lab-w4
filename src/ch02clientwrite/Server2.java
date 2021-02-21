package ch02clientwrite;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server2 {
    public static void main(String[] args) throws Exception {
        ServerSocket serverSocket = new ServerSocket();
        serverSocket.bind(new InetSocketAddress(8080), 10000);
        System.out.println("Listening to 8080");
        while (true) {
            Socket clientSocket = serverSocket.accept();
//            serverSocket.getChannel().accept();
            System.out.printf("Client connected %s:%d in\n",
                    clientSocket.getInetAddress().getHostAddress(),
                    clientSocket.getPort());

            Thread t1 = new Thread(new ClientHandler2(clientSocket));
            t1.start();
        }
    }

}

class ClientHandler2 implements Runnable {
    private Socket clientSocket;

    public ClientHandler2(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try {
            Scanner scanner = new Scanner(clientSocket.getInputStream());
            while (scanner.hasNextLine()) {
                System.out.println("has next line");
                String command = scanner.nextLine();
                System.out.printf("GOT %s\n", command);

                if (command.equals("UPDATE")) {
                    String data = String.format("TIME:%d\n", System.currentTimeMillis());
                    clientSocket.getOutputStream().write(data.getBytes());
                    clientSocket.getOutputStream().flush();
                } else {
                    clientSocket.getOutputStream().write("Error wrong command\n".getBytes());
                    clientSocket.getOutputStream().flush();

                }

            }
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
