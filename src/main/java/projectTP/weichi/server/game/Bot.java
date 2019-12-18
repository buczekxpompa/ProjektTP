package projectTP.weichi.server.game;

import projectTP.weichi.server.support.Point;

public class Bot {
    Game game;

    Bot(Game game) {
        this.game = game;
    }

    Point bestMove() {
        int best = 0;
        Point bestPoint = new Point(-2,-2);

        for(int i = 0; i < game.getSize(); i++){
            for(int j = 0; j < game.getSize(); j++){
                if(evaluate(i, j) > best) {
                    if(game.validateMove(new Point(i,j)))
                        bestPoint = new Point(i, j);
                }
            }
        }

        return bestPoint;
    }

    private int evaluate(int i, int j) {
        int counter = 0;
        for(int a = 0; a < game.getSize(); a++){
            if(game.fields[i][a] != BoardField.BLACK)
                counter++;
            else {
                counter = 0;
                break;
            }
        }
        for(int a = 0; a < game.getSize(); a++){
            if(game.fields[a][j] != BoardField.BLACK)
                counter++;
            else {
                counter = 0;
                break;
            }
        }
        return counter;
    }
}
