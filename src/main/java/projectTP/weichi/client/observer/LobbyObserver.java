package projectTP.weichi.client.observer;

import projectTP.weichi.client.Client;

public class LobbyObserver implements Observer {
    private Client client;

    public LobbyObserver(Client c) {
        this.client = c;
    }
    @Override
    public void onEvent(ObservableEvent event) {
        if(!event.getId().equals("0")) client.joinGame(event.getId());
        else client.startSizeFrame();
    }
}
