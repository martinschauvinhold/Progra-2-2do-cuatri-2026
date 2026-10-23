package structures;

public interface SimpleTree<E extends Comparable<E>> {
    public void insert(E value);
    public void remove(E value);
    public E get(E value);
    public boolean contains(E value);
    public SimpleList<E> inOrder();
    public void clear();
    public int size();
    public boolean isEmpty();
}
