package exercises;

import structures.SimpleArrayStack;
import structures.SimpleStack;

import java.util.Scanner;

public class NavegadorWebExercise extends Exercise {
    private SimpleStack<String> backStack = new SimpleArrayStack<>(100);
    private SimpleStack<String> forwardStack = new SimpleArrayStack<>(100);
    
    private String paginaActual;
    private boolean firstTime = true;

    public NavegadorWebExercise(Scanner scanner) {
        super(scanner); 
        precargarDatos(); // Inicializa la base de datos de testeo al instanciar
    }

    // Método que simula una sesión de navegación previa
    private void precargarDatos() {
        // Historial hacia atrás (el tope es la última página visitada antes de la actual)
        backStack.push("www.google.com");
        backStack.push("www.wikipedia.org/wiki/Pila_(estructura_de_datos)");
        
        // Página actual simulada
        paginaActual = "www.java.com"; 
        
        // Historial hacia adelante (el tope es la página siguiente inmediata)
        forwardStack.push("www.stackoverflow.com");
        forwardStack.push("www.github.com"); 
    }

    @Override
    protected void exerciseLogic() { 
        if (firstTime) {
            System.out.println("\n--- Historial de Navegador Web ---");
            firstTime = false;
        }

        System.out.println("\n--- PÁGINA ACTUAL: " + paginaActual + " ---");
        System.out.println("1. Ir a una nueva página");
        System.out.println("2. Volver atrás");
        System.out.println("3. Volver adelante");
        System.out.println("4. Salir al menú principal");
        System.out.print("Opción: ");

        String input = scanner.nextLine().trim();

        if (input.equals("1")) {
            irNuevaPagina();
        } else if (input.equals("2")) {
            volverAtras();
        } else if (input.equals("3")) {
            volverAdelante();
        } else if (input.equals("4")) {
            System.out.println("Cerrando navegador y volviendo al menú principal...");
            running = false; 
        } else {
            System.out.println("Comando inválido. Ingresa un número del 1 al 4.");
        }
    }

    private void irNuevaPagina() {
        System.out.print("Nombre de la página: ");
        String nuevaPagina = scanner.nextLine();
        
        if (!nuevaPagina.trim().isEmpty()) {
            backStack.push(paginaActual);
            paginaActual = nuevaPagina;
            forwardStack.clear(); 
        } else {
            System.out.println("No se ingresó un nombre válido.");
        }
    }

    private void volverAtras() {
        if (!backStack.isEmpty()) {
            forwardStack.push(paginaActual);
            paginaActual = backStack.pop(); 
        } else {
            System.out.println("\n[!] No hay historial hacia atrás.");
        }
    }

    private void volverAdelante() {
        if (!forwardStack.isEmpty()) {
            backStack.push(paginaActual);
            paginaActual = forwardStack.pop(); 
        } else {
            System.out.println("\n[!] No hay historial hacia adelante.");
        }
    }
}