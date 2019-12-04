package projectTP.weichi.client.observer;

import projectTP.weichi.client.Client;

public class SizeFrameObserver implements Observer{
    Client client;

    public SizeFrameObserver(Client c) {
        client = c;
    }
    @Override
    public void onEvent(ObservableEvent event) {
        client.createGame(event.getBot(), event.getSize());
    }
}
