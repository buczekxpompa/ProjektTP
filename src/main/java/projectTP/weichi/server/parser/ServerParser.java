package projectTP.weichi.server.parser;

import projectTP.weichi.server.support.GameConfig;
import projectTP.weichi.server.support.Point;

public interface ServerParser {
    void setLine(String line);
    Point parsePoint();
    GameConfig parseGameConfig();
    String parseMoveResponse(String response);
}
