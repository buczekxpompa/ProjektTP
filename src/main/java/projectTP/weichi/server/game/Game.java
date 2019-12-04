package projectTP.weichi.server.game;

import projectTP.weichi.server.support.Point;


public class Game {

    boolean blackPassed = false;
    boolean whitePassed = false;

    Bot aiBot = null;
    int boardSize;

    public Game(boolean bot, int size) {
        if(bot) addBot();
        boardSize = size;
    }

    public void addBot() {
        aiBot = new Bot();
    }

    public boolean won() {
        return blackPassed && whitePassed;
    }
    public void move(Point x) {

    }
}