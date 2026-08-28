import java.util.Random;

public class Playlist {
    private final SimpleArrayList<Song> songs = new SimpleArrayList<>();
    private int currentIndex = -1;
    private boolean playing = false;
    private PlaybackMode playbackMode = PlaybackMode.ORDER;
    private boolean loopAfterLast = false;

    public enum PlaybackMode {
        ORDER,
        RANDOM
    }

    public void addSong(String title) {
        if (title == null || title.trim().isEmpty()) {
            throw new InvalidPlaylistOperationException("El nombre de la canción no puede estar vacío.");
        }

        songs.add(new Song(title));
        if (currentIndex == -1) {
            currentIndex = 0;
        }
    }

    public void removeSongByName(String title) {
        if (songs.isEmpty()) {
            throw new InvalidPlaylistOperationException("La playlist está vacía.");
        }

        for (int i = 0; i < songs.size(); i++) {
            if (songs.get(i).getTitle().equalsIgnoreCase(title.trim())) {
                songs.remove(i);

                if (songs.isEmpty()) {
                    currentIndex = -1;
                    playing = false;
                } else if (currentIndex >= songs.size()) {
                    currentIndex = songs.size() - 1;
                }

                return;
            }
        }

        throw new InvalidPlaylistOperationException("La canción '" + title + "' no existe en la playlist.");
    }

    public void removeSongByIndex(int index) {
        if (index < 0 || index >= songs.size()) {
            throw new InvalidPlaylistOperationException("Índice inválido.");
        }

        songs.remove(index);

        if (songs.isEmpty()) {
            currentIndex = -1;
            playing = false;
        } else if (currentIndex >= songs.size()) {
            currentIndex = songs.size() - 1;
        }
    }

    public Song play() {
        if (songs.isEmpty()) {
            throw new InvalidPlaylistOperationException("No hay canciones para reproducir.");
        }

        if (currentIndex == -1) {
            currentIndex = 0;
        }

        playing = true;
        return songs.get(currentIndex);
    }

    public void pause() {
        playing = false;
    }

    public Song previous() {
        if (songs.isEmpty()) {
            throw new InvalidPlaylistOperationException("La playlist está vacía.");
        }

        if (currentIndex == -1) {
            currentIndex = 0;
            playing = true;
            return songs.get(currentIndex);
        }

        if (currentIndex == 0) {
            currentIndex = songs.size() - 1;
        } else {
            currentIndex--;
        }

        playing = true;
        return songs.get(currentIndex);
    }

    public Song next() {
        if (songs.isEmpty()) {
            throw new InvalidPlaylistOperationException("La playlist está vacía.");
        }

        if (currentIndex == -1) {
            currentIndex = 0;
            playing = true;
            return songs.get(currentIndex);
        }

        if (playbackMode == PlaybackMode.RANDOM) {
            int nextIndex;
            do {
                nextIndex = new Random().nextInt(songs.size());
            } while (songs.size() > 1 && nextIndex == currentIndex);

            currentIndex = nextIndex;
        } else {
            currentIndex++;

            if (currentIndex >= songs.size()) {
                if (loopAfterLast) {
                    currentIndex = 0;
                } else {
                    currentIndex = songs.size() - 1;
                    playing = false;
                    return songs.get(currentIndex);
                }
            }
        }

        playing = true;
        return songs.get(currentIndex);
    }

    public String getCurrentSongName() {
        if (songs.isEmpty() || currentIndex < 0 || currentIndex >= songs.size()) {
            return "Ninguna";
        }
        return songs.get(currentIndex).getTitle();
    }

    public boolean isPlaying() {
        return playing;
    }

    public void setPlaybackMode(PlaybackMode mode) {
        if (mode == null) {
            throw new InvalidPlaylistOperationException("El modo de reproducción no puede ser nulo.");
        }
        this.playbackMode = mode;
    }

    public PlaybackMode getPlaybackMode() {
        return playbackMode;
    }

    public void setLoopAfterLast(boolean loopAfterLast) {
        this.loopAfterLast = loopAfterLast;
    }

    public boolean isLoopAfterLast() {
        return loopAfterLast;
    }

    public SimpleArrayList<Song> getSongs() {
        SimpleArrayList<Song> result = new SimpleArrayList<>();
        for (int i = 0; i < songs.size(); i++) {
            result.add(songs.get(i));
        }
        return result;
    }

    public int getCurrentIndex() {
        return currentIndex;
    }

    public int size() {
        return songs.size();
    }

    public boolean isEmpty() {
        return songs.isEmpty();
    }

    public String printPlaylist() {
        if (songs.isEmpty()) {
            return "La playlist está vacía.";
        }

        StringBuilder result = new StringBuilder();
        for (int i = 0; i < songs.size(); i++) {
            Song song = songs.get(i);
            if (i == currentIndex && playing) {
                result.append("-> ");
            } else {
                result.append("   ");
            }

            result.append(i + 1).append(". ").append(song.getTitle());
            if (i == currentIndex && playing) {
                result.append(" [Reproduciendo]");
            }
            result.append("\n");
        }
        return result.toString();
    }
}