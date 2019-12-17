package projectTP.weichi.server;

import projectTP.weichi.server.exceptions.*;
import projectTP.weichi.server.game.Game;
import projectTP.weichi.server.parser.ServerParser;
import projectTP.weichi.server.parser.ServerParserJson;
import projectTP.weichi.server.support.CombinedGame;
import projectTP.weichi.server.support.GameConfig;
import projectTP.weichi.server.support.Point;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {
    private ServerSocket server;
    private ArrayList<CombinedGame> allGames = new ArrayList<>();

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


    public class ServerThread extends Thread {
        private BufferedReader input;
        private PrintWriter output;
        private PrintWriter player2;
        private String line;
        private ServerParser parser = new ServerParserJson();
        private Game game;
        private boolean join;

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
            output.println(parser.prepareGames(allGames));
            while (true){
                game = createGame();
                if(join)
                    playJoin();
                else
                    playBot();
            }
        }

        private void playJoin() {
            do {
                readInput();
                parser.setLine(line);
                Point x = parser.parsePoint();
                String response = parser.parseMoveResponse(game.move(x));
                output.println(response);
                player2.println(response);
            } while(!game.won());
        }

        private void playBot() {
            do {
                readInput();
                parser.setLine(line);
                Point x = parser.parsePoint();
                String response = parser.parseMoveResponse(game.move(x));
                output.println(response);
            } while(!game.won());
        }

        private Game createGame() {
            readInput();
            parser.setLine(line);
            GameConfig config = parser.parseGameConfig();

            if(!config.getId().equals("")) {
                for (CombinedGame x : allGames) {
                    if(config.getId().equals(x.getGame().getID())) {
                        output.println(parser.prepareGameConfig(x.getGame().getSize()));
                        player2 = x.getPlayer().getOutput();
                        x.getPlayer().setPlayer2(output);
                        join = true;
                        return x.getGame();
                    }
                }
            }

            Game g = new Game(config.getBot(), config.getSize());

            if(!g.getBot()) {
                allGames.add(new CombinedGame(g, this));
                join = true;
            } else join = false;
            return g;
        }

        private PrintWriter getOutput() {
            return output;
        }

        public void setPlayer2(PrintWriter player2) {
            this.player2 = player2;
        }

        private void readInput() {
            try {
                line = input.readLine();
                            }
            catch (IOException ignored) {}
        }

    }
}