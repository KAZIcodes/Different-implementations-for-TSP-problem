package MST;

import Input.*;

import java.io.FileNotFoundException;
import java.util.*;

public class TSPChristofides {

    public static String main(Input input) throws FileNotFoundException {

        Graph graph = new Graph(input.getSize());
        for (int i =0; i < input.getSize(); i++) {
            for (int j = 0; j < input.getSize(); j++) {
                graph.addEdge(i, j, input.getGraph()[i][j]);
            }
        }


        // Step 1: Compute Minimum Spanning Tree (MST)
        Graph mst = graph.primMST(input.getStartCity());

        // Step 2: Find odd degree vertices in the MST
        List<Integer> oddVertices = mst.getOddDegreeVertices(mst);

        // Step 3: Find a minimum weight perfect matching for the odd degree vertices
        List<int[]> matching = graph.greedyMatching(oddVertices);

        // Step 4: Combine MST and matching to form Eulerian circuit
        List<Integer> eulerianPath = mst.combineMSTAndMatching(mst, matching);

        // Step 5: Shortcut Eulerian path to get Hamiltonian cycle
        List<Integer> hamiltonianCycle = mst.shortcutEulerianPath(eulerianPath);


        // Output the result
        System.out.println("\nChristofides Result: ");
        String cost = calculateCost(graph, hamiltonianCycle);
        //System.out.println("Hamiltonian Cycle: " + hamiltonianCycle);
        int[] res = rotateArray(hamiltonianCycle, hamiltonianCycle.indexOf(input.getStartCity()));
        List<String> indexToCoordinate = input.getIndexToCoordinate();
        System.out.println("TSP Route: ");
        if (indexToCoordinate == null)
            System.out.println(Arrays.toString(res));
        else {
            for (int node : res){
                System.out.print("(" + indexToCoordinate.get(node) + ") ");
            }
            System.out.println("(" + indexToCoordinate.get(res[0]) + ")");
        }

        return cost;
    }

    static String calculateCost(Graph graph, List<Integer> hamiltonianCycle){
        double sum = 0;
        for (int i = 0; i < hamiltonianCycle.size(); i++){
            if (i+1 == hamiltonianCycle.size()){
                sum += graph.adjMatrix[hamiltonianCycle.get(i)][hamiltonianCycle.get(0)];
            }
            else {
                sum += graph.adjMatrix[hamiltonianCycle.get(i)][hamiltonianCycle.get(i+1)];
            }
        }
        System.out.println("Cost: " + sum);
        return String.valueOf(sum);
    }

    public static int[] rotateArray(List<Integer> arr, int index) {
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
        return rotated;
    }
}
