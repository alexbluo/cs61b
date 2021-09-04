package timingtest;
import edu.princeton.cs.algs4.Stopwatch;

/**
 * Created by hug.
 */
public class TimeSLList {
    private static void printTimingTable(AList<Integer> Ns, AList<Double> times, AList<Integer> opCounts) {
        System.out.printf("%12s %12s %12s %12s\n", "N", "time (s)", "# ops", "microsec/op");
        System.out.printf("------------------------------------------------------------\n");
        for (int i = 0; i < Ns.size(); i += 1) {
            int N = Ns.get(i);
            double time = times.get(i);
            int opCount = opCounts.get(i);
            double timePerOp = time / opCount * 1e6;
            System.out.printf("%12d %12.2f %12d %12.2f\n", N, time, opCount, timePerOp);
        }
    }

    public static void main(String[] args) {
        timeGetLast();
    }

    public static void timeGetLast() {
        // TODO: YOUR CODE HERE
        AList<Integer> Ns = new AList<Integer>();
        Ns.addLast(8000);
        Ns.addLast(16000);
        Ns.addLast(32000);
        Ns.addLast(64000);
        Ns.addLast(128000);
        AList<Integer> ops = new AList<Integer>();
        ops.addLast(10000);
        ops.addLast(10000);
        ops.addLast(10000);
        ops.addLast(10000);
        ops.addLast(10000);
        AList<Double> times = new AList<Double>();

        SLList<Integer> fill = new SLList<Integer>();
        for (int n = 0; n < 8000; n++) {
            fill.addLast(1);
        }
        Stopwatch sw = new Stopwatch();
        for (int m = 0; m < 10000; m++) {
            fill.getLast();
        }
        double time = sw.elapsedTime();
        times.addLast(time);

        fill = new SLList<Integer>();
        for (int n = 0; n < 16000; n++) {
            fill.addLast(1);
        }
        sw = new Stopwatch();
        for (int m = 0; m < 10000; m++) {
            fill.getLast();
        }
        time = sw.elapsedTime();
        times.addLast(time);

        fill = new SLList<Integer>();
        for (int n = 0; n < 32000; n++) {
            fill.addLast(1);
        }
        sw = new Stopwatch();
        for (int m = 0; m < 10000; m++) {
            fill.getLast();
        }
        time = sw.elapsedTime();
        times.addLast(time);

        fill = new SLList<Integer>();
        for (int n = 0; n < 64000; n++) {
            fill.addLast(1);
        }
        sw = new Stopwatch();
        for (int m = 0; m < 10000; m++) {
            fill.getLast();
        }
        time = sw.elapsedTime();
        times.addLast(time);

        fill = new SLList<Integer>();
        for (int n = 0; n < 128000; n++) {
            fill.addLast(1);
        }
        sw = new Stopwatch();
        for (int m = 0; m < 10000; m++) {
            fill.getLast();
        }
        time = sw.elapsedTime();
        times.addLast(time);
        printTimingTable(Ns, times, ops);
    }

}
