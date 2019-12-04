package projectTP.weichi.client.observer;

public class ObservableEvent {
    int x;
    int y;
    boolean bot;
    int size;

    public ObservableEvent(boolean bot, int size) {
        x = -1;
        y = -1;
        this.bot = bot;
        this.size = size;
    }

    public ObservableEvent(int x, int y) {
        this.x = x;
        this.y = y;
        bot = false;
        size = -1;
    }

    public int getSize() {
        return size;
    }

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }
    public boolean getBot() {
        return bot;
    }
}
