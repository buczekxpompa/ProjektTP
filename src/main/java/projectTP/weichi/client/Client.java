package projectTP.weichi.client;

import projectTP.weichi.client.frames.GameFrame;
import projectTP.weichi.client.frames.SizeFrame;
import projectTP.weichi.client.observer.GameFrameObserver;
import projectTP.weichi.client.observer.SizeFrameObserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client extends Thread {
    Socket socket;
    private PrintWriter output;
    private BufferedReader input;
    private GameFrame gameFrame;

    public Client() {
        connect();
        startSizeFrame();
    }

    private void startSizeFrame() {
        SizeFrame sizeFrame = new SizeFrame();
        SizeFrameObserver sizeFrameObserver = new SizeFrameObserver(this);
        sizeFrame.addObserver(sizeFrameObserver);
    }

    @Override
    public void run() {
        //TODO: play
        GameFrameObserver gameFrameObserver = new GameFrameObserver(this);
        gameFrame.addObserver(gameFrameObserver);

        // rematch?
    }

    private void connect() {
        try {
            socket = new Socket("localhost",4999);
            output = new PrintWriter(socket.getOutputStream(),true);
            input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (UnknownHostException e) {
            System.out.println("Could not connect to server");
        } catch (IOException e) {
            System.out.println("Buffers' configuration problems occurred (Client).");
        }

    }

    public void makeMove(int x, int y) {
        ClientParser parser = new ClientParserJson(x, y);
        output.println(parser.prepareMove());
    }
    public void createGame(boolean bot, int size) {
        gameFrame = new GameFrame(size);
        this.start();
        ClientParser parser = new ClientParserJson(bot, size);
        output.println(parser.prepareGameConfig());
    }
}
