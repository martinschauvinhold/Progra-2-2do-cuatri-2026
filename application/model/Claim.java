package model;

public class Claim {
    private final String title;
    private final String description;
    private final UrgencyLevel urgency;

    public Claim(String title, String description, UrgencyLevel urgency) {
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("El título no puede estar vacío.");
        }
        if (description == null || description.trim().isEmpty()) {
            throw new IllegalArgumentException("La descripción no puede estar vacía.");
        }
        if (urgency == null) {
            throw new IllegalArgumentException("La urgencia es obligatoria.");
        }

        this.title = title.trim();
        this.description = description.trim();
        this.urgency = urgency;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public UrgencyLevel getUrgency() {
        return urgency;
    }

    @Override
    public String toString() {
        return "[" + urgency.getLabel() + "] " + title + " - " + description;
    }
}