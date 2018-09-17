package task;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ServerThread extends Thread {
    static MySocket[] sockets = new MySocket[2];
    static CrossesProtocole cp = new CrossesProtocole();
    MySocket socket;

    public ServerThread(Socket socket, int n) throws IOException {
        super("ServerThread");
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(
                new InputStreamReader(
                        socket.getInputStream()));
        this.socket = new MySocket(socket, n, out, in);
        sockets[n] = this.socket;
    }

    public void run() {
        try {
            socket.out.println(cp.startGame(socket.n));

            while (!CrossesProtocole.over) {
                cp.getNumber(socket);
                String answer0, answer1;
                if (CrossesProtocole.over) {
                    if (socket.n == CrossesProtocole.currentPlayer) {
                        socket.out.println("Canvas:" + cp.canvas + "\nYou win!");
                    } else {
                        socket.out.println("Canvas:" + cp.canvas + "\nYou lose!");
                    }

                } else {
                    answer0 = "Canvas:" + cp.canvas + "\nWaiting for your turn...";
                    answer1 = "Canvas:" + cp.canvas + "\nYour turn\nChoose number of cell";
                    sockets[CrossesProtocole.currentPlayer].out.println(answer0);
                    sockets[Math.abs(CrossesProtocole.currentPlayer - 1)].out.println(answer1);
                    CrossesProtocole.currentPlayer = Math.abs(CrossesProtocole.currentPlayer - 1);
                }
            }
            Client.over = true;
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
