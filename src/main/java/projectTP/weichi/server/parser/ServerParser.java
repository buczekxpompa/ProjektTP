package projectTP.weichi.server.parser;

import projectTP.weichi.server.game.Game;
import projectTP.weichi.server.support.GameConfig;
import projectTP.weichi.server.support.Point;

import java.util.ArrayList;

public interface ServerParser {
    void setLine(String line);
    Point parsePoint();
    GameConfig parseGameConfig();
    String parseMoveResponse(String response);
    String prepareGameConfig(int size);
    String prepareGames(ArrayList<Game> games);
}
