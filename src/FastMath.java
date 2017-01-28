import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Ivan on 28.01.2017.
 */
public class FastMath {

    public static KaratsubaNumber addTwoValues(KaratsubaNumber firstNumber, KaratsubaNumber secondNumber) throws Exception {

        if (firstNumber.isZero()) {
            secondNumber.removeZero();
            return new KaratsubaNumber(secondNumber);
        }
        if (secondNumber.isZero()) {
            firstNumber.removeZero();
            return new KaratsubaNumber(firstNumber);
        }

        KaratsubaNumber result = new KaratsubaNumber();
        int transfer = 0;
        int i = secondNumber.length() - 1;
        int x = firstNumber.length() - 1;

        //if(!secondNumber.getSign() || !firstNumber.getSign()) return subtractFromSelf(firstNumber, secondNumber);

        boolean sizer = firstNumber.length() > secondNumber.length();

        for (; i >= 0 && x >= 0; i--, x--) {

            int sum = secondNumber.getValue(i) +  firstNumber.getValue(x) + transfer;
            transfer = getTransfer(result, sum);
        }

        i = sizer ? x : i;
        ArrayList<Integer> temp = sizer ? firstNumber.getNumber() : secondNumber.getNumber();

        for (; i >= 0; i--) {
            int sum = temp.get(i) + transfer;
            transfer = getTransfer(result, sum);
        }

        if(transfer != 0) appendTransfer(result, transfer, i);
        Collections.reverse(result.getNumber());

        result.removeZero();

        return result;
    }

    private static int getTransfer(KaratsubaNumber karatsubaNumber, int sum) {
        int count;
        int transfer;
        if (Math.abs(sum) >= 10) {
            int t = sum / 10;
            count = sum % 10;
            karatsubaNumber.getNumber().add(count);
            transfer = t;
        } else {
            karatsubaNumber.getNumber().add(sum);
            transfer = 0;
        }
        return transfer;
    }

    private static void appendTransfer(KaratsubaNumber karatsubaNumber, int transfer, int indexFrom) {
        int i = indexFrom - 1;
        int count;
        while(transfer != 0 && i >= 0) {
            int sum = karatsubaNumber.getValue(i) + transfer;
            if (Math.abs(sum) >= 10) {
                int t = sum / 10;
                count = sum % 10;
                karatsubaNumber.setValue(i, count);
                transfer = t;
            } else {
                karatsubaNumber.setValue(i, sum);
                transfer = 0;
            }
        }

        if(transfer != 0) {
            karatsubaNumber.getNumber().add(karatsubaNumber.length(), transfer);
        }
    }
}
