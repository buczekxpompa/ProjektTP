package projectTP.weichi.client;

import projectTP.weichi.server.game.BoardField;
import projectTP.weichi.server.support.ColoredPoint;
import projectTP.weichi.server.support.Point;

import java.util.ArrayList;

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

    @Override
    public ArrayList<ColoredPoint> parseResponse(String line) {
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
                }
            }
            output.add(new ColoredPoint(x, y , color));
        }

        return output;
    }


}
