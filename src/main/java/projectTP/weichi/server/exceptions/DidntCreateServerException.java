package projectTP.weichi.server.exceptions;

public class DidntCreateServerException extends Exception {
    int port;

    public DidntCreateServerException(int port){
        super();
        this.port = port;
    }


    public int getPort() {
        return port;
    }
}
