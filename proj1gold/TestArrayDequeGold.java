import static org.junit.Assert.*;
import org.junit.Test;
public class TestArrayDequeGold {

    /**Test for studentArrayDeque
     * @source StudentArrayDequeLauncher
     * */
    @Test
    public void testStudentArrayDeque() {
        StudentArrayDeque<Integer> studentDeque = new StudentArrayDeque<Integer>();
        ArrayDequeSolution<Integer> trueDeque = new ArrayDequeSolution<Integer>();
        String[] messages = new String[50];
        int operationCount = -1;
        String finalMessage = null;
        int currentPosition = 0;

        for (int i = 0; i < 50; i += 1) {

            double numberBetweenZeroAndOne = StdRandom.uniform();

            if (numberBetweenZeroAndOne < 0.5) {
                if (numberBetweenZeroAndOne <= 0.4) {
                    studentDeque.addLast(i);
                    trueDeque.addLast(i);
                    messages[i] = "addLast(" + i + ")";
                    operationCount += 1;
                    currentPosition = studentDeque.size() - 1;
                } else {
                    if (studentDeque.size() > 0) {
                        studentDeque.removeLast();
                        messages[i] = "removeLast(" + i + ")";
                        operationCount += 1;
                        currentPosition = studentDeque.size() - 1;
                    }
                    if (trueDeque.size() > 0) {
                        trueDeque.removeLast();
                        messages[i] = "removeLast(" + i + ")";
                    }
                }

            } else {
                if (numberBetweenZeroAndOne >= 0.6) {
                    studentDeque.addFirst(i);
                    trueDeque.addFirst(i);
                    messages[i] = "addFirst(" + i + ")";
                    operationCount += 1;
                    currentPosition = 0;
                } else {
                    if (studentDeque.size() > 0) {
                        studentDeque.removeFirst();
                        messages[i] = "removeFirst(" + i + ")";
                        operationCount += 1;
                        currentPosition = 0;
                    }
                    if (trueDeque.size() > 0) {
                        trueDeque.removeFirst();
                        messages[i] = "removeFirst(" + i + ")";
                    }
                }
            }
            if (studentDeque.size() != 0 && studentDeque.get(currentPosition) != trueDeque.get(currentPosition)) {
                finalMessage = messages[operationCount - 2] + "\n" + messages[operationCount - 1] +
                               "\n" + messages[operationCount];
            }
        }


        for (int i = 0; i < trueDeque.size() - 1; i += 1) {
            assertEquals(finalMessage, studentDeque.get(i), trueDeque.get(i));
        }
    }
}
