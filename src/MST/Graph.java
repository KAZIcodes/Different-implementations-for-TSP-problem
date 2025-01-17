package MST;

import java.util.*;

public class Graph {
    int V;
    double[][] adjMatrix;

    public Graph(int V) {
        this.V = V;
        adjMatrix = new double[V][V];
    }

    public void addEdge(int u, int v, double weight) {
        adjMatrix[u][v] = weight;
        // adjMatrix[v][u] = weight; // Assuming undirected graph
    }

    // Optimized Minimum Spanning Tree using Prim's Algorithm with variable starting city
    public Graph primMST(int startCity) {
        Graph mst = new Graph(V);
        boolean[] inMST = new boolean[V];
        double[] key = new double[V];
        Arrays.fill(key, Double.MAX_VALUE);
        key[startCity] = 0.0;  // Starting from the specified city
        int[] parent = new int[V];
        Arrays.fill(parent, -1);  // No parent initially

        PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingDouble(a -> a[1]));
        pq.offer(new int[]{startCity, 0});  // Add start city to priority queue

        while (!pq.isEmpty()) {
            int[] u = pq.poll();
            int node = u[0];

            if (inMST[node]) continue;
            inMST[node] = true;

            for (int v = 0; v < V; v++) {
                if (adjMatrix[node][v] != 0.0 && !inMST[v] && adjMatrix[node][v] < key[v]) {
                    key[v] = adjMatrix[node][v];
                    parent[v] = node;
                    pq.offer(new int[]{v, (int) adjMatrix[node][v]}); // Cast weight to int for compatibility
                }
            }
        }

        // Build the MST using the parent array
        for (int v = 0; v < V; v++) {
            if (parent[v] != -1) {
                mst.adjMatrix[parent[v]][v] = adjMatrix[parent[v]][v];
                mst.adjMatrix[v][parent[v]] = adjMatrix[parent[v]][v];  // Undirected graph
            }
        }

        return mst;
    }

    // Get the odd degree vertices from the MST
    public List<Integer> getOddDegreeVertices(Graph mst) {
        List<Integer> oddVertices = new ArrayList<>();
        for (int i = 0; i < V; i++) {
            int degree = 0;
            for (int j = 0; j < V; j++) {
                if (mst.adjMatrix[i][j] != 0.0) degree++;
            }
            if (degree % 2 != 0) {
                oddVertices.add(i);
            }
        }
        return oddVertices;
    }

    // Optimized greedy matching for odd-degree vertices
    public List<int[]> greedyMatching(List<Integer> oddVertices) {
        List<int[]> matching = new ArrayList<>();
        boolean[] matched = new boolean[V];
        for (int i = 0; i < oddVertices.size(); i++) {
            if (matched[oddVertices.get(i)]) continue;
            int u = oddVertices.get(i);
            double minWeight = Double.MAX_VALUE;
            int vToMatch = -1;

            // Find the closest unpaired vertex to match with
            for (int j = i + 1; j < oddVertices.size(); j++) {
                int v = oddVertices.get(j);
                if (matched[v]) continue;
                if (adjMatrix[u][v] < minWeight) {
                    minWeight = adjMatrix[u][v];
                    vToMatch = v;
                }
            }

            if (vToMatch != -1) {
                matching.add(new int[]{u, vToMatch});
                matched[u] = true;
                matched[vToMatch] = true;
            }
        }
        return matching;
    }

    // Combine MST and matching to form Eulerian circuit
    public List<Integer> combineMSTAndMatching(Graph mst, List<int[]> matching) {
        List<Integer> eulerianPath = new ArrayList<>();
        Map<Integer, List<Integer>> graph = new HashMap<>();

        // Build an adjacency list from the MST
        for (int i = 0; i < mst.V; i++) {
            for (int j = 0; j < mst.V; j++) {
                if (mst.adjMatrix[i][j] != 0.0) {
                    graph.computeIfAbsent(i, k -> new ArrayList<>()).add(j);
                }
            }
        }

        // Add edges from the matching
        for (int[] match : matching) {
            graph.computeIfAbsent(match[0], k -> new ArrayList<>()).add(match[1]);
            graph.computeIfAbsent(match[1], k -> new ArrayList<>()).add(match[0]);
        }

        // Perform a DFS to create the Eulerian path
        Set<Integer> visited = new HashSet<>();
        dfs(0, graph, visited, eulerianPath);

        return eulerianPath;
    }

    // Depth-First Search (DFS) helper function
    private void dfs(int node, Map<Integer, List<Integer>> graph, Set<Integer> visited, List<Integer> path) {
        visited.add(node);
        path.add(node);

        for (int neighbor : graph.getOrDefault(node, new ArrayList<>())) {
            if (!visited.contains(neighbor)) {
                dfs(neighbor, graph, visited, path);
            }
        }
    }

    // Shortcut the Eulerian path to get Hamiltonian cycle
    public List<Integer> shortcutEulerianPath(List<Integer> eulerianPath) {
        Set<Integer> visited = new HashSet<>();
        List<Integer> hamiltonianCycle = new ArrayList<>();

        for (int vertex : eulerianPath) {
            if (!visited.contains(vertex)) {
                visited.add(vertex);
                hamiltonianCycle.add(vertex);
            }
        }

        return hamiltonianCycle;
    }
}
