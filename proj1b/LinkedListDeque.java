/** The first part of project 1a, use circular sentinel node*/
public class LinkedListDeque<T> implements Deque<T>{
    /**The "default node" in the LinkedList. It is a circular version
     * (i.e. it points to itself in both directions
     * when the LikedListDeque is empty).
     * However, we do not want to know the value of it*/
    private Node sentinel;

    /**The size of LinkedListDeque*/
    private int size;

    /**The basic unit of LinkedListDeque*/
    public static class Node<T> {
        /** Previous node address*/
        private Node prev;

        /** value of a node*/
        private T value;

        /**Next node address*/
        private Node next;

        /** Constructor of Node given a value*/
        public Node(T value) {
            this.value = value;
            this.next = null;
            this.prev = null;
        }

        /**Constructor of Node given all information*/
        public Node(Node prev, T value, Node next) {
            this.prev = prev;
            this.value = value;
            this.next = next;
        }
    }

    /**Constructor of LinkedListDeque when creating an empty one*/
    public LinkedListDeque() {
        this.sentinel = new Node(null, 63, null);
        this.sentinel.prev = this.sentinel;
        this.sentinel.next = this.sentinel;
        size = 0;
    }

    /**Constructor of LinkedListDeque given a value*/
    public LinkedListDeque(T first) {
        this.sentinel = new Node(null, 63, null);
        Node head = new Node(first);
        head.prev = this.sentinel;
        head.next = this.sentinel;
        this.sentinel.prev = head;
        this.sentinel.next = head;
        size = 1;
    }

    /**Constructor of LinkedListDeque given existing LinkedListDeque*/
    public LinkedListDeque(LinkedListDeque other) {
        this.sentinel = new Node(null, 63, null);
        this.sentinel.prev = this.sentinel;
        this.sentinel.next = this.sentinel;
        size = 0;
        for (int i = 0; i < other.size(); i++) {
            addLast((T) other.get(i));
        }
    }

    /**Add the first node */
    public void addFirst(T item) {
        Node newHead = new Node(this.sentinel, item, this.sentinel.next);
        this.sentinel.next = newHead;
        if (this.sentinel.prev == this.sentinel) { //If the LinkedListDeque is empty
            this.sentinel.prev = newHead;
        }
        size += 1;
    }

    /**Add the last node*/
    public void addLast(T item) {
        Node newLast = new Node(this.sentinel.prev, item, this.sentinel);
        this.sentinel.prev.next = newLast;
        this.sentinel.prev = newLast;
        this.size += 1;
    }

    /**Return if the LinkedListDeque is empty
     * @return if the LinkedListDeque is empty*/
    public boolean isEmpty() {
        if (this.sentinel.next == null || this.sentinel.next == this.sentinel) {
            return true;
        }
        return false;
    }

    /**Return the size of LinkedListDeque
     * @return size of LinkedListDeque
     * */
    public int size() {
        return this.size;
    }

    /** Print the LinkedListDeque in the format of "item0 item1 item2..."*/
    public void printDeque() {
        Node ptr = this.sentinel;
        while (ptr.next != this.sentinel) {
            System.out.print(ptr.next.value + " ");
            ptr = ptr.next;
        }
        System.out.println();
    }

    /**Remove the first node
     * @return first node's value*/
    public T removeFirst() {
        Node firstNode = this.sentinel.next;
        this.sentinel.next = this.sentinel.next.next;
        this.size -= 1;
        return (T) firstNode.value;
    }

    /**Remove the last node
     * @return last node's value*/
    public T removeLast() {
        Node lastNode = this.sentinel.prev;
        this.sentinel.prev = this.sentinel.prev.prev;
        this.size -= 1;
        return (T) lastNode.value;
    }

    /**Return the value of a node
     * @return value of a node*/
    public T get(int index) {
        int cnt = -1;
        Node ptr = this.sentinel;
        while (cnt != index) {
            ptr = ptr.next;
            cnt += 1;
        }
        return (T) ptr.value;
    }

    /**Recursive version of get, a helper functions on Node class is used*/
    public T getRecursive(int index) {
        if (index > this.size - 1 || index < 0) {
            return null;
        } else {
            Node finalNode = getRecursiveHelper(0, index, this.sentinel.next);
            return (T) finalNode.value;
        }
    }

    /**Recursive return the Node which has the same index*/
    private Node getRecursiveHelper(int cnt, int index, Node n) {
        if (cnt == index) {
            return n;
        }
        return getRecursiveHelper(cnt + 1, index, n.next);
    }
}
