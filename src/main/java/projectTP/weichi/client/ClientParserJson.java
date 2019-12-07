package projectTP.weichi.client;

public class ClientParserJson implements ClientParser{
    int x = -1;
    int y = -1;
    boolean bot = false;
    int size = -1;

    ClientParserJson(int x, int y) {
        this.x = x;
        this.y = y;
    }
    ClientParserJson(boolean bot, int size) {
        this.bot = bot;
        this.size = size;
    }
    @Override
    public String prepareMove() {
        StringBuilder builder = new StringBuilder();
        builder.append("{\"type\":\"move\",\"x\":\"" );
        builder.append(x);
        builder.append("\",\"y\":\"");
        builder.append(y);
        builder.append("\"}");
        System.out.println(builder.toString());
        return builder.toString();
    }

    @Override
    public String prepareGameConfig() {
        StringBuilder builder = new StringBuilder();
        builder.append("{\"type\":\"gameConfig\",\"bot\":\"" );
        builder.append(bot);
        builder.append("\",\"size\":\"");
        builder.append(size);
        builder.append("\"}");
        System.out.println(builder.toString());
        return builder.toString();
    }
}
