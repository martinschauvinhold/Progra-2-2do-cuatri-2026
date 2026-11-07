package structures;

import model.Edge;

public class DijkstraSolver<T> {

    public SimpleDictionary<T, Edge<T>> dijkstraAllNodes(T origin, Graph<T> graph) {
        SimpleDictionary<T, Edge<T>> table = new SimpleArrayDictionary<T, Edge<T>>();
        SimpleSet<T> visited = new SimpleArraySet<T>();
        SimplePriorityQueue<T> unvisited = new SimpleArrayPriorityQueue<T>();

        // Asignamos valores iniciales, cargamos todos los nodos del grafo
        SimpleList<T> vertices = graph.vertices();

        // Los pasamos a la tabla, con prev = null y costo infinito
        for(int i = 0; i < vertices.size(); i++){
            table.put(vertices.get(i), new Edge<T>(null, Integer.MAX_VALUE));
        }

        // El nodo inicial no tiene costo
        table.get(origin).setWeight(0);
        //Lo marcamos como no visitado, con su costo 0
        unvisited.enqueue(origin, table.get(origin).getWeight());

        while(!unvisited.isEmpty()){
            // Sacamos el nodo con menor costo
            T current = unvisited.dequeue();

            if(visited.contains(current)){
                continue;
            }

            // Obtenemos sus vecinos
            SimpleList<Edge<T>> neighbors = graph.getNeighbors(current);

            for(int i = 0; i < neighbors.size(); i++){

                // El costo total al vecino es:
                // costo hasta current + costo del current al vecino
                int totalWeight = table.get(current).getWeight() + neighbors.get(i).getWeight();
                unvisited.enqueue(neighbors.get(i).getDestination(), totalWeight);

                if(totalWeight < table.get(neighbors.get(i).getDestination()).getWeight()){

                    // Actualizamos la tabla
                    // el anterior del vecino es el current, y el costo el que calculamos
                    table.put(neighbors.get(i).getDestination(), new Edge<>(current, totalWeight));

                }

                visited.add(current);

            }
        }

        return table;
    }

}
