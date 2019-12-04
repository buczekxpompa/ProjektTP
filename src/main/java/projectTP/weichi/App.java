package projectTP.weichi;


import projectTP.weichi.client.Client;
import projectTP.weichi.server.Server;

public class App
{
    public static void main( String[] args ) {
        Thread server = new Server();
        new Client();
        server.start();
    }
}
