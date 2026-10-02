package model;
import structures.SimpleSet;
import structures.SimpleArraySet;
public class Personaje {
    private String nombre;
    private SimpleSet<String> habilidades;

    public Personaje(String nombre) {
        this.nombre = nombre;
        // Instanciamos el TDA Set que armaste con el profesor
        this.habilidades = new SimpleArraySet<>();
    }

    public String getNombre() {
        return nombre;
    }

    public SimpleSet<String> getHabilidades() {
        return habilidades;
    }

    // Sobrescribimos equals para que el Set de Personajes sepa compararlos por nombre
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Personaje other = (Personaje) obj;
        return nombre.equalsIgnoreCase(other.nombre);
    }
}