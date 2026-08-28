public class Song {

    private final String title;

    public Song(String title) {
        if (title == null || title.trim().isEmpty()) {
            throw new InvalidPlaylistOperationException("El nombre de la canción no puede estar vacío.");
        }
        this.title = title.trim();
    }

    public String getTitle() {
        return title;
    }

    @Override
    public String toString() {
        return title;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Song)) return false;
        Song other = (Song) obj;
        return title.equalsIgnoreCase(other.title);
    }

    @Override
    public int hashCode() {
        return title.toLowerCase().hashCode();
    }
}