package projectTP.weichi.client.frames;

import projectTP.weichi.client.ButtonCoordinates;

import javax.swing.*;
import java.awt.*;

public class GameFrame {
    GameFrame(int size)
    {
        JPanel field = new JPanel();
        for(int i=0; i<size; i++)
        {
            for(int j=0; j<size; j++)
            {
                field.add(new ButtonCoordinates(i, j));
            }
        }
    }
}
