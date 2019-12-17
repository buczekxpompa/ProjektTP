package projectTP.weichi.server.game;

import projectTP.weichi.server.support.Point;

import java.nio.charset.Charset;
import java.util.Random;


public class Game {
    BoardField[][] fields;
    BoardField[][] stateChange;
    boolean blackPassed = false;
    boolean whitePassed = false;
    boolean blacksTurn = true;
    public String id;

    Bot aiBot = null;
    int boardSize;

    public Game(boolean bot, int size) {
        if(bot) addBot();
        else addPlayer();
        boardSize = size;
        fields = new BoardField[size][size];
        stateChange = new BoardField[size][size];
        for(int i = 0; i < boardSize; i++) {
            for(int j = 0; j < boardSize; j++){
                fields[i][j] = BoardField.EMPTY;
            }
        }
        randomID();
    }

    private void addPlayer() {

    }

    private void randomID() {
        byte[] bytes = new byte[10];
        Random random = new Random();
        random.nextBytes(bytes);
        id = new String(bytes, Charset.defaultCharset());
    }

    public String getID() {
        return id;
    }

    public boolean getBot() {
        return aiBot != null;
    }

    private void addBot() {
        aiBot = new Bot();
    }

    public int getSize() {
        return boardSize;
    }

    public boolean won() {
        return blackPassed && whitePassed;
    }
    public String move(Point point) {
        StringBuilder output = new StringBuilder();
        if(point.getX() == -2 && point.getY() == -2) return pass();
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

    private String pass() {
        if(blacksTurn) blackPassed = true;
        else whitePassed = true;
        blacksTurn = !blacksTurn;
        return "pass";
    }


    // ***** RULES ***** //

    private boolean validateMove(Point point) {
        return !(dead(point) || occupied(point) || koViolation(point));
    }

    private void capture(Point point) {
        //TODO: implement
    }

    private boolean dead(Point point) {
        //TODO: implement
        return false;
    }

    private boolean koViolation(Point point) {
        //TODO: implement
        return false;
    }

    private boolean occupied(Point point) {
        int x = point.getX();
        int y = point.getY();
        return fields[x][y] != BoardField.EMPTY;
    }


}