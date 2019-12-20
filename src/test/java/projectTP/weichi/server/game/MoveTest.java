package projectTP.weichi.server.game;

import org.junit.Test;
import projectTP.weichi.server.parser.ServerParser;
import projectTP.weichi.server.parser.ServerParserJson;
import projectTP.weichi.server.blocks.Point;

import static org.junit.Assert.assertEquals;

public class MoveTest {

    @Test
    public void moveTest1() {
        Game game = new Game(false, 19);
        String output = game.move(new Point(4,6));
        assertEquals("\"4,6,BLACK\"", output);
        output = game.move(new Point(6, 9));
        assertEquals("\"6,9,WHITE\"", output);
    }

    @Test
    public void moveTestResponse1() {
        Game game = new Game(false, 19);
        String output = game.move(new Point(4,6));
        ServerParser parser = new ServerParserJson();
        parser.setLine(output);
        assertEquals("[{\"x\":\"4\",\"y\":\"6\",\"color\":\"BLACK\"}]", parser.parseMoveResponse());
        output = game.move(new Point(6, 9));
        parser.setLine(output);
        assertEquals("[{\"x\":\"6\",\"y\":\"9\",\"color\":\"WHITE\"}]", parser.parseMoveResponse());
    }

}
