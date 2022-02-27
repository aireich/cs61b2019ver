package bearmaps.proj2ab;

import java.util.HashMap;
import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Set;

public class ArrayHeapMinPQ<T> implements ExtrinsicMinPQ<T>{

    private class Node<T> implements Comparable<Node>{
        private T value;
        private double priority;
        private int index;

        public Node(T value, double priority) {
            this.value = value;
            this.priority = priority;
            this.index = -1;
        }

        @Override
        public int compareTo(Node o) {
            if (this == o) {
                return 0;
            }
            if (o == null) {
                return -1;
            }
            return Double.compare(this.priority, o.priority);
        }
    }

    private Node<T>[] items;
    private int size;
    private static final double loadFactor = 0.75;
    private HashMap<T, Integer> itemIndexMap;

    public ArrayHeapMinPQ() {
        items = new Node[16];
        items[0] = new Node(null, -1);
        this.size = 0;
        this.itemIndexMap = new HashMap<T, Integer>();
    }

    private void resize(int factor) {
        Node[] copy =  new Node[items.length * factor];
        System.arraycopy(items, 0, copy, 0, items.length);
        this.items = copy;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean contains(T item) {
        if (size == 0) {
            throw new NoSuchElementException();
        }
        return items[itemIndexMap.get(item)] != null;
    }

    private int find(T item) {
        return itemIndexMap.get(item);
    }

    @Override
    public T getSmallest() {
        if (size > 0) {
            return (T) items[1].value;
        }
        throw new NoSuchElementException();
    }

    private Node leftChild(int k) {
        if (k >= items.length / 2) {
            return null;
        }
        return items[k * 2];
    }

    private Node rightChild(int k) {
        if (k >= items.length / 2) {
            return null;
        }
        return items[k * 2 + 1];
    }

    private Node parent(int k) {
        if (k < 0) {
            throw new IllegalArgumentException();
        }
        return items[k / 2];
    }

    @Override
    public void add(T item, double priority) {
        if (itemIndexMap.containsKey(item)) {
            return;
        }
        if (size >= loadFactor * items.length) {
            resize(2);
        }
        Node newNode = new Node(item, priority);
        newNode.index = size + 1;
        items[size + 1] = newNode;
        swimUp(newNode);
        itemIndexMap.put(item, newNode.index);
        size += 1;
    }

    private void swimUp(Node n) {
        boolean needSwap = true;
        int k = n.index;
        while (needSwap) {
            if (items[k].compareTo(parent(k)) < 0) {
                swap(k, k/2);
                k = k/2;
            } else {
                needSwap = false;
            }
        }
    }

    void sink(Node n) {
        boolean needSwap = true;
        int k = n.index;
        while (needSwap) {
            Node smallChild = null;
            if (leftChild(k) == null) {
                smallChild = rightChild(k);
            }
            else if (leftChild(k).compareTo(rightChild(k)) < 0) {
                smallChild = leftChild(k);
            } else {
                smallChild = rightChild(k);
            }
            if (items[k].compareTo(smallChild) > 0) {
                int newK = smallChild.index;;
                swap(k, smallChild.index);
                k = newK;
            } else {
                needSwap = false;
            }
        }

    }

    private void swap(int k, int z) {
        Node temp = items[z];
        items[z] = items[k];
        items[k] = temp;
        items[z].index = z;
        items[k].index = k;
    }

    @Override
    public T removeSmallest() {
        Node small = items[1];
        swap(size, 1);
        if (size > 1) {
            items[size] = null;
        }
        size -= 1;
        sink(items[1]);
        return (T) small.value;
    }

    @Override
    public void changePriority(T item, double priority) {
        if (!contains(item)) {
            throw new NoSuchElementException();
        }
        int index = itemIndexMap.get(item);
        Node n = items[index];
        n.priority = priority;
        swimUp(n);
        sink(n);
    }
}
