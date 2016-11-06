import java.util.ArrayList;
import java.util.Collections;
import java.util.regex.Pattern;

/**
 * Created by Andrew on 30.10.2016.
 */
public class BigData {

    public ArrayList<Integer> value = new ArrayList<>();
    private boolean isNegative;

    private BigData() {}

    private BigData(ArrayList<Integer> integer, boolean negative) {
        this.isNegative = negative;
        this.value = integer;
    }

    BigData(String inner) throws Exception {
        if(!checkStringForNumbers(inner)) throw new Exception("");
        for (char number : inner.toCharArray()) {
            if (Character.isDigit(number)) {
                value.add(Integer.parseInt(String.valueOf(number)));
            } else if (number == '-') {
                isNegative = true;
            }

        }

    }

    public static BigData addTwoValues(BigData val, BigData two) {

        if (zeroTransformer(val.value)) {
            two.value = removeZero(two.value);
            return two;
        }
        if (zeroTransformer(two.value)) {
            val.value = removeZero(val.value);
            return val;
        }

        ArrayList<Integer> result = new ArrayList<>();
        int transfer = 0;
        int i = two.value.size() - 1;
        int x = val.value.size() - 1;

        if(two.isNegative || val.isNegative) return subtractFromSelf(val, two);

        boolean sizer = val.value.size() > two.value.size();

        for (; i >= 0 && x >= 0; i--, x--) {

            int sum = two.value.get(i) +  val.value.get(x) + transfer;
            transfer = getTransfer(result, sum);
        }

        i = sizer ? x : i;
        ArrayList<Integer> temp = sizer ? val.value : two.value;

        for (; i >= 0; i--) {
            int sum = temp.get(i) + transfer;
            transfer = getTransfer(result, sum);
        }

        if(transfer != 0) appendTransfer(result, transfer, i);
        Collections.reverse(result);

        result = removeZero(result);

        return new BigData(result, false);
    }

    private static boolean zeroTransformer (ArrayList<Integer> array){
        return findNotZero(array) == -1;
    }

    private static int findNotZero(ArrayList<Integer> array) {

        for (int i = 0; i < array.size(); i++) {
            if (array.get(i) > 0) return i;
        }
        return  -1;
    }

    private static ArrayList<Integer> removeZero (ArrayList<Integer> array) {

        ArrayList<Integer> result = new ArrayList<>();

        int index = findNotZero(array);

        if (index != -1) {
            for (int i = index; i < array.size(); i++) {
                result.add(array.get(i));
            }
        } else {
            return array;
        }

        return result;
    }

    private static int getTransfer(ArrayList<Integer> result, int sum) {
        int count;
        int transfer;
        if (Math.abs(sum) >= 10) {
            int t = sum / 10;
            count = sum % 10;
            result.add(count);
            transfer = t;
        } else {
            result.add(sum);
            transfer = 0;
        }
        return transfer;
    }

    public static BigData subtractFromSelf(BigData val, BigData two) {
        ArrayList<Integer> result = new ArrayList<>();
        int from = findSelectFrom(val, two);

        if (zeroTransformer(val.value)){
            two.value =removeZero(two.value);
            two.isNegative = !two.isNegative;
            return two;
        }

        if (zeroTransformer(two.value)){
            val.value =removeZero(val.value);
            return val;
        }

        ArrayList<Integer> fromData = (from == 1) ? val.value : two.value;
        ArrayList<Integer> substrData = (from == 1) ? two.value : val.value;
        boolean negative = from == 2;

        int i = fromData.size() - 1;
        int x = substrData.size() - 1;

        for (; i >= 0 && x >= 0; i--, x--) {
            if(fromData.get(i) < substrData.get(x)) {
                int index = findNextToBorrow(fromData, i);
                if(index == -1) break;
                borrowFromIndex(fromData, index, i);
            }
            result.add(fromData.get(i) - substrData.get(x));
        }

        for (int j = i; j >= 0; j--) {

            if (j == 0 && fromData.get(j) == 0) break;

            result.add(fromData.get(j));
        }

        int countOfZeros = 0;
        for (Integer integer : result) {
            if(integer == 0) countOfZeros++;
            else break;
        }

        if(countOfZeros != result.size()) {

            Collections.reverse(result);
        }
        else {
            result.clear();
            result.add(0);
        }

        result = removeZero(result);

        return new BigData(result, negative);
    }

    private static void normalize(BigData data, BigData data2) {
        int choice = data.value.size() > data2.value.size() ? 1 : 2;
        int number = -1;
        switch (choice) {
            case 1:
                number = data.value.size()%2 == 0 ? data.value.size() : data.value.size() + 1;
                break;
            case 2:
                number = data2.value.size()%2 == 0 ? data2.value.size() : data2.value.size() + 1;
                break;
        }
        normalizeOne(data, number);
        normalizeOne(data2, number);
    }

    private static void normalizeOne(BigData source, int normalsize) {
        while (true) {
            if(source.value.size() == normalsize) break;
            source.value.add(0,0);
        }
    }

    public static BigData multipleTwoItems(BigData one, BigData two) throws Exception {
        if(one.value.size() == 1 && two.value.size() == 1) {
            ArrayList<Integer> res = new ArrayList<>();
            int t = one.value.get(0) * two.value.get(0);
            return new BigData(new Integer(t).toString());
        }

        normalize(one, two);

        BigData xh = new BigData();
        xh.value = getHalf(one.value, true);
        xh.isNegative = one.isNegative;

        BigData xl = new BigData();
        xl.value = getHalf(one.value, false);
        xl.isNegative = one.isNegative;

        BigData yh = new BigData();
        yh.value = getHalf(two.value, true);
        yh.isNegative = two.isNegative;

        BigData yl = new BigData();
        yl.value = getHalf(two.value, false);
        yl.isNegative = two.isNegative;

        BigData a = multipleTwoItems(xh, yh);
        BigData d = multipleTwoItems(xl, yl);
        BigData sumOfXhXl = addTwoValues(xh, xl);
        BigData sumOfYhYl = addTwoValues(yh, yl);
        BigData e = multipleTwoItems(sumOfXhXl, sumOfYhYl);

        e = subtractFromSelf(e,a);
        e = subtractFromSelf(e, d);

        BigData power1 = new BigData(new Integer((int)Math.pow(10, one.value.size())).toString());
        BigData power2 = new BigData(new Integer((int)Math.pow(10, (int)(one.value.size() / 2))).toString());


        BigData multAOnTenN = appendZero(a, power1);
        BigData multEOnTenN = appendZero(e, power2);
        BigData addAE = addTwoValues(multAOnTenN, multEOnTenN);

        BigData result = addTwoValues(addAE, d);

        return result;
    }

    private static BigData appendZero(BigData value, BigData pow) {

        BigData result = new BigData();

        value.value.forEach(x -> result.value.add(x));

        for (int i = 0; i < pow.value.size() - 1; i++) {
            result.value.add(0);
        }

        return  result;
    }

    private boolean checkStringForNumbers(String input) {
        return Pattern.matches("^(-)?(\\d)+$", input);
    }

    private static void appendTransfer(ArrayList<Integer> result, int transfer, int indexFrom) {
        int i = indexFrom - 1;
        int count;
        while(transfer != 0 && i >= 0) {
            int sum = result.get(i) + transfer;
            if (Math.abs(sum) >= 10) {
                int t = sum / 10;
                count = sum % 10;
                result.set(i, count);
                transfer = t;
            } else {
                result.set(i, sum);
                transfer = 0;
            }
        }

        if(transfer != 0) {
            result.add(result.size(), transfer);
        }
    }

    public static ArrayList<Integer> getHalf(ArrayList<Integer> source, boolean first) {
        ArrayList<Integer> list = new ArrayList<Integer>();
        int index = first ? 0 : source.size() / 2;
        int end = first ? source.size() / 2 : source.size();
        for (int i = index; i < end; i++) {
            list.add(source.get(i));
        }
        return list;
    }

    private static int findNextToBorrow(ArrayList<Integer> source, int indexFrom) {
        for (int i = indexFrom - 1; i >= 0; i--) {
            if(source.get(i) > 0) return i;
        }
        return -1;
    }

    private static void borrowFromIndex(ArrayList<Integer> source, int indexFromBorrow, int indexBorrowTo) {
        source.set(indexFromBorrow, source.get(indexFromBorrow) - 1);
        for (int i = indexFromBorrow + 1; i < indexBorrowTo; i++) {
            source.set(i, 9);
        }
        source.set(indexBorrowTo, source.get(indexBorrowTo) + 10);
    }

    private static int findSelectFrom(BigData val, BigData other) {
        int from = 0;
        int idx = findNotZero(val.value);
        int idxOther = findNotZero(other.value);
        int len = val.value.size() - idx;
        int lenOther = other.value.size() - idxOther;
        if(len > lenOther) {
            from = 1;
        }
        else if(len < lenOther) {
            from = 2;
        }
        else {

            if (idx == -1 || idxOther == -1) return from;

            from = (other.value.get(idxOther) < val.value.get(idx)) ? 1 : 2;
        }
        return from;
    }

    @Override
    public String toString() {
        boolean f = false;
        StringBuilder sb = new StringBuilder();
        if(isNegative) sb.append('-');
        for (int i = 0; i < value.size(); i++) {
            if (value.get(i) >= 0 && !f) {
                f = true;
            }
            if (f) sb.append(value.get(i));
        }
        return sb.toString();
    }

}