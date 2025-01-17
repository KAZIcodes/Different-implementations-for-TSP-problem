package Input;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class InputGetter {
    private static double[][] graph;
    private static int n;
    public static Input getInput() throws FileNotFoundException {
        Scanner scanner = new Scanner(new File("input.txt"));
        String startCity = scanner.nextLine(); // Read starting city
        List<Integer> cityList = new ArrayList<>();
        List<double[]> lines = new ArrayList<>();


        // Read edges
        while (scanner.hasNext()) {
            double source = Double.parseDouble(scanner.next());
            double destination = Double.parseDouble(scanner.next());
            double cost = Double.parseDouble(scanner.next());
            lines.add(new double[]{source, destination, cost});

            if (!cityList.contains((int)source)) cityList.add((int)source);
            if (!cityList.contains((int)destination)) cityList.add((int)destination);
        }

        n = cityList.size();
        graph = new double[n][n];
        for (double[] line : lines){
            graph[(int)line[0]][(int)line[1]] = line[2];
            graph[(int)line[1]][(int)line[0]] = line[2];
        }

        return new Input(Integer.parseInt(startCity), graph, n, null);

    }


    public static Input getInputCoordinate() throws FileNotFoundException {
        Scanner scanner = new Scanner(new File("/Users/amirali/desktop/algoproject/algoproject/src/input.txt"));

        // Read the constant multiplier
        double multiplier = Double.parseDouble(scanner.nextLine().trim());

        // Read the city coordinates
        List<double[]> coordinates = new ArrayList<>(); // Store unique (X, Y) coordinates
        Map<String, Integer> coordinateToIndex = new HashMap<>(); // Map to track coordinate -> index mapping
        List<String> indexToCoordinate = new ArrayList<>(); // Reverse map: index -> coordinate

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine().trim();
            if (line.isEmpty()) continue;

            String[] parts = line.split("\\) \\("); // Split by ") ("
            parts[0] = parts[0].replace("(", "").trim(); // Remove "("
            parts[1] = parts[1].replace(")", "").trim(); // Remove ")"

            String[] source = parts[0].split(",\\s*"); // Split "X_source_city, Y_source_city"
            String[] destination = parts[1].split(",\\s*"); // Split "X_destination_city, Y_destination_city"

            // Parse coordinates
            double x1 = Double.parseDouble(source[0]);
            double y1 = Double.parseDouble(source[1]);
            double x2 = Double.parseDouble(destination[0]);
            double y2 = Double.parseDouble(destination[1]);

            // Convert to string keys for the map
            String sourceKey = x1 + "," + y1;
            String destinationKey = x2 + "," + y2;

            // Add to map and list if not already present
            if (!coordinateToIndex.containsKey(sourceKey)) {
                coordinateToIndex.put(sourceKey, coordinates.size());
                coordinates.add(new double[]{x1, y1});
                indexToCoordinate.add(sourceKey);
            }
            if (!coordinateToIndex.containsKey(destinationKey)) {
                coordinateToIndex.put(destinationKey, coordinates.size());
                coordinates.add(new double[]{x2, y2});
                indexToCoordinate.add(destinationKey);
            }
        }

        // Number of cities
        int n = coordinates.size();
        double[][] graph = new double[n][n];

        // Compute distances
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i != j) {
                    double[] city1 = coordinates.get(i);
                    double[] city2 = coordinates.get(j);

                    // Euclidean distance formula
                    double distance = Math.sqrt(Math.pow(city1[0] - city2[0], 2) + Math.pow(city1[1] - city2[1], 2));
                    double finalDistance = (distance * multiplier); // Apply multiplier
                    graph[i][j] = finalDistance;
                    //graph[j][i] =  finalDistance;
                }
            }
        }

        // Print the adjacency matrix for verification
//        int count = 0;
//        for (int i = 0; i < n; i++) {
//            for (int j = 0; j < n; j++) {
//                System.out.print(graph[i][j] + " ");
//                count++;
//            }
//            System.out.println();
//        }
//        System.out.println("\n" + count + "n: " + n + "\n");

        // Create an Input object for the TSP algorithm
        return new Input(0, graph, n, indexToCoordinate); // Include reverse mapping
    }


