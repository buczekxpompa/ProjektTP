package projectTP.weichi.client;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class ButtonCoordinated extends JButton
{
    private int x;
    private int y;

    public ButtonCoordinated(int x, int y)
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
