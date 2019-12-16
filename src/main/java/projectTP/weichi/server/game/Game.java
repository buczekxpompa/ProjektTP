package projectTP.weichi.server.game;

import projectTP.weichi.server.support.Point;

import java.util.Random;


public class Game {
    BoardField[][] fields;
    BoardField[][] stateChange;
    boolean blackPassed = false;
    boolean whitePassed = false;
    boolean blacksTurn = true;
    public int id;


    Bot aiBot = null;
    int boardSize;

    public Game(boolean bot, int size) {
        if(bot) addBot();
        boardSize = size;
        fields = new BoardField[size][size];
        stateChange = new BoardField[size][size];
        for(int i = 0; i < boardSize; i++) {
            for(int j = 0; j < boardSize; j++){
                fields[i][j] = BoardField.EMPTY;
            }
        }

    }

    private void addBot() {
        aiBot = new Bot();
    }

    public boolean won() {
        return blackPassed && whitePassed;
    }
    public String move(Point point) {
        StringBuilder output = new StringBuilder();
        if(!validateMove(point)) return output.toString();

        for(int i = 0; i < boardSize; i++) {
            System.arraycopy(fields[i], 0, stateChange[i], 0, boardSize);
        }

        if(blacksTurn) fields[point.getX()][point.getY()] = BoardField.BLACK;
        else fields[point.getX()][point.getY()] = BoardField.WHITE;
        capture(point);

        for(int i = 0; i < boardSize; i++) {
            for(int j = 0; j < boardSize; j++){
                if(stateChange[i][j] != fields[i][j]){
                    output.append("\"")
                            .append(i)
                            .append(",")
                            .append(j)
                            .append(",")
                            .append(fields[i][j])
                            .append("\"");
                }
            }
        }

        blacksTurn = !blacksTurn;
        return output.toString();
    }


    // ***** RULES ***** //

    private boolean validateMove(Point point) {
        if(!occupied(point)) return false;
        if(!koViolation(point)) return false;
        return dead(point);
    }

    private void capture(Point point) {
        //TODO: implement
    }

    private boolean dead(Point point) {
        //TODO: implement
        return true;
    }

    private boolean koViolation(Point point) {
        //TODO: implement
        return true;
    }

    private boolean occupied(Point point) {
        int x = point.getX();
        int y = point.getY();
        return fields[x][y] == BoardField.EMPTY;
    }


}