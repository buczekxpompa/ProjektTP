package projectTP.weichi;

import projectTP.weichi.Client.Client;
import projectTP.weichi.Server.Server;


public class App
{
    public static void main( String[] args ) {
        Thread server = new Server();
        Thread client = new Client();
        server.start();
        client.start();
    }
}
