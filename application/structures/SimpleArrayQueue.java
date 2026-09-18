package structures;

import java.util.NoSuchElementException;

public class SimpleArrayQueue<E> implements SimpleQueue<E> {
    private Object[] array;
    private int size;

    public SimpleArrayQueue(int capacity) {
        this.array = new Object[capacity];
        this.size = 0;
    }

    @Override
    public void enqueue(E element) {
        if (size == array.length) {
            throw new IllegalStateException("La cola está llena.");
        }
        array[size] = element;
        size++;
    }

    @SuppressWarnings("unchecked")
    @Override
    public E dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException("La cola está vacía. No se puede remover ningún elemento.");
        }
        E element = (E) array[0];
        shiftLeft();
        size--;
        return element;
    }

    @SuppressWarnings("unchecked")
    @Override
    public E peek() {
        if (isEmpty()) {
            throw new NoSuchElementException("La cola está vacía.");
        }
        return (E) array[0];
    }

    @Override
    public void clear() {
        for (int i = 0; i < size; i++) {
            array[i] = null;
        }
        size = 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    // El primer elemento siempre queda en el índice 0.
    // Al remover, corremos todo un lugar a la izquierda.
    private void shiftLeft() {
        for (int i = 0; i < size - 1; i++) {
            array[i] = array[i + 1];
        }
        array[size - 1] = null;
    }
}
