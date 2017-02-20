package edu.algorithmes_specialization.week2.advanced;

/**
 * Created by Ivan on 20.02.2017.
 */
public class PointPair {
    public static final double DEFAULT_DISTANCE = -2;
    private Point one;
    private Point two;

    public double getEuclideanDistance() {
        return euclideanDistance;
    }

    private double euclideanDistance;

    public PointPair(Point one, Point two) {
        this.one = one;
        this.two = two;
        this.euclideanDistance = DEFAULT_DISTANCE;
    }

    public PointPair(Point one, Point two, double euclideanDistance) {
        this(one, two);
        this.euclideanDistance = euclideanDistance;
    }

    public Point getOne() {
        return one;
    }

    public Point getTwo() {
        return two;
    }
}
