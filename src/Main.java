/**
 * Created by Andrew on 30.10.2016.
 */
public class Main {

    public static void main (String[] args) throws Exception {

        BigData valueOne = new BigData("3141592653589793238462643383279502884197169399375105820974944592");
        BigData valueTwo = new BigData("2718281828459045235360287471352662497757247093699959574966967627");

        BigData res = BigData.simpleMultiple(valueOne,valueTwo);
        System.out.println(res);
    }

}
