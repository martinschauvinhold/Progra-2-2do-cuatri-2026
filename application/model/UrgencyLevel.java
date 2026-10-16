package model;

import java.util.Locale;

public enum UrgencyLevel {
    CRITICO(1, "Crítico"),
    ALTO(2, "Alto"),
    MEDIO(3, "Medio"),
    BAJO(4, "Bajo");

    private final int priority;
    private final String label;

    UrgencyLevel(int priority, String label) {
        this.priority = priority;
        this.label = label;
    }

    public int getPriority() {
        return priority;
    }

    public String getLabel() {
        return label;
    }

    public static UrgencyLevel fromInput(String input) {
        if (input == null) {
            throw new IllegalArgumentException("La urgencia no puede ser nula.");
        }

        String normalized = input.trim().toUpperCase(Locale.ROOT);

        switch (normalized) {
            case "1":
            case "CRITICO":
            case "CRÍTICO":
                return CRITICO;
            case "2":
            case "ALTO":
                return ALTO;
            case "3":
            case "MEDIO":
                return MEDIO;
            case "4":
            case "BAJO":
                return BAJO;
            default:
                throw new IllegalArgumentException("Urgencia inválida.");
        }
    }

    @Override
    public String toString() {
        return label;
    }
}