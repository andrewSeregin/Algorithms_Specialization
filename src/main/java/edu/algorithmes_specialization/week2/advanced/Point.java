package edu.algorithmes_specialization.week2.advanced;

/**
 * Created by Ivan on 20.02.2017.
 */
public class Point {
    private int x;
    private int y;

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int compareX(Point p) {
        int res = -1;
        if(x > p.getX()) {
            res = 1;
        }
        else if(x == p.getX()) {
            res = 0;
        }
        return res;
    }

    public int compareY(Point p) {
        int res = -1;
        if(y > p.getY()) {
            res = 1;
        }
        else if(y == p.getY()) {
            res = 0;
        }
        return res;
    }

    @Override
    public String toString() {
        return String.format("{%s, %s}", x, y);
    }
}
