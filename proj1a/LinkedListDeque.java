public class LinkedListDeque<T> {
    private Node sentinel;
    private int size;

    public static class Node<T> {
        private Node prev;
        private T value;
        private Node next;

        public Node(T value) {
            this.value = value;
            this.next = null;
            this.prev = null;
        }

        public Node(Node prev, T value, Node next) {
            this.prev = prev;
            this.value = value;
            this.next = next;
        }
    }

    public LinkedListDeque() {
        this.sentinel = new Node(null, 63, null);
        this.sentinel.prev = this.sentinel;
        this.sentinel.next = this.sentinel;
        size = 0;
    }

    public LinkedListDeque(T first) {
        this.sentinel = new Node(null, 63, null);
        Node head = new Node(first);
        head.prev = this.sentinel;
        head.next = this.sentinel;
        this.sentinel.prev = head;
        this.sentinel.next = head;
        size = 1;
    }

    public LinkedListDeque(LinkedListDeque other) {
        this.sentinel = new Node(null, 63, null);
        this.sentinel.prev = this.sentinel;
        this.sentinel.next = this.sentinel;
        size = 0;
        for (int i = 0; i < other.size(); i++) {
            addLast((T) other.get(i));
        }
    }

    public void addFirst(T item) {
        Node newHead = new Node(this.sentinel, item, this.sentinel.next);
        this.sentinel.next = newHead;
        if (this.sentinel.prev == this.sentinel) {
            this.sentinel.prev = newHead;
        }
        size += 1;
    }

    public void addLast(T item) {
        Node newLast = new Node(this.sentinel.prev, item, this.sentinel);
        this.sentinel.prev.next = newLast;
        this.sentinel.prev = newLast;
        this.size += 1;
    }

    public boolean isEmpty() {
        if (this.sentinel.next == null || this.sentinel.next == this.sentinel) {
            return true;
        }
        return false;
    }

    public int size() {
        return this.size;
    }

    public void printDeque() {
        Node ptr = this.sentinel;
        while (ptr.next != this.sentinel) {
            System.out.print(ptr.next.value + " ");
            ptr = ptr.next;
        }
        System.out.println();
    }

    public T removeFirst() {
        Node firstNode = this.sentinel.next;
        this.sentinel.next = this.sentinel.next.next;
        this.size -= 1;
        return (T) firstNode.value;
    }

    public T removeLast() {
        Node lastNode = this.sentinel.prev;
        this.sentinel.prev = this.sentinel.prev.prev;
        this.size -= 1;
        return (T) lastNode.value;
    }

    public T get(int index) {
        int cnt = -1;
        Node ptr = this.sentinel;
        while (cnt != index) {
            ptr = ptr.next;
            cnt += 1;
        }
        return (T) ptr.value;
    }

    public T getRecursive(int index) {
        if (index > this.size - 1 || index < 0) {
            return null;
        } else {
            Node finalNode = getRecursiveHelper(0, index, this.sentinel.next);
            return (T) finalNode.value;
        }
    }

    private Node getRecursiveHelper(int cnt, int index, Node n) {
        if (cnt == index) {
            return n;
        }
        return getRecursiveHelper(cnt + 1, index, n.next);
    }
}
