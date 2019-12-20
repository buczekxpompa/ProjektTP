package projectTP.weichi.server.blocks;

public class GameConfig {
    private boolean bot;
    private int size;
    String id;

    public GameConfig(boolean bot, int size, String id) {
        this.bot = bot;
        this.size = size;
        this.id = id;
    }

    public GameConfig() {
        this.bot = false;
        this.size = 19;
        this.id = "";
    }

    public int getSize() {
        return size;
    }
    public boolean getBot() {
        return bot;
    }
    public String getId() {
        return id;
    }
}
