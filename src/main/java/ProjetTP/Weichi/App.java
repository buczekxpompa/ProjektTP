package ProjetTP.Weichi;


import ProjetTP.Weichi.client.Client;
import ProjetTP.Weichi.server.Server;

public class App
{
    public static void main( String[] args ) {
        Thread server = new Server();
        Thread client = new Client();
        server.start();
        client.start();
    }
}
