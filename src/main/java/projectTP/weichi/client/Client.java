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
import projectTP.weichi.server.support.GameConfig;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class Client extends Thread {
    private String line;
    private PrintWriter output;
    private BufferedReader input;
    private GameFrame gameFrame;
    private ClientParser parser = new ClientParserJson();

    public Client() {
        connect();
        startLobby();
    }

    private void startLobby() {
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

    @Override
    public void run() {
        GameFrameObserver gameFrameObserver = new GameFrameObserver(this);
        gameFrame.addObserver(gameFrameObserver);

        // rematch?
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
        gameFrame = new GameFrame(size);
    }
    public void makeMove(int x, int y) {
        output.println(parser.prepareMove(x, y));
        readInput();
        ArrayList<ColoredPoint> changes = parser.parseResponse(line);
        gameFrame.updateState(changes);
    }
    public void createGame(boolean bot, int size) {
        gameFrame = new GameFrame(size);
        this.start();
        output.println(parser.prepareGameConfig(bot, size));
    }
    private void readInput() {
        try { line = input.readLine(); }
        catch (IOException e) {
            System.out.println("Reading problem occurred.");
        }
    }
}
