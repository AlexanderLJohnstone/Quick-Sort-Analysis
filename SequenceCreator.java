
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Comparator;

public class SequenceCreator {

    /**
     * Main method calls the test generator and writes all the values to a csv
     *
     * @param args program arguments
     */
    public static void main(String args[]){

        ArrayList<Pair> data = SequenceCreator.testDataGenerator();
        try{
            //write all data to a csv
            PrintWriter pw = new PrintWriter(new File("Extended.csv"));
            StringBuilder sb = new StringBuilder();
            sb.append("Sorted Value");
            sb.append(',');
            sb.append("Time");
            sb.append('\n');
            for(int i =0; i < data.size(); i++){
                sb.append((data.get(i).getValue()/ 10000.0));
                sb.append(',');
                sb.append((double) data.get(i).getKey()/1000.0);
                sb.append('\n');
            }
            pw.write(sb.toString());
            pw.close();
        }catch(FileNotFoundException e){
            System.out.println(e.getMessage());
        }

    }

    /**
     * Takes a sequence and performs the specified number of swaps on random elements
     *
     * @param sequence ordered sequence
     * @param swaps number of swaps to be performed
     * @return unordered sequence
     */
    public static ArrayList<Integer> randomiseSequence(ArrayList<Integer> sequence, int swaps){
        while(swaps > 0){
            int term1 = (int) (Math.random() * sequence.size());
            int term2 = (int) (Math.random() * sequence.size());
            Quicksort.swap(sequence, term1, term2);
            swaps--;
        }
        return sequence;
    }

    /**
     * Calculate the minimum number of swaps it takes to sort an array
     *
     * @param list a list of integers
     * @return the minimum number of swaps
     */
    public static int minSwaps(ArrayList<Integer> list){
        //create array list of pair with elements and the index they appear at originally
        ArrayList<Pair> listPositions = new ArrayList<>();
        for(int i = 0; i < list.size(); i++){
            listPositions.add(new Pair(list.get(i), i));
        }
        Comparator comparator =  new Comparator<Pair>()
        {
            @Override
            public int compare(Pair pair1, Pair pair2) {
                if (pair1.getKey() < pair2.getKey())
                    return -1;
                else if (pair1.getKey() == pair2.getKey())
                    return 0;

                else
                    return 1;
            }
        };
        listPositions.sort(comparator);
        boolean[] visited = new boolean[list.size()];
        for(int i = 0; i < visited.length; i++){
            visited[i] = false;
        }

        int swaps = 0;

        for(int i = 0; i < list.size(); i++){
            int run = 0;
            if(visited[i] || listPositions.get(i).getValue() == i){
                //do nothing
            } else{

                int position = i;
                //find the number of ellements in a run or cycle
                while(visited[position] == false){
                    visited[position] = true;
                    position = listPositions.get(position).getValue();
                    run++;
                }

            }
            if(run > 0){
                swaps += run - 1;
            }
        }
        return swaps;
    }

    /**
     * This method returns a list of times and sorted values showing how long it takes the
     * quicksort to perform with that sorted value (on average). It increases the sorted value
     * with each iteration and then creates 1000 (most likely different) sequences with that
     * sorted value to be averaged across.
     *
     * @return times and sorted values pairs
     */
    public static ArrayList<Pair> testDataGenerator(){
        ArrayList<Pair> times = new ArrayList<>();

        //add the pathological case
        ArrayList<ArrayList<Integer>> initials = new ArrayList<>();
        for(int j = 0; j < 100; j++) {
            initials.add(new ArrayList<>());
            for (int i = j; i < 1000 + j; i++) {
                initials.get(j).add(i);
            }
        }

        times.add(new Pair(getAverageTimeBubble(initials), 0));

        ArrayList<Integer> previousSequence = initials.get(0);
        //do for sorted values between 20 and 1000
        for(int i = 0; i < 200; i++) {
            ArrayList<ArrayList<Integer>> currentSequences = new ArrayList<>();
            double sortedValue = 0;
            for(int j = 0; j < 100; j++){//create 1000 sequences for sorted value
                //temp sequence stops sv from increasing too much
                ArrayList<Integer> tempSequence = new ArrayList<>();
                tempSequence.addAll(previousSequence);
                //add a new sequence with 2 swaps
                currentSequences.add(randomiseSequence(tempSequence,  5));
                sortedValue += minSwaps(currentSequences.get(j));
            }
            //get sorted score
            sortedValue = (int) Math.ceil(sortedValue /100.0);
            System.out.println(sortedValue);
            //use first index as previous sequence to base next sv sequence off
            previousSequence = new ArrayList<>();
            previousSequence.addAll(currentSequences.get(0));
            //create pair
            times.add(new Pair(getAverageTimeBubble(currentSequences), (int)sortedValue));
        }
        return times;

    }

    private static int getAverageTimeBubble(ArrayList<ArrayList<Integer>> sequence){
        ArrayList<Long> times = new ArrayList<>();
        for(int i = 0; i < sequence.size(); i++){
            long start = System.currentTimeMillis();
            BubbleSort.sort(sequence.get(i));
            long end = System.currentTimeMillis();
            times.add(end - start);
        }
        double average = 0;
        for(int i = 0; i < times.size(); i++){
            average += times.get(i);
        }
        average = average/(double)sequence.size();
        System.out.println(average + " ms");
        return (int) (average* 1000);
    }


}

