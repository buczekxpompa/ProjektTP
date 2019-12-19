package projectTP.weichi.server.parser;

import projectTP.weichi.server.blocks.CombinedGame;
import projectTP.weichi.server.blocks.GameConfig;
import projectTP.weichi.server.blocks.Point;

import java.util.ArrayList;

public interface ServerParser {
    /** sets string which will be parsed by another methods
     * @param line String to parse */
    void setLine(String line);
    /** parses string to point
     * @return point where player made a move*/
    Point parsePoint();
    /** parses string to GameConfig
     * @return game configuration sent by a client*/
    GameConfig parseGameConfig();
    /** parses changes in the board from game after a move
     * @return changes in a string*/
    String parseMoveResponse();
    String prepareGameConfig(int size);
    String prepareGames(ArrayList<CombinedGame> games);
    String parseWinner();
}
