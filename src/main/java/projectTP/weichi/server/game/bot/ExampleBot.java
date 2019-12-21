package projectTP.weichi.server.game.bot;

import projectTP.weichi.server.blocks.Point;
import projectTP.weichi.server.game.BoardField;
import projectTP.weichi.server.game.Game;

public class ExampleBot implements Bot {
    Game game;

    public ExampleBot(Game game) {
        this.game = game;
    }

    public Point bestMove() {
        Point bestPoint = new Point(-2,-2);

        int best = 0;
        for(int i = 0; i < game.getSize(); i++) {
            for(int j = 0; j < game.getSize(); j++) {
                int surr = 0;
                if(game.capture(new Point(i, j))) {
                    if(game.validateMove(new Point(i, j))) {
                        bestPoint = new Point(i, j);
                        best = 4;
                    }
                } else {
                    if (i - 1 >= 0 && game.getField(i - 1, j) == BoardField.BLACK) surr++;
                    if (j - 1 >= 0 && game.getField(i, j - 1) == BoardField.BLACK) surr++;
                    if (j + 1 < game.getSize() && game.getField(i, j + 1) == BoardField.BLACK) surr++;
                    if (i + 1 < game.getSize() && game.getField(i + 1, j) == BoardField.BLACK) surr++;
                    if (surr > best && surr < 3) {
                        if (game.validateMove(new Point(i, j)))
                            bestPoint = new Point(i, j);
                    }
                }
            }
        }

        for(int i = 0; i < game.getSize(); i++) {
            for (int j = 0; j < game.getSize(); j++) {
                if(game.getField(i,j) == BoardField.WHITE) {
                    if(atari(new Point(i, j))) {
                        Point newBet = searchEscape(new Point(i, j));
                        if(game.validateMove(newBet)) {
                            bestPoint = newBet;
                        }
                    }
                }
            }
        }
        return bestPoint;
    }

    private Point searchEscape(Point point) {
        int x = point.getX();
        int y = point.getY();

        if(x-1 >= 0 && game.getField(x-1, y) == BoardField.EMPTY) return new Point(x-1, y);
        if(y-1 >= 0 && game.getField(x, y-1) == BoardField.EMPTY) return new Point(x, y-1);
        if(y+1 >= game.getSize() && game.getField(x, y+1) == BoardField.EMPTY) return new Point(x, y+1);
        if(x+1 >= game.getSize() && game.getField(x+1, y) == BoardField.EMPTY) return new Point(x+1, y);

        return null;
    }

    private boolean atari(Point point) {
        int liberties = 0;
        int x = point.getX();
        int y = point.getY();

        if(x-1 >= 0 && game.getField(x-1, y) == BoardField.EMPTY) liberties++;
        if(y-1 >= 0 && game.getField(x, y-1) == BoardField.EMPTY) liberties++;
        if(y+1 >= game.getSize() && game.getField(x, y+1) == BoardField.EMPTY) liberties++;
        if(x+1 >= game.getSize() && game.getField(x+1, y) == BoardField.EMPTY) liberties++;

        return liberties == 1;
    }
}
