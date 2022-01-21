/**
The second part of project 1a. This is a circular array version.
 **/
public class ArrayDeque<T> {
    /** Resize factor */
    private static final int FACTOR = 2;

    /** The authentic array (i.e. whole physical array) */
    private T[] items;

    /** The size of the virtual array we store information*/
    private int size;

    /** The position which marks the end of the circular,
     * which we place at the middle of the physical array*/
    private int endIndex;

    /** The start position of the virtual array*/
    private int headPosition;

    /** The end position of the virtual array*/
    private int tailPosition;

    /**Constructor of empty arrayDeque */
    public ArrayDeque() {
        items = (T[]) new Object[8];
        size = 0;
        endIndex = 3;
        headPosition = 6;
        tailPosition = 6;
        //By default, if the headPosition equals to the tailPosition, this
        // position is left for addLast() method
    }

    /** Constructor when passing a existing ArrayDeque */
    public ArrayDeque(ArrayDeque other) {
        System.arraycopy(other, 0, items, 0, other.physicalSize());
        size = other.size();
        endIndex = other.endIndex;
        headPosition = other.headPosition;
        tailPosition = other.tailPosition;
    }

    /**Return the physical array size
     * @return the physical array size
     * */
    public int physicalSize() {
        return items.length;
    }

    /** Resize the array
     * @param capacity the new capacity we need
     * */
    private void resize(int capacity) {
        T[] newArray = (T[]) new Object[capacity];
        // If the original array's items appear in the front of the array
        // (i.e. It has shown the circular form),
        // we need to consider two different situations
        if (tailPosition >= 0) {
            //The first situation, the headPosition is on the right-hand side of endIndex
            if (headPosition >= endIndex) {
                System.arraycopy(items, headPosition, newArray,
                        capacity - items.length + headPosition, items.length - headPosition);
                System.arraycopy(items, 0, newArray, 0, size - items.length + headPosition);
                headPosition = capacity - items.length + headPosition;
            } else { //The second situation, both headPosition and
                     // tailPosition are on the left-hand side of endIndex
                System.arraycopy(items, headPosition, newArray, capacity - headPosition - 1, size);
            }
        } else {
            System.arraycopy(items, headPosition, newArray, capacity - headPosition - 1, size);
        }
        items = newArray;
        endIndex = capacity / 2 - 1;
    }

    /** Calculate the new headPosition or tailPosition after add or remove.
     * Based on www.geeksforgeeks.org/modulo-operations-in-programming-with-negative-results/
     * @param pointer headPosition
     * @param length items.length
     * @return new headPosition or tailPositiion
     * */
    public int calcPosition(int pointer, int length) {
        int quotient = (int) Math.floor((double) pointer / length);
        return pointer - quotient * length;
    }

    /** Add an item to the first position
     * @param item new item
     * */
    public void addFirst(T item) {
        //If the front or tail meets the endIndex, we need to resize the array
        if (headPosition == endIndex || tailPosition == endIndex) {
            resize(items.length * FACTOR);
        }
        size += 1;
        headPosition = calcPosition(headPosition - 1, items.length);
        items[headPosition] = item;
    }

    /** Add a item to the last position
     * @param item new item
     * */
    public void addLast(T item) {
        //If the front or tail meets the endIndex, we need to resize the array
        if (headPosition == endIndex || tailPosition == endIndex) {
            resize(items.length * FACTOR);
        }
        size += 1;
        items[tailPosition] = item;
        tailPosition = calcPosition(tailPosition + 1, items.length);
    }

    /** Return if the virtual array is empty
     * @return if the virtual array is empty
     * */
    public boolean isEmpty() {
        return size == 0;
    }

    /** Return the size of the virtual array
     * @return ArrayDeque size
     */
    public int size() {
        return size;
    }

    /** Print the content of the array in the format of "items0 item1 item2..."
     * */
    public void printDeque() {
        if (tailPosition >= 0) {
            for (int i = headPosition; i < items.length - 1; i++) {
                System.out.println(items[i] + " ");
            }
            for (int j = 0; j < tailPosition; j++) {
                System.out.println(items[j] + " ");
            }
            System.out.println();
        } else {
            for (int i = headPosition; i < tailPosition; i++) {
                System.out.println(items[i] + " ");
            }
            System.out.println();
        }
    }

    /** Remove the first item
     * @return first item
     * */
    public T removeFirst() {
        T value = items[headPosition];
        headPosition = calcPosition(headPosition + 1, items.length);
        size -= 1;
        return value;
    }

    /**Remove the last item f
     * @return last item
     * */
    public T removeLast() {
        T value = items[tailPosition];
        tailPosition = calcPosition(tailPosition - 1, items.length);
        size -= 1;
        return value;
    }

    /** Get the ith item based on index
     * @param index index of ith item
     * @return ith item value
     * */
    public T get(int index) {
        T value = items[calcPosition(headPosition + index, items.length)];
        return value;
    }
}
