package structures;

public class SimpleArraySet<E> implements SimpleSet<E> {
    private static final int DEFAULT_CAPACITY = 50;
    private E[] array;
    private int size = 0;

    @SuppressWarnings("unchecked")
    public SimpleArraySet() {
        array = (E[]) new Object[DEFAULT_CAPACITY];
    }

    @SuppressWarnings("unchecked")
    public SimpleArraySet(SimpleSet<E> original) {
        array = original.toArray((E[]) new Object[0]);
        size = original.size();
    }

    @Override
    public boolean add(E element) {
        if (contains(element)) return false;

        validateSize(size + 1); 
        
        array[size] = element;
        size++;
        return true;
    }

    private void validateSize(int newSize) {
        if (newSize > array.length) {
            @SuppressWarnings("unchecked")
            E[] newArray = (E[]) new Object[array.length * 2];
            for (int i = 0; i < size; i++) {
                newArray[i] = array[i];
            }
            array = newArray;
        }
    }

    @Override
    public boolean remove(E element) {
        for (int i = 0; i < size; i++) {
            if (array[i].equals(element)) {
                if (i < size - 1) array[i] = array[size - 1];

                array[size - 1] = null;
                size--;
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean contains(E element) {
        for (int i = 0; i < size; i++) {
            if (array[i].equals(element)) return true;
        }
        return false;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void clear() {
        array = (E[]) new Object[size];
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
    @Override
    public E[] toArray(E[] typeArray) {
        E[] result = (E[]) java.lang.reflect.Array.newInstance(typeArray.getClass().getComponentType(), size);
        for (int i = 0; i < size; i++) {
            result[i] = array[i];
        }
        return result;
    }

    @Override
    public SimpleSet<E> unionWith(SimpleSet<E> other) {
        if (other == null) throw new IllegalArgumentException("Other Set cannot be null");
        
        SimpleSet<E> result = new SimpleArraySet<E>();

        for (int i = 0; i < size; i++) {
            result.add(array[i]);
        }
        
        @SuppressWarnings("unchecked")
        E[] otherArray = other.toArray((E[]) new Object[0]);
        for (int i = 0; i < other.size(); i++) {
            result.add(otherArray[i]);
        }
        
        return result;
    }

    @Override
    public SimpleSet<E> intersectWith(SimpleSet<E> other) {
        if (other == null) throw new IllegalArgumentException("Other Set cannot be null");
        
        SimpleSet<E> result = new SimpleArraySet<E>();
        
        for (int i = 0; i < size; i++) {
            if (other.contains(array[i])) {
                result.add(array[i]);
            }
        }
        return result;
    }

    @Override
    public SimpleSet<E> differenceWith(SimpleSet<E> other) {
        if (other == null) throw new IllegalArgumentException("Other Set cannot be null");

        SimpleSet<E> result = new SimpleArraySet<E>();

        for (int i = 0; i < size; i++) {
            if (!other.contains(array[i])) {
                result.add(array[i]);
            }
        }
        return result;
    }
}