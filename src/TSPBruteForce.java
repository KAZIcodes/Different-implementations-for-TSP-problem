import Input.Input;
import Input.InputGetter;

import java.util.*;
import java.io.*;

public class TSPBruteForce {


    public static void main(Input input) throws IOException {
        double[][] graph = input.getGraph();
        int n = input.getSize();



        // Brute force TSP
        int[] path = new int[n];
        for (int i = 0; i < n; i++) path[i] = i;
        double minCost = Double.MAX_VALUE;
        List<Integer> bestPath = new ArrayList<>();

        // Generate all permutations of the cities
        do {
            double cost = 0;
            for (int i = 0; i < n - 1; i++) {
                cost += graph[path[i]][path[i + 1]];
            }
            cost += graph[path[n - 1]][path[0]]; // Return to starting point
            if (cost < minCost) {
                minCost = cost;
                bestPath.clear();
                for (int city : path) {
                    bestPath.add(city);
                }
            }
        } while (nextPermutation(path));

        System.out.println("\nBrute Force Results:");
        System.out.println("Minimum cost: " + minCost);
        List<String> indexToCoordinate = input.getIndexToCoordinate();
        System.out.println("TSP Route: ");
        if (indexToCoordinate == null)
            for (int i = 0; i < bestPath.size(); i++) {
                if (bestPath.get(i) == input.getStartCity()){
                    rotateArray(bestPath, i);
                    break;
                }
            }
        else {
            for (int node : bestPath){
                System.out.print("(" + indexToCoordinate.get(node) + ") ");
            }
            System.out.println("(" + indexToCoordinate.get(bestPath.get(0)) + ")");
        }
    }

    // Helper function to generate the next permutation of the array
    private static boolean nextPermutation(int[] array) {
        int i = array.length - 1;
        while (i > 0 && array[i - 1] >= array[i]) {
            i--;
        }
        if (i <= 0) return false;
        int j = array.length - 1;
        while (array[j] <= array[i - 1]) {
            j--;
        }
        swap(array, i - 1, j);
        j = array.length - 1;
        while (i < j) {
            swap(array, i++, j--);
        }
        return true;
    }

    // Helper function to swap two elements
    private static void swap(int[] array, int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    // Function to rotate the array
    public static void rotateArray(List<Integer> arr, int index) {
        int n = arr.size();
        int[] rotated = new int[n];
        int pos = 0;

        // Copy elements from the found index to the start of the array
        for (int i = index; i < n; i++) {
            rotated[pos++] = arr.get(i);
        }

        // Copy remaining elements from the beginning to the found index
        for (int i = 0; i < index; i++) {
            rotated[pos++] = arr.get(i);
        }

        // Print the result
        System.out.println(Arrays.toString(rotated));
    }
}
