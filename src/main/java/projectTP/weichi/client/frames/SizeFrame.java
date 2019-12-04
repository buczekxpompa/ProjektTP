package projectTP.weichi.client.frames;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class SizeFrame extends JFrame {


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

                }
            };
            acceptButton.setPreferredSize(new Dimension(75, 30));


            JButton botAdd = new JButton("Bot");
            botAdd.setPreferredSize(new Dimension(75, 30));

            JButton playerAdd = new JButton("Player");
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

            //accept button
            this.add(acceptButton);

            setVisible(true);
            setDefaultCloseOperation(EXIT_ON_CLOSE); //tak bedzie
        }



}
