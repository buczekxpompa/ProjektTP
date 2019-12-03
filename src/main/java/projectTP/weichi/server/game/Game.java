package projectTP.weichi.server.game;

import projectTP.weichi.server.game.bot.Bot;

public class Game {
    boolean player1passed = false;
    boolean player2passed = false;
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

        return player1passed && player2passed;
    }
}