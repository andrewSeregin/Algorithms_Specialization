/**
 * Created by Andrew on 30.10.2016.
 */
public class Main {

    public static void main (String[] args) throws Exception {
        KaratsubaNumber number1, number2;
        number1 = new KaratsubaNumber("0000000000000000000009");
        number2 = new KaratsubaNumber("5456456456456456456454564564");

        System.out.println(FastMath.addTwoValues(number1,number2));

    }

}
