package graphModule;

public class Edge<T> {
    public T destination;
    public int weight;

    public Edge(T destination, int weight) {
        this.destination = destination;
        this.weight = weight;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Edge)) return false;
        Edge<?> other = (Edge<?>) obj;
        return destination.equals(other.destination) && weight == other.weight;
    }
}
