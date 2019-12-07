package projectTP.weichi.server;

import projectTP.weichi.server.support.GameConfig;
import projectTP.weichi.server.support.Point;

// make it singleton?
public class ServerParserJson implements ServerParser {
    private String parsedLine;

    ServerParserJson(String line) {
        this.parsedLine = line;
    }

    @Override
    public Point parsePoint() {
        int x = -1;
        int y = -1;
        String[] args = parsedLine.split("\"");
        for(int i = 0; i < args.length; i++) {

            if(args[i].contentEquals("x")) {
                try { x = Integer.parseInt(args[i+2]); }
                catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
            if(args[i].contentEquals("y")) {
                try { y = Integer.parseInt(args[i+2]); }
                catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
        }
        return new Point(x, y);
    }

    public GameConfig parseGameConfig() {
        boolean bot = true;
        int size = -1;
        String[] args = parsedLine.split("\"");
        for(int i = 0; i < args.length; i++) {
            if(args[i].contentEquals("bot")) {
                try { bot = Boolean.parseBoolean(args[i+2]); }
                catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
            if(args[i].contentEquals("size")) {
                try { size = Integer.parseInt(args[i+2]); }
                catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
        }
        return new GameConfig(bot, size);
    }
}
