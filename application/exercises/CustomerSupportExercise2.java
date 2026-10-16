package exercises;

import model.Claim;
import model.UrgencyLevel;
import structures.SimpleArrayPriorityQueue;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CustomerSupportExercise2 extends Exercise {
    private final SimpleArrayPriorityQueue<Claim> queue = new SimpleArrayPriorityQueue<>();
    private boolean firstTime = true;

    public CustomerSupportExercise2(Scanner scanner) {
        super(scanner);
        precargarDatos();
    }

    private void precargarDatos() {
        queue.enqueue(new Claim("Pago duplicado", "Se cobraron dos veces por el mismo servicio.", UrgencyLevel.CRITICO),
                UrgencyLevel.CRITICO.getPriority());

        queue.enqueue(new Claim("Producto defectuoso", "La caja llegó rota y sin sellos.", UrgencyLevel.ALTO),
                UrgencyLevel.ALTO.getPriority());

        queue.enqueue(new Claim("Entrega tardía", "El pedido llegó con 5 días de retraso.", UrgencyLevel.MEDIO),
                UrgencyLevel.MEDIO.getPriority());

        queue.enqueue(new Claim("Consulta sobre factura", "Necesito aclarar un cargo del mes anterior.", UrgencyLevel.BAJO),
                UrgencyLevel.BAJO.getPriority());

        queue.enqueue(new Claim("Error en la app", "No me deja iniciar sesión desde Android.", UrgencyLevel.ALTO),
                UrgencyLevel.ALTO.getPriority());

        System.out.println("Se precargaron reclamos de prueba.");
    }

    @Override
    protected void exerciseLogic() {
        if (firstTime) {
            System.out.println("\n=== Sistema de Atención al Cliente ===");
            System.out.println("El sistema ordena los reclamos por urgencia.");
            firstTime = false;
        }

        System.out.println("\n1. Redactar reclamo");
        System.out.println("2. Ver cola de reclamos");
        System.out.println("3. Volver al menú principal");
        String option = readLine("Opción: ").trim();

        switch (option) {
            case "1":
                createClaim();
                break;
            case "2":
                viewReports();
                break;
            case "3":
                running = false;
                System.out.println("Volviendo al menú principal...");
                break;
            default:
                System.out.println("Opción inválida.");
                break;
        }
    }

    private void createClaim() {
        String title = readNonEmptyText("Título del reclamo: ");
        String description = readNonEmptyText("Descripción: ");

        UrgencyLevel urgency = readUrgency();

        Claim claim = new Claim(title, description, urgency);
        queue.enqueue(claim, urgency.getPriority());

        System.out.println("Reclamo registrado con éxito.");
    }

    private void viewReports() {
        if (queue.isEmpty()) {
            System.out.println("No hay reclamos en la cola.");
            return;
        }

        List<Claim> orderedClaims = new ArrayList<>();
        SimpleArrayPriorityQueue<Claim> tempQueue = new SimpleArrayPriorityQueue<>();

        while (!queue.isEmpty()) {
            Claim claim = queue.dequeue();
            orderedClaims.add(claim);
            tempQueue.enqueue(claim, claim.getUrgency().getPriority());
        }

        while (!tempQueue.isEmpty()) {
            Claim claim = tempQueue.dequeue();
            queue.enqueue(claim, claim.getUrgency().getPriority());
        }

        System.out.println("\n=== Cola de reclamos ordenada por urgencia ===");
        for (int i = 0; i < orderedClaims.size(); i++) {
            Claim claim = orderedClaims.get(i);
            System.out.println((i + 1) + ". " + claim);
        }

        System.out.println("\nEl reclamo prioritario es: " + orderedClaims.get(0));
        System.out.println("1. Marcar como resuelto");
        System.out.println("2. Dejarlo en la cola");
        System.out.println("3. Volver");

        String option = readLine("Opción: ").trim();

        if ("1".equals(option)) {
            Claim resolved = queue.dequeue();
            System.out.println("Reclamo resuelto: " + resolved.getTitle());
        } else if ("2".equals(option)) {
            System.out.println("Se deja el reclamo en la cola.");
        } else if ("3".equals(option)) {
            return;
        } else {
            System.out.println("Opción inválida. El reclamo queda en la cola.");
        }
    }

    private UrgencyLevel readUrgency() {
        while (true) {
            System.out.println("Urgencias disponibles:");
            System.out.println("1. Crítico");
            System.out.println("2. Alto");
            System.out.println("3. Medio");
            System.out.println("4. Bajo");

            String input = readLine("Seleccione urgencia: ").trim();

            try {
                return UrgencyLevel.fromInput(input);
            } catch (IllegalArgumentException e) {
                System.out.println("Ingresó una urgencia inválida. Intente nuevamente.");
            }
        }
    }

    private String readNonEmptyText(String prompt) {
        while (true) {
            String value = readLine(prompt).trim();
            if (!value.isEmpty()) {
                return value;
            }
            System.out.println("El campo no puede quedar vacío.");
        }
    }

    private String readLine(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }
}