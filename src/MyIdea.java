import DP.TSPDynamicProgramming;
import Input.*;
import MST.TSPChristofides;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

public class MyIdea {

    public static String main(Input input) throws IOException {

        int startNode = input.getStartCity(); // Starting node for TSP
        double[][] graph = input.getGraph(); // Assuming input provides a double[][] graph

        long startTime = System.currentTimeMillis();
        List<Integer> tspRoute = findTSPRoute(graph, startNode);
        double cost = 0.0;
        for (int i = 0; i < tspRoute.size() - 1; i++) {
            cost += graph[tspRoute.get(i)][tspRoute.get(i + 1)];
        }

        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        System.out.println("\nMy Results:");
        System.out.println("Cost: " + cost);
        List<String> indexToCoordinate = input.getIndexToCoordinate();
        System.out.println("TSP Route: ");
        if (indexToCoordinate == null)
            System.out.println(tspRoute);
        else {
            for (int node : tspRoute) {
                System.out.print("(" + indexToCoordinate.get(node) + ") ");
            }
            System.out.println();
        }
        System.out.println("Time taken: " + duration + " milliseconds");


        //startTime = System.currentTimeMillis();
        //TSPChristofides.main(new String[]{""});
        //TSPDynamicProgramming.main(input);
        //TSPBruteForce.main(new String[]{""});
        //DisjointCycleTSP.main(new String[]{""});
        //endTime = System.currentTimeMillis();
        //duration = endTime - startTime;
        //System.out.println("Time taken: " + duration + " milliseconds");
        return String.valueOf(cost);
    }

    public static List<Integer> findTSPRoute(double[][] graph, int startNode) {
        int n = graph.length;
        boolean[] visited = new boolean[n]; // To track visited nodes
        List<Integer> route = new ArrayList<>(); // To store the final route

        int currentNode = startNode;
        route.add(currentNode);
        visited[currentNode] = true;

        // Iterate until all nodes are visited
        while (route.size() < n) {
            int nextNode = -1;
            double minValue = Double.MAX_VALUE;

            // Check each edge connected to the current node
            for (int neighbor = 0; neighbor < n; neighbor++) {
                if (!visited[neighbor] && graph[currentNode][neighbor] > 0) {
                    // Calculate the sum of weights for the greedy choice
                    double value = calculateGreedyValue(graph, currentNode, neighbor, visited);

                    if (value < minValue) {
                        minValue = value;
                        nextNode = neighbor;
                    }
                }
            }

            // Move to the chosen next node
            if (nextNode != -1) {
                route.add(nextNode);
                visited[nextNode] = true;
                currentNode = nextNode;
            } else {
                break; // No valid next node (should not occur in a complete graph)
            }
        }

        // Return to the start node to complete the cycle
        route.add(startNode);
        return route;
    }

    public static double calculateGreedyValue(double[][] graph, int currentNode, int neighbor, boolean[] visited) {
        int n = graph.length;
        double value = graph[currentNode][neighbor];

        // Find the minimum edge weight for the neighbor's neighbors
        double minNeighborValue = Double.MAX_VALUE;
        int secondLevelNode = -1;

        for (int nextNeighbor = 0; nextNeighbor < n; nextNeighbor++) {
            if (nextNeighbor != currentNode && !visited[nextNeighbor] && graph[neighbor][nextNeighbor] > 0) {
                if (graph[neighbor][nextNeighbor] < minNeighborValue) {
                    minNeighborValue = graph[neighbor][nextNeighbor];
                    secondLevelNode = nextNeighbor;
                }
            }
        }

        // Add the minimum value from the second level if it exists
        if (secondLevelNode != -1) {
            double minSecondLevelValue = Double.MAX_VALUE;
            for (int nextLevelNeighbor = 0; nextLevelNeighbor < n; nextLevelNeighbor++) {
                if (nextLevelNeighbor != neighbor && nextLevelNeighbor != currentNode && !visited[nextLevelNeighbor] && graph[secondLevelNode][nextLevelNeighbor] > 0) {
                    minSecondLevelValue = Math.min(minSecondLevelValue, graph[secondLevelNode][nextLevelNeighbor]);
                }
            }
            if (minSecondLevelValue != Double.MAX_VALUE) {
                value += minNeighborValue + minSecondLevelValue;
            } else {
                value += minNeighborValue; // If no valid third level exists
            }
        }

        return value;
    }
}
