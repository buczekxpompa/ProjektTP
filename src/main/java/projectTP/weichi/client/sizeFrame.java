package projectTP.weichi.client;

import javax.swing.*;
import java.awt.*;

public class sizeFrame extends JFrame {

        public static final int WYS = 125;
        public static final int SZER = 300;
        private JButton nine;
        private JButton thirteen;
        private JButton nineteen;

        public sizeFrame()
        {
            nine = new JButton("9x9");
            thirteen = new JButton("13x13");
            nineteen = new JButton("19x19");

            setLayout(new FlowLayout());
            setPreferredSize(new Dimension(SZER, WYS));
            add(nine);
            add(thirteen);
            add(nineteen);

            setVisible(true);
        }
}
