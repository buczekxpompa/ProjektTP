package projectTP.weichi.client.frames;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class SizeFrame extends JFrame {

        //public static final int WYS = 1024;
        //public static final int SZER = 768;
        private JButton nine, thirteen, nineteen, acceptButton, botAdd, playerAdd;
        private JFrame frame;
        boolean bot;
        int size;

        private JLabel labelOne, labelTwo;
        private JPanel panelOne, panelTwo;


    String accept()
        {
            String defaultTXT = "{}";
            return "return dwa kułołty i tyle żeby sie nie przypierdalał";
        }


        public SizeFrame()
        {
            //9x9
            nine = new JButton("9x9") {
                @Override
                protected void fireActionPerformed(ActionEvent event) {
                    size=9;
                }
            };
            nine.setPreferredSize(new Dimension(75,30));

            //13x13
            thirteen = new JButton("13x13"){
                @Override
                protected void fireActionPerformed(ActionEvent event) {
                    size=13;
                }
            };
            thirteen.setPreferredSize(new Dimension(75,30));

            //19x19
            nineteen = new JButton("19x19"){
                @Override
                protected void fireActionPerformed(ActionEvent event) {
                    size=19;
                }
            };
            nineteen.setPreferredSize(new Dimension(75,30));

            //accept button
            acceptButton = new JButton(" Accept!"){
                @Override
                protected void fireActionPerformed(ActionEvent event) {

                }
            };
            acceptButton.setPreferredSize(new Dimension(75, 30));
            this.frame.add(acceptButton);

            botAdd = new JButton("Bot");
            botAdd.setPreferredSize(new Dimension(75, 30));

            playerAdd = new JButton("Player");
            playerAdd.setPreferredSize(new Dimension(75, 30));

            setLayout(new FlowLayout());
            setSize(800, 600);
            //add(nine);
           //add(thirteen);
            //add(nineteen);
            //add(botAdd);
            //add(playerAdd);

            //panelOne
            panelOne = new JPanel();
            panelOne.setPreferredSize(new Dimension(100,150));
            labelOne = new JLabel("Choose size");
            panelOne.add(labelOne);
            panelOne.add(nine);
            panelOne.add(thirteen);
            panelOne.add(nineteen);
            this.add(panelOne);

            //panelTwo
            panelTwo = new JPanel();
            panelTwo.setPreferredSize(new Dimension(100, 150));
            labelTwo = new JLabel("Choose opponent");
            panelTwo.add(labelTwo);
            panelTwo.add(botAdd);
            panelTwo.add(playerAdd);
            this.add(panelTwo);

            setVisible(true);
            setDefaultCloseOperation(EXIT_ON_CLOSE); //tak bedzie
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

}
