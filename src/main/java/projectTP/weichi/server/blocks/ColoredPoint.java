package projectTP.weichi.server.blocks;

import projectTP.weichi.server.game.BoardField;

public class ColoredPoint extends Point {
    protected BoardField color;

    public ColoredPoint(int x, int y, BoardField color) {
        super(x, y);
        this.color = color;
    }

    public void setColor(BoardField c) {
        color = c;
    }

    public BoardField getColor() {
        return color;
    }
}
