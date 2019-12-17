package projectTP.weichi.client.frames;

import projectTP.weichi.client.observer.Observable;
import projectTP.weichi.client.observer.ObservableEvent;
import projectTP.weichi.client.observer.Observer;
import projectTP.weichi.server.support.ColoredPoint;
import projectTP.weichi.server.support.Point;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;


public class GameFrame extends JFrame implements Observable{
    private Observer observer;
    private boolean blacksTurn = true;
    private JLabel blacksTurnLabel = new JLabel("Turn: Black        ");
    private JLabel passed = new JLabel("                          ");
    private ArrayList<ButtonCoordinated> fields = new ArrayList<>();

    public GameFrame(int size, String player) {
        super();
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setBounds(10,10,size * 25 + 300,size * 25 + 60);
        JPanel field = new JPanel();
        setLayout(new FlowLayout(FlowLayout.CENTER));
        field.setPreferredSize(new Dimension(size*25,size*25));
        field.setLayout(new GridLayout(size, size));

        for(int i = 0; i < size; i++) {
            for(int j = 0; j < size; j++) {
                final int finalI = i;
                final int finalJ = j;
                final ButtonCoordinated button = new ButtonCoordinated(finalI,finalJ) {
                    @Override
                    protected void fireActionPerformed(ActionEvent event) {
                        makeMove(new Point(finalI, finalJ));
                    }
                };
                button.setBackground(FieldColor.EMPTY);
                button.setBorder(BorderFactory.createLineBorder(Color.darkGray,2));

                fields.add(button);
                field.add(button);
            }
        }
        add(new JLabel("Player:  " + player + "       "));
        add(field);
        add(blacksTurnLabel);
        add(new JButton("Pass") {
            @Override
            protected void fireActionPerformed(ActionEvent event) {
                pass();
            }
        });

        setVisible(true);
    }

    private void pass() {
        makeMove(new Point(-2,-2));
        updateTurn();
        if(blacksTurn) passed.setText("White has passed!         ");
        else passed.setText("Black has passed!         ");
    }

    public void addObserver(Observer observer) {
        this.observer = observer;
    }

    public void removeObserver() {
        this.observer = null;
    }

    public void makeMove(Point point) {
        observer.onEvent(new ObservableEvent(point.getX(), point.getY()));
    }

    public void updateState(ArrayList<ColoredPoint> changes) {
        if(changes.size() > 1) {
            updateTurn();
            for(ColoredPoint change : changes) {
                for(ButtonCoordinated field : fields) {
                    if(field.getCoordinateX() == change.getX() && field.getCoordinateY() == change.getY()) {
                        switch (change.getColor()) {
                            case EMPTY: field.changeColor(FieldColor.EMPTY);
                                break;
                            case BLACK: field.changeColor(FieldColor.BLACK);
                                break;
                            case WHITE: field.changeColor(FieldColor.WHITE);
                                break;
                        }
                    }
                }
            }
        }
    }

    private void updateTurn() {
        blacksTurn = !blacksTurn;
        if(blacksTurn)
            blacksTurnLabel.setText("Turn: Black        ");
        else
            blacksTurnLabel.setText("Turn: White        ");
    }
}
