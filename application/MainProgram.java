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
                + "\n0: exercises.TestExercise"
                + "\n1: exercises.ListExercise"
                + "\n2: model.Playlist musical"
                + "\n3: Navegador Web (TP 04)"
                + "\n4: Impresora (TP 05)"
                + "\n5: Salir");

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
            running = false;
            exercise = null;
        } else {
            System.out.println("\nRespuesta invalida");
            exercise = null;
        }
    }
}