import java.util.ArrayList;

public class BubbleSort {

    public static ArrayList<Integer> sort(ArrayList<Integer> list){
        for(int i = 0; i < list.size() - 1; i++) {
            for(int j = 0; j < list.size() - 1-i; j++){
                if(list.get(j) > list.get(j+1)){
                    Quicksort.swap(list, j, j+1);
                }
            }
        }
        return list;
    }
}
