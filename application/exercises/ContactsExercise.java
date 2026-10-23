package exercises;

import model.Contact;
import structures.BST;
import structures.SimpleList;
import structures.SimpleTree;

import java.util.Scanner;

public class ContactsExercise extends Exercise {
    private final SimpleTree<Contact> contacts = new BST<>();

    public ContactsExercise(Scanner scanner) {
        super(scanner);
    }

    @Override
    protected void exerciseLogic() {
        System.out.println("\n=== Agenda de contactos ===");
        System.out.println("1. Crear contacto");
        System.out.println("2. Editar contacto");
        System.out.println("3. Borrar contacto");
        System.out.println("4. Ver todos los contactos");
        System.out.println("5. Cargar datos de prueba");
        System.out.println("6. Volver al menú principal");

        String option = readNonEmptyInput("Seleccione una opción: ");

        switch (option) {
            case "1":
                createContact();
                break;
            case "2":
                editContact();
                break;
            case "3":
                deleteContact();
                break;
            case "4":
                listContacts();
                break;
            case "5":
                loadTestData();
                break;
            case "6":
                running = false;
                break;
            default:
                System.out.println("Opción inválida.");
                break;
        }
    }

    private void createContact() {
        String name = readNonEmptyInput("Nombre: ");

        if (contacts.contains(new Contact(name))) {
            System.out.println("Ya existe un contacto con ese nombre.");
            return;
        }

        String phone = readPhone("Número: ");
        String email = readEmail("Mail: ");

        contacts.insert(new Contact(name, phone, email));
        System.out.println("Contacto creado correctamente.");
    }

    private void editContact() {
        Contact contact = findContact();
        if (contact == null) return;

        System.out.println("\nEditando: " + contact);
        System.out.println("1. Nombre");
        System.out.println("2. Número");
        System.out.println("3. Mail");
        System.out.println("4. Cancelar");

        String option = readNonEmptyInput("¿Qué dato querés editar? ");

        switch (option) {
            case "1":
                renameContact(contact);
                break;
            case "2":
                contact.setPhone(readPhone("Nuevo número: "));
                System.out.println("Número actualizado.");
                break;
            case "3":
                contact.setEmail(readEmail("Nuevo mail: "));
                System.out.println("Mail actualizado.");
                break;
            case "4":
                System.out.println("Edición cancelada.");
                break;
            default:
                System.out.println("Opción inválida.");
                break;
        }
    }

    private void renameContact(Contact contact) {
        String newName = readNonEmptyInput("Nuevo nombre: ");

        boolean sameContact = newName.equalsIgnoreCase(contact.getName());
        if (!sameContact && contacts.contains(new Contact(newName))) {
            System.out.println("Ya existe un contacto con ese nombre.");
            return;
        }

        contacts.remove(contact);
        contacts.insert(new Contact(newName, contact.getPhone(), contact.getEmail()));
        System.out.println("Nombre actualizado.");
    }

    private void deleteContact() {
        Contact contact = findContact();
        if (contact == null) return;

        String answer = readNonEmptyInput("¿Seguro que querés borrar a " + contact.getName() + "? (s/n): ");

        if (answer.equalsIgnoreCase("s")) {
            contacts.remove(contact);
            System.out.println("Contacto borrado.");
        } else {
            System.out.println("No se borró el contacto.");
        }
    }

    private void listContacts() {
        if (contacts.isEmpty()) {
            System.out.println("No hay contactos cargados.");
            return;
        }

        SimpleList<Contact> ordered = contacts.inOrder();

        System.out.println("\nContactos (" + ordered.size() + "):");
        for (int i = 0; i < ordered.size(); i++) {
            System.out.println("- " + ordered.get(i));
        }
    }

    private void loadTestData() {
        Contact[] testData = {
            new Contact("Juan Perez", "1134567890", "juan.perez@gmail.com"),
            new Contact("Ana Gomez", "1145678901", "ana.gomez@hotmail.com"),
            new Contact("Carlos Lopez", "1156789012", "carlos.lopez@yahoo.com"),
            new Contact("Maria Fernandez", "1167890123", "maria.fernandez@gmail.com"),
            new Contact("Lucia Martinez", "1178901234", "lucia.martinez@uade.edu.ar"),
            new Contact("Pedro Sanchez", "1189012345", "pedro.sanchez@gmail.com"),
            new Contact("Sofia Romero", "1190123456", "sofia.romero@outlook.com"),
            new Contact("Diego Torres", "1123456789", "diego.torres@gmail.com"),
            new Contact("Valentina Diaz", "1132165498", "valen.diaz@hotmail.com"),
            new Contact("Martin Alvarez", "1165498732", "martin.alvarez@gmail.com")
        };

        int added = 0;
        for (Contact contact : testData) {
            if (!contacts.contains(contact)) {
                contacts.insert(contact);
                added++;
            }
        }

        System.out.println("Se cargaron " + added + " contactos de prueba.");
    }

    private Contact findContact() {
        if (contacts.isEmpty()) {
            System.out.println("No hay contactos cargados.");
            return null;
        }

        String name = readNonEmptyInput("Nombre del contacto: ");
        Contact key = new Contact(name);

        if (!contacts.contains(key)) {
            System.out.println("No existe un contacto con ese nombre.");
            return null;
        }
        return contacts.get(key);
    }

    private String readPhone(String prompt) {
        while (true) {
            String phone = readNonEmptyInput(prompt);
            int digits = phone.replaceAll("[^0-9]", "").length();

            if (phone.matches("\\+?[0-9 -]+") && digits >= 6 && digits <= 15) {
                return phone;
            }
            System.out.println("Número inválido. Usá solo números (entre 6 y 15 dígitos).");
        }
    }

    private String readEmail(String prompt) {
        while (true) {
            String email = readNonEmptyInput(prompt);

            if (email.matches("[^@\\s]+@[^@\\s]+\\.[^@\\s]+")) {
                return email;
            }
            System.out.println("Mail inválido. Ejemplo: nombre@gmail.com");
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
