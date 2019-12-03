package ProjetTP.Weichi.Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class SizeFrame extends JFrame {

        public static final int WYS = 125;
        public static final int SZER = 300;
        private JButton nine;
        private JButton thirteen;
        private JButton nineteen;
        private JButton botAdd;
        private JButton playerAdd;
        boolean bot;
        int size;


        String accept()
        {
            String defaultTXT = "{}"
            return "return dwa kułołty i tyle żeby sie nie przypierdalał";
        }


        public void sizeFrame()
        {
            nine = new JButton("9x9");
            thirteen = new JButton("13x13");
            nineteen = new JButton("19x19");
            botAdd = new JButton("Add a bot");
            playerAdd = new JButton("Add a player");

            setLayout(new FlowLayout());
            setPreferredSize(new Dimension(SZER, WYS));
            add(nine);
            add(thirteen);
            add(nineteen);
            add(botAdd);
            add(playerAdd);

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

}
