package projectTP.weichi.client.frames;

import projectTP.weichi.client.observer.Observable;
import projectTP.weichi.client.observer.ObservableEvent;
import projectTP.weichi.client.observer.Observer;
import projectTP.weichi.server.game.BoardField;
import projectTP.weichi.server.blocks.ColoredPoint;
import projectTP.weichi.server.blocks.Point;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;


public class GameFrame extends JFrame implements Observable {
    private boolean win = false;
    private Observer observer;
    private boolean blacksTurn = true;
    private JLabel blacksTurnLabel = new JLabel("Turn: Black        ");
    private JLabel passed = new JLabel("                    ");
    private ArrayList<ButtonCoordinated> fields = new ArrayList<>();

    public GameFrame(int size, String player, String id) {
        super(id);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setBounds(10,10,size * 25 + 300,size * 25 + 120);
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
        JPanel playerField = new JPanel();
        playerField.setLayout(new FlowLayout());
        playerField.add(new JLabel("Player:  " + player + "       "));
        playerField.add(passed);
        add(playerField);
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

    public boolean updateState(ArrayList<ColoredPoint> changes) {
        if(changes.size() > 0) {
            updateTurn();
            for(ColoredPoint change : changes) {
                if(change.getX() == -4 && change.getY() == -4) {
                    winner(null);
                    return true;
                }
                if(change.getY() == -2 && change.getX() == -2) {
                    if(blacksTurn) passed.setText("White has passed!   ");
                    else passed.setText("Black has passed!   ");
                    return true;
                }
                if(change.getY() == -3 && change.getX() == -3) {
                    winner(change.getColor());
                    return true;
                }
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
            passed.setText("                    ");
            return true;
        }
        return false;
    }

    private void winner(BoardField color) {
        if(!win) {
            win = true;
            JDialog win = new JDialog();
            if(color == null) {
                win.add(new JLabel("Second player resigned, you win!!!"));
            } else {
                switch (color) {
                    case BLACK:
                        win.add(new JLabel("Black player is a winner!!!"));
                        break;
                    case WHITE:
                        win.add(new JLabel("White player is a winner!!!"));
                        break;
                    case EMPTY:
                        win.add(new JLabel("It is a draw!!!"));
                        break;
                    default:

                }
            }
            win.setBounds(50, 50, 250, 70);
            win.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            win.setVisible(true);
            this.dispose();
        }
        this.dispose();
    }

    private void updateTurn() {
        blacksTurn = !blacksTurn;
        if(blacksTurn)
            blacksTurnLabel.setText("Turn: Black        ");
        else
            blacksTurnLabel.setText("Turn: White        ");
    }
}
