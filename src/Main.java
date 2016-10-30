/**
 * Created by Andrew on 30.10.2016.
 */
public class Main {

    public static void main (String[] args) {

        BigData valueOne = new BigData("4592");
        BigData valueTwo = new BigData("67627");

        valueOne.addToSelf(valueTwo);
        System.out.println(valueOne);

    }

}
