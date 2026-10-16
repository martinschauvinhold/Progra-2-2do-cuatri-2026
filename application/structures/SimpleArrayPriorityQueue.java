package structures;

import java.util.NoSuchElementException;

public class SimpleArrayPriorityQueue <E> implements SimplePriorityQueue<E> {
    private E[] elements;
    private int [] priorities;
    private int size = 0;
    private static final int DEFAULT_SIZE = 4;

    @SuppressWarnings("unchecked")
    public SimpleArrayPriorityQueue() {
        this.elements = (E[]) new Object[DEFAULT_SIZE];
        this.priorities = new int[DEFAULT_SIZE];
    }

    @Override
    public void enqueue(E element, int priority) {
        validateSize(size + 1);
        int insertIndex = size;
        for (int i = size; i > 0 && priority < priorities[i - 1]; i--) {
            insertIndex = i - 1;
            elements[i] = elements[i - 1];
            priorities[i] = priorities[i - 1];
        }
        elements[insertIndex] = element;
        priorities[insertIndex] = priority;
        size++;
    }

    @Override
    public int getHighestPriority() {
        if (isEmpty()) {
            throw new NoSuchElementException("Queue is empty.");
        }
        return priorities[0];
    }


    @SuppressWarnings("unchecked")
    @Override
    public E dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException("Queue is empty.");
        }
        E element = (E) elements[0];
        shiftLeft(0);
        size--;
        return element;
    }

    @SuppressWarnings("unchecked")
    @Override
    public E peek() {
        if (isEmpty()) {
            throw new NoSuchElementException("Queue is empty.");
        }
        return elements[0];
    }

    @Override
    public void clear() {
        elements = (E[]) new Object[DEFAULT_SIZE];
        priorities = new int[DEFAULT_SIZE];
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

    @SuppressWarnings("unchecked")
    private void resize(){
        E[] nextArray = (E[]) new Object[elements.length * 2];
        int[] nextPriorities = new int[priorities.length * 2];
        for(int i = 0; i < elements.length; i++) {
            nextArray[i] = elements[i];
            nextPriorities[i] = priorities[i];
        }
        elements = nextArray;
        priorities = nextPriorities;
    }

    // El primer elemento siempre queda en el índice 0.
    // Al remover, corremos todo un lugar a la izquierda.
    private void shiftLeft(int index) {
        for (int i = index; i < size - 1; i++) {
            elements[i] = elements[i + 1];
            priorities[i] = priorities[i + 1];
        }
        elements[size - 1] = null;
        priorities[size - 1] = 0;
    }

    private void validateSize(int newSize) {
        if (newSize > elements.length) {
            resize();
        }
    }

}
