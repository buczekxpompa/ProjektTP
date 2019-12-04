package projectTP.weichi.client;

import javax.swing.*;

public class ButtonCoordinates extends JButton
{
    private int x;
    private int y;

    public ButtonCoordinates(int x, int y)
    {
        super();
        this.x = x;
        this.y = y;
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }
}
