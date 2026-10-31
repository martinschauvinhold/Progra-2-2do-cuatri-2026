package model;

public class Edge<T> {

    private T destination;
    private int weight;

    public Edge(T destination, int weight) {
        this.destination = destination;
        this.weight = weight;
    }

    public T getDestination() {
        return destination;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public void setDestination(T destination) {
        this.destination = destination;
    }

    @Override
    public boolean equals(Object other) {
        if(other.getClass() != this.getClass()){
            return false;
        }

        // Casteamos a Edge para acceder a las variables
        Edge<T> otherEdge = (Edge<T>) other;

        // Devolvemos si ambas variables son iguales o no
        return otherEdge.destination.equals(this.destination) && otherEdge.weight == this.weight;
    }
}
