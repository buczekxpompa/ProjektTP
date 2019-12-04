package projectTP.weichi;

import projectTP.weichi.client.frames.GameFrame;

public class GameFrameTest {
    public static void main(String[] args) {
        GameFrame okno = new GameFrame(19);
        System.out.println(okno.getBounds());
        System.out.println(okno.isVisible());
    }
}
