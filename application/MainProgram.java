import exercises.*;

import java.util.Scanner;

public class MainProgram {
    private boolean running = true;
    private Exercise exercise;

    public static void main(String[] args) {
        new MainProgram().run();
    }

    private void run() {
        Scanner scanner = new Scanner(System.in);
        
        while (running) {
            selectExercise(scanner);
            if (exercise != null) {
                exercise.run();
            }
        }
        
        scanner.close();
    }

    private void selectExercise(Scanner scanner) {
        System.out.println("\nSelecciona un ejercicio:"
                + "\n0: TestExercise"
                + "\n1: ListExercise"
                + "\n2: Playlist musical"
                + "\n3: Navegador Web (TP 04)"
                + "\n4: Impresora (TP 05)"
                + "\n5: Personajes de Videojuegos (TP 06)"
                + "\n6: Salir");

        String userInput = scanner.nextLine();

        // Cadena estructurada if-else if pura para evitar el uso de break
        if (userInput.equals("0")) {
            exercise = new TestExercise(scanner);
        } else if (userInput.equals("1")) {
            exercise = new ListExercise(scanner);
        } else if (userInput.equals("2")) {
            exercise = new PlaylistExercise(scanner);
        } else if (userInput.equals("3")) {
            exercise = new NavegadorWebExercise(scanner);
        } else if (userInput.equals("4")) {
            exercise = new ImpresoraExercise(scanner);
        } else if (userInput.equals("5")) {
            exercise = new PersonajesExercise(scanner); // Tu nuevo TP 06
        } else if (userInput.equals("6")) {
            running = false; // Termina el bucle principal de forma limpia
            exercise = null; // Evita que se vuelva a ejecutar el último ejercicio en memoria
        } else {
            System.out.println("\nRespuesta invalida");
            exercise = null;
        }
    }
}