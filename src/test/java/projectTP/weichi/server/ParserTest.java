package projectTP.weichi.server;

import org.junit.Test;
import projectTP.weichi.server.parser.ServerParser;
import projectTP.weichi.server.parser.ServerParserJson;
import projectTP.weichi.server.support.GameConfig;
import projectTP.weichi.server.support.Point;

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



}
