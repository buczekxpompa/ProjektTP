package projectTP.weichi.client.observer;

public interface Observer {
    void onEvent(MoveEvent event);

    void onEvent(ObservableEvent event);
}
