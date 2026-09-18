import java.util.Scanner;

public class ImpresoraExercise extends Exercise {
    // Utilizamos dos colas internas para priorizar el orden de impresión
    private SimpleQueue<Documento> colaBlancoNegro = new SimpleArrayQueue<>(50);
    private SimpleQueue<Documento> colaColor = new SimpleArrayQueue<>(50);

    private boolean firstTime = true;

    public ImpresoraExercise(Scanner scanner) {
        super(scanner);
        precargarDatos(); // Base de datos pre-programada para facilitar el testeo
    }

  private void precargarDatos() {
        // Documentos de texto o manuales (Blanco y Negro)
        colaBlancoNegro.enqueue(new Documento("Diagrama_Infraestructura_BiBank.pdf", 3, false));
        colaBlancoNegro.enqueue(new Documento("Manual_Usuario_Bajaj_Rouser_P150.pdf", 45, false));
        colaBlancoNegro.enqueue(new Documento("Guia_Estudio_AWS_Solutions_Architect.docx", 12, false));

        // Presentaciones o imágenes (Color)
        colaColor.enqueue(new Documento("Presentacion_Oral_Ingles_AI.pptx", 10, true));
        colaColor.enqueue(new Documento("Foto_Gata.png", 1, true));
    }

    @Override
    protected void exerciseLogic() {
        if (firstTime) {
            System.out.println("\n=== Sistema de Impresión ===");
            firstTime = false;
        }

        int totalDocs = colaBlancoNegro.size() + colaColor.size();
        System.out.println("\n--- ESTADO DE LA IMPRESORA ---");
        System.out.println("Documentos totales en cola: " + totalDocs);
        System.out.println("1. Crear documento y enviar a la cola");
        System.out.println("2. Imprimir documentos");
        System.out.println("3. Cancelar cola de impresión");
        System.out.println("4. Salir al menú principal");
        System.out.print("Opción: ");

        String input = scanner.nextLine().trim();

        // Flujo estructurado sin break
        if (input.equals("1")) {
            crearDocumento();
        } else if (input.equals("2")) {
            imprimirCola();
        } else if (input.equals("3")) {
            cancelarCola();
        } else if (input.equals("4")) {
            System.out.println("Cerrando sistema de impresión...");
            running = false;
        } else {
            System.out.println("\n[!] Comando inválido. Ingrese una opción del 1 al 4."); // Evitando crashes por input inválido
        }
    }

    private void crearDocumento() {
        System.out.print("Nombre del documento: ");
        String nombre = scanner.nextLine().trim();
        
        // Control estructurado sin "return" anticipados
        if (!nombre.isEmpty()) {
            System.out.print("Cantidad de páginas: ");
            String paginasStr = scanner.nextLine().trim();
            int paginas = 0;
            boolean inputValido = true;
            
            // La aplicación maneja inputs inválidos del usuario evitando crashes por letras
            try {
                paginas = Integer.parseInt(paginasStr);
                if (paginas <= 0) {
                    System.out.println("\n[!] Error: La cantidad de páginas debe ser mayor a 0.");
                    inputValido = false;
                }
            } catch (NumberFormatException e) {
                System.out.println("\n[!] Error: Ingresaste letras. Debes ingresar un número válido.");
                inputValido = false;
            }

            if (inputValido) {
                System.out.print("¿Es a color? (s/n): ");
                String colorStr = scanner.nextLine().trim().toLowerCase();
                
                if (colorStr.equals("s")) {
                    colaColor.enqueue(new Documento(nombre, paginas, true));
                    System.out.println("\n[+] Documento a color enviado a la cola.");
                } else if (colorStr.equals("n")) {
                    colaBlancoNegro.enqueue(new Documento(nombre, paginas, false));
                    System.out.println("\n[+] Documento B/N enviado a la cola.");
                } else {
                    System.out.println("\n[!] Error: Respuesta inválida. Debes ingresar 's' o 'n'.");
                }
            }
        } else {
            System.out.println("\n[!] Error: El nombre no puede estar vacío.");
        }
    }

    private void imprimirCola() {
        if (colaBlancoNegro.isEmpty() && colaColor.isEmpty()) {
            System.out.println("\n[i] La cola está vacía, no hay nada que imprimir.");
        } else {
            System.out.println("\n============= INICIANDO IMPRESIÓN =============");
            
            // Empezando por todos los documentos en blanco y negro
            while (!colaBlancoNegro.isEmpty()) {
                imprimirDocumento(colaBlancoNegro.dequeue());
            }
            
            // Y siguiendo con todos los de color
            while (!colaColor.isEmpty()) {
                imprimirDocumento(colaColor.dequeue());
            }
            
            System.out.println("============= IMPRESIÓN FINALIZADA =============");
        }
    }

    private void imprimirDocumento(Documento doc) {
        String tipo = "Color";
        if (!doc.isColor()) {
            tipo = "Blanco y Negro";
        }
        
        System.out.println("\nProcesando: " + doc.getNombre() + " (" + tipo + ")");
        
        int paginaActual = 1;
        // Se deben mostrar en orden las páginas de todos los documentos[cite: 5]
        while (paginaActual <= doc.getPaginas()) {
            System.out.println(" -> Imprimiendo página " + paginaActual + " de " + doc.getPaginas());
            paginaActual++;
        }
    }

    private void cancelarCola() {
        colaBlancoNegro.clear(); // Opcion para cancelar la cola entera[cite: 5]
        colaColor.clear();
        System.out.println("\n[!] Se ha cancelado la impresión y vaciado la cola.");
    }

    
}