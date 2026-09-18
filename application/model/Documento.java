package model;

public class Documento {
    private String nombre;
    private int paginas;
    private boolean esColor;

    public Documento(String nombre, int paginas, boolean esColor) {
        this.nombre = nombre;
        this.paginas = paginas;
        this.esColor = esColor;
    }

    public String getNombre() {
        return nombre;
    }

    public int getPaginas() {
        return paginas;
    }

    public boolean isColor() {
        return esColor;
    }
}