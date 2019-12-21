package projectTP.weichi.server.blocks;

public class Point {
    protected int x;
    protected int y;

    public Point() {
        x = 0;
        y = 0;
    }
    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    // Not used, but cool for the future
    public boolean equals(Point comprised) {
        return this.getY() == comprised.getY() && this.getX() == comprised.getX();
    }

    // Not used, but cool for the future
    public static boolean equals(Point point1, Point point2 ) {
        return point1.getY() == point2.getY() && point1.getX() == point2.getX();
    }
}
