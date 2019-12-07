package projectTP.weichi.server.game;

import org.junit.Test;
import projectTP.weichi.server.support.Point;

import static org.junit.Assert.assertEquals;

public class MoveTest {

    @Test
    public void moveTest() {
        Game game = new Game(false, 19);
        String output = game.move(new Point(4,6));
        assertEquals("\"(4,6)BLACK\"", output);
        output = game.move(new Point(6, 9));
        assertEquals("\"(6,9)WHITE\"", output);
    }
}
