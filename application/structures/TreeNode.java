package structures;

public class TreeNode<E> {
    public E value;
    public TreeNode<E> left = null;
    public TreeNode<E> right = null;
    public int height = 1;

    public TreeNode(E value) {
        this.value = value;
    }
}
