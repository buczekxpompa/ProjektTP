package projectTP.weichi.server;

import projectTP.weichi.server.exceptions.*;
import projectTP.weichi.server.game.Game;
import projectTP.weichi.server.parser.ServerParser;
import projectTP.weichi.server.parser.ServerParserJson;
import projectTP.weichi.server.support.GameConfig;
import projectTP.weichi.server.support.Point;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private ServerSocket server;

    public Server() {
        try {
            makeServer();
        }
        catch (DidntCreateServerException ex) {
            System.out.println("Could not create server on port " + ex.getPort() + ".");
        }
        try { serverLoop(); }
        catch (DidntConnectException e) {
            e.printStackTrace();
        }
    }

    private void serverLoop() throws DidntConnectException{
        while (true) {
            Socket socket;
            try {
                socket = server.accept();
                System.out.println("client connected");
            } catch (Exception ex) {
                throw new DidntConnectException();
            }

            ServerThread thread = new ServerThread(socket);
            thread.start();
        }
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


    class ServerThread extends Thread {
        private BufferedReader input;
        private PrintWriter output;
        private String line;
        private ServerParser parser = new ServerParserJson();
        private Game game;

        ServerThread(Socket socket) {
            try {
                connect(socket);
            } catch (DidntConfigureCorrectlyException e) {
                e.printStackTrace();
            }
        }

        private void connect(Socket socket) throws DidntConfigureCorrectlyException {
            try {
                input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                output = new PrintWriter(socket.getOutputStream(),true);
            } catch (IOException e) {
                throw new DidntConfigureCorrectlyException();
            }
        }

        @Override
        public void run() {

            game = createGame();
            play();
            // rematch?
        }

        private Game createGame() {
            readInput();
            parser.setLine(line);
            GameConfig config = parser.parseGameConfig();
            return new Game(config.getBot(), config.getSize());
        }

        private void play() {
            do {
                readInput();
                parser.setLine(line);
                Point x = parser.parsePoint();
                String response = parser.parseMoveResponse(game.move(x));
                output.println(response);
            } while(!game.won());
        }

        private void readInput() {
            try { line = input.readLine(); }
            catch (IOException e) {
                System.out.println("Reading problem occurred.");
            }
        }

    }
}