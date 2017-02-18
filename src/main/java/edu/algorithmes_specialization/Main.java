package edu.algorithmes_specialization;

import edu.algorithmes_specialization.week1.FastMath;
import edu.algorithmes_specialization.week1.Number;
import edu.algorithmes_specialization.week2.InversionsCounter;

import java.util.ArrayList;

/**
 * Created by Andrew on 30.10.2016.
 */
public class Main {

    public static void main (String[] args) throws Exception {
        //Number number1, number2;
        //number1 = new Number("3141592653589793238462643383279502884197169399375105820974944592");
        //number2 = new Number("2718281828459045235360287471352662497757247093699959574966967627");

        //System.out.println(FastMath.multiply(number1,number2) + " multiplication result");
        ArrayList<Integer> lst = new ArrayList<>();
        lst.add(1);
        lst.add(3);
        lst.add(5);
        lst.add(2);
        lst.add(4);
        lst.add(6);
        System.out.println("Count of inversions: " + new InversionsCounter().CountInversions(lst));
    }

}
