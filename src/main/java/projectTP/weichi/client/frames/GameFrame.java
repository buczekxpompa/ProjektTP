package projectTP.weichi.client.frames;

import projectTP.weichi.client.ButtonCoordinated;

import projectTP.weichi.client.observer.Observable;
import projectTP.weichi.client.observer.ObservableEvent;
import projectTP.weichi.client.observer.Observer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class GameFrame extends JPanel implements Observable{
    private Observer observer;

    public GameFrame(int size) {
        super();
        setBounds(10,10,600,600);
        JPanel field = new JPanel();
        field.setLayout(new GridLayout(size, size));

        for(int i = 0; i < size; i++) {
            for(int j = 0; j < size; j++) {
                field.add(new ButtonCoordinated(i, j) {
                    @Override
                    protected void fireActionPerformed(ActionEvent event) {
                        makeMove(this);
                    }
                });
            }
        }
        add(field);
        setVisible(true);
    }

    public void addObserver(Observer observer) {
        this.observer = observer;
    }

    public void removeObserver() {
        this.observer = null;
    }

    public void makeMove(ButtonCoordinated button) {
        observer.onEvent(new ObservableEvent(button.getX(), button.getY()));
    }
}
