package edu.algorithmes_specialization;

import edu.algorithmes_specialization.week1.FastMath;
import edu.algorithmes_specialization.week1.Number;

/**
 * Created by Andrew on 30.10.2016.
 */
public class Main {

    public static void main (String[] args) throws Exception {
        Number number1, number2;
        number1 = new Number("3141592653589793238462643383279502884197169399375105820974944592");
        number2 = new Number("2718281828459045235360287471352662497757247093699959574966967627");

        System.out.println(FastMath.multiply(number1,number2) + " multiplication result");

    }

}
