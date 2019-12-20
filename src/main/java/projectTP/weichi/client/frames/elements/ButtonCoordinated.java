package projectTP.weichi.client.frames.elements;

import javax.swing.*;
import java.awt.*;

public class ButtonCoordinated extends JButton {
    private int coordinateX;
    private int coordinateY;

    public ButtonCoordinated(int x, int y) {
        super();
        coordinateX = x;
        coordinateY = y;
    }

    public void setY(int y) {
        coordinateY = y;
    }

    public void setX(int x) {
        coordinateX = x;
    }

    public int getCoordinateX() {
        return coordinateX;
    }

    public int getCoordinateY() {
        return coordinateY;
    }

    public void changeColor(Color color) {
        setBackground(color);
    }
}
