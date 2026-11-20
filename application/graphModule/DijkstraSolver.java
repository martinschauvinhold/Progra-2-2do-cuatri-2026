package graphModule;

import structures.SimpleArrayDictionary;
import structures.SimpleArrayList;
import structures.SimpleArrayPriorityQueue;
import structures.SimpleArraySet;
import structures.SimpleDictionary;
import structures.SimpleList;
import structures.SimplePriorityQueue;
import structures.SimpleSet;

public class DijkstraSolver<T> {

    public SimpleDictionary<T, Edge<T>> dijkstraAllNodes(T origin, Graph<T> graph) {
        // Inicializamos las colecciones
        SimpleDictionary<T, Edge<T>> table = new SimpleArrayDictionary<>();
        SimplePriorityQueue<T> unvisited = new SimpleArrayPriorityQueue<>();
        SimpleSet<T> visited = new SimpleArraySet<>();

        // Asignamos valores iniciales
        // Cargamos todos los nodos del grafo
        SimpleList<T> vertices = graph.vertices();
        for (int i = 0; i < vertices.size(); i++) {
            table.put(vertices.get(i), new Edge<T>(null, Integer.MAX_VALUE));
        }

        // El nodo inicial no tiene costo
        table.get(origin).weight = 0;

        // Lo encolamos con su costo (0)
        unvisited.enqueue(origin, table.get(origin).weight);

        while (!unvisited.isEmpty()) {
            // Sacamos el de menor costo acumulado
            T current = unvisited.dequeue();

            // Salteamos este nodo si ya lo habíamos visitado
            if (visited.contains(current)) continue;

            // Revisamos los vecinos
            SimpleList<Edge<T>> neighbors = graph.getNeighbors(current);

            for (int i = 0; i < neighbors.size(); i++) {
                // El costo total al vecino es:
                // Costo hasta current + costo del current al vecino
                int totalWeight = table.get(current).weight + neighbors.get(i).weight;

                // Encolamos al vecino para visitar, con el costo que calculamos
                unvisited.enqueue(neighbors.get(i).destination, totalWeight);

                // Si el nuevo costo calculado es menor al que ya estaba en la tabla...
                if (totalWeight < table.get(neighbors.get(i).destination).weight) {
                    // Actualizamos la tabla: el anterior del vecino es el current,
                    // y el costo el que calculamos
                    table.put(neighbors.get(i).destination, new Edge<T>(current, totalWeight));
                }
            }

            // Cuando pasamos por todos los vecinos, marcamos al current como visitado
            visited.add(current);
        }

        return table;
    }

    public SimpleList<T> shortestPath(T origin, T destination, Graph<T> graph) {
        SimpleDictionary<T, Edge<T>> table = dijkstraAllNodes(origin, graph);

        if (!table.containsKey(destination) || table.get(destination).weight == Integer.MAX_VALUE) {
            return new SimpleArrayList<>();
        }

        SimpleList<T> path = new SimpleArrayList<>();
        T current = destination;

        while (current != null) {
            path.add(0, current);
            current = table.get(current).destination;
        }

        return path;
    }
}
