package projectTP.weichi.server.support;

public class GameConfig {
    private boolean bot;
    private int size;

    public GameConfig(boolean bot, int size) {
        this.bot = bot;
        this.size = size;
    }

    public GameConfig() {
        this.bot = false;
        this.size = 19;
    }

    public int getSize() {
        return size;
    }
    public boolean getBot() {
        return bot;
    }
}
