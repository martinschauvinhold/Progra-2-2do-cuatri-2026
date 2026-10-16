package structures;

public class SimpleArrayDictionary<K, V> implements SimpleDictionary<K, V> {
    private K[] keys;
    private V[] values;
    private static final int DEFAULT_SIZE = 4;
    private int size = 0;

    @SuppressWarnings("unchecked")
    public SimpleArrayDictionary() {
        this.keys = (K[]) new Object[DEFAULT_SIZE];
        this.values = (V[]) new Object[DEFAULT_SIZE];
    }

    @Override
    public V put(K key, V value) {
        if (key == null) {
            throw new NullPointerException("Key cannot be null");
        }

        int index = indexOf(key);

        if (index != -1) {
            V oldValue = values[index];
            values[index] = value;
            return oldValue;
        }

        validateSize(size + 1);
        keys[size] = key;
        values[size] = value;
        size++;
        return null;
    }

    @Override
    public boolean remove(K key) {
        if (key == null) {
            throw new NullPointerException("Key cannot be null");
        }

        int index = indexOf(key);
        if (index == -1) {
            return false;
        }

        if (index != size - 1) {
            keys[index] = keys[size - 1];
            values[index] = values[size - 1];
        }

        keys[size - 1] = null;
        values[size - 1] = null;
        size--;
        return true;
    }

    @Override
    public boolean containsKey(K key) {
        if (key == null) {
            throw new NullPointerException("Key cannot be null");
        }
        return indexOf(key) != -1;
    }

    @Override
    public V get(K key) {
        if (key == null) {
            throw new NullPointerException("Key cannot be null");
        }

        int index = indexOf(key);
        if (index == -1) {
            return null;
        }
        return values[index];
    }

    @SuppressWarnings("unchecked")
    @Override
    public void clear() {
        this.keys = (K[]) new Object[DEFAULT_SIZE];
        this.values = (V[]) new Object[DEFAULT_SIZE];
        this.size = 0;
    }

    @Override
    public SimpleList<K> keys() {
        SimpleList<K> result = new SimpleArrayList<>(Math.max(size, 1));
        for (int i = 0; i < size; i++) {
            result.add(keys[i]);
        }
        return result;
    }

    @Override
    public SimpleList<V> values() {
        SimpleList<V> result = new SimpleArrayList<>(Math.max(size, 1));
        for (int i = 0; i < size; i++) {
            result.add(values[i]);
        }
        return result;
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
    private void resize() {
        int newLength = (keys.length == 0) ? DEFAULT_SIZE : keys.length * 2;

        K[] nextKeys = (K[]) new Object[newLength];
        V[] nextValues = (V[]) new Object[newLength];

        for (int i = 0; i < size; i++) {
            nextKeys[i] = keys[i];
            nextValues[i] = values[i];
        }

        this.keys = nextKeys;
        this.values = nextValues;
    }

    private void validateSize(int newSize) {
        if (newSize > keys.length) {
            resize();
        }
    }

    private int indexOf(K key) {
        for (int i = 0; i < size; i++) {
            if (keys[i] != null && keys[i].equals(key)) {
                return i;
            }
        }
        return -1;
    }
}