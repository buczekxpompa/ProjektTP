package projectTP.weichi.client.frames;

import projectTP.weichi.client.ButtonCoordinates;

import projectTP.weichi.client.observer.Observable;
import projectTP.weichi.client.observer.Observer;

import javax.swing.*;
import java.awt.*;

public class GameFrame extends JPanel implements Observable
{
    GameFrame(int size)
    {
        JPanel field = new JPanel();
        field.setLayout(new GridLayout(size, size));
        field.setVisible(true);

        for(int i=0; i<size; i++)
        {
            for(int j=0; j<size; j++)
            {
                field.add(new ButtonCoordinates(i, j));

            }
        }
    }
    @Override
    public void addObserver(Observer observer) {

    }

    @Override
    public void removeObserver() {

    }
}
