import java.util.NoSuchElementException;

public class SimpleArrayQueue<E> implements SimpleQueue<E> {
    private Object[] array;
    private int front;
    private int rear;
    private int count;

    public SimpleArrayQueue(int capacity) {
        this.array = new Object[capacity];
        this.front = 0;
        this.rear = -1;
        this.count = 0;
    }

    @Override
    public void enqueue(E element) {
        if (count == array.length) {
            throw new IllegalStateException("La cola está llena.");
        }
        rear = (rear + 1) % array.length;
        array[rear] = element;
        count++;
    }

    @SuppressWarnings("unchecked")
    @Override
    public E dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException("La cola está vacía. No se puede remover ningún elemento.");
        }
        E element = (E) array[front];
        array[front] = null; 
        front = (front + 1) % array.length;
        count--;
        return element;
    }

    @SuppressWarnings("unchecked")
    @Override
    public E peek() {
        if (isEmpty()) {
            throw new NoSuchElementException("La cola está vacía.");
        }
        return (E) array[front];
    }

    @Override
    public void clear() {
        for (int i = 0; i < array.length; i++) {
            array[i] = null;
        }
        front = 0;
        rear = -1;
        count = 0;
    }

    @Override
    public int size() {
        return count;
    }

    @Override
    public boolean isEmpty() {
        return count == 0;
    }
}