package projectTP.weichi.client.frames;

import projectTP.weichi.client.observer.Observable;
import projectTP.weichi.client.observer.Observer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class SizeFrame extends JFrame implements Observable {

        //public static final int WYS = 1024;
        //public static final int SZER = 768;
        private JButton nine;
        private JButton thirteen;
        private JButton nineteen;
        private JButton botAdd;
        private JButton playerAdd;
        private JFrame frame;
        boolean bot;
        int size;
        Observer observer;

        private JLabel labelOne, labelTwo;
        private JPanel panelOne, panelTwo;


    String accept()
        {
            String defaultTXT = "{}";
            return "return dwa kułołty i tyle żeby sie nie przypierdalał";
        }


        public SizeFrame()
        {
            nine = new JButton("9x9");
            thirteen = new JButton("13x13");
            nineteen = new JButton("19x19");
            botAdd = new JButton("Bot");
            playerAdd = new JButton("Player");

            setLayout(new FlowLayout());
            setSize(800, 600);
            //add(nine);
           //add(thirteen);
            //add(nineteen);
            //add(botAdd);
            //add(playerAdd);

            //panelOne
            panelOne = new JPanel();
            panelOne.setPreferredSize(new Dimension(150,150));
            labelOne = new JLabel("Choose size");
            panelOne.add(labelOne);
            panelOne.add(nine);
            panelOne.add(thirteen);
            panelOne.add(nineteen);
            this.add(panelOne);

            //panelTwo
            panelTwo = new JPanel();
            panelTwo.setPreferredSize(new Dimension(150, 150));
            labelTwo = new JLabel("Choose opponent");
            panelTwo.add(labelTwo);
            panelTwo.add(botAdd);
            panelTwo.add(playerAdd);
            this.add(panelTwo);

            setVisible(true);
        }

        public void actionPerformed(ActionEvent e) {
            Object source = e.getSource();

            if(source == nine)
            {
                size=9;
            }
            else if(source == thirteen)
            {
                size=13;
            }
            else if(source == nineteen)
            {
                size=19;
            }
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
