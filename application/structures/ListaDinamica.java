package structures;

public class ListaDinamica<T> {
    
    private Nodo<T> cabeza;
    private int tamaño;
    
    public ListaDinamica() {
        this.cabeza = null;
        this.tamaño = 0;
    }
    
    public void insertarAlInicio(T dato) {
        Nodo<T> nuevoNodo = new Nodo<>(dato);
        nuevoNodo.setSiguiente(cabeza);
        cabeza = nuevoNodo;
        tamaño++;
    }
    
    public void insertarAlFinal(T dato) {
        Nodo<T> nuevoNodo = new Nodo<>(dato);
        if (cabeza == null) {
            cabeza = nuevoNodo;
        } else {
            Nodo<T> actual = cabeza;
            while (actual.getSiguiente() != null) {
                actual = actual.getSiguiente();
            }
            actual.setSiguiente(nuevoNodo);
        }
        tamaño++;
    }
    
    public void insertarEnPosicion(int posicion, T dato) {
        if (posicion < 0 || posicion > tamaño) {
            throw new IndexOutOfBoundsException("Posición inválida");
        }
        if (posicion == 0) {
            insertarAlInicio(dato);
        } else {
            Nodo<T> actual = cabeza;
            for (int i = 0; i < posicion - 1; i++) {
                actual = actual.getSiguiente();
            }
            Nodo<T> nuevoNodo = new Nodo<>(dato);
            nuevoNodo.setSiguiente(actual.getSiguiente());
            actual.setSiguiente(nuevoNodo);
            tamaño++;
        }
    }
    
    public T eliminarAlInicio() {
        if (cabeza == null) {
            throw new IllegalStateException("Lista vacía");
        }
        T dato = cabeza.getDato();
        cabeza = cabeza.getSiguiente();
        tamaño--;
        return dato;
    }
    
    public T eliminarAlFinal() {
        if (cabeza == null) {
            throw new IllegalStateException("Lista vacía");
        }
        if (cabeza.getSiguiente() == null) {
            T dato = cabeza.getDato();
            cabeza = null;
            tamaño--;
            return dato;
        }
        Nodo<T> actual = cabeza;
        while (actual.getSiguiente().getSiguiente() != null) {
            actual = actual.getSiguiente();
        }
        T dato = actual.getSiguiente().getDato();
        actual.setSiguiente(null);
        tamaño--;
        return dato;
    }
    
    public T eliminarEnPosicion(int posicion) {
        if (posicion < 0 || posicion >= tamaño) {
            throw new IndexOutOfBoundsException("Posición inválida");
        }
        if (posicion == 0) {
            return eliminarAlInicio();
        }
        Nodo<T> actual = cabeza;
        for (int i = 0; i < posicion - 1; i++) {
            actual = actual.getSiguiente();
        }
        T dato = actual.getSiguiente().getDato();
        actual.setSiguiente(actual.getSiguiente().getSiguiente());
        tamaño--;
        return dato;
    }
    
    public T obtener(int posicion) {
        if (posicion < 0 || posicion >= tamaño) {
            throw new IndexOutOfBoundsException("Posición inválida");
        }
        Nodo<T> actual = cabeza;
        for (int i = 0; i < posicion; i++) {
            actual = actual.getSiguiente();
        }
        return actual.getDato();
    }
    
    public int getTamaño() {
        return tamaño;
    }
    
    public boolean estaVacia() {
        return tamaño == 0;
    }
    
    public void mostrar() {
        Nodo<T> actual = cabeza;
        System.out.print("Lista: ");
        while (actual != null) {
            System.out.print(actual.getDato() + " -> ");
            actual = actual.getSiguiente();
        }
        System.out.println("null");
    }
    
    private static class Nodo<T> {
        private T dato;
        private Nodo<T> siguiente;
        
        public Nodo(T dato) {
            this.dato = dato;
            this.siguiente = null;
        }
        
        public T getDato() {
            return dato;
        }
        
        public void setDato(T dato) {
            this.dato = dato;
        }
        
        public Nodo<T> getSiguiente() {
            return siguiente;
        }
        
        public void setSiguiente(Nodo<T> siguiente) {
            this.siguiente = siguiente;
        }
    }
}
