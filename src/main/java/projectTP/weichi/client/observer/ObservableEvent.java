package projectTP.weichi.client.observer;

public class ObservableEvent {
    private int x;
    private int y;
    private boolean bot;
    private int size;
    private String id;

    public ObservableEvent(boolean bot, int size) {
        x = -1;
        y = -1;
        this.bot = bot;
        this.size = size;
        this.id = "";
    }

    public ObservableEvent(int x, int y) {
        this.x = x;
        this.y = y;
        bot = false;
        size = -1;
        this.id = "";
    }

    public ObservableEvent(String id) {
        this.x = -1;
        this.y = -1;
        bot = false;
        size = -1;
        this.id = id;
    }

    public String getId() {
        return id;
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
