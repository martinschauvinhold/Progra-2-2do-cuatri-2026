package exercises;

import exceptions.InvalidPlaylistOperationException;
import model.Playlist;
import model.Song;

import java.util.Scanner;

public class PlaylistExercise extends Exercise {
    private final Playlist playlist = new Playlist();
    private boolean firstTime = true;

    public PlaylistExercise(Scanner scanner) {
        super(scanner);
    }

    @Override
    protected void exerciseLogic() {
        if (firstTime) {
            System.out.println("\nBienvenido a la model.Playlist Musical.");
            firstTime = false;
        }

        showStatus();
        showMenu();

        String option = readLine("Opción: ").trim().toLowerCase();

        try {
            switch (option) {
                case "1":
                    addSongFlow();
                    break;
                case "2":
                    removeSongFlow();
                    break;
                case "3":
                    playFlow();
                    break;
                case "4":
                    pauseFlow();
                    break;
                case "5":
                    previousFlow();
                    break;
                case "6":
                    nextFlow();
                    break;
                case "7":
                    setModeFlow();
                    break;
                case "8":
                    toggleLoopFlow();
                    break;
                case "9":
                    running = false;
                    System.out.println("Volviendo al menú principal...");
                    break;
                default:
                    System.out.println("Opción inválida. Intente nuevamente.");
                    break;
            }
        } catch (InvalidPlaylistOperationException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void showStatus() {
        System.out.println("\n=== model.Playlist ===");
        System.out.println(playlist.printPlaylist());

        if (playlist.isEmpty()) {
            System.out.println("Canción actual: Ninguna");
        } else {
            System.out.println("Canción actual: " + playlist.getCurrentSongName());
        }

        System.out.println("Modo: " + playlist.getPlaybackMode());
        System.out.println("Loop al final: " + (playlist.isLoopAfterLast() ? "Activado" : "Desactivado"));
        System.out.println("Estado: " + (playlist.isPlaying() ? "Reproduciendo" : "Detenida"));
    }

    private void showMenu() {
        System.out.println("\nSeleccione una opción:");
        System.out.println("1. Agregar canción");
        System.out.println("2. Remover canción");
        System.out.println("3. Reproducir");
        System.out.println("4. Detener");
        System.out.println("5. Anterior");
        System.out.println("6. Siguiente");
        System.out.println("7. Cambiar modo (orden / aleatorio)");
        System.out.println("8. Alternar loop al final");
        System.out.println("9. Volver al menú principal");
    }

    private void addSongFlow() {
        String title = readNonEmptyText("Ingrese el nombre de la canción:");
        playlist.addSong(title);
        System.out.println("Canción agregada correctamente.");
    }

    private void removeSongFlow() {
        if (playlist.isEmpty()) {
            System.out.println("La playlist está vacía.");
            return;
        }

        System.out.println("1. Remover por nombre");
        System.out.println("2. Remover por número");
        String choice = readLine("Opción: ").trim();

        try {
            if ("1".equals(choice)) {
                String title = readNonEmptyText("Ingrese el nombre de la canción a remover:");
                playlist.removeSongByName(title);
                System.out.println("Canción removida.");
            } else if ("2".equals(choice)) {
                int index = readPositiveInt("Ingrese el número de la canción:");
                playlist.removeSongByIndex(index - 1);
                System.out.println("Canción removida.");
            } else {
                System.out.println("Opción inválida.");
            }
        } catch (InvalidPlaylistOperationException e) {
            throw e;
        }
    }

    private void playFlow() {
        if (playlist.isEmpty()) {
            System.out.println("No hay canciones en la playlist.");
            return;
        }

        System.out.println("Reproduciendo: " + playlist.play().getTitle());
    }

    private void pauseFlow() {
        playlist.pause();
        System.out.println("Reproducción detenida.");
    }

    private void previousFlow() {
        if (playlist.isEmpty()) {
            System.out.println("No hay canciones para retroceder.");
            return;
        }

        System.out.println("Anterior: " + playlist.previous().getTitle());
    }

    private void nextFlow() {
        if (playlist.isEmpty()) {
            System.out.println("No hay canciones para avanzar.");
            return;
        }

        if (playlist.isPlaying() || playlist.size() > 0) {
            Song nextSong = playlist.next();
            if (nextSong == null) {
                System.out.println("No hay más canciones y la reproducción se detuvo.");
            } else {
                System.out.println("Siguiente: " + nextSong.getTitle());
            }
        }
    }

    private void setModeFlow() {
        System.out.println("1. Orden");
        System.out.println("2. Aleatorio");
        String choice = readLine("Modo: ").trim();

        if ("1".equals(choice)) {
            playlist.setPlaybackMode(Playlist.PlaybackMode.ORDER);
            System.out.println("Modo cambiado a orden.");
        } else if ("2".equals(choice)) {
            playlist.setPlaybackMode(Playlist.PlaybackMode.RANDOM);
            System.out.println("Modo cambiado a aleatorio.");
        } else {
            System.out.println("Opción inválida.");
        }
    }

    private void toggleLoopFlow() {
        playlist.setLoopAfterLast(!playlist.isLoopAfterLast());
        System.out.println("Loop al final: " + (playlist.isLoopAfterLast() ? "Activado" : "Desactivado"));
    }

    private String readNonEmptyText(String prompt) {
        String value;
        do {
            value = readLine(prompt).trim();
            if (value.isEmpty()) {
                System.out.println("No puedes dejar este campo vacío.");
            }
        } while (value.isEmpty());

        return value;
    }

    private int readPositiveInt(String prompt) {
        while (true) {
            String input = readLine(prompt).trim();
            try {
                int value = Integer.parseInt(input);
                if (value <= 0) {
                    throw new NumberFormatException();
                }
                return value;
            } catch (NumberFormatException e) {
                System.out.println("Debes ingresar un número válido mayor a 0.");
            }
        }
    }

    private String readLine(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }
}