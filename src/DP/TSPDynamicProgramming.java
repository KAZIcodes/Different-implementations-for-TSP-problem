package DP;

import Input.*;

import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class TSPDynamicProgramming {

    public static void main(Input input) throws FileNotFoundException {

        HeldKarp calc = new HeldKarp(input.getGraph(), input.getStartCity());
        List<Integer> result = calc.calculateHeldKarp();

        // List results
        System.out.println("\nDP Results:");
        System.out.println("Shortest Path Cost: " + calc.getOpt());
        List<String> indexToCoordinate = input.getIndexToCoordinate();
        System.out.println("TSP Route: ");
        double cost = 0;
        if (indexToCoordinate == null){
            for (int i = 0; i < result.size(); i++) {
                System.out.print(result.get(i) + " ");
                //cost += input.getGraph()[result.get(i)][result.get(i+1)];
            }
            System.out.println();
        }
        else {
            for (int node : result){
                System.out.print("(" + indexToCoordinate.get(node) + ") ");
            }
            System.out.println();
        }
    }


    public static int constrain(int min, int max, int number) {
        if(number < min) {
            return min;
        } else if (number > max) {
            return max;
        } else {
            return number;
        }
    }

}