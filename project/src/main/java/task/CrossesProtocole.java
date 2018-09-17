package task;

import java.io.IOException;

public class CrossesProtocole {
    static String canvas = "000000000";
    static int currentPlayer = 0;
    static boolean over = false;

    public String startGame(int n) {
        String theOutput = null;
        if (n == 0) {
            theOutput = "Hello, player 1!\nYou will play for X\nCanvas:" + canvas + "\nChoose number of cell";
        } else {
            theOutput = "Hello, player 2!\nYou will play for O\nWaiting for your turn...";
        }
        return theOutput;
    }

    public void getNumber(MySocket socket) throws IOException {
        int n;
        while (!over) {
            try {
                if (socket.n != currentPlayer) {
                    if (socket.in.ready()) {
                        socket.out.println("You must wait your turn");
                        socket.in.readLine();
                    }
                    continue;
                }
                n = Integer.parseInt(socket.in.readLine());
                System.out.println(n);
                if (n >= 0 && n <= 8 && canvas.charAt(n) == '0') {
                    if (socket.n == 0) {
                        canvas = canvas.substring(0, n) + 'X' + canvas.substring(n + 1);
                    }
                    if (socket.n == 1) {
                        canvas = canvas.substring(0, n) + 'O' + canvas.substring(n + 1);
                    }
                    over = isGameOver(n);
                    break;
                }
                socket.out.println("Choose free cell and enter its number");
            } catch (NumberFormatException e) {
                socket.out.println("Please enter the number");
            }
        }
    }

    static boolean isGameOver(int n) {
        //поиск совпадений по горизонтали
        int row = n - n % 3; //номер строки - проверяем только её
        if (canvas.charAt(row) == canvas.charAt(row + 1) &&
                canvas.charAt(row) == canvas.charAt(row + 2)) return true;
        //поиск совпадений по вертикали
        int column = n % 3; //номер столбца - проверяем только его
        if (canvas.charAt(column) == canvas.charAt(column + 3))
            if (canvas.charAt(column) == canvas.charAt(column + 6)) return true;
        //мы здесь, значит, первый поиск не положительного результата
        //если значение n находится на одной из граней - возвращаем false
        if (n % 2 != 0) return false;
        //проверяем принадлежит ли к левой диагонали значение
        if (n % 4 == 0) {
            //проверяем есть ли совпадения на левой диагонали
            if (canvas.charAt(0) == canvas.charAt(4) &&
                    canvas.charAt(0) == canvas.charAt(8)) return true;
            if (n != 4) return false;
        }
        return canvas.charAt(2) == canvas.charAt(4) &&
                canvas.charAt(2) == canvas.charAt(6);
    }
}
