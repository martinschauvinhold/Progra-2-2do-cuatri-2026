package structures;

public interface SimpleStack<E> {
    void push(E element);
    E pop();
    E peek();
    void clear();
    int size();
    boolean isEmpty();
}