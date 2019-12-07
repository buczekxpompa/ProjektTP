package projectTP.weichi.server;

import projectTP.weichi.server.support.GameConfig;
import projectTP.weichi.server.support.Point;

public interface ServerParser {
    String line = "";
    Point parsePoint();
    GameConfig parseGameConfig();

}
