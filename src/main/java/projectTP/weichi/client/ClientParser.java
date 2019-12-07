package projectTP.weichi.client;

import projectTP.weichi.server.support.ColoredPoint;

import java.util.ArrayList;

public interface ClientParser {
    String prepareMove();
    String prepareGameConfig();
    ArrayList<ColoredPoint> parseResponse(String line);
}
