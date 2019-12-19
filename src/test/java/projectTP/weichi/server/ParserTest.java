package projectTP.weichi.server;

import org.junit.Test;
import projectTP.weichi.server.game.Game;
import projectTP.weichi.server.parser.ServerParser;
import projectTP.weichi.server.parser.ServerParserJson;
import projectTP.weichi.server.blocks.CombinedGame;
import projectTP.weichi.server.blocks.GameConfig;
import projectTP.weichi.server.blocks.Point;


import java.util.ArrayList;

import static org.junit.Assert.*;

public class ParserTest {
    ServerParser parser = new ServerParserJson();

    @Test
    public void testGameConfig1() {
        parser.setLine("{\"type\":\"gameConfig\",\"bot\":\"false\",\"size\":\"13\"}");
        GameConfig config = parser.parseGameConfig();
        assertFalse(config.getBot());
        assertEquals(13, config.getSize());
    }

    @Test
    public void testGameConfig2() {
        parser.setLine("{\"type\":\"gameConfig\",\"bot\":\"true\",\"size\":\"9\"}");
        GameConfig config = parser.parseGameConfig();
        assertTrue(config.getBot());
        assertEquals(9, config.getSize());
    }

    @Test
    public void testGameConfig3() {
        parser.setLine("{\"type\":\"gameConfig\",\"bot\":\"false\",\"size\":\"19\"}");
        GameConfig config = parser.parseGameConfig();
        assertFalse(config.getBot());
        assertEquals(19, config.getSize());
    }

    @Test
    public void testPoint1() {
        parser.setLine("{\"type\":\"move\",\"x\":\"1\",\"y\":\"13\"}");
        Point point = parser.parsePoint();
        assertEquals(1, point.getX());
        assertEquals(13, point.getY());
    }
    @Test
    public void testPoint2() {
        parser.setLine("{\"type\":\"move\",\"x\":\"9\",\"y\":\"9\"}");
        Point point = parser.parsePoint();
        assertEquals(9, point.getX());
        assertEquals(9, point.getY());
    }


    @Test
    public void testPoint3() {
        parser.setLine("{\"type\":\"move\",\"x\":\"14\",\"y\":\"2\"}");
        Point point = parser.parsePoint();
        assertEquals(14, point.getX());
        assertEquals(2, point.getY());
    }

    @Test
    public void testSize1() {
        assertEquals("{\"size\":\"9\"}", parser.prepareGameConfig(9));
        assertEquals("{\"size\":\"13\"}", parser.prepareGameConfig(13));
        assertEquals("{\"size\":\"19\"}", parser.prepareGameConfig(19));
    }

    @Test
    public void testAllGames1() {
        ArrayList<CombinedGame> allGames = new ArrayList<>();
        assertEquals("[]", parser.prepareGames(allGames));
        Game game1 = new Game(true, 13);
        allGames.add(new CombinedGame(game1, null));
        assertEquals("[{\"id\":\"" + game1.getID() + "\"}]", parser.prepareGames(allGames));
        Game game2 = new Game(false, 9);
        allGames.add(new CombinedGame(game2, null));
        assertEquals("[{\"id\":\"" + game1.getID() + "\"},{\"id\":\"" + game2.getID() + "\"}]", parser.prepareGames(allGames));
        Game game3 = new Game(true, 19);
        allGames.add(new CombinedGame(game3, null));
        assertEquals("[{\"id\":\"" + game1.getID() + "\"},{\"id\":\"" + game2.getID() + "\"},{\"id\":\"" + game3.getID() + "\"}]", parser.prepareGames(allGames));
    }

    @Test
    public void testWinner() {
        parser.setLine("Black");
        assertEquals("{\"winner\":\"Black\"}" , parser.parseWinner());
        parser.setLine("White");
        assertEquals("{\"winner\":\"White\"}" , parser.parseWinner());
        parser.setLine("Draw");
        assertEquals("{\"winner\":\"Draw\"}" , parser.parseWinner());
    }
}
