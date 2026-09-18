package structures;

public interface SimpleQueue<E> {
    void enqueue(E element);
    E dequeue();
    E peek();
    void clear();
    int size();
    boolean isEmpty();
}