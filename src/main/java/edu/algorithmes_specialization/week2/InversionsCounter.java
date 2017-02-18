package edu.algorithmes_specialization.week2;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Ivan on 18.02.2017.
 */
public class InversionsCounter {
    public int CountInversions(ArrayList<Integer> array){
        return SortAndCount(array).getCountOfInversions();
    }

    private SortArrayResult SortAndCount(ArrayList<Integer> array) {
        if(array.size() == 1) {
            return new SortArrayResult(array, 0);
        }

        SortArrayResult a = SortAndCount(getArrayHalf(array, 1));
        SortArrayResult b = SortAndCount(getArrayHalf(array,2));
        SortArrayResult d = mergeAndCount(a.getSortedArray(), b.getSortedArray(), array.size());

        return new SortArrayResult(d.getSortedArray(),
                a.getCountOfInversions() + b.getCountOfInversions() + d.getCountOfInversions());
    }

    private SortArrayResult mergeAndCount(ArrayList<Integer> first, ArrayList<Integer> second, int size) {
        int i = 0, j = 0;
        int sizeFirst = first.size();
        int sizeSecond = second.size();
        int countOfInversions = 0;
        ArrayList<Integer> mergedArray = new ArrayList<>();

        for(int k = 0; k < size && j < sizeSecond && i < sizeFirst; k++) {
            if(first.get(i) < second.get(j)) {
                mergedArray.add(k, first.get(i));
                i++;
            }
            else {
                mergedArray.add(k, second.get(j));
                j++;
                countOfInversions += sizeFirst - i;
            }
        }
        if(i != sizeFirst) {
            for (; i < sizeFirst; i++){
                mergedArray.add(first.get(i));
            }
        }

        if(j != sizeSecond) {
            for (; j < sizeSecond; j++) {
                mergedArray.add(second.get(j));
            }
        }
        return new SortArrayResult(mergedArray, countOfInversions);
    }

    private ArrayList<Integer> getArrayHalf(ArrayList<Integer> array, int halfNumber) {
        ArrayList<Integer> result = new ArrayList<>();
        int startIndex, endIndex;
        if(halfNumber == 1) {
            startIndex = 0;
            endIndex = array.size() / 2;
        }
        else {
            startIndex = array.size() / 2;
            endIndex = array.size();
        }

        for (int i = startIndex; i < endIndex; i++) {
            result.add(array.get(i));
        }
        return result;
    }

    private class SortArrayResult{
        private ArrayList<Integer> sortedArray;
        private int countOfInversions;

        public ArrayList<Integer> getSortedArray() {
            return sortedArray;
        }

        public int getCountOfInversions() {
            return countOfInversions;
        }

        public SortArrayResult(ArrayList<Integer> sortedArray, int countOfInversions) {
            this.sortedArray = sortedArray;
            this.countOfInversions = countOfInversions;
        }
    }
}
