package graphModule;

import structures.SimpleList;

public interface Graph<T> {
    SimpleList<T> vertices();
    void addVertex(T vertex);
    void removeVertex(T vertex);
    void addEdge(T from, T to, int weight);
    void removeEdge(T from, T to);
    SimpleList<Edge<T>> getNeighbors(T vertex);
    boolean containsVertex(T vertex);
    boolean containsEdge(T from, T to);
    int getWeight(T from, T to);
}
