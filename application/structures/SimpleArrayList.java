package structures;

public class SimpleArrayList<E> implements SimpleList<E> {

    public static final int DEFAULT_CAPACITY = 4;
    private E[] elements;
    private int size = 0;

    @SuppressWarnings("unchecked")
    public SimpleArrayList(){
        this.elements = (E[]) new Object[DEFAULT_CAPACITY];
    }

    @SuppressWarnings("unchecked")
    public SimpleArrayList(int capacity){
        if(capacity <= 0)
            throw new IllegalArgumentException("Capacity must be greater than 0");
        this.elements = (E[]) new Object[capacity];
    }

    @Override
    public boolean add(E element) {
        validateSize(size + 1);
        elements[size] = element;
        size++;
        return true;
    }

    @Override
    public void add(int index, E element) {
        if(index == size) {
            add(element);
            return;
        }
        validateSize(size + 1);
        validateIndex(index);
        shiftRight(index);
        elements[index] = element;
        size++;

    }

    @Override
    public boolean remove(Object o) {
        for(int i = 0; i < size; i++) {
            if(elements[i].equals(o)) {
                shiftLeft(i);
                size--;
                return true;
            }
        }
        return false;
    }

    @Override
    public E remove(int index) {
        validateIndex(index);
        E removedElement = elements[index];
        shiftLeft(index);
        size--;
        return removedElement;
    }

    @Override
    public E get(int index) {
        validateIndex(index);

        return elements[index];
    }

    @Override
    public E set(int index, E element) {
        validateIndex(index);
        E previousElement = elements[index];
        elements[index] = element;
        return previousElement;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void clear() {
        elements = (E[]) new Object[elements.length];
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

    @Override
    public boolean contains(Object o) {
        for (int i = 0; i < size; i++) {
            if (elements[i].equals(o)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int indexOf(Object o) {
        for (int i = 0; i < size; i++) {
            if (elements.equals(o)) {
                return i;
            }
        }
        return -1;
    }

    private void validateSize(int newSize) {
        if(newSize >= elements.length) {
            resize();
        }
    }

    private void resize(){
        E[] newElements = (E[]) new Object[elements.length * 2];
        for(int i = 0; i < elements.length; i++) {
            newElements[i] = elements[i];
        }
        elements = newElements;
    }

    private void validateIndex(int index) {
        if(index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
    }

    private void shiftRight(int index) {
        for(int i = size; i > index; i--) {
            elements[i] = elements[i - 1];
        }
    }

    private void shiftLeft(int index) {
        for(int i = index; i < size - 1; i++) {
            elements[i] = elements[i + 1];
        }
        elements[size] = null;
    }

}
