package edu.algorithmes_specialization.week2;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Ivan on 18.02.2017.
 */
public class InversionsCounter {
    public double countInversions(ArrayList<Integer> array){
        return sortAndCount(array).getCountOfInversions();
    }

    private SortArrayResult sortAndCount(ArrayList<Integer> array) {
        if(array.size() == 1) {
            return new SortArrayResult(array, 0);
        }

        SortArrayResult a = sortAndCount(getArrayHalf(array, 1));
        SortArrayResult b = sortAndCount(getArrayHalf(array,2));
        SortArrayResult d = mergeAndCount(a.getSortedArray(), b.getSortedArray(), array.size());

        double aCount = a.getCountOfInversions();
        double bCount = b.getCountOfInversions();
        double dCount = d.getCountOfInversions();
        System.out.println("aCount = " + aCount);
        System.out.println("bCount = " + bCount);
        System.out.println("dCount = " + dCount + "\n\n");
        return new SortArrayResult(d.getSortedArray(),
                 aCount + bCount + dCount);
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
                //System.out.println("sizeFirst - i:" + (sizeFirst - i));
                countOfInversions += sizeFirst - i;
                //System.out.println("count of inversions: " + countOfInversions + "\n\n");
                j++;
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
        private double countOfInversions;

        public ArrayList<Integer> getSortedArray() {
            return sortedArray;
        }

        public double getCountOfInversions() {
            return countOfInversions;
        }

        public SortArrayResult(ArrayList<Integer> sortedArray, double countOfInversions) {
            this.sortedArray = sortedArray;
            this.countOfInversions = countOfInversions;
        }
    }
}
