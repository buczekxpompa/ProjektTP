package projectTP.weichi.client;

import projectTP.weichi.client.frames.SizeFrame;

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

    public Client() {
        super();
        connect();
    }

    @Override
    public void run() {

        //TODO: set up the game
        new SizeFrame();
        //TODO: play

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
}
