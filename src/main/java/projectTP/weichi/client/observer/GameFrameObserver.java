package projectTP.weichi.client.observer;

import org.graalvm.compiler.lir.sparc.SPARCMove;
import projectTP.weichi.client.Client;

public class GameFrameObserver implements Observer{
    Client client;

    public GameFrameObserver(Client c){
        client = c;
    }

    @Override
    public void onEvent(ObservableEvent event) {
        client.makeMove(event.getX(), event.getY());
    }

}
