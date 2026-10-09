package structures;

public interface SimplePriorityQueue <E> {

    void enqueue(E element, int priority);
    E dequeue();
    E peek();
    public int getHighestPriority();
    void clear();
    int size();
    boolean isEmpty();
}
