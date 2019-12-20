package projectTP.weichi.server.game;

import projectTP.weichi.server.blocks.Point;

import java.util.Random;


public class Game {
    BoardField[][] fields;
    BoardField[][] stateChange;
    private boolean blackPassed = false;
    private boolean whitePassed = false;
    private boolean blacksTurn = true;
    private  String id = "";

    private  Bot aiBot = null;
    private int boardSize;

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
        randomID();
    }

    public Game() {
        randomID();
    }

    private void randomID() {
        Random random = new Random();
        for(int i = 0; i < 10; i++)
            id += String.valueOf(random.nextInt(9));
    }

    public String getID() {
        return id;
    }

    public boolean getBot() {
        return aiBot != null;
    }

    private void addBot() {
        aiBot = new Bot(this);
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

        unPass();
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

    private void unPass() {
        if(blacksTurn) blackPassed = false;
        else whitePassed = false;
    }

    private String pass() {
        if(blacksTurn) blackPassed = true;
        else whitePassed = true;
        blacksTurn = !blacksTurn;
        return "pass";
    }


    // ***** RULES ***** //

    public boolean validateMove(Point point) {
        return !(dead(point) || occupied(point) || koViolation(point));
    }

    private int capture(Point point) {
        //TODO: implement
        return 0;
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

    private int countTerritory(BoardField bField) {
        //TODO: implement
        return 0;
    }

    public String countWinner() {
        if(countTerritory(BoardField.WHITE) > countTerritory(BoardField.BLACK)) return "White";
        if(countTerritory(BoardField.WHITE) < countTerritory(BoardField.BLACK)) return "Black";
        return "draw";
    }

    public Point botMove() {
        return aiBot.bestMove();
    }
}