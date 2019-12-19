package projectTP.weichi.server.blocks;

import projectTP.weichi.server.Server;
import projectTP.weichi.server.game.Game;

public class CombinedGame {
    private Game game;
    private Server.ServerThread player;

    public CombinedGame(Game game, Server.ServerThread output) {
        this.game = game;
        this.player = output;
    }

    public Game getGame() {
        return game;
    }

    public Server.ServerThread getPlayer() {
        return player;
    }
}
