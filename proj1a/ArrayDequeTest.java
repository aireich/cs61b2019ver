/** Test for ArrayDeque* */
public  class ArrayDequeTest {

    public static void testPass(boolean value) {
        if (value) {
            System.out.println("Test Passed");
        } else {
            System.out.println("Test failed!");
        }
    }
    /** Test if the ArrayDeque is empty after created*/
    public static void testEmpty() {
        System.out.println("Running testEmpty");
        boolean passed = true;
        ArrayDeque ad1 = new ArrayDeque();
        passed = ad1.isEmpty() && passed;
        ad1.addFirst(5);
        passed = !ad1.isEmpty() && passed;
        ad1.removeFirst();
        passed = ad1.isEmpty() && passed;
        ad1.addFirst(5);
        ad1.removeLast();
        passed = ad1.isEmpty() && passed;
        testPass(passed);
    }

    /**Test the add function and resize function*/
    public static void testAddResize() {
        System.out.println("Running testAddResize");
        boolean passed = true;
        ArrayDeque ad1 = new ArrayDeque();
        ad1.addFirst(5);
        ad1.addFirst(0);
        ad1.addLast(10);
        passed = ((int) ad1.get(0) == 0) && ((int) ad1.get(1) == 5)
                && ((int) ad1.get(2) == 10) && passed;
        ArrayDeque ad2 = new ArrayDeque();
        ad2.addLast(5);
        ad2.addFirst(0);
        passed = ((int) ad2.get(0) == 0) && ((int) ad2.get(1) == 5)  && passed;
        ad1.addLast(15);
        ad1.addLast(20);
        passed = ((int) ad1.get(3) == 15) && ((int) ad1.get(4) == 20) && passed;
        ad1.addFirst(-5);
        ad1.addFirst(-10);
        ad1.addFirst(-15);
        ad1.addFirst(-20);
        passed = ((int) ad1.get(0) == -20) && passed;
        ad2.addLast(10);
        ad2.addLast(15);
        ad2.addLast(20);
        ad2.addLast(25);
        ad2.addLast(30);
        ad2.addLast(35);
        passed = ((int) ad2.get(7) == 35) && passed;
        testPass(passed);
    }

    /**Test remove methods and resize*/
    public static void testRemoveResize() {
        System.out.println("Running testRemoveResize");
        boolean passed = true;
        ArrayDeque ad1 = new ArrayDeque();
        ad1.addLast(5);
        ad1.addLast(10);
        ad1.addLast(15);
        ad1.addLast(20);
        ad1.addLast(25);
        ad1.addLast(30);
        ad1.removeFirst();
        ad1.removeFirst();
        ad1.removeFirst();
        ad1.removeFirst();
        int removed1 = (int) ad1.removeFirst();
        passed = removed1 == 25 && passed;
        passed = ((int) ad1.get(0) == 30) && passed;
        ad1.addLast(35);
        int removed2 = (int) ad1.removeLast();
        passed = removed2 == 35 && passed;
        passed = ((int) ad1.get(0) == 30) && passed;
        testPass(passed);
    }

    public static void main(String[] args) {
        testEmpty();
        testAddResize();
        testRemoveResize();
    }
}
