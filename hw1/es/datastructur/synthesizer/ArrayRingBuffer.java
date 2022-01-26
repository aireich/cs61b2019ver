package es.datastructur.synthesizer;
import java.util.Iterator;

//TODO: Make sure to that this class and all of its methods are public
//TODO: Make sure to add the override tag for all overridden methods
//TODO: Make sure to make this class implement BoundedQueue<T>

public class ArrayRingBuffer<T> implements BoundedQueue<T> {
    /* Index for the next dequeue or peek. */
    private int first;
    /* Index for the next enqueue. */
    private int last;
    /* Variable for the fillCount. */
    private int fillCount;
    /* Array for storing the buffer data. */
    private T[] rb;

    /**
     * Create a new ArrayRingBuffer with the given capacity.
     */
    public ArrayRingBuffer(int capacity) {
        // TODO: Create new array with capacity elements.
        //       first, last, and fillCount should all be set to 0.
        this.rb = (T[]) new Object[capacity];
        this.first = 0;
        this.last = 0;
        this.fillCount = 0;
    }

    private int update(int index) {
        return index % rb.length;
    }

    /**
     * Adds x to the end of the ring buffer. If there is no room, then
     * throw new RuntimeException("Ring buffer overflow").
     */
    @Override
    public void enqueue(T x) {
        // TODO: Enqueue the item. Don't forget to increase fillCount and update
        //       last.
        if (fillCount == capacity()) {
            throw new RuntimeException("Ring Buffer overflow");
        }
        last = update(last + 1);
        rb[last] = x;
        fillCount += 1;
        if (fillCount == 1) {
            first = last;
        }
    }

    /**
     * Dequeue oldest item in the ring buffer. If the buffer is empty, then
     * throw new RuntimeException("Ring buffer underflow").
     */
    @Override
    public T dequeue() {
        if (fillCount == 0) {
            throw new RuntimeException("Ring Buffer underflow");
        }
        // TODO: Dequeue the first item. Don't forget to decrease fillCount and
        //       update first.
        T item = rb[first];
        first = update(first + 1);
        fillCount -= 1;
        return item;
    }

    /**
     * Return oldest item, but don't remove it. If the buffer is empty, then
     * throw new RuntimeException("Ring buffer underflow").
     */
    @Override
    public T peek() {
        // TODO: Return the first item. None of your instance variables should
        //       change.
        if (fillCount == 0) {
            throw new RuntimeException("Ring Buffer underflow");
        }
        return rb[first];
    }

    @Override
    public int capacity() {
        return rb.length;
    }

    @Override
    public int fillCount() {
        return fillCount;
    }

    // TODO: When you get to part 4, implement the needed code to support
    //       iteration and equals.

    @Override
    public boolean equals(Object o) {
        boolean result = true;
        if (o.getClass() == this.getClass()) {
            ArrayRingBufferIterator iter1 = new ArrayRingBufferIterator(this);
            ArrayRingBufferIterator iter2 = new ArrayRingBufferIterator((ArrayRingBuffer) o);
            while (iter1.hasNext() && iter2.hasNext()) {
                if (iter1.next() != iter2.next()) {
                    result = false;
                }
            }
        }
        return result;
    }


    @Override
    public Iterator<T> iterator() {
        return new ArrayRingBufferIterator(this);
    }

    private class ArrayRingBufferIterator implements Iterator<T> {
        private ArrayRingBuffer buffer;
        private int loopedCout;
        private int position;
        public ArrayRingBufferIterator(ArrayRingBuffer buffer) {
            this.buffer = buffer;
            position = first;
            loopedCout = 0;
        }

        @Override
        public T next() {
            if (hasNext()) {
                T result = rb[position];
                position = update(position + 1);
                loopedCout += 1;
                return result;
            }
            return null;
        }

        @Override
        public boolean hasNext() {
            if (loopedCout < fillCount) {
                return true;
            }
            return false;
        }
    }
}
    // TODO: Remove all comments that say TODO when you're done.
