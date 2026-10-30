package structures;

import java.util.NoSuchElementException;

public class BST<E extends Comparable<E>> {
    private int count = 0;
    private TreeNode<E> root = null;

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
            this.count++;
            return new TreeNode<>(value);
        }

        int comparison = value.compareTo(current.getValue());

        if (comparison > 0) {
            current.setRight(insertRecursive(current.getRight(), value));
        }

        if (comparison < 0) {
            current.setLeft(insertRecursive(current.getLeft(), value));
        }

        return current;
    }

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

        int comparison = value.compareTo(current.getValue());

        if (comparison == 0) {
            // Correccion: el count-- no va aca arriba (antes de los casos), porque en el
            // Caso 3 se descontaria dos veces. Ahora esta dentro de los casos 1 y 2.
            // Caso 1: hoja (no tiene hijos)
            if (current.getLeft() == null && current.getRight() == null) {
                count--;
                return null;
            } else if (current.getLeft() != null && current.getRight() == null) {
                count--;
                return current.getLeft();
            } else if (current.getLeft() == null && current.getRight() != null) {
                count--;
                return current.getRight();
            } else {
                // tiene los dos hijos
                TreeNode<E> successor = getMinChild(current.getRight());
                current.setValue(successor.getValue());
                current.setRight(removeRecursive(current.getRight(), successor.getValue()));
            }
        }
        if (comparison > 0) {
            current.setRight(removeRecursive(current.getRight(), value));
        }
        if (comparison < 0) {
            current.setLeft(removeRecursive(current.getLeft(), value));
        }

        return current;
    }

    protected TreeNode<E> getMinChild(TreeNode<E> current) {
        while (current.getLeft() != null) {
            current = current.getLeft();
        }
        return current;
    }

    public E get(E value) {
        if (value == null) {
            throw new IllegalArgumentException("No se puede buscar null.");
        }

        TreeNode<E> node = findRecursive(root, value);
        if (node == null) {
            throw new NoSuchElementException("El valor no existe en el arbol: " + value);
        }
        return node.getValue();
    }

    public boolean contains(E value) {
        if (value == null) {
            throw new IllegalArgumentException("No se puede buscar null.");
        }
        return findRecursive(root, value) != null;
    }

    protected TreeNode<E> findRecursive(TreeNode<E> current, E value) {
        if (current == null) return null;

        int comparison = value.compareTo(current.getValue());

        if (comparison > 0) return findRecursive(current.getRight(), value);
        if (comparison < 0) return findRecursive(current.getLeft(), value);

        return current;
    }

    public void clear() {
        root = null;
        count = 0;
    }

    public int size() {
        return count;
    }

    public boolean isEmpty() {
        return count == 0;
    }

    protected int getNodeHeight(TreeNode<E> current) {
        if (current == null) {
            return -1;
        }
        int leftHeight = getNodeHeight(current.getLeft());
        int rightHeight = getNodeHeight(current.getRight());
        return Math.max(leftHeight, rightHeight) + 1;
    }

    protected int getNodeBalanceFactor(TreeNode<E> current) {
        if (current == null) {
            return 0;
        }
        int leftHeight = getNodeHeight(current.getLeft());
        int rightHeight = getNodeHeight(current.getRight());
        return leftHeight - rightHeight;
    }

    public SimpleList<E> preOrder() {
        SimpleList<E> result = new SimpleArrayList<>();
        preOrderRecursive(root, result);
        return result;
    }

    protected void preOrderRecursive(TreeNode<E> current, SimpleList<E> result) {
        if (current == null) return;

        result.add(current.getValue());          // 1) procesar nodo actual
        preOrderRecursive(current.getLeft(), result);   // 2) izquierdo
        preOrderRecursive(current.getRight(), result);  // 3) derecho
    }

    public SimpleList<E> inOrder() {
        SimpleList<E> result = new SimpleArrayList<>();
        inOrderRecursive(root, result);
        return result;
    }

    protected void inOrderRecursive(TreeNode<E> current, SimpleList<E> result) {
        if (current == null) return;

        inOrderRecursive(current.getLeft(), result);   // hijo izquierdo
        result.add(current.getValue());              // nodo actual
        inOrderRecursive(current.getRight(), result); // hijo derecho
    }

    public SimpleList<E> postOrder() {
        SimpleList<E> result = new SimpleArrayList<>();
        postOrderRecursive(root, result);
        return result;
    }

    protected void postOrderRecursive(TreeNode<E> current, SimpleList<E> result) {
        if (current == null) return;

        postOrderRecursive(current.getLeft(), result);    // hijo izquierdo
        postOrderRecursive(current.getRight(), result);   // hijo derecho
        result.add(current.getValue());                   // nodo actual
    }

    public SimpleList<E> levelOrder() {
        SimpleList<E> result = new SimpleArrayList<>();
        if (root == null) {
            return result;
        }

        SimpleArrayQueue<TreeNode<E>> queue = new SimpleArrayQueue<>(Math.max(1, count));
        queue.enqueue(root);

        while (!queue.isEmpty()) {
            TreeNode<E> current = queue.dequeue();
            result.add(current.getValue());

            if (current.getLeft() != null) {
                queue.enqueue(current.getLeft());
            }
            if (current.getRight() != null) {
                queue.enqueue(current.getRight());
            }
        }

        return result;
    }

}
