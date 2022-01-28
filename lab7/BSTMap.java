import java.util.Iterator;
import java.util.Set;

/***A Map based on Binary Search Tree (NOT FINISHED VERSION)
 * @source (underlying idea, almost same) https://algs4.cs.princeton.edu/32bst/BST.java.html
 * @source http://www-inst.eecs.berkeley.edu//~cs61b/fa14/book2/data-structures.pdf
 * @source (printInOrder first method) https://github.com/PigZhuJ/cs61b_sp19/blob/master/lab7/BSTMap.java
 * @source (printInOrder second method which I should have done that) https://medium.com/javarevisited/
 * how-to-print-nodes-of-a-binary-search-tree-in-sorted-order-8a4e52eb8856
 * @author aireich
 * */
public class BSTMap<K extends Comparable, V> implements Map61B<K, V> {

    /** A private class to represent the basic unit of tree**/
    private class Node<K, V> {
        private K key;
        private V value;
        private Node left = null;
        private Node right = null;
        private int size = 0;

        public Node(K key, V value, int size) {
            this.key = key;
            this.value = value;
            this.size = size;
        }
    }

    /**The root node of BST*/
    private Node root;

    @Override
    public boolean containsKey(K key) {
        if (key == null) {
            return false;
        }
        return get(key) != null;
    }

    /**The size of overall BSTMap**/
    @Override
    public int size() {
        if (root == null) {
            return 0;
        }
        return root.size;
    }

    /**Private helper to get the size of a node**/
    private  int size(Node n) {
        if (n == null) {
            return 0;
        } else {
            return n.size;
        }
    }

    @Override
    public V get(K key) {
        return get(root, key);
    }

    /**A private helper for get method. This method compensates the issue that BSTMap cannot access to subtrees'
     * left and right nodes**/
    private V get (Node node, K key) {
        if (node == null) {
            return null;
        } else if (key.compareTo(node.key) < 0) {
            return (V) get(node.left, key);
        } else if (key.compareTo(node.key) > 0) {
            return (V) get(node.right, key);
        } else {
            return (V) node.value;
        }
    }


    @Override
    public void clear() {
        root = null;
    }

    @Override
    public void put(K key, V value) {
        root = put(root, key, value);
    }

    /**A private helper for put method**/
    private Node put(Node node, K key, V value) {
        if (node == null) {
            return new Node<K, V>(key, value, 1);
        } else if (key.compareTo(node.key) < 0) {
            node.left = put(node.left, key, value);
        } else if (key.compareTo(node.key) > 0) {
            node.right = put(node.right, key, value);
        } else {
            node.value = value;
        }
        node.size = 1 + size(node.left) + size(node.right);
        return node;
    }

    /**Print items in BSTMap in increasing order  (The first method)*/
    public void printInOrder() {
        for (int i = 0; i < root.size; i++) {
            System.out.println(getNode(i).key);
        }
    }


    /**Private helper for getting ith node
     * @source https://github.com/PigZhuJ/cs61b_sp19/blob/master/lab7/BSTMap.java**/
    private Node getNode(int index) {
        return getNode(root, index);
    }

    private Node getNode(Node node, int index) {
        if (node == null) {
            return null;
        }
        int compSize = size(node.left);
        if (index < compSize) {
            return getNode(node.left, index);
        } else if (index > compSize) {
            return getNode(node.right, index - compSize - 1);
        } else {
            return node;
        }
    }


    /**Print items in BSTMap in increasing order  (The second method) **/
    public void inOrder() {
        inOrder(root);
    }

    private void inOrder(Node node) {
        if (node == null) {
            return;
        }
        inOrder(node.left);
        System.out.println(node.key);
        inOrder(node.right);
    }



    @Override
    public Iterator<K> iterator() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Set<K> keySet() {
        throw new UnsupportedOperationException();
    }


    @Override
    public V remove(K key) {
        throw new UnsupportedOperationException();
    }

    @Override
    public V remove(K key, V value) {
        throw new UnsupportedOperationException();
    }
}
