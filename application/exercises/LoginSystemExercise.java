package exercises;

import model.User;
import structures.SimpleArrayDictionary;
import structures.SimpleDictionary;
import structures.SimpleList;

import java.util.Scanner;

public class LoginSystemExercise extends Exercise {
    private final SimpleDictionary<String, User> users;

    public LoginSystemExercise(Scanner scanner) {
        super(scanner);
        this.users = new SimpleArrayDictionary<>();
        loadDefaultUsers();
    }

    private void loadDefaultUsers() {
        users.put("admin", new User("admin", "1234"));
        users.put("juan", new User("juan", "abcd"));
        users.put("ana", new User("ana", "5678"));
    }

    @Override
    protected void exerciseLogic() {
        System.out.println("\n=== Sistema de login ===");
        System.out.println("1. Registrar usuario");
        System.out.println("2. Ingresar");
        System.out.println("3. Ver usuarios");
        System.out.println("4. Volver al menú principal");

        String option = readNonEmptyInput("Seleccione una opción: ").trim();

        switch (option) {
            case "1":
                registerUser();
                break;
            case "2":
                login();
                break;
            case "3":
                listUsers();
                break;
            case "4":
                running = false;
                break;
            default:
                System.out.println("Opción inválida.");
                break;
        }
    }

    private void registerUser() {
        String username = readNonEmptyInput("Nombre de usuario: ");

        if (users.containsKey(username)) {
            System.out.println("Ese usuario ya existe.");
            return;
        }

        String password = readNonEmptyInput("Contraseña: ");

        if (password.length() < 4) {
            System.out.println("La contraseña debe tener al menos 4 caracteres.");
            return;
        }

        users.put(username, new User(username, password));
        System.out.println("Usuario registrado correctamente.");
    }

    private void login() {
        String username = readNonEmptyInput("Usuario: ");

        User user = users.get(username);
        if (user == null) {
            System.out.println("El usuario no existe.");
            return;
        }

        if (user.isBlocked()) {
            System.out.println("La cuenta está bloqueada. Contactá al administrador.");
            return;
        }

        String password = readNonEmptyInput("Contraseña: ");

        if (user.getPassword().equals(password)) {
            user.resetFailedAttempts();
            user.setBlocked(false);
            System.out.println("¡Bienvenido/a, " + username + "!");
            return;
        }

        user.incrementFailedAttempts();

        if (user.getFailedAttempts() >= 3) {
            user.setBlocked(true);
            System.out.println("Contraseña incorrecta. La cuenta fue bloqueada.");
            return;
        }

        int remainingAttempts = 3 - user.getFailedAttempts();
        System.out.println("Contraseña incorrecta. Intentos restantes: " + remainingAttempts);
    }

    private void listUsers() {
        SimpleList<String> userNames = users.keys();

        if (userNames.isEmpty()) {
            System.out.println("No hay usuarios registrados.");
            return;
        }

        System.out.println("\nUsuarios registrados:");
        for (int i = 0; i < userNames.size(); i++) {
            String username = userNames.get(i);
            User user = users.get(username);
            String state = user.isBlocked() ? "BLOQUEADA" : "ACTIVA";
            System.out.println("- " + username + " [" + state + "]");
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
}