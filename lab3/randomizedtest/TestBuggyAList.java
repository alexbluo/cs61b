package randomizedtest;

import edu.princeton.cs.algs4.StdRandom;
import org.junit.Test;
import timingtest.AList;

import static org.junit.Assert.*;

/**
 * Created by hug.
 */
public class TestBuggyAList {
  // YOUR TESTS HERE

    @Test
    public void testThreeAndThreeRemove() {

        AListNoResizing<Integer> noBug = new AListNoResizing<Integer>();
        BuggyAList<Integer> bug = new BuggyAList<Integer>();
        for (int i = 0; i < 3; i++) {
            noBug.addLast(i);
            bug.addLast(i);
        }

        assertEquals(bug.removeLast(), noBug.removeLast());
        assertEquals(bug.removeLast(), noBug.removeLast());
        assertEquals(bug.removeLast(), noBug.removeLast());
    }
    @Test
    public void randomizedTest() {
        AListNoResizing<Integer> L = new AListNoResizing<>();
        BuggyAList<Integer> LOL = new BuggyAList<>();
        int N = 5000;
        for (int i = 0; i < N; i += 1) {
            int operationNumber = StdRandom.uniform(0, 4);
            if (operationNumber == 0) {
                // addLast
                int randVal = StdRandom.uniform(0, 100);
                L.addLast(randVal);
                LOL.addLast(randVal);
                System.out.println("addLast(" + randVal + ")");
            } else if (operationNumber == 1) {
                // size
                int size = L.size();
                System.out.println("L.size(): " + size);
                System.out.println("LOL.size(): " + LOL.size());
            } else if (operationNumber == 2 && L.size() > 0) {
                System.out.println("L.getLast(): " + L.getLast());
                System.out.println("LOL.getLast(): " + LOL.getLast());
            }  else if (operationNumber == 3 && L.size() > 0) {
                System.out.println("L.removeLast(): " + L.removeLast());
                System.out.println("LOL.removeLast(): " + LOL.removeLast());
            }
        }
    }
}
