package projectTP.weichi.client.observer;

import projectTP.weichi.client.Client;

public class GameFrameObserver implements Observer{
    Client client;

    public GameFrameObserver(Client c){
        client = c;
    }

    @Override
    public void onEvent(MoveEvent event) {

    }

    @Override
    public void onEvent(ObservableEvent event) {

    }
}
