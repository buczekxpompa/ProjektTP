package projectTP.weichi.server.game;

import org.junit.Test;
import projectTP.weichi.server.blocks.Point;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class DeadTest {
    @Test
    public void test1() {
        Game game = new Game(false, 19);
        game.fields[1][0] = BoardField.WHITE;
        game.fields[2][1] = BoardField.WHITE;
        game.fields[1][2] = BoardField.WHITE;
        game.fields[0][1] = BoardField.WHITE;
        assertFalse(game.validateMove(new Point(1,1)));
        game.move(new Point(10,10));
        assertTrue(game.validateMove(new Point(1, 1)));
    }

    @Test
    public void test2() {
        Game game = new Game(false, 19);
        game.fields[1][0] = BoardField.WHITE;
        game.fields[2][1] = BoardField.WHITE;
        game.fields[1][3] = BoardField.WHITE;
        game.fields[0][1] = BoardField.BLACK;
        assertTrue(game.validateMove(new Point(1,1)));
        game.move(new Point(10,10));
        assertTrue(game.validateMove(new Point(1, 1)));
    }

    @Test
    public void test3() {
        Game game = new Game(false, 19);
        game.fields[0][1] = BoardField.WHITE;
        game.fields[1][0] = BoardField.WHITE;
        game.fields[1][2] = BoardField.WHITE;
        game.fields[2][0] = BoardField.WHITE;
        game.fields[2][2] = BoardField.WHITE;
        game.fields[3][1] = BoardField.WHITE;
        game.fields[2][1] = BoardField.BLACK;
        assertFalse(game.validateMove(new Point(1, 1)));
    }
}
