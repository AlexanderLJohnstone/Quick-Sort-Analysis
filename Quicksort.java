import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;

public class Quicksort {


    /**
     * Takes an arralist and sorts it using a quicksort
     *
     * @param list array list of integers
     * @return sorted arraylist
     */
    public static ArrayList<Integer> Quicksort(ArrayList<Integer> list){
        if (list.size() <= 1){
            return list;
        }else{
            int pivot = list.get(list.size() - 1);
            ArrayList<Integer> p1 = new ArrayList<>();
            ArrayList<Integer> p2 = new ArrayList<>();
            for(int i = 0; i < list.size() - 1; i++){
                if (list.get(i) <= pivot){
                    p1.add(list.get(i));
                }else{
                    p2.add(list.get(i));
                }
            }
            ArrayList<Integer> sorted = new ArrayList<>();
            sorted.addAll(Quicksort(p1));
            sorted.add(pivot);
            sorted.addAll(Quicksort(p2));
            return sorted;
        }

    }

    /**
     * takes an array list and swaps two specified elements
     *
     * @param list array list of integers
     * @param i first index
     * @param j second index
     */
    public static void swap(ArrayList<Integer> list, int i, int j){
        int temp = list.get(i);
        list.set(i, list.get(j));
        list.set(j, temp);
    }



}
