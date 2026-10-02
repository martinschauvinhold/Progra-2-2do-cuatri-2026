package exercises;
import model.Personaje;
import structures.SimpleSet;
import structures.SimpleArraySet;

import java.util.Scanner;

public class PersonajesExercise extends Exercise {
    // Conjunto principal que almacena todos los personajes
    private SimpleSet<Personaje> personajes = new SimpleArraySet<>();
    private boolean firstTime = true;

    public PersonajesExercise(Scanner scanner) {
        super(scanner);
        precargarDatos(); // Base de datos pre-programada para facilitar el testeo
    }

    private void precargarDatos() {
        Personaje mario = new Personaje("Mario");
        mario.getHabilidades().add("Saltar");
        mario.getHabilidades().add("Correr");
        mario.getHabilidades().add("Fuego");

        Personaje link = new Personaje("Link");
        link.getHabilidades().add("Espada");
        link.getHabilidades().add("Escudo");
        link.getHabilidades().add("Correr");

        Personaje kratos = new Personaje("Kratos");
        kratos.getHabilidades().add("Espada");
        kratos.getHabilidades().add("Fuerza");
        kratos.getHabilidades().add("Ira");

        personajes.add(mario);
        personajes.add(link);
        personajes.add(kratos);
    }

    @Override
    protected void exerciseLogic() {
        if (firstTime) {
            System.out.println("\n=== Análisis de Personajes de Videojuegos ===");
            firstTime = false;
        }

        System.out.println("\n--- MENÚ PRINCIPAL ---");
        System.out.println("1. Crear personaje nuevo");
        System.out.println("2. Asignar habilidad a un personaje");
        System.out.println("3. Quitar habilidad a un personaje");
        System.out.println("4. Comparar dos personajes");
        System.out.println("5. Ver todos los personajes");
        System.out.println("6. Salir");
        System.out.print("Opción: ");

        String input = scanner.nextLine().trim();

        // Control estructurado puro sin break
        if (input.equals("1")) {
            crearPersonaje();
        } else if (input.equals("2")) {
            asignarHabilidad();
        } else if (input.equals("3")) {
            quitarHabilidad();
        } else if (input.equals("4")) {
            compararPersonajes();
        } else if (input.equals("5")) {
            mostrarPersonajes();
        } else if (input.equals("6")) {
            System.out.println("Cerrando la herramienta de análisis...");
            running = false;
        } else {
            System.out.println("\n[!] Comando inválido. Ingrese una opción del 1 al 6."); // Prevención de crashes
        }
    }

    private void crearPersonaje() {
        System.out.print("Ingrese el nombre del nuevo personaje: ");
        String nombre = scanner.nextLine().trim();

        if (!nombre.isEmpty()) {
            Personaje nuevo = new Personaje(nombre);
            boolean agregado = personajes.add(nuevo); // El Set evita duplicados automáticamente

            if (agregado) {
                System.out.println("\n[+] Personaje '" + nombre + "' creado con éxito.");
            } else {
                System.out.println("\n[!] Error: El personaje ya existe en la base de datos.");
            }
        } else {
            System.out.println("\n[!] Error: El nombre no puede estar vacío.");
        }
    }

    private void asignarHabilidad() {
        System.out.print("Ingrese el nombre del personaje: ");
        String nombre = scanner.nextLine().trim();
        Personaje p = buscarPersonaje(nombre);

        if (p != null) {
            System.out.print("Ingrese la habilidad a enseñar: ");
            String habilidad = scanner.nextLine().trim();
            
            if (!habilidad.isEmpty()) {
                boolean asignada = p.getHabilidades().add(habilidad); // Se deben poder asignar habilidades
                if (asignada) {
                    System.out.println("\n[+] Habilidad '" + habilidad + "' asignada a " + p.getNombre() + ".");
                } else {
                    System.out.println("\n[!] " + p.getNombre() + " ya poseía esa habilidad.");
                }
            } else {
                System.out.println("\n[!] Error: La habilidad no puede estar vacía.");
            }
        } else {
            System.out.println("\n[!] Error: Personaje no encontrado.");
        }
    }

    private void quitarHabilidad() {
        System.out.print("Ingrese el nombre del personaje: ");
        String nombre = scanner.nextLine().trim();
        Personaje p = buscarPersonaje(nombre);

        if (p != null) {
            System.out.print("Ingrese la habilidad a olvidar: ");
            String habilidad = scanner.nextLine().trim();
            
            // La aplicación debe manejar inputs para evitar que el TDA lance excepciones no capturadas
            if (p.getHabilidades().contains(habilidad)) {
                p.getHabilidades().remove(habilidad); // Se deben poder quitar habilidades[cite: 13]
                System.out.println("\n[-] Habilidad '" + habilidad + "' removida de " + p.getNombre() + ".");
            } else {
                System.out.println("\n[!] Error: " + p.getNombre() + " no posee esa habilidad.");
            }
        } else {
            System.out.println("\n[!] Error: Personaje no encontrado.");
        }
    }

    private void compararPersonajes() {
        System.out.print("Ingrese el nombre del primer personaje: ");
        Personaje p1 = buscarPersonaje(scanner.nextLine().trim());
        
        System.out.print("Ingrese el nombre del segundo personaje: ");
        Personaje p2 = buscarPersonaje(scanner.nextLine().trim());

        if (p1 != null && p2 != null) {
            System.out.println("\n=== COMPARACIÓN: " + p1.getNombre() + " vs " + p2.getNombre() + " ===");
            
            // Habilidades en común (Intersección)[cite: 13]
            SimpleSet<String> comunes = p1.getHabilidades().intersectWith(p2.getHabilidades());
            System.out.println("\n> Habilidades en común:");
            imprimirSet(comunes);

            // Habilidades únicas del P1 (Diferencia)[cite: 13]
            SimpleSet<String> unicasP1 = p1.getHabilidades().differenceWith(p2.getHabilidades());
            System.out.println("\n> Habilidades exclusivas de " + p1.getNombre() + ":");
            imprimirSet(unicasP1);

            // Habilidades únicas del P2 (Diferencia)[cite: 13]
            SimpleSet<String> unicasP2 = p2.getHabilidades().differenceWith(p1.getHabilidades());
            System.out.println("\n> Habilidades exclusivas de " + p2.getNombre() + ":");
            imprimirSet(unicasP2);

        } else {
            System.out.println("\n[!] Error: Uno o ambos personajes no fueron encontrados.");
        }
    }

    private void mostrarPersonajes() {
        System.out.println("\n--- LISTA DE PERSONAJES ---");
        Object[] arrayPersonajes = personajes.toArray(new Personaje[0]);
        
        int i = 0;
        while (i < personajes.size()) {
            Personaje p = (Personaje) arrayPersonajes[i];
            System.out.println("- " + p.getNombre() + " | Habilidades: " + p.getHabilidades().size());
            i++;
        }
    }

    // Método auxiliar de búsqueda con lógica estructurada pura (sin return prematuro ni break)
    private Personaje buscarPersonaje(String nombre) {
        Personaje encontrado = null;
        Object[] array = personajes.toArray(new Personaje[0]);
        int i = 0;
        
        while (i < personajes.size() && encontrado == null) {
            Personaje p = (Personaje) array[i];
            if (p.getNombre().equalsIgnoreCase(nombre)) {
                encontrado = p;
            }
            i++;
        }
        return encontrado;
    }

    // Método auxiliar para imprimir los contenidos de un Set
    private void imprimirSet(SimpleSet<String> set) {
        if (set.isEmpty()) {
            System.out.println("  (Ninguna)");
        } else {
            Object[] array = set.toArray(new String[0]);
            int i = 0;
            while (i < set.size()) {
                System.out.println("  - " + array[i]);
                i++;
            }
        }
    }
}