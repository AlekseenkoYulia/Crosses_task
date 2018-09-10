package task;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
    static boolean over = false;
    public static void main(String[] args) {
        String hostName = "localhost";
        int portNumber = 80;

        try {
            Socket socket = new Socket(hostName, portNumber);
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));
            BufferedReader stdIn =
                    new BufferedReader(new InputStreamReader(System.in));

            String line;
            while (!over) {
                if (stdIn.ready()) {
                    out.println(stdIn.readLine());
                }
                while (in.ready()) {
                    line = in.readLine();
                    if (line.indexOf("Canvas") != -1) {
                        drawCanvas(line.substring(7));
                    } else System.out.println(line);
                }
            }

            socket.close();
            out.close();
            in.close();
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host " + hostName);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to " +
                    hostName);
            System.exit(1);
        }
    }

    static void drawCanvas(String canvas) {
        System.out.println("     |     |     ");
        for (int i = 0; i < canvas.length(); i++) {
            if (i != 0) {
                if (i % 3 == 0) {
                    System.out.println();
                    System.out.println("_____|_____|_____");
                    System.out.println("     |     |     ");
                } else
                    System.out.print("|");
            }

            if (canvas.charAt(i) == '0') System.out.print("  " + i + "  ");
            if (canvas.charAt(i) == 'X') System.out.print("  X  ");
            if (canvas.charAt(i) == 'O') System.out.print("  O  ");
        }
        System.out.println();
        System.out.println("     |     |     ");
    }
}

