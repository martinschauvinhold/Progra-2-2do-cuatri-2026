import java.util.Scanner;

public class MainProgram {
    private boolean running = true; //[cite: 2]
    private Exercise exercise; //[cite: 2]

    public static void main(String[] args) {
        new MainProgram().run(); //[cite: 2]
    }

    private void run() {
        Scanner scanner = new Scanner(System.in); //[cite: 2]
        
        while (running) {
            selectExercise(scanner); //[cite: 2]
            if (exercise != null) {
                exercise.run(); //[cite: 2]
            }
        }
        
        scanner.close(); //[cite: 2]
    }

    private void selectExercise(Scanner scanner) {
        System.out.println("\nSelecciona un ejercicio:"
                + "\n0: TestExercise"
                + "\n1: ListExercise"
                + "\n2: Playlist musical"
                + "\n3: Navegador Web (TP 04)"
                + "\n4: Impresora (TP 05)"
                + "\n5: Salir");

        String userInput = scanner.nextLine(); //[cite: 2]

        // Cadena estructurada if-else if para evitar el uso de break
        if (userInput.equals("0")) {
            exercise = new TestExercise(scanner); //[cite: 2]
        } else if (userInput.equals("1")) {
            exercise = new ListExercise(scanner); //[cite: 2]
        } else if (userInput.equals("2")) {
            exercise = new PlaylistExercise(scanner); //[cite: 2]
        } else if (userInput.equals("3")) {
            exercise = new NavegadorWebExercise(scanner);
        } else if (userInput.equals("4")) {
            exercise = new ImpresoraExercise(scanner);
        } else if (userInput.equals("5")) {
            running = false; //[cite: 2]
            exercise = null; // Evita que el último ejercicio se vuelva a ejecutar antes de salir
        } else {
            System.out.println("\nRespuesta invalida");
            exercise = null; //[cite: 2]
        }
    }
}