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

            setSize(800, 600);
            //add(nine);
           //add(thirteen);
            //add(nineteen);
            //add(botAdd);
            //add(playerAdd);

            setLayout(new FlowLayout());
            //panelOne
            JPanel panelOne = new JPanel();
            panelOne.setPreferredSize(new Dimension(800,50));
            JLabel labelOne = new JLabel("Main menu");
            panelOne.add(labelOne, BorderLayout.PAGE_START);
//            panelOne.add(nine);
//            panelOne.add(thirteen);
//            panelOne.add(nineteen);
//            panelOne.add(botAdd);
//            panelOne.add(playerAdd);
//            panelOne.add(acceptButton);
            this.add(panelOne);

            //panelTwo
            JPanel panelTwo = new JPanel();
            JLabel labelTwo = new JLabel("Choose size");
            panelTwo.setLayout(new FlowLayout());
            panelTwo.setPreferredSize(new  Dimension(800, 50));
            panelTwo.add(labelTwo);
            panelTwo.add(nine);
            panelTwo.add(thirteen);
            panelTwo.add(nineteen);
            this.add(panelTwo);

            //panelThree
            JPanel panelThree = new JPanel();
            JLabel labelThree = new JLabel("Choose opponent");
            panelThree.setPreferredSize(new Dimension(800, 50));
            panelThree.setLayout(new FlowLayout());
            panelThree.add(labelThree);
            panelThree.add(botAdd);
            panelThree.add(playerAdd);

            this.add(panelThree);

            JPanel panelFour = new JPanel();
            panelFour.setPreferredSize(new Dimension(800, 50));
            panelFour.setLayout(new FlowLayout());
            panelFour.add(acceptButton);
            this.add(panelFour);


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
