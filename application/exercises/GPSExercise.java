package exercises;

import graphModule.DijkstraSolver;
import graphModule.Edge;
import graphModule.Graph;
import graphModule.ListGraph;
import structures.SimpleList;

import java.util.Scanner;

public class GPSExercise extends Exercise {
    private final Graph<String> map;
    private final DijkstraSolver<String> dijkstra;

    public GPSExercise(Scanner scanner) {
        super(scanner);
        this.map = new ListGraph<>();
        this.dijkstra = new DijkstraSolver<>();
        loadDefaultMap();
        printMap();
    }

    // Grafo precargado al iniciar, como pide la consigna
    private void loadDefaultMap() {
        map.addVertex("Once");
        map.addVertex("Caballito");
        map.addVertex("Flores");
        map.addVertex("Palermo");
        map.addVertex("Belgrano");
        map.addVertex("Retiro");

        map.addEdge("Once", "Caballito", 8);
        map.addEdge("Once", "Palermo", 12);
        map.addEdge("Caballito", "Flores", 6);
        map.addEdge("Caballito", "Palermo", 5);
        map.addEdge("Flores", "Caballito", 6);
        map.addEdge("Flores", "Once", 10);
        map.addEdge("Palermo", "Belgrano", 7);
        map.addEdge("Palermo", "Retiro", 9);
        map.addEdge("Belgrano", "Retiro", 4);
        map.addEdge("Retiro", "Palermo", 9);
    }

    @Override
    protected void exerciseLogic() {
        System.out.println("\n=== GPS ===");
        System.out.println("1. Ver mapa");
        System.out.println("2. Calcular ruta más corta");
        System.out.println("3. Agregar ubicación");
        System.out.println("4. Agregar camino entre ubicaciones");
        System.out.println("5. Volver al menú principal");

        String option = readNonEmptyInput("Seleccione una opción: ").trim();

        switch (option) {
            case "1":
                printMap();
                break;
            case "2":
                calculateRoute();
                break;
            case "3":
                addLocation();
                break;
            case "4":
                addPath();
                break;
            case "5":
                running = false;
                break;
            default:
                System.out.println("Opción inválida.");
                break;
        }
    }

    private void calculateRoute() {
        String origin = readNonEmptyInput("Punto de origen: ");
        if (!map.containsVertex(origin)) {
            System.out.println("La ubicación \"" + origin + "\" no existe en el mapa.");
            return;
        }

        String destination = readNonEmptyInput("Punto de destino: ");
        if (!map.containsVertex(destination)) {
            System.out.println("La ubicación \"" + destination + "\" no existe en el mapa.");
            return;
        }

        // Dijkstra ya devuelve el camino reconstruido, no hay que recalcular nada acá
        SimpleList<String> path = dijkstra.shortestPath(origin, destination, map);

        if (path.isEmpty()) {
            System.out.println("No hay un camino posible entre esas ubicaciones.");
            return;
        }

        // Recorremos el camino para sumar el costo total y armar el texto "A → B → C"
        int totalCost = 0;
        StringBuilder route = new StringBuilder(path.get(0));
        for (int i = 0; i < path.size() - 1; i++) {
            totalCost += map.getWeight(path.get(i), path.get(i + 1));
            route.append(" -> ").append(path.get(i + 1));
        }

        System.out.println("\nRuta más corta: " + route);
        System.out.println("Costo total: " + totalCost);
    }

    private void addLocation() {
        String name = readNonEmptyInput("Nombre de la nueva ubicación: ");

        if (map.containsVertex(name)) {
            System.out.println("Esa ubicación ya existe.");
            return;
        }

        map.addVertex(name);
        System.out.println("Ubicación agregada.");
        printMap();
    }

    private void addPath() {
        String from = readNonEmptyInput("Ubicación de origen: ");
        if (!map.containsVertex(from)) {
            System.out.println("La ubicación \"" + from + "\" no existe.");
            return;
        }

        String to = readNonEmptyInput("Ubicación de destino: ");
        if (!map.containsVertex(to)) {
            System.out.println("La ubicación \"" + to + "\" no existe.");
            return;
        }

        int weight = readPositiveInt("Costo del camino (minutos): ");
        if (weight < 0) return; // input inválido, ya se avisó en readPositiveInt

        map.addEdge(from, to, weight);
        System.out.println("Camino agregado.");
        printMap();
    }

    // Imprime arista por arista, formato "A → B: peso", como pide la consigna
    private void printMap() {
        System.out.println("\nMapa actual:");
        SimpleList<String> vertices = map.vertices();

        for (int i = 0; i < vertices.size(); i++) {
            String vertex = vertices.get(i);
            SimpleList<Edge<String>> neighbors = map.getNeighbors(vertex);

            for (int j = 0; j < neighbors.size(); j++) {
                Edge<String> edge = neighbors.get(j);
                System.out.println(vertex + " -> " + edge.destination + ": " + edge.weight);
            }
        }
    }

    private String readNonEmptyInput(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine();

            if (input == null || input.trim().isEmpty()) {
                System.out.println("No puede quedar vacío.");
                continue;
            }

            return input.trim();
        }
    }

    private int readPositiveInt(String prompt) {
        System.out.print(prompt);
        String input = scanner.nextLine().trim();

        try {
            int value = Integer.parseInt(input);
            if (value < 0) {
                System.out.println("El costo no puede ser negativo.");
                return -1;
            }
            return value;
        } catch (NumberFormatException e) {
            System.out.println("Eso no es un número válido.");
            return -1;
        }
    }
}
