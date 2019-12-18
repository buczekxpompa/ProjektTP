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

    //zajmowane pola (otaczane przez przeciwnika)
    private capture(Point point) {
        //TODO: implement
        if(blacksTurn && move(Point point) == 0) {
            return {fields[point.getX()][point.getY()] = BoardField.EMPTY;}
        }
    }

    //martwe pole jak zwroci 0 to jest martwe a jak co innego to bedzie git //nie ma oddechów
    private boolean dead(Point point) {
        //TODO: implement
        if(move(Point point) == 0)
        {
            return false;
        }
        else return true;
    }

    //KO rule
    private boolean koViolation(Point point) {
        //TODO: implement
        if(blacksTurn && point.getX() == 0 && point.getY() == 0 && !blacksTurn && point.getX() == 0  && point.getY() == 0)
        {
            System.out.println("Forbidden move");
        }
        return false;
    }

    private boolean occupied(Point point) {
        int x = point.getX();
        int y = point.getY();
        return fields[x][y] != BoardField.EMPTY;
    }


}