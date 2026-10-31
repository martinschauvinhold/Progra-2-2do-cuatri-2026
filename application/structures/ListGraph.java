package structures;

import model.Edge;

public class ListGraph<T> implements Graph<T> {

    private SimpleArrayDictionary<T, SimpleArrayList<Edge<T>>> adjacencyList;

    public ListGraph(){
        this.adjacencyList = new SimpleArrayDictionary<T, SimpleArrayList<Edge<T>>>();
    }

    @Override
    public SimpleList<T> vertices() {
        return adjacencyList.keys();
    }

    @Override
    public boolean addVertex(T vertex) {
        if(containsVertex(vertex)){
            return false;
        }
        adjacencyList.put(vertex, new SimpleArrayList<Edge<T>>());
        return true;
    }

    @Override
    public boolean removeVertex(T vertex) {
        if(!containsVertex(vertex)) {
            return false;
        }

        // Cacheamos la lista de vertices
        SimpleList<T> vertices = vertices();

        // Removemos todos los edges que apunten a vertex
        // De esta manera evitamos referencias obsoletas
        for(int i = 0; i < vertices.size(); i++){
            removeEdge(vertices.get(i), vertex);
        }

        // Finalmente removemos el vertice
        adjacencyList.remove(vertex);
        return true;
    }

    @Override
    public boolean addEdge(T from, T to, int weight) {
        addVertex(from);
        addVertex(to);

        // Buscamos el edge entre from y to, si no existe guarda null
        Edge<T> edge = getEdge(from, to);

        if(edge == null){
            // Si no existe, lo creamos y lo agregamos a la lista de vecinos
            Edge<T> newEdge = new Edge<T>(to, weight);
            adjacencyList.get(from).add(newEdge);
            return true;
        } else if(edge.getWeight() != weight){
            // Si existe pero el peso es diferente, lo actualizamos
            edge.setWeight(weight);
            return true;
        }

        return false;
    }

    @Override
    public boolean removeEdge(T from, T to) {

        Edge<T> edge = getEdge(from, to);
        if(edge == null){
            return false;
        }

        adjacencyList.get(from).remove(to);
        return true;
    }

    @Override
    public SimpleList<Edge<T>> getNeighbors(T vertex) {
        return adjacencyList.get(vertex);
    }

    @Override
    public boolean containsVertex(T vertex) {
        return adjacencyList.containsKey(vertex);
    }

    @Override
    public boolean containsEdge(T from, T to) {
        return getEdge(from, to) != null;
    }

    @Override
    public int getWeight(T from, T to) {
        Edge<T> edge = getEdge(from, to);
        if(edge != null){
            return edge.getWeight();

        }
        return -1;
    }

    private Edge<T> getEdge(T from, T to) {

        if (!containsVertex(from)) {
            return null;
        }

        // Guardamos la lista de todos los edges de ese nodo
        SimpleList<Edge<T>> neighbors = getNeighbors(from);

        // Recorremos la lista buscando un edge que lleve a to
        for(int i = 0; i < neighbors.size(); i++){
            if(neighbors.get(i).getDestination().equals(to)){
                return neighbors.get(i);
            }
        }
        return null;

    }
}
