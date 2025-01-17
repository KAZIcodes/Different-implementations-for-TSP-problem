package Input;

import java.util.List;

public class Input {
    private int startCity;
    private double[][] graph;
    private int size;
    private List<String> indexToCoordinate;

    public Input(int startCity, double[][] graph, int size, List<String> indexToCoordinate) {
        this.startCity = startCity;
        this.graph = graph;
        this.size = size;
        this.indexToCoordinate = indexToCoordinate;
    }

    // Getters
    public int getStartCity() {
        return startCity;
    }

    public double[][] getGraph() {
        return graph;
    }

    public int getSize() {
        return size;
    }

    public List<String> getIndexToCoordinate() {
        return indexToCoordinate;
    }
}
