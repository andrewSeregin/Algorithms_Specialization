/**
 * Created by Andrew on 30.10.2016.
 */
public class Main {

    public static void main (String[] args) throws Exception {

        BigData valueOne = new BigData("255");
        BigData valueTwo = new BigData("1845");

        BigData res = BigData.simpleMultiple(valueOne,valueTwo);
        System.out.println(res);

    }

}
