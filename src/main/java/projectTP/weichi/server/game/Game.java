package projectTP.weichi.server.game;

import projectTP.weichi.server.blocks.Point;

import java.util.ArrayList;
import java.util.Random;


public class Game {
    BoardField[][] fields;
    BoardField[][] stateChange;
    private boolean blackPassed = false;
    private boolean whitePassed = false;
    private boolean blacksTurn = true;
    private  String id = "";

    private ArrayList<Point> visited = new ArrayList<>();
    private ArrayList<Point> pot = new ArrayList<>();
    private Point origin;
    private BoardField[][][] twoLastMoves;
    private Point[] twoLastPoints = new Point[]{new Point(0, 0), new Point(0, 0)};

    private  Bot aiBot = null;
    private int boardSize;
    private int blackPoints = 0;
    private int whitePoints = 7;

    public Game(boolean bot, int size) {
        if(bot) addBot();
        boardSize = size;
        fields = new BoardField[size][size];
        stateChange = new BoardField[size][size];
        twoLastMoves = new BoardField[2][size][size];
        for(int i = 0; i < boardSize; i++) {
            for(int j = 0; j < boardSize; j++){
                fields[i][j] = BoardField.EMPTY;
                twoLastMoves[0][i][j] = BoardField.EMPTY;
                twoLastMoves[1][i][j] = BoardField.EMPTY;
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
        int x = point.getX();
        int y = point.getY();
        StringBuilder output = new StringBuilder();
        if(x == -2 && y == -2) return pass();
        if(!validateMove(point)) return output.toString();

        unPass();
        for(int i = 0; i < boardSize; i++) {
            System.arraycopy(fields[i], 0, stateChange[i], 0, boardSize);
        }

        if(blacksTurn) fields[x][y] = BoardField.BLACK;
        else fields[x][y] = BoardField.WHITE;


        int capturePoints = 0;
        if(blacksTurn) {
            if(x-1 >= 0 && fields[x-1][y] == BoardField.WHITE && preDead(new Point(x-1, y), true)) capturePoints += kill(new Point(x-1, y));
            if(x+1 < boardSize && fields[x+1][y] == BoardField.WHITE && preDead(new Point(x+1, y), true)) capturePoints += kill(new Point(x+1, y));
            if(y+1 < boardSize && fields[x][y+1] == BoardField.WHITE && preDead(new Point(x, y+1), true)) capturePoints += kill(new Point(x, y+1));
            if(y-1 >= 0 && fields[x][y-1] == BoardField.WHITE && preDead(new Point(x, y-1), true)) capturePoints += kill(new Point(x, y-1));
            blackPoints += capturePoints;
            twoLastMoves[0] = fields.clone();
        } else {
            if(x-1 >= 0 && fields[x-1][y] == BoardField.BLACK && preDead(new Point(x-1, y), true)) capturePoints += kill(new Point(x-1, y));
            if(x+1 < boardSize && fields[x+1][y] == BoardField.BLACK && preDead(new Point(x+1, y), true)) capturePoints += kill(new Point(x+1, y));
            if(y+1 < boardSize && fields[x][y+1] == BoardField.BLACK && preDead(new Point(x, y+1), true)) capturePoints += kill(new Point(x, y+1));
            if(y-1 >= 0 && fields[x][y-1] == BoardField.BLACK && preDead(new Point(x, y-1), true)) capturePoints += kill(new Point(x, y-1));
            whitePoints += capturePoints;
            twoLastMoves[1] = fields.clone();
        }

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
        return !(occupied(point) || koViolation(point) || !(!preDead(point, false) || capture(point)));
    }

    private boolean capture(Point point) {
        int x = point.getX();
        int y = point.getY();

        if(blacksTurn) {
            if(x-1 >= 0 && fields[x-1][y] == BoardField.WHITE && preDead(new Point(x-1, y), true)) return true;
            if(x+1 < boardSize && fields[x+1][y] == BoardField.WHITE && preDead(new Point(x+1, y), true)) return true;
            if(y+1 < boardSize && fields[x][y+1] == BoardField.WHITE && preDead(new Point(x, y+1), true)) return true;
            return y - 1 >= 0 && fields[x][y - 1] == BoardField.WHITE && preDead(new Point(x, y - 1), true);
        } else {
            if(x-1 >= 0 && fields[x-1][y] == BoardField.BLACK && preDead(new Point(x-1, y), true)) return true;
            if(x+1 < boardSize && fields[x+1][y] == BoardField.BLACK && preDead(new Point(x+1, y), true)) return true;
            if(y+1 < boardSize && fields[x][y+1] == BoardField.BLACK && preDead(new Point(x, y+1), true)) return true;
            return y - 1 >= 0 && fields[x][y - 1] == BoardField.BLACK && preDead(new Point(x, y - 1), true);
        }
    }

    private int kill(Point point) {
        System.out.println("killing");
        int out = 1;
        int x = point.getX();
        int y = point.getY();

        fields[x][y] = BoardField.EMPTY;

        if(blacksTurn) {
            if(x-1 >= 0 && fields[x-1][y] == BoardField.WHITE) out+= kill(new Point(x-1, y));
            if(x+1 < boardSize && fields[x+1][y] == BoardField.WHITE) out += kill(new Point(x+1, y));
            if(y+1 < boardSize && fields[x][y+1] == BoardField.WHITE) out += kill(new Point(x, y+1));
            if(y-1 >= 0 && fields[x][y-1] == BoardField.WHITE) out += kill(new Point(x, y-1));
        } else {
            if(x-1 >= 0 && fields[x-1][y] == BoardField.BLACK) out+= kill(new Point(x-1, y));
            if(x+1 < boardSize && fields[x+1][y] == BoardField.BLACK) out += kill(new Point(x+1, y));
            if(y+1 < boardSize && fields[x][y+1] == BoardField.BLACK) out += kill(new Point(x, y+1));
            if(y-1 >= 0 && fields[x][y-1] == BoardField.BLACK) out += kill(new Point(x, y-1));
        }

        return out;
    }

    private boolean preDead(Point point, boolean toKill) {
        visited = new ArrayList<>();
        pot = new ArrayList<>();
        return dead(point, toKill);
    }

    private boolean dead(Point point, boolean toKill) {
        boolean cond = blacksTurn != toKill;
        System.out.println(pot);

        int x = point.getX();
        int y = point.getY();
        if(fields[x][y] == BoardField.EMPTY) origin = new Point(x, y);

        if(x-1 >= 0 && fields[x-1][y] == BoardField.EMPTY && !(x-1 == origin.getX() && y == origin.getY())) return false;
        if(x+1 < boardSize && fields[x+1][y] == BoardField.EMPTY && !(x+1 == origin.getX() && y == origin.getY())) return false;
        if(y+1 < boardSize && fields[x][y+1] == BoardField.EMPTY && !(x == origin.getX() && y+1 == origin.getY())) return false;
        if(y-1 >= 0 && fields[x][y-1] == BoardField.EMPTY && !(x == origin.getX() && y-1 == origin.getY())) return false;

        if (cond) {
            if(x-1 >= 0 && fields[x-1][y] == BoardField.BLACK) pot.add(new Point(x-1, y));
            if(x+1 < boardSize && fields[x+1][y] == BoardField.BLACK) pot.add(new Point(x+1, y));
            if(y+1 < boardSize && fields[x][y+1] == BoardField.BLACK) pot.add(new Point(x, y+1));
            if(y-1 >= 0 && fields[x][y-1] == BoardField.BLACK) pot.add(new Point(x, y-1));
        } else {
            if(x-1 >= 0 && fields[x-1][y] == BoardField.WHITE) pot.add(new Point(x-1, y));
            if(x+1 < boardSize && fields[x+1][y] == BoardField.WHITE) pot.add(new Point(x+1, y));
            if(y+1 < boardSize && fields[x][y+1] == BoardField.WHITE) pot.add(new Point(x, y+1));
            if(y-1 >= 0 && fields[x][y-1] == BoardField.WHITE) pot.add(new Point(x, y-1));
        }

        for(int i = 0; i < pot.size(); i++) {
            boolean visit = false;
            for(Point v :visited) {
                if(pot.get(i).getX() == v.getX() && pot.get(i).getY() == v.getY()) {
                    visit = true;
                    pot.remove(pot.get(i));
                    break;
                }
            }
            if(!visit) {
                visited.add(pot.get(i));
                if(!dead(pot.get(i), toKill)) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean koViolation(Point point) {
        if(blacksTurn) {
            for(int j = 0; j < boardSize; j++) {
                for(int i = 0; i < boardSize; i++) {
                    if(twoLastMoves[0][j][i] != fields[j][i]) return false;
                }
            }

            if(!(twoLastPoints[0].getX() == point.getX() && twoLastPoints[0].getY() == point.getY())){
                twoLastPoints[0] = point;
                return false;
            }
        } else {
            for(int j = 0; j < boardSize; j++) {
                for(int i = 0; i < boardSize; i++) {
                    if(twoLastMoves[1][j][i] != fields[j][i]) return false;
                }
            }

            if(!(twoLastPoints[1].getX() == point.getX() && twoLastPoints[1].getY() == point.getY())){
                twoLastPoints[1] = point;
                return false;
            }
        }
        return true;
    }

    private boolean occupied(Point point) {
        int x = point.getX();
        int y = point.getY();
        return fields[x][y] != BoardField.EMPTY;
    }

    private int countTerritory(BoardField bField) {
        int out = 0;
        if(bField == BoardField.BLACK) out += blackPoints;
        else out += whitePoints;

        //TODO: implement

        return out;
    }

    public String countWinner() {
        if(countTerritory(BoardField.WHITE) >= countTerritory(BoardField.BLACK)) return "White";
        return "Black";
    }

    public Point botMove() {
        return aiBot.bestMove();
    }
}