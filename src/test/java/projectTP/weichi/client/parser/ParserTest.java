package projectTP.weichi.client.parser;

import org.junit.Test;
import projectTP.weichi.server.game.BoardField;
import projectTP.weichi.server.game.Game;
import projectTP.weichi.server.blocks.ColoredPoint;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class ParserTest {
    ClientParser parser = new ClientParserJson();

    @Test
    public void testMove() {
        assertEquals("{\"type\":\"move\",\"x\":\"1\",\"y\":\"4\"}", parser.prepareMove(1,4));
        assertEquals("{\"type\":\"move\",\"x\":\"19\",\"y\":\"0\"}", parser.prepareMove(19,0));
        assertEquals("{\"type\":\"move\",\"x\":\"12\",\"y\":\"5\"}", parser.prepareMove(12,5));
    }

    @Test
    public void testConfig1() {
        assertEquals("{\"type\":\"gameConfig\",\"bot\":\"true\",\"size\":\"19\"}", parser.prepareGameConfig(true, 19));
        assertEquals("{\"type\":\"gameConfig\",\"bot\":\"true\",\"size\":\"9\"}", parser.prepareGameConfig(true, 9));
        assertEquals("{\"type\":\"gameConfig\",\"bot\":\"false\",\"size\":\"13\"}", parser.prepareGameConfig(false, 13));
    }

    @Test
    public void testResponse() {
        ArrayList<ColoredPoint> coloredPoints;
        ArrayList<ColoredPoint> expected = new ArrayList<>();
        coloredPoints = parser.parseResponse("[{\"x\":\"4\",\"y\":\"6\",\"color\":\"BLACK\"}]");
        expected.add(new ColoredPoint(4,6, BoardField.BLACK));
        int counter = 0;
        for(ColoredPoint point : coloredPoints) {
            for( ColoredPoint exp : expected) {
                if (exp.getColor() == point.getColor() && exp.getX() == point.getX() && exp.getY() == point.getY())
                    counter++;
            }
        }
        assertEquals(coloredPoints.size(), counter);
    }

    @Test
    public void testResponse2() {
        ArrayList<ColoredPoint> coloredPoints;
        ArrayList<ColoredPoint> expected = new ArrayList<>();
        coloredPoints = parser.parseResponse("[{\"x\":\"6\",\"y\":\"9\",\"color\":\"WHITE\"}]");
        expected.add(new ColoredPoint(6,9, BoardField.WHITE));
        int counter = 0;
        for(ColoredPoint point : coloredPoints) {
            for( ColoredPoint exp : expected) {
                if (exp.getColor() == point.getColor() && exp.getX() == point.getX() && exp.getY() == point.getY())
                    counter++;
            }
        }
        assertEquals(coloredPoints.size(), counter);
    }

    @Test
    public void testConfig2() {
        Game game = new Game();
        assertEquals("{\"type\":\"gameConfig\",\"id\":\"" + game.getID() + "\"}", parser.prepareGameConfig(game.getID()));
    }

    @Test
    public void testGetConfig() {
        assertEquals(13, parser.parseGameConfig("{\"size\":\"13\"}"));
        assertEquals(9, parser.parseGameConfig("{\"size\":\"9\"}"));
        assertEquals(19, parser.parseGameConfig("{\"size\":\"19\"}"));
    }

    @Test
    public void testGames() {
        Game game1 = new Game(), game2 = new Game(), game3 = new Game();
        ArrayList<String> gameIDs;
        ArrayList<String> expected = new ArrayList<>();
        gameIDs = parser.parseGames("[{\"id\":\"" + game1.getID() + "\"}, {\"id\":\"" + game1.getID() + "\"}, {\"id\":\"" + game1.getID() + "\"}]");
        expected.add(game1.getID());
        expected.add(game2.getID());
        expected.add(game3.getID());
        int counter = 0;
        for(String id : gameIDs) {
            for( String exp : expected) {
                if (exp.equals(id))
                    counter++;
            }
        }
        assertEquals(gameIDs.size(), counter);
    }
}
