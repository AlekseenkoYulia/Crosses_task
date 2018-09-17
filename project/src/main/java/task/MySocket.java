package task;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class MySocket {
    Socket socket;
    int n;
    PrintWriter out;
    BufferedReader in;

    public MySocket(Socket socket, int n, PrintWriter out, BufferedReader in) {
        this.socket = socket;
        this.n = n;
        this.out = out;
        this.in = in;
    }

    public void close() throws IOException {
        socket.close();
        out.close();
        in.close();
    }
}
