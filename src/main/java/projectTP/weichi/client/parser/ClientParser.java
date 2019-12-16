package projectTP.weichi.client.parser;

import projectTP.weichi.server.support.ColoredPoint;

import java.util.ArrayList;

public interface ClientParser {
    String prepareMove(int x, int y);
    String prepareGameConfig(boolean bot, int size);
    ArrayList<ColoredPoint> parseResponse(String line);
}
