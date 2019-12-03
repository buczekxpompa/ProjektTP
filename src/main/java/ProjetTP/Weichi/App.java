package ProjetTP.Weichi;

import ProjetTP.Weichi.Client.Client;
import ProjetTP.Weichi.Server.Server;

public class App
{
    public static void main( String[] args ) {
        Thread server = new Server();
        Thread client = new Client();
        server.start();
        client.start();
    }
}
