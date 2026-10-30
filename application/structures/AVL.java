package structures;

public class AVL <E extends Comparable<E>> extends BST<E> {

    protected TreeNode<E> insertRecursive(TreeNode<E> current, E element) {

        // Version resumida en una linea
        // return rebalanceNode(super.insertRecursive(current, element));

        // Repetimos el insertRecursive original de BST
        current = super.insertRecursive(current, element);
        // Lo rebalanceamos
        current = rebalanceNode(current);
        // Lo devolvemos
        return current;
    }

    protected TreeNode<E> removeRecursive(TreeNode<E> current, E element) {
        return rebalanceNode(super.removeRecursive(current, element));
    }

    private TreeNode<E> rebalanceNode(TreeNode<E> current) {

        int balanceFactor = getNodeBalanceFactor(current);

        if (balanceFactor > 1) {
            //Caso LL
            if (getNodeBalanceFactor(current.getLeft()) >= 0) {
                return rotateRight(current);
            //Caso LR
            } else {
                return rotateLeftRight(current);
            }
        }
        // Casos Rx
        if (balanceFactor < -1) {
            // Caso RR
            if (getNodeBalanceFactor(current.getRight()) <= 0) {
                return rotateLeft(current);
            // Caso RL
            } else {
                return rotateRightLeft(current);
            }
        }

        return current;
    }

    private TreeNode<E> rotateRight(TreeNode<E> y) {
        //Variables auxiliares
        TreeNode<E> x = y.getLeft();
        TreeNode<E> t2 = x.getRight();

        //Reasignamos los nodos
        x.setRight(y);
        y.setLeft(t2);

        //Queda X en la posicion de Y
        return x;
    }

    private TreeNode<E> rotateLeft(TreeNode<E> x) {
        //Variables auxiliares
        TreeNode<E> y = x.getRight();
        TreeNode<E> t2 = y.getLeft();

        //Reasignamos los nodos
        y.setLeft(x);
        x.setRight(t2);

        //Queda Y en la posicion de X
        return y;
    }

    private TreeNode<E> rotateRightLeft(TreeNode<E> current) {
        //primero rotamos "el del medio"
        current.setRight(rotateRight(current.getRight()));
        //luego rotamos el que recibimos originalmente
        return rotateLeft(current);
    }

    private TreeNode<E> rotateLeftRight(TreeNode<E> current) {
        current.setLeft(rotateLeft(current.getLeft()));
        return rotateRight(current);
    }

}
