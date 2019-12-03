package projectTP.weichi.server;


import projectTP.weichi.server.game.Game;


import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server extends Thread {
    /** Stores server socket*/
    ServerSocket server = null;
    /** Stores client*/
    Socket frame = null;
    /** Stores input stream*/
    BufferedReader input = null;
    /** Stores output stream*/
    PrintWriter output = null;
    /** Stores input line*/
    String line = "";
    private Game game = null;

    @Override
    public void run() {
        try { acceptConnection(); }
        catch (Exception ex) {
            System.out.println("Server did not recive connection");
            interrupt();
        }
        //TODO: make the game
        game = makeNewGame(false, 19);
        //TODO: do logic

        // rematch?

    }

    private Game makeNewGame(boolean bot, int size) {
        return new Game(bot, size);
    }

    private void acceptConnection() throws Exception {
        //TODO: implement
    }
}
