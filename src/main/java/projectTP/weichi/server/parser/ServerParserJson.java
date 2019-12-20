package projectTP.weichi.server.parser;

import projectTP.weichi.server.blocks.CombinedGame;
import projectTP.weichi.server.blocks.GameConfig;
import projectTP.weichi.server.blocks.Point;

import java.util.ArrayList;

// make it singleton?
public class ServerParserJson implements ServerParser {
    private String parsedLine;

    public void setLine(String line) {
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
        String id = "";
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
            if(args[i].contentEquals("id")) {
                id = args[i+2];
            }
        }
        return new GameConfig(bot, size, id);
    }

    @Override
    public String parseMoveResponse() {
        StringBuilder builder = new StringBuilder();
        builder.append("[");
        String[] args = parsedLine.split("\"");
        for(String arg : args) {
            if(arg.equals("pass")){
                return "{\"pass\":1}";
            }
            if(!arg.isEmpty()) {
                String[] point = arg.split(",");
                builder.append("{\"x\":\"")
                        .append(point[0])
                        .append("\",\"y\":\"")
                        .append(point[1])
                        .append("\",\"color\":\"")
                        .append(point[2])
                        .append("\"},");
            }
        }
        builder.deleteCharAt(builder.toString().length()-1);
        builder.append("]");
        return builder.toString();
    }

    @Override
    public String prepareGameConfig(int size) {
        return "{\"size\":\"" +
                size +
                "\"}";
    }

    @Override
    public String prepareGames(ArrayList<CombinedGame> games) {
        StringBuilder out = new StringBuilder("[");
        for(CombinedGame game : games) {
            out.append("{\"id\":\"")
                    .append(game.getGame().getID())
                    .append("\"},");
        }
        if(out.lastIndexOf(",") != -1) out.deleteCharAt(out.lastIndexOf(","));
        out.append("]");
        System.out.println(out.toString());
        return out.toString();
    }

    @Override
    public String parseWinner() {
        return "{\"winner\":\"" +
                parsedLine +
                "\"}";
    }
}
