import java.util.Scanner;

public class MainProgram {
    private boolean running = true; // Variable que mantiene el programa en ejecución[cite: 2]
    private Exercise exercise; // Variable polimórfica para instanciar el ejercicio elegido[cite: 2]

    public static void main(String[] args) {
        new MainProgram().run(); //[cite: 2]
    }

    private void run() {
        Scanner scanner = new Scanner(System.in); //[cite: 2]
        while (running) {
            selectExercise(scanner); //[cite: 2]
            if (exercise != null) {
                exercise.run(); // Ejecuta la lógica interna del ejercicio seleccionado[cite: 2]
            }
        }
        scanner.close(); //[cite: 2]
    }

    private void selectExercise(Scanner scanner) {
        System.out.println("Selecciona un ejercicio:"
                + "\n0: TestExercise"
                + "\n1: ListExercise"
                + "\n2: Playlist musical"
                + "\n3: Navegador Web" // Nueva opción agregada
                + "\n4: Salir"); // Opción desplazada al número 4

        String userInput = scanner.nextLine(); //[cite: 2]

        // Cadena if-else if estructurada para evitar el uso de break
        if (userInput.equals("0")) {
            exercise = new TestExercise(scanner); //[cite: 2]
        } else if (userInput.equals("1")) {
            exercise = new ListExercise(scanner); //[cite: 2]
        } else if (userInput.equals("2")) {
            exercise = new PlaylistExercise(scanner); //[cite: 2]
        } else if (userInput.equals("3")) {
            exercise = new NavegadorWebExercise(scanner); // Instancia de tu TP 04
        } else if (userInput.equals("4")) {
            running = false; // Corta el bucle while(running) de MainProgram[cite: 2]
        } else {
            System.out.println("\nRespuesta invalida");
            exercise = null; // Evita que se intente ejecutar un ejercicio inexistente[cite: 2]
        }
    }
}