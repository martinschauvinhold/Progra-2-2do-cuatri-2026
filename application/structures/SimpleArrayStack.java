package structures;

import java.util.NoSuchElementException;

public class SimpleArrayStack<E> implements SimpleStack<E> {
    private Object[] array;
    private int top;

    public SimpleArrayStack(int capacity) {
        this.array = new Object[capacity];
        this.top = -1; // -1 indica que la pila está vacía
    }

    @Override
    public void push(E element) {
        // Genera excepción si se excede la capacidad de la pila
        if (top == array.length - 1) {
            throw new IllegalStateException("La pila está llena.");
        }
        top++;
        array[top] = element;
    }

    @SuppressWarnings("unchecked")
    @Override
    public E pop() {
        // Las implementaciones del TDA en sí deben generar excepciones si reciben comandos o datos inválidos
        if (isEmpty()) {
            throw new NoSuchElementException("La pila está vacía. No se puede remover ningún elemento.");
        }
        E element = (E) array[top];
        array[top] = null; // Liberar referencia para el Garbage Collector
        top--;
        return element;
    }

    @SuppressWarnings("unchecked")
    @Override
    public E peek() {
        if (isEmpty()) {
            throw new NoSuchElementException("La pila está vacía. No hay elementos para observar.");
        }
        return (E) array[top];
    }

    @Override
    public void clear() {
        // Limpiamos las referencias del arreglo
        for (int i = 0; i <= top; i++) {
            array[i] = null;
        }
        top = -1;
    }

    @Override
    public int size() {
        return top + 1;
    }

    @Override
    public boolean isEmpty() {
        return top == -1;
    }
}