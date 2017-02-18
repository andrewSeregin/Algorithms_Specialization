import edu.algorithmes_specialization.week1.*;

import edu.algorithmes_specialization.week1.Number;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigInteger;

/**
 * Created by Ivan on 30.01.2017.
 */

interface Testable{
    Number performAction(Number one, Number two) throws Exception;
}

interface Testable2 {
    BigInteger performAction(BigInteger one, BigInteger two);
}


public class NumberTest {
    public void startTest(String defaultValue, String defValue2, Testable test, Testable2 test2) throws Exception {
        for (int i = 0; i < 100000; i++) {
            Number num = new Number(defaultValue + i);
            Number num2 = new Number(defValue2);
            BigInteger int1 = new BigInteger(defaultValue + i);
            BigInteger int2 = new BigInteger(defValue2);
            Number res = test.performAction(num, num2);
            BigInteger resB = test2.performAction(int1, int2);
            Assert.assertEquals(res.toString(), resB.toString());
        }
    }

    @Test
    public void testAddNumbers() throws Exception {
        String defaultValue = "10000500000";
        String defValue2 = "454545";
        startTest(defaultValue, defValue2, (x, y) -> FastMath.append(x,y), (x,y) -> x.add(y));
    }

    @Test
    public void testAddMinusPlusNumbers() throws Exception {
        String defaultValue = "-10000500000";
        String defValue2 = "454545";
        startTest(defaultValue, defValue2, (x, y) -> FastMath.append(x,y), (x,y) -> x.add(y));
    }

    @Test
    public void testAddPlusMinus() throws Exception {
        String defaultValue = "10000500000";
        String defValue2 = "-454545";
        startTest(defaultValue, defValue2, (x, y) -> FastMath.append(x,y), (x,y) -> x.add(y));
    }

    @Test
    public void testAddMinusMinus() throws Exception {
        String defaultValue = "-10000500000";
        String defValue2 = "-454545";
        startTest(defaultValue, defValue2, (x, y) -> FastMath.append(x,y), (x,y) -> x.add(y));
    }

    @Test
    public void testSubPlusPlus() throws Exception{
        String defaultValue = "10000500000";
        String defValue2 = "454545";
        startTest(defaultValue, defValue2, (x, y) -> FastMath.substract(x,y), (x,y) -> x.subtract(y));
    }

    @Test
    public void testSubPlusMinus() throws Exception{
        String defaultValue = "10000500000";
        String defValue2 = "-454545";
        startTest(defaultValue, defValue2, (x, y) -> FastMath.substract(x,y), (x,y) -> x.subtract(y));
    }

    @Test
    public void testSubMinusPlus() throws Exception{
        String defaultValue = "-10000500000";
        String defValue2 = "454545";
        startTest(defaultValue, defValue2, (x, y) -> FastMath.substract(x,y), (x,y) -> x.subtract(y));
    }

    @Test
    public void testSubMinusMinus() throws Exception{
        String defaultValue = "-10000500000";
        String defValue2 = "-454545";
        startTest(defaultValue, defValue2, (x, y) -> FastMath.substract(x,y), (x,y) -> x.subtract(y));
    }

    @Test
    public void testMultPlusPlus() throws Exception{
        String defaultValue = "10000500000";
        String defValue2 = "454545";
        startTest(defaultValue, defValue2, (x, y) -> FastMath.multiply(x,y), (x, y) -> x.multiply(y));
    }
}
