package projectTP.weichi.server.game;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TerritoryTest {
    @Test
    public void test1() {
        Game game = new Game(false, 9);
        game.fields[0][1] = BoardField.BLACK;
        game.fields[1][1] = BoardField.BLACK;
        game.fields[1][0] = BoardField.BLACK;
        game.fields[4][3] = BoardField.WHITE;
        assertEquals(1, game.countTerritory(BoardField.BLACK));
        assertEquals(7, game.countTerritory(BoardField.WHITE));
    }

    @Test
    public void test4() {
        Game game = new Game(false, 9);
        game.fields[0][2] = BoardField.BLACK;
        game.fields[1][2] = BoardField.BLACK;
        game.fields[2][1] = BoardField.BLACK;
        game.fields[2][0] = BoardField.BLACK;

        game.fields[3][3] = BoardField.WHITE;
        assertEquals(4, game.countTerritory(BoardField.BLACK));
        assertEquals(7, game.countTerritory(BoardField.WHITE));

        game.fields[0][0] = BoardField.WHITE;
        assertEquals(0, game.countTerritory(BoardField.BLACK));
    }
}
