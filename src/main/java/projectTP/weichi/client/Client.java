package projectTP.weichi.client;


import projectTP.weichi.client.frames.GameFrame;
import projectTP.weichi.client.frames.Lobby;
import projectTP.weichi.client.frames.SizeFrame;
import projectTP.weichi.client.observer.GameFrameObserver;
import projectTP.weichi.client.observer.LobbyObserver;
import projectTP.weichi.client.observer.SizeFrameObserver;
import projectTP.weichi.client.parser.ClientParser;
import projectTP.weichi.client.parser.ClientParserJson;
import projectTP.weichi.server.support.ColoredPoint;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class Client {
    private boolean reading = false;
    private String line;
    private PrintWriter output;
    private BufferedReader input;
    private GameFrame gameFrame;
    private ClientParser parser = new ClientParserJson();

    public Client() {
        connect();
        startLobby();
    }

    public void startLobby() {
        readInput();
        Lobby lobby = new Lobby(parser.parseGames(line));
        LobbyObserver lobbyObserver = new LobbyObserver(this);
        lobby.addObserver(lobbyObserver);
    }

    public void startSizeFrame() {
        SizeFrame sizeFrame = new SizeFrame();
        SizeFrameObserver sizeFrameObserver = new SizeFrameObserver(this);
        sizeFrame.addObserver(sizeFrameObserver);
    }

    private void connect() {
        try {
            Socket socket = new Socket("localhost", 4999);
            output = new PrintWriter(socket.getOutputStream(),true);
            input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (UnknownHostException e) {
            System.out.println("Could not connect to server");
        } catch (IOException e) {
            System.out.println("Buffers' configuration problems occurred (Client).");
        }

    }

    public void joinGame(String id) {
        output.println(parser.prepareGameConfig(id));
        readInput();
        int size = parser.parseGameConfig(line);
        gameFrame = new GameFrame(size, "BLACK", id);
        gameFrame.addObserver( new GameFrameObserver(this));
        Thread thread = new Thread() {
            @Override
            public void run() {
                readInput();
                ArrayList<ColoredPoint> changes = parser.parseResponse(line);
                gameFrame.updateState(changes);
            }
        };
        thread.start();
    }
    public void makeMove(final int x, final int y) {
        Thread thread = new Thread() {
            @Override
            public void run() {
                ArrayList<ColoredPoint> changes;
                output.println(parser.prepareMove(x, y));
                readInput();
                changes = parser.parseResponse(line);
                if(gameFrame.updateState(changes)) {
                    gameFrame.revalidate();
                    do {
                        readInput();
                        changes = parser.parseResponse(line);
                    } while(!gameFrame.updateState(changes));
                    gameFrame.revalidate();
                }
            }

        };
        thread.start();
    }
    public void createGame(final boolean bot, int size) {
        output.println(parser.prepareGameConfig(bot, size));
        readInput();
        gameFrame = new GameFrame(size, "BLACK", line);
        gameFrame.addObserver( new GameFrameObserver(this));
        if (!bot) {
            Thread thread = new Thread() {
                @Override
                public void run() {
                    readInput();
                }
            };
            thread.start();
        }
    }
    private void readInput() {
        System.out.println("waiting for input");
        reading = true;
        try { line = input.readLine(); }
        catch (IOException e) {
            System.out.println("Reading problem occurred.");
        }
        reading = false;
        System.out.println("got it \n");
    }

    public boolean isReading() {
        return reading;
    }
}