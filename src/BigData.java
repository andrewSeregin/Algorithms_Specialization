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

        Collections.reverse(result);
        return new BigData(result, false);
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

        return new BigData(result, negative);
    }

    public static BigData multipleTwoItems(BigData one, BigData two) throws Exception {
        if(one.value.size() == 1) {
            ArrayList<Integer> res = new ArrayList<>();
            int t = one.value.get(0) * two.value.get(0);
            return new BigData(new Integer(t).toString());
        }
        BigData xh = new BigData();
        xh.value = getHalf(one.value, false);
        xh.isNegative = one.isNegative;

        BigData xl = new BigData();
        xl.value = getHalf(one.value, true);
        xl.isNegative = one.isNegative;

        BigData yh = new BigData();
        yh.value = getHalf(two.value, false);
        yh.isNegative = two.isNegative;

        BigData yl = new BigData();
        yl.value = getHalf(two.value, true);
        yl.isNegative = two.isNegative;

        BigData a = multipleTwoItems(xh, yh);
        BigData d = multipleTwoItems(xl, yl);
        BigData e = multipleTwoItems(addTwoValues(xh, xl), addTwoValues(yh, yl));
        e = subtractFromSelf(e,a);
        e = subtractFromSelf(e, d);

        BigData power1 = new BigData(new Integer((int)Math.pow(10, one.value.size())).toString());
        BigData power2 = new BigData(new Integer((int)Math.pow(10, (int)(one.value.size() / 2))).toString());

        //TODO : Fix me not now!!!!!!!
        BigData xy = simpleMultiple(a, power1);
        xy = addTwoValues(xy, simpleMultiple(e, power2));
        xy = addTwoValues(xy, d);
        return xy;
    }

    public static BigData simpleMultiple(BigData one, BigData two) {
        ArrayList<Integer> result = new ArrayList<>();
        int transfer = 0;
        boolean sizer = one.value.size() > two.value.size();
        int i = sizer ? one.value.size() - 1 : two.value.size() - 1;
        int x = sizer ? two.value.size() - 1 : one.value.size() - 1;
        ArrayList<Integer> fromData = sizer ? one.value : two.value;
        ArrayList<Integer> multdata = sizer ? two.value : one.value;

        int sum;
        int pass = 0;
        for (; x >= 0; x--) {
            for (int z = i; z >= 0; z--) {
                if(multdata.get(x) != 0)sum = fromData.get(z) * multdata.get(x) + transfer;
                else {
                    result.add(0);
                    break;
                }
                transfer = getTransfer(result, sum);
            }
            pass++;
        }

        Collections.reverse(result);
        return new BigData(result, false);
    }

    private static int addTransferOfUpdate(ArrayList<Integer> result, int sum, int pass, int index) {
        int count;
        int transfer;
        boolean indexFlag = index - pass >= 0;
        if(pass == 0) {
            if (Math.abs(sum) >= 10) {
                int t = sum / 10;
                count = sum % 10;
                result.add(count);
                transfer = t;
            } else {
                result.add(sum);
                transfer = 0;
            }
        }
        else {
            int res = (indexFlag) ? result.get(index - pass) : 0;
            if (Math.abs(sum) >= 10) {
                int t = sum / 10;
                count = sum % 10;
                if(indexFlag) {
                    count += res;
                    if(count >= 10) {
                        int tt = count / 10;
                        count = count %10;
                        result.set(index - pass, count);
                        appendTransfer(result, tt, index - pass);
                    }
                    else {
                        result.set(index - pass, count);
                    }
                }
                else result.add(0, count);
                transfer = t;
            } else {
                result.add(sum);
                transfer = 0;
            }
        }
        return transfer;
    }

    private static void appendTransfer(ArrayList<Integer> result, int transfer, int indexFrom) {
        int i = indexFrom - 1;
        int count;
        while(transfer != 0 || i >= 0) {
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
            result.add(0, transfer);
        }
    }

    public static ArrayList<Integer> getHalf(ArrayList<Integer> source, boolean first) {
        ArrayList<Integer> list = new ArrayList<Integer>();
        int index = first ? source.size() - 1 : source.size() / 2 - 1;
        int end = first ? source.size() / 2 : 0;
        for (int i = index; i >= end; i--) {
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
        if(val.value.size() > other.value.size()) {
            from = 1;
        }
        else if(val.value.size() < other.value.size()) {
            from = 2;
        }
        else {
            from = (other.value.get(0) < val.value.get(0)) ? 1 : 2;
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

    private boolean checkStringForNumbers(String input) {
        return Pattern.matches("^(-)?(\\d)+$", input);
    }
}
