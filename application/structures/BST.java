package structures;

import java.util.NoSuchElementException;

public class BST<E extends Comparable<E>> implements SimpleTree<E> {
    protected int count = 0;
    protected TreeNode<E> root = null;

    @Override
    public void insert(E value) {
        if (value == null) {
            throw new IllegalArgumentException("No se puede insertar null.");
        }
        root = insertRecursive(root, value);
    }

    protected TreeNode<E> insertRecursive(TreeNode<E> current, E value) {
        // Caso base: recibimos un espacio vacio
        // Devuelve el nodo que va a ocupar ese lugar
        if (current == null) {
            count++;
            return new TreeNode<>(value);
        }

        int comparison = value.compareTo(current.value);

        if (comparison == 0) {
            throw new IllegalArgumentException("El valor ya existe en el arbol: " + value);
        }
        if (comparison > 0) {
            current.right = insertRecursive(current.right, value);
        }
        if (comparison < 0) {
            current.left = insertRecursive(current.left, value);
        }

        return current;
    }

    @Override
    public void remove(E value) {
        if (value == null) {
            throw new IllegalArgumentException("No se puede remover null.");
        }
        root = removeRecursive(root, value);
    }

    protected TreeNode<E> removeRecursive(TreeNode<E> current, E value) {
        if (current == null) {
            throw new NoSuchElementException("El valor no existe en el arbol: " + value);
        }

        int comparison = value.compareTo(current.value);

        if (comparison == 0) {
            // Caso 1: hoja (no tiene hijos)
            if (current.left == null && current.right == null) {
                count--;
                return null;
            }
            // Caso 2: solo tiene un hijo
            else if (current.left == null) {
                count--;
                return current.right;
            } else if (current.right == null) {
                count--;
                return current.left;
            }
            // Caso 3: tiene los dos hijos
            else {
                TreeNode<E> successor = getMinChild(current.right);
                current.value = successor.value;
                current.right = removeRecursive(current.right, successor.value);
            }
        }
        if (comparison > 0) {
            current.right = removeRecursive(current.right, value);
        }
        if (comparison < 0) {
            current.left = removeRecursive(current.left, value);
        }

        return current;
    }

    protected TreeNode<E> getMinChild(TreeNode<E> current) {
        while (current.left != null) {
            current = current.left;
        }
        return current;
    }

    @Override
    public E get(E value) {
        if (value == null) {
            throw new IllegalArgumentException("No se puede buscar null.");
        }

        TreeNode<E> node = findRecursive(root, value);
        if (node == null) {
            throw new NoSuchElementException("El valor no existe en el arbol: " + value);
        }
        return node.value;
    }

    @Override
    public boolean contains(E value) {
        if (value == null) {
            throw new IllegalArgumentException("No se puede buscar null.");
        }
        return findRecursive(root, value) != null;
    }

    protected TreeNode<E> findRecursive(TreeNode<E> current, E value) {
        if (current == null) return null;

        int comparison = value.compareTo(current.value);

        if (comparison > 0) return findRecursive(current.right, value);
        if (comparison < 0) return findRecursive(current.left, value);

        return current;
    }

    @Override
    public SimpleList<E> inOrder() {
        SimpleList<E> result = new SimpleArrayList<>();
        inOrderRecursive(root, result);
        return result;
    }

    protected void inOrderRecursive(TreeNode<E> current, SimpleList<E> result) {
        if (current == null) return;

        inOrderRecursive(current.left, result);
        result.add(current.value);
        inOrderRecursive(current.right, result);
    }

    @Override
    public void clear() {
        root = null;
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
