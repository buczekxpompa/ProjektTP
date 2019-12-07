package projectTP.weichi.server;

import projectTP.weichi.server.support.GameConfig;
import projectTP.weichi.server.support.Point;

public interface ServerParser {
    Point parsePoint();
    GameConfig parseGameConfig();
    String parseMoveResponse(String response);
}
