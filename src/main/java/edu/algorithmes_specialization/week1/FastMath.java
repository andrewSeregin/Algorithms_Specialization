package edu.algorithmes_specialization.week1;

import java.util.ArrayList;
import java.util.Collections;

public class FastMath {

    public static Number append(Number firstNumber, Number secondNumber) throws Exception {

        if (firstNumber.isZero()) {
            secondNumber.removeZero();
            return new Number(secondNumber);
        }
        if (secondNumber.isZero()) {
            firstNumber.removeZero();
            return new Number(firstNumber);
        }

        Number result = new Number();
        Number firstCopy = new Number(firstNumber);
        Number secondCopy = new Number(secondNumber);
        int transfer = 0;
        int i = secondNumber.length() - 1;
        int x = firstNumber.length() - 1;

        SignType type = detectSign(firstNumber, secondNumber);

        if(type == SignType.DOUBLE_MINUS) {
            secondCopy.invertSign();
            firstCopy.invertSign();
        }
        else if(type == SignType.PLUS_MINUS) {
            secondCopy.invertSign();
            result = substract(secondCopy,firstCopy);
            result.invertSign();
            return result;
        }
        else if(type == SignType.MINUS_PLUS) {
            firstCopy.invertSign();
            result = substract(firstCopy, secondCopy);
            result.invertSign();
            return result;
        }

        if(!secondCopy.getSign() || !firstCopy.getSign()) return substract(firstCopy, secondCopy);

        boolean sizer = firstCopy.length() > secondCopy.length();

        for (; i >= 0 && x >= 0; i--, x--) {

            int sum = secondCopy.getValue(i) +  firstCopy.getValue(x) + transfer;
            transfer = getTransfer(result, sum);
        }

        i = sizer ? x : i;
        ArrayList<Integer> temp = sizer ? firstCopy.getNumber() : secondCopy.getNumber();

        for (; i >= 0; i--) {
            int sum = temp.get(i) + transfer;
            transfer = getTransfer(result, sum);
        }

        if(transfer != 0) appendTransfer(result, transfer, i);
        Collections.reverse(result.getNumber());

        result.removeZero();

        if(type == SignType.DOUBLE_MINUS) {
            result.invertSign();
        }

        return result;
    }

    private static int getTransfer(Number karatsubaNumber, int sum) {
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

    private static void appendTransfer(Number karatsubaNumber, int transfer, int indexFrom) {
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

    private static void normalize (Number firstNumber, Number secondNumber) {
        int choice = firstNumber.length() > secondNumber.length() ? 1 : 2;
        int number = -1;
        switch (choice) {
            case 1:
                number = firstNumber.length() % 2 == 0 ?
                        firstNumber.length() : firstNumber.length() + 1;
                break;
            case 2:
                number = secondNumber.length() % 2 == 0 ?
                       secondNumber.length() : secondNumber.length() + 1;
                break;
        }

        firstNumber.normalize(number);
        secondNumber.normalize(number);
    }

    public static Number substract(Number first, Number second) throws Exception {

        Number result;
        int compareResult = first.compareTo(second);

        if (first.isZero()) {
            result = new Number(second);
            result.invertSign();
            result.removeZero();
            return result;
        }

        if (second.isZero()) {
            result = new Number(first);
            result.removeZero();
            return result;
        }

        result = new Number();
        Number copyFirst = (compareResult == 1) ? new Number(first) : new Number(second);
        Number copySecond = (compareResult == 1) ? new Number(second) : new Number(first);
        SignType sign = detectSign(first, second);

        if(sign == SignType.PLUS_MINUS) {
            Number copyTwo = new Number(second);
            copyTwo.invertSign();
            return append(first, copyTwo);
        } else if(sign == SignType.MINUS_PLUS) {
            Number copyOne = new Number(first);
            copyOne.invertSign();
            result = append(copyOne, second);
            result.invertSign();
            return result;
        }

        int i = copyFirst.length() - 1;
        int j = copySecond.length() - 1;

        for (; i >= 0 && j >= 0; i--, j--) {
            if(copyFirst.getValue(i) < copySecond.getValue(j)) {
                int indNZ = copyFirst.findNextNotZeroIndex(i);
                if(indNZ == -1) break;
                copyFirst.setNineBeforeFinalDigit(indNZ, i);
            }

            int sum = copyFirst.getValue(i)- copySecond.getValue(j);
            result.getNumber().add(sum);
        }

        for (int x = i; x >= 0; x--) {

            if (x == 0 && copyFirst.getValue(x) == 0) break;

            result.getNumber().add(copyFirst.getValue(x));
        }

        if(result.findNotZeroIndex() != -1) {
            Collections.reverse(result.getNumber());
        }
        else {
            result.getNumber().clear();
            result.getNumber().add(0);
        }

        result.removeZero();

        if(sign == SignType.DOUBLE_MINUS || compareResult == -1) {
            result.invertSign();
        }

        return result;
    }

    private static SignType detectSign (Number first, Number second){
        SignType type = SignType.DOUBLE_PLUS;
        int signOne = first.getSign() ? 1 : -1;
        int signTwo = second.getSign() ? 1 : -1;

        if(first.getSign()) {
            type = second.getSign() ?
                    SignType.DOUBLE_PLUS : SignType.PLUS_MINUS;
        }
        else {
            type = second.getSign() ?
                    SignType.MINUS_PLUS : SignType.DOUBLE_MINUS;
        }
        return type;
    }

    public static Number multiply(Number one, Number two) throws Exception {
        if (one.length() == 1 && two.length() == 1) {
            int number = one.getValue(0) * two.getValue(0);
            return new Number(new Integer(number).toString());
        }

        Number firstCopy = new Number(one);
        Number secondCopy = new Number(two);

        normalize(firstCopy, secondCopy);

        Number xh = firstCopy.getHalfOfNumber(true);
        Number xl = firstCopy.getHalfOfNumber(false);
        Number yh = secondCopy.getHalfOfNumber(true);
        Number yl = secondCopy.getHalfOfNumber(false);

        Number a = multiply(xh, yh);
        Number d = multiply(xl, yl);

        Number sumOfXhXl = append(xh, xl);
        Number sumOfYhYl = append(yh, yl);

        Number e = multiply(sumOfXhXl, sumOfYhYl);
        e = substract(e, a);
        e = substract(e, d);

        a.multiplyByPowerOfTen(firstCopy.length());
        e.multiplyByPowerOfTen(firstCopy.length() / 2);

        Number addAE = append(a, e);

        return append(addAE, d);
    }

}
