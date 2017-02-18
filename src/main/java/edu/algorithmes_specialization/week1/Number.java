package edu.algorithmes_specialization.week1;

import java.util.ArrayList;
import java.util.regex.Pattern;

public class Number implements Comparable<Number> {
    private ArrayList<Integer> number;
    private boolean sign;

    public Number() throws Exception {
        this("");
    }

    public Number(String numberStr) throws Exception {
        number = new ArrayList<>();
        sign = true;

        if(numberStr.length() > 0 && !checkStringForNumbers(numberStr)) throw new Exception("Number in wrong format.");

        setNumberFromString(numberStr);
    }

    public Number(Number karatsubaNumber) {
        this.sign = karatsubaNumber.getSign();
        this.number =  new ArrayList<>(karatsubaNumber.getNumber());
    }

    //check if current number is equal to 0
    public boolean isZero(){
        return findNotZeroIndex() == -1;
    }

    //returns index of first occurrence of not zero number
    public int findNotZeroIndex() {

        for (int i = 0; i < length(); i++) {
            if (getValue(i) > 0) return i;
        }
        return  -1;
    }

    //removes zeros from number start
    public void removeZero () {
        int index = findNotZeroIndex();
        int size = length() - index;

        if(index == -1) return;

        while (length() != size){
            number.remove(0);
        }
    }

    //Returns first or second part of number
    public Number getHalfOfNumber(boolean isFirstHalf) throws Exception {
        ArrayList<Integer> list = new ArrayList<Integer>();
        int start = isFirstHalf ? 0 : length() / 2;
        int end = isFirstHalf ? length() / 2 : length();

        for (int i = start; i < end; i++) {
            list.add(getValue(i));
        }

        Number karatsubaNumber = new Number();
        karatsubaNumber.number = list;
        karatsubaNumber.sign = this.sign;
        return karatsubaNumber;
    }

    //borrow ten from number starts in specified index to digit with end index
    public void setNineBeforeFinalDigit(int start, int end) {
        this.setValue(start, getValue(start) - 1);
        for (int i = start + 1; i < end; i++) {
            this.setValue(i, 9);
        }
        this.setValue(end, getValue(end) + 10);
    }

    /*
    Find index from we can borrow ten
    @param start - position in original array from we substitute one to find position
    of the next element from which we will start
    */
    public int findNextNotZeroIndex(int start) {
        for (int i = start - 1; i >= 0; i--) {
            if(getValue(i) > 0) return i;
        }
        return -1;
    }

    //makes number equal to length of other number
    public void normalize(int size) {
        while (length() != size){
            number.add(0, 0);
        }
    }

    //multiply number by power of ten
    public void multiplyByPowerOfTen(int power) {
        for (int i = 0; i < power; i++) {
            number.add(0);
        }
    }

    public ArrayList<Integer> getNumber() {
        return number;
    }

    public boolean getSign() {
        return sign;
    }

    //Sets number array form specified sting containing number
    //representation
    private void setNumberFromString(String numberStr) {
        for (char digit : numberStr.toCharArray()) {
            if (Character.isDigit(digit)) {
                number.add(Integer.parseInt(String.valueOf(digit)));
            } else if (digit == '-') {
                sign = false;
            }
        }
    }

    private boolean checkStringForNumbers(String input) {
        return Pattern.matches("^(-)?(\\d)+$", input);
    }
    
    public int getValue(int index) {
        return number.get(index);
    }
    
    public void setValue(int index, int value){
        this.number.set(index, value);
    }
    
    public int length(){
        return number.size();
    }

    public void invertSign() {
        this.sign = !this.sign;
    }

    // will return 1 if this > number, 0 if  this == number
    // and -1 if this < number
    public int compareTo(Number number) {

        int result = 0;

        int firstIndex = this.findNotZeroIndex();
        int secondIndex = number.findNotZeroIndex();

        int lengthFirst = this.length() - (firstIndex >= 0 ? firstIndex : this.length());
        int lengthSecond = number.length() - (secondIndex >= 0 ? secondIndex : number.length());

        if (lengthFirst > lengthSecond) {
            result = 1;
        } else if (lengthSecond > lengthFirst){
            result = -1;
        } else if(firstIndex != -1 && secondIndex != -1){
            for (int i = firstIndex, j = secondIndex; i < lengthFirst && j < lengthSecond ; i++, j++) {
                result = Integer.compare(this.getValue(i), number.getValue(j));
                if(result != 0) break;
            }
        }

        return result;
    }

    @Override
    public String toString() {
        boolean f = false;
        StringBuilder sb = new StringBuilder();
        if(!sign) sb.append('-');
        for (int i = 0; i < length(); i++) {
            if (getValue(i) >= 0 && !f) {
                f = true;
            }
            if (f) sb.append(getValue(i));
        }
        return sb.toString();
    }
}
