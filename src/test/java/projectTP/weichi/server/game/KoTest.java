package projectTP.weichi.server.game;

import org.junit.Test;
import projectTP.weichi.server.blocks.Point;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class KoTest {
    @Test
    public void testKo() {
        Game game = new Game(false, 19);
        game.move(new Point(6,6));
        game.move(new Point(6,7));
        game.move(new Point(5,5));
        game.move(new Point(5,8));
        game.move(new Point(4,6));
        game.move(new Point(4,7));
        game.move(new Point(5,7));
        game.move(new Point(5,6));
        assertFalse(game.validateMove(new Point(5, 7)));
        game.move(new Point(0,0));
        game.move(new Point(1,1));
        assertTrue(game.validateMove(new Point(5, 7)));
    }

    @Test
    public void testNotKo() {
        Game game = new Game(false, 19);
        game.move(new Point(2, 1));
        game.move(new Point(0, 1));
        game.move(new Point(3, 0));
        game.move(new Point(1, 0));
        game.move(new Point(3, 2));
        game.move(new Point(1, 2));
        game.move(new Point(4, 1));
        game.move(new Point(2, 0));
        game.move(new Point(1, 1));
        game.move(new Point(2, 2));

        game.move(new Point(10, 10));
        game.move(new Point(3, 1));
        assertTrue(game.validateMove(new Point(2, 1)));
    }
}
