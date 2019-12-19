package projectTP.weichi.server;

import projectTP.weichi.server.exceptions.*;
import projectTP.weichi.server.game.Game;
import projectTP.weichi.server.parser.ServerParser;
import projectTP.weichi.server.parser.ServerParserJson;
import projectTP.weichi.server.blocks.CombinedGame;
import projectTP.weichi.server.blocks.GameConfig;
import projectTP.weichi.server.blocks.Point;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {
    protected ServerSocket server;
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

    protected void serverLoop() throws DidntConnectException{
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

    protected void makeServer() throws DidntCreateServerException {
        int port = 4999;
        try {
            server = new ServerSocket(port);
            System.out.println("server open on port: " + port);
        } catch (IOException e) {
            throw new DidntCreateServerException(port);
        }
    }


    public class ServerThread extends Thread {
        protected BufferedReader input;
        protected PrintWriter output;
        private PrintWriter player2;
        protected String line;
        private ServerParser parser = new ServerParserJson();
        private Game game;
        private boolean join;
        CombinedGame sharedGame;

        ServerThread(Socket socket) {
            try {
                connect(socket);
            } catch (DidntConfigureCorrectlyException e) {
                e.printStackTrace();
            }
        }

        protected void connect(Socket socket) throws DidntConfigureCorrectlyException {
            try {
                input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                output = new PrintWriter(socket.getOutputStream(),true);
            } catch (IOException e) {
                throw new DidntConfigureCorrectlyException();
            }
        }

        @Override
        public void run() {
            do {
                output.println(parser.prepareGames(allGames));
                game = createGame();
            } while(game == null);
            if(join)
                playJoin();
            else
                playBot();
        }

        private void playJoin() {
            if(sharedGame != null) {
                readInput();
                allGames.remove(sharedGame);
            }
            do {
                readInput();
                parser.setLine(line);
                Point x = parser.parsePoint();
                parser.setLine(game.move(x));
                String response = parser.parseMoveResponse();
                output.println(response);
                player2.println(response);
            } while(!game.won());

            String winner = game.countWinner();
            parser.setLine(winner);
            String res = parser.parseWinner();
            output.println(res);
            player2.println(res);
            output.println(res);
            player2.println(res);
        }

        private void playBot() {
            do {
                readInput();
                parser.setLine(line);
                Point x = parser.parsePoint();
                parser.setLine(game.move(x));
                String response = parser.parseMoveResponse();
                output.println(response);

                x = game.botMove();
                parser.setLine(game.move(x));
                response = parser.parseMoveResponse();
                output.println(response);
            } while(!game.won());

            String winner = game.countWinner();
            parser.setLine(winner);
            String res = parser.parseWinner();
            output.println(res);
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
                        player2.println("gotow");
                        return x.getGame();
                    }
                }
            }
            Game g = null;
            if(config.getSize() > 0) {
                g = new Game(config.getBot(), config.getSize());
                output.println(g.getID());

                if (!g.getBot()) {
                    sharedGame = new CombinedGame(g, this);
                    allGames.add(sharedGame);
                    join = true;
                } else join = false;
            }
            return g;
        }

        private PrintWriter getOutput() {
            return output;
        }

        public void setPlayer2(PrintWriter player2) {
            this.player2 = player2;
        }

        protected void readInput() {
            try {
                line = input.readLine();
            }
            catch (IOException ex) {
                output.println("you won");
                player2.println("you won");
            }
        }

    }
}