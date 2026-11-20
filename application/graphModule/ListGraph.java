package graphModule;

import structures.SimpleArrayDictionary;
import structures.SimpleArrayList;
import structures.SimpleDictionary;
import structures.SimpleList;

public class ListGraph<T> implements Graph<T> {
    private final SimpleDictionary<T, SimpleList<Edge<T>>> adjacency;

    public ListGraph() {
        this.adjacency = new SimpleArrayDictionary<>();
    }

    @Override
    public void addVertex(T vertex) {
        if (vertex == null) {
            throw new IllegalArgumentException("El vértice no puede ser null.");
        }
        if (containsVertex(vertex)) return;

        adjacency.put(vertex, new SimpleArrayList<>());
    }

    @Override
    public void removeVertex(T vertex) {
        if (!containsVertex(vertex)) return;

        adjacency.remove(vertex);

        SimpleList<T> allVertices = vertices();
        for (int i = 0; i < allVertices.size(); i++) {
            removeEdge(allVertices.get(i), vertex);
        }
    }

    @Override
    public void addEdge(T from, T to, int weight) {
        if (!containsVertex(from) || !containsVertex(to)) {
            throw new IllegalArgumentException("Ambos vértices deben existir en el grafo.");
        }

        Edge<T> existing = getEdge(from, to);
        if (existing != null) {
            existing.weight = weight;
            return;
        }

        adjacency.get(from).add(new Edge<>(to, weight));
    }

    @Override
    public void removeEdge(T from, T to) {
        SimpleList<Edge<T>> neighbors = adjacency.get(from);
        if (neighbors == null) return;

        Edge<T> existing = getEdge(from, to);
        if (existing != null) neighbors.remove(existing);
    }

    @Override
    public SimpleList<Edge<T>> getNeighbors(T vertex) {
        if (!containsVertex(vertex)) {
            throw new IllegalArgumentException("El vértice no existe en el grafo.");
        }
        return adjacency.get(vertex);
    }

    @Override
    public boolean containsVertex(T vertex) {
        return adjacency.containsKey(vertex);
    }

    @Override
    public boolean containsEdge(T from, T to) {
        return getEdge(from, to) != null;
    }

    @Override
    public int getWeight(T from, T to) {
        Edge<T> edge = getEdge(from, to);
        if (edge == null) {
            throw new IllegalArgumentException("No existe una arista entre esos vértices.");
        }
        return edge.weight;
    }

    @Override
    public SimpleList<T> vertices() {
        return adjacency.keys();
    }

    private Edge<T> getEdge(T from, T to) {
        SimpleList<Edge<T>> neighbors = adjacency.get(from);
        if (neighbors == null) return null;

        for (int i = 0; i < neighbors.size(); i++) {
            if (neighbors.get(i).destination.equals(to)) {
                return neighbors.get(i);
            }
        }
        return null;
    }
}
