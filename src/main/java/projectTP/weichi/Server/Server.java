package projectTP.weichi.Server;

import projectTP.weichi.Server.exceptions.DidntConfigureCorrectlyException;
import projectTP.weichi.Server.exceptions.DidntConnectException;
import projectTP.weichi.Server.exceptions.DidntCreateServerException;
import projectTP.weichi.Server.exceptions.ReadingException;
import projectTP.weichi.Server.game.Game;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server extends Thread {
    private Game game = null;
    private ServerSocket server;
    private Socket socket = null;
    private BufferedReader input;
    private PrintWriter output;
    private String line;

    @Override
    public void run() {
        try {
            makeServer();
            connect();
        }
        catch (DidntConnectException ex) {
            System.out.println("Server didn't connect.");
            interrupt();
        }
        catch (DidntCreateServerException ex) {
            System.out.println("Could not create server on port " + ex.getPort() + ".");
        }
        catch (DidntConfigureCorrectlyException ex) {
            System.out.println("Buffers' configuration occurred.");
        }


        try {
            game = createGame();
        }
        catch (ReadingException ex) {
            System.out.println("Reading problem occurred.");
        }

        do {
            play();
        } while(!game.won());

        // rematch?
    }

    private void play() {
        //TODO: implement
    }

    private void makeServer() throws DidntCreateServerException {
        int port = 4999;
        try {
            server = new ServerSocket(port);
            System.out.println("server open on port: " + port);
        } catch (IOException e) {
            throw new DidntCreateServerException(port);
        }
    }

    private void connect() throws DidntConnectException, DidntConfigureCorrectlyException {
        try {
            socket = server.accept();
            System.out.println("client connected");
        }
        catch (Exception ex) { throw new DidntConnectException(); }

        try {
            input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            output = new PrintWriter(socket.getOutputStream(),true);
        } catch (IOException e) {
            throw new DidntConfigureCorrectlyException();
        }
    }

    private Game createGame() throws ReadingException {
        try {
            line = input.readLine();
        } catch (IOException e) {
            throw new ReadingException();
        }
        //TODO: make args not static
        return new Game(false, 19);
    }
}