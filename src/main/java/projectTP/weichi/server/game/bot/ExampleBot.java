package projectTP.weichi.server.game.bot;

import projectTP.weichi.server.blocks.Point;
import projectTP.weichi.server.game.BoardField;
import projectTP.weichi.server.game.Game;

import java.util.ArrayList;

public class ExampleBot implements Bot {
    Game game;
    ArrayList<Point> pot = new ArrayList<>();
    ArrayList<Point> visited = new ArrayList<>();

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
                        System.out.println("capture: " + bestPoint.getX() + " " + bestPoint.getY());
                        best = 4;
                    }
                } else {
                    if (i - 1 >= 0 && game.getField(i - 1, j) == BoardField.BLACK) surr++;
                    if (j - 1 >= 0 && game.getField(i, j - 1) == BoardField.BLACK) surr++;
                    if (j + 1 < game.getSize() && game.getField(i, j + 1) == BoardField.BLACK) surr++;
                    if (i + 1 < game.getSize() && game.getField(i + 1, j) == BoardField.BLACK) surr++;
                    if (surr > best && surr < 3) {
                        if (game.validateMove(new Point(i, j))) {
                            bestPoint = new Point(i, j);
                            System.out.println("best: " + bestPoint.getX() + " " + bestPoint.getY());
                        }
                    }
                }
            }
        }

        for(int i = 0; i < game.getSize(); i++) {
            for (int j = 0; j < game.getSize(); j++) {
                if(game.getField(i,j) == BoardField.WHITE) {
                    visited = new ArrayList<>();
                    pot = new ArrayList<>();
                    if(atari(new Point(i, j)) == 1) {
                        Point newBet = searchEscape(new Point(i, j));
                        if(newBet != null) {
                            if (game.validateMove(newBet)) {
                                bestPoint = newBet;
                                System.out.println("defence: " + bestPoint.getX() + " " + bestPoint.getY());
                            }
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
        if(y+1 < game.getSize() && game.getField(x, y+1) == BoardField.EMPTY) return new Point(x, y+1);
        if(x+1 < game.getSize() && game.getField(x+1, y) == BoardField.EMPTY) return new Point(x+1, y);

        return null;
    }

    private int atari(Point point) {
        int liberties = 0;
        int x = point.getX();
        int y = point.getY();

        if(x-1 >= 0 && game.getField(x-1, y) == BoardField.EMPTY) liberties++;
        if(y-1 >= 0 && game.getField(x, y-1) == BoardField.EMPTY) liberties++;
        if(y+1 < game.getSize() && game.getField(x, y+1) == BoardField.EMPTY) liberties++;
        if(x+1 < game.getSize() && game.getField(x+1, y) == BoardField.EMPTY) liberties++;

        if(x-1 >= 0 && game.getField(x-1, y) == BoardField.WHITE) pot.add(new Point(x-1, y));
        if(y-1 >= 0 && game.getField(x, y-1) == BoardField.WHITE) pot.add(new Point(x, y-1));
        if(y+1 < game.getSize() && game.getField(x, y+1) == BoardField.WHITE) pot.add(new Point(x, y+1));
        if(x+1 < game.getSize() && game.getField(x+1, y) == BoardField.WHITE) pot.add(new Point(x+1, y));

        for(int i = 0; i < pot.size(); i++) {
            boolean visit = false;
            for (Point value : visited) {
                if (pot.get(i).getX() == value.getX() && pot.get(i).getY() == value.getY()) {
                    visit = true;
                    break;
                }
            }
            if(!visit) {
                visited.add(pot.get(i));
                liberties += atari(pot.get(i));
                pot.remove(pot.get(i));
            }
        }

        return liberties;
    }
}
