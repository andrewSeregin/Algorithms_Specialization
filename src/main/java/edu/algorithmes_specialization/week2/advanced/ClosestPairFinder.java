package edu.algorithmes_specialization.week2.advanced;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Ivan on 20.02.2017.
 */
public class ClosestPairFinder {

    public PointPair findClosestPair(ArrayList<Point> points) {
        ArrayList<Point> px = getSortedCopy(points, true);
        ArrayList<Point> py = getSortedCopy(points, false);
        return null;
    }

    private PointPair closestPair(ArrayList<Point> px, ArrayList<Point> py) {
        PointPair result;
        if(px.size() <= 3 || py.size() <= 3) {
            PointPair best = null;
            double minDistance = PointPair.DEFAULT_DISTANCE;
            for (int i = 0; i < px.size(); i++) {
                for (int j = 0; j < py.size(); j++) {
                    double d = euclideanDistance(px.get(i), py.get(j));
                    if(d <= minDistance || minDistance == PointPair.DEFAULT_DISTANCE) {
                        minDistance = d;
                        best = new PointPair(px.get(i), py.get(j), d);
                    }
                }
            }
            result = best;
            return result;
        }

        ArrayList<Point> qx = getArrayHalf(px, 1);
        ArrayList<Point> qy = getArrayHalf(py, 1);
        ArrayList<Point> rx = getArrayHalf(px, 2);
        ArrayList<Point> ry = getArrayHalf(py, 2);

        PointPair p1 = closestPair(qx, qy);
        PointPair p2 = closestPair(rx, ry);
        double delta = min(p1, p2);
        PointPair p3 = closestSplitPair(px, py, delta);
        return minPair(p1, p2, p3);
    }

    private PointPair minPair(PointPair... pointPairs) {
        double result = -2;
        PointPair best = null;
        for (PointPair pair : pointPairs) {
            double d = pair.getEuclideanDistance();
            if (d != PointPair.DEFAULT_DISTANCE && (result == PointPair.DEFAULT_DISTANCE || d < result)) {
                result = d;
                best = pair;
            }
        }
        return best;
    }

    private ArrayList<Point> getPointsWithDelta(ArrayList<Point> points, Point middleX, double delta) {
        ArrayList<Point> result = new ArrayList<>();
        for (Point point : points) {
            if(point.getX() > middleX.getX() - delta && point.getX() < middleX.getX() + delta) {
                result.add(point);
            }
        }
        return result;
    }

    private double min(PointPair... pointPairs) {
        double result = -2;
        for (PointPair pair : pointPairs) {
            double d = pair.getEuclideanDistance();
            if (d != PointPair.DEFAULT_DISTANCE && (result == PointPair.DEFAULT_DISTANCE || d < result)) {
                result = d;
            }
        }
        return result;
    }

    private PointPair closestSplitPair(ArrayList<Point> px, ArrayList<Point> py, double delta) {
        Point middle = px.get(px.size() - 1);
        ArrayList<Point> sy = getPointsWithDelta(py, middle, delta);

        double best = delta;
        PointPair bestPair = null;

        for (int i = 0; i < sy.size(); i++) {
            int size = Math.min(7, sy.size() - i);
            for (int j = 0; j < size; j++) {
                Point p1 = sy.get(i);
                Point p2 = sy.get(i + j);
                double distance = euclideanDistance(p1, p2);
                if(distance < best) {
                    best = distance;
                    bestPair = new PointPair(p1, p2, distance);
                }
            }
        }
        return bestPair;
    }

    private ArrayList<Point> getArrayHalf(ArrayList<Point> array, int halfNumber) {
        ArrayList<Point> result = new ArrayList<>();
        int startIndex, endIndex;
        if(halfNumber == 1) {
            startIndex = 0;
            endIndex = array.size() / 2;
        }
        else {
            startIndex = array.size() / 2;
            endIndex = array.size();
        }

        for (int i = startIndex; i < endIndex; i++) {
            result.add(array.get(i));
        }
        return result;
    }

    private double euclideanDistance(Point one, Point two) {
        double result = 0.0;
        result = Math.sqrt(Math.pow(one.getX() - two.getX(), 2) + Math.pow(one.getY() - two.getY(), 2));
        return result;
    }

    private ArrayList<Point> getSortedCopy(ArrayList<Point> points, boolean sortAxis) {
        ArrayList<Point> copy = new ArrayList<>();
        Collections.copy(copy, points);
        copy.sort((x, y) -> {
            if(sortAxis) return x.compareX(y);
            else return x.compareY(y);
        });
        return copy;
    }
}
