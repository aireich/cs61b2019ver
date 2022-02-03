import  org.junit.Test;
import static org.junit.Assert.*;
public class BSTest {

    @Test
    public void testAverageDepth() {
        BST<Integer> bst1 = new BST<Integer>();
        bst1.add(1);
        assertEquals(0.0, bst1.averageDepth(), 0.00001);
        bst1.add(2);
        bst1.add(3);
        assertEquals(1.0, bst1.averageDepth(), 0.001);
        bst1.add(4);
        bst1.add(5);
        bst1.add(6);
        assertEquals(2.5, bst1.averageDepth(), 0.00001);

        BST<Integer> bst2 = new BST<Integer>();
        bst2.add(5);
        bst2.add(3);
        bst2.add(7);
        bst2.add(2);
        assertEquals(1.0, bst2.averageDepth(), 0.00001);

        BST<Integer> bst3 = new BST<Integer>();
        bst3.add(5);
        bst3.add(3);
        bst3.add(7);
        bst3.add(2);
        bst3.add(4);
        bst3.add(6);
        bst3.add(8);
        assertEquals(1.428, bst3.averageDepth(), 0.001);
        bst3.add(1);
        assertEquals(1.625, bst3.averageDepth(), 0.001);

        BST<String> bst4 = new BST<String>();
        bst4.add("k");
        bst4.add("e");
        bst4.add("v");
        bst4.add("b");
        bst4.add("g");
        bst4.add("p");
        bst4.add("y");
        bst4.add("a");
        bst4.add("d");
        bst4.add("f");
        bst4.add("j");
        bst4.add("r");
        bst4.add("z");
        bst4.add("s");
        assertEquals(2.28, bst4.averageDepth(), 0.01);
    }
}
