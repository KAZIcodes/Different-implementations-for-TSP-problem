import Input.*;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static MST.TSPChristofides.rotateArray;

public class DisjointCycleTSP {

    private static int n; // number of cities
    private static double[][] dist; // distance matrix
    private static ArrayList<Integer> bestTour = new ArrayList<>(); // best tour found
    private static double minCost = Double.MAX_VALUE; // minimum cost

    public static void main(Input input) throws FileNotFoundException {
        // Example distance matrix (replace with your own data)
        dist = input.getGraph(); // Assuming the input provides a double[][] graph
        n = input.getSize();

        // Step 1: Initialize empty cycles
        ArrayList<ArrayList<Integer>> cycles = new ArrayList<>();
        boolean[] visited = new boolean[n];

        // Step 2: Decompose into initial disjoint cycles using a greedy approach
        for (int start = 0; start < n; start++) {
            if (!visited[start]) {
                ArrayList<Integer> cycle = new ArrayList<>();
                visited[start] = true;
                cycle.add(start);
                buildCycle(start, visited, cycle);
                cycles.add(cycle);
            }
        }

        // Step 3: Merge cycles in a way that minimizes the total cost
        ArrayList<Integer> finalTour = mergeCycles(cycles);

        // Step 4: Calculate the cost of the final tour
        double totalCost = calculateCost(finalTour);

        // Step 5: Output the results
        System.out.println("\nDisjoint Cycle Results:");
        System.out.println("Cost: " + totalCost);

        int[] res = rotateArray(finalTour, finalTour.indexOf(input.getStartCity()));
        List<String> indexToCoordinate = input.getIndexToCoordinate();
        System.out.println("TSP Route: ");
        if (indexToCoordinate == null)
            System.out.println(Arrays.toString(res));
        else {
            for (int node : res) {
                System.out.print("(" + indexToCoordinate.get(node) + ") ");
            }
            System.out.println("(" + indexToCoordinate.get(res[0]) + ")");
        }
    }

    // Build a cycle starting from a node using a greedy approach
    private static void buildCycle(int current, boolean[] visited, ArrayList<Integer> cycle) {
        int lastCity = current;
        while (cycle.size() < n / 8) { // Greedily find the nearest unvisited city
            int nextCity = -1;
            double minDist = Double.MAX_VALUE;
            for (int i = 0; i < n; i++) {
                if (!visited[i] && dist[lastCity][i] < minDist) {
                    nextCity = i;
                    minDist = dist[lastCity][i];
                }
            }

            // Ensure nextCity is valid
            if (nextCity != -1) {
                visited[nextCity] = true;
                cycle.add(nextCity);
                lastCity = nextCity;
            } else {
                // If no valid nextCity is found, break the loop
                break;
            }
        }
    }

    // Merge disjoint cycles to form a final TSP tour
    private static ArrayList<Integer> mergeCycles(ArrayList<ArrayList<Integer>> cycles) {
        ArrayList<Integer> mergedTour = new ArrayList<>();
        // Start with the first cycle
        mergedTour.addAll(cycles.get(0));

        // Greedily merge each subsequent cycle by finding the minimum cost edge
        for (int i = 1; i < cycles.size(); i++) {
            ArrayList<Integer> cycle = cycles.get(i);
            double minCostMerge = Double.MAX_VALUE;
            int mergeAtIndex = -1;

            // Find the cheapest way to merge the cycles
            for (int cityInMergedTour : mergedTour) {
                for (int cityInCycle : cycle) {
                    if (dist[cityInMergedTour][cityInCycle] < minCostMerge) {
                        minCostMerge = dist[cityInMergedTour][cityInCycle];
                        mergeAtIndex = cityInCycle;
                    }
                }
            }

            // Merge the cycle into the tour
            mergedTour.add(mergeAtIndex);
            cycle.remove(Integer.valueOf(mergeAtIndex)); // Remove merged city
            mergedTour.addAll(cycle); // Add remaining cities from cycle
        }

        return mergedTour;
    }

    // Calculate the total cost of the tour
    private static double calculateCost(ArrayList<Integer> tour) {
        double totalCost = 0.0;
        for (int i = 0; i < tour.size() - 1; i++) {
            totalCost += dist[tour.get(i)][tour.get(i + 1)];
        }
        totalCost += dist[tour.get(tour.size() - 1)][tour.get(0)]; // Closing the cycle
        return totalCost;
    }
}
