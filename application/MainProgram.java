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
        System.out.println("Selecciona un ejercicio:"
                + "\n0: TestExercise"
                + "\n1: ListExercise"
                + "\n2: Playlist musical"
                + "\n3: Salir");

        String userInput = scanner.nextLine();

        switch (userInput) {
            case "0":
                exercise = new TestExercise(scanner);
                break;
            case "1":
                exercise = new ListExercise(scanner);
                break;
            case "2":
                exercise = new PlaylistExercise(scanner);
                break;
            case "3":
                running = false;
                break;
            default:
                System.out.println("\nRespuesta invalida");
                exercise = null;
                break;
        }
    }
}