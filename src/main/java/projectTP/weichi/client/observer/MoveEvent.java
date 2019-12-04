package projectTP.weichi.client.observer;


public class MoveEvent extends ObservableEvent {
    int x;
    int y;

    MoveEvent(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
