package projectTP.weichi.client.frames;

import projectTP.weichi.client.observer.Observable;
import projectTP.weichi.client.observer.ObservableEvent;
import projectTP.weichi.client.observer.Observer;
import projectTP.weichi.server.game.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

public class Lobby extends JFrame implements Observable {
    Observer observer;

    public Lobby(ArrayList<String> games) {
        super();
        setBounds(10,10,400,600);
        setLayout(new FlowLayout());
        add(new JLabel("              LOBBY            "));
        add(new JButton("New Game") {
            @Override
            protected void fireActionPerformed(ActionEvent event) {
                observer.onEvent(new ObservableEvent("0"));
            }
        });
        for(final String game : games) {
            JPanel panel = new JPanel();
            panel.setLayout(new FlowLayout());
            panel.add(new JLabel(game + "             "));
            panel.add(new JButton("Join"){
                @Override
                protected void fireActionPerformed(ActionEvent event) {
                   observer.onEvent(new ObservableEvent(game));
                }
            });
            panel.setVisible(true);
            add(panel);
        }
        setVisible(true);
    }

    @Override
    public void addObserver(Observer observer) {
        this.observer = observer;
    }

    @Override
    public void removeObserver() {
        observer = null;
    }
}
