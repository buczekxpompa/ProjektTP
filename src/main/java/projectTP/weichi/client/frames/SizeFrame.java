package projectTP.weichi.client.frames;

import projectTP.weichi.client.observer.Observable;
import projectTP.weichi.client.observer.ObservableEvent;
import projectTP.weichi.client.observer.Observer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class SizeFrame extends JFrame implements Observable {
    boolean bot;
    int size;
    Observer observer;

    public SizeFrame()
    {
        //9x9
        JButton nine = new JButton("9x9") {
                @Override
                protected void fireActionPerformed(ActionEvent event) {
                    size=9;
                }
            };
        nine.setPreferredSize(new Dimension(75,30));

        //13x13
        JButton thirteen = new JButton("13x13"){
                @Override
                protected void fireActionPerformed(ActionEvent event) {
                    size=13;
                }
            };
        thirteen.setPreferredSize(new Dimension(75,30));

        //19x19
        JButton nineteen = new JButton("19x19"){
                @Override
                protected void fireActionPerformed(ActionEvent event) {
                    size=19;
                }
            };
        nineteen.setPreferredSize(new Dimension(75,30));

        //accept button
        JButton acceptButton = new JButton(" Accept!"){
                @Override
                protected void fireActionPerformed(ActionEvent event) {
                    accept();
                }
            };
        acceptButton.setPreferredSize(new Dimension(75, 30));
        this.add(acceptButton);

        JButton botAdd = new JButton("Bot") {
                @Override
                protected void fireActionPerformed(ActionEvent event) {
                    bot = true;
                }
            };
        botAdd.setPreferredSize(new Dimension(75, 30));

        JButton playerAdd = new JButton("Player"){
                @Override
                protected void fireActionPerformed(ActionEvent event) {
                    bot = false;
                }
            };
        playerAdd.setPreferredSize(new Dimension(75, 30));

        setLayout(new FlowLayout());
        setSize(800, 600);

        //panelOne
        JPanel panelOne = new JPanel();
        panelOne.setPreferredSize(new Dimension(100,150));
        panelOne.add(new JLabel("Choose size"));
        panelOne.add(nine);
        panelOne.add(thirteen);
        panelOne.add(nineteen);
        this.add(panelOne);

        //panelTwo
        JPanel panelTwo = new JPanel();
        panelTwo.setPreferredSize(new Dimension(100, 150));
        panelTwo.add(new JLabel("Choose opponent"));
        panelTwo.add(botAdd);
        panelTwo.add(playerAdd);
        this.add(panelTwo);

        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE); //tak bedzie
    }

    private void accept() {
        observer.onEvent(new ObservableEvent(bot, size));
        this.dispose();
    }

    @Override
    public void addObserver(Observer observer) {
        this.observer = observer;
    }

    @Override
    public void removeObserver() {
        this.observer = null;
    }
}
