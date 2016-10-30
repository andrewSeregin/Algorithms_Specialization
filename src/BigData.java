import java.util.ArrayList;

/**
 * Created by Andrew on 30.10.2016.
 */
public class BigData {

    private boolean isNegative;
    public ArrayList<Integer> value = new ArrayList<>();

    BigData(String inner) {

        for (char number : inner.toCharArray()) {
            if (Character.isDigit(number)) {
                value.add(Integer.parseInt(String.valueOf(number)));
            } else if (number == '-') {
                isNegative = true;
            }

        }

    }

    public void addToSelf(BigData val) {

        ArrayList<Integer> result = new ArrayList<>();
        for (int i = 0; i < val.value.size() + this.value.size() + 2; i++) {
            result.add(0);
        }
        int index = result.size() - 1;
        int count = -50;
        int transfer = 0;
        int i = value.size() - 1;
        int x = val.value.size() - 1;

        boolean f = val.value.size() > value.size();

        for (; i >= 0 && x >= 0; i--, x--) {

            int sum = value.get(i) + val.value.get(x) + transfer;

            if (sum >= 10) {
                int t = sum / 10;
                count = sum % 10;
                result.set(index, count);
                transfer = t;
            } else {
                result.set(index, sum);
                transfer = 0;
            }

            index--;

        }

        i = f ? x : i;
        ArrayList<Integer> temp = f ? val.value : value;

        for ( ; i >= 0 ; i--) {
            int sum = temp.get(i)  + transfer;

            if (sum >= 10) {
                int t = sum / 10;
                count = sum % 10;
                result.set(index, count);
                transfer = t;
            } else {
                result.set(index, sum);
                transfer = 0;
            }

            index--;
        }

        value = result;

    }

    public void subtractFromSelf(BigData val){
        
        ArrayList<Integer> array = value;
        
        if (array.size() <  val.value.size()) {
            array = val.value;
        } else if (array.size() == val.value.size() && array.get(0) < val.value.get(0)) array = val.value;


    }

    @Override
    public String toString() {
        boolean f = false;
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i< value.size(); i++) {
            if(value.get(i) > 0 && !f) {
                f = true;
            }
            if(f) sb.append(value.get(i));
        }
        return sb.toString();
    }
}
