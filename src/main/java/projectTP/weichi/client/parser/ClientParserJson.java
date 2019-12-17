package projectTP.weichi.client.parser;

import projectTP.weichi.server.game.BoardField;
import projectTP.weichi.server.support.ColoredPoint;
import projectTP.weichi.server.support.GameConfig;

import java.util.ArrayList;

public class ClientParserJson implements ClientParser{

    @Override
    public String prepareMove(int x, int y) {
        return "{\"type\":\"move\",\"x\":\"" +
                x +
                "\",\"y\":\"" +
                y +
                "\"}";
    }

    @Override
    public String prepareGameConfig(boolean bot, int size) {
        return "{\"type\":\"gameConfig\",\"bot\":\"" +
                bot +
                "\",\"size\":\"" +
                size +
                "\"}";
    }

    @Override
    public ArrayList<ColoredPoint> parseResponse(String line) {
        System.out.println(line);
        ArrayList<ColoredPoint> output = new ArrayList<>();
        String[] args = line.split("}");
        for(String arg: args) {
            String[] point = arg.split("\"");
            int x = -1;
            int y = -1;
            BoardField color = BoardField.EMPTY;
            for(int i = 0; i < point.length; i++) {
                if(point[i].contentEquals("x")) {
                    try {
                        x = Integer.parseInt(point[i + 2]);
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                } else if(point[i].contentEquals("y")) {
                    try {
                        y = Integer.parseInt(point[i + 2]);
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                } else if(point[i].contentEquals("color")) {
                    String colorStr = point[i+2];
                    switch(colorStr) {
                        case "BLACK": color = BoardField.BLACK;
                            break;
                        case "WHITE": color = BoardField.WHITE;
                            break;
                        case "EMPTY": color = BoardField.EMPTY;
                    }
                } else if(point[i].contentEquals("pass")) {
                    output.add(new ColoredPoint(-2, -2, BoardField.BLACK));
                    output.add(new ColoredPoint(-2, -2, BoardField.WHITE));
                }
            }
            output.add(new ColoredPoint(x, y , color));
        }

        return output;
    }

    @Override
    public String prepareGameConfig(String id) {
        return  "{\"type\":\"gameConfig\",\"id\":\"" +
                id +
                "\"}";
    }

    @Override
    public int parseGameConfig(String line) {
        int out = 0;
        String[] args = line.split("\"");
        for(int i = 0; i < args.length; i++ ) {
            if(args[i].equals("size")) {
                try { out = Integer.parseInt(args[i+2]); }
                catch (NumberFormatException ignored) {}
            }
        }
        return out;
    }

    @Override
    public ArrayList<String> parseGames(String line) {
        ArrayList<String> output = new ArrayList<>();
        String[] objs = line.split(",");
        for( String obj : objs) {
            String[] args = obj.split("\"");
            for(int i = 0; i < args.length; i++) {
                if(args[i].equals("id")) {
                    output.add(args[i+2]);
                }
            }
        }
        System.out.println(line);
        return output;
    }


}
