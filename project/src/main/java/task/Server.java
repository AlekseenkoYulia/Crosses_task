package task;

import java.io.*;
import java.net.ServerSocket;

public class Server {
    public static void main(String[] args) {
        int portNumber = 80;

        try {
            ServerSocket serverSocket = new ServerSocket(portNumber);

            for (int n = 0; n <= 1; n++) {
                ServerThread thread = new ServerThread(serverSocket.accept(), n);
                thread.start();
            }

            serverSocket.close();
        } catch (IOException e) {
            System.err.println("Could not listen on port " + portNumber);
            System.exit(-1);
        }
    }
}

