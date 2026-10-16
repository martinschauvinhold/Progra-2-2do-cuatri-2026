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
                + "\n6: Atención al cliente"
                + "\n7: Salir");

        String userInput = scanner.nextLine();

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
            exercise = new PersonajesExercise(scanner);
        } else if (userInput.equals("6")) {
            exercise = new CustomerSupportExercise(scanner);
        } else if (userInput.equals("7")) {
            running = false;
            exercise = null;
        } else {
            System.out.println("\nRespuesta invalida");
            exercise = null;
        }
    }
}