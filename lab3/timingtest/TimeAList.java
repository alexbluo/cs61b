package timingtest;
import edu.princeton.cs.algs4.Stopwatch;

/**
 * Created by hug.
 */
public class TimeAList {
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
        timeAListConstruction();
    }

    public static void timeAListConstruction() {
        // TODO: YOUR CODE HERE
        AList<Integer> Ns = new AList<Integer>();
        AList<Double> times = new AList<Double>();
        AList<Integer> fill = new AList<Integer>();

        Ns.addLast(8000);
        Stopwatch sw = new Stopwatch();
        for (int i = 0; i < 8000; i++) {
            fill.addLast(1);
        }
        double time = sw.elapsedTime();
        times.addLast(time);

        Ns.addLast(16000);
        sw = new Stopwatch();
        for (int i = 0; i < 16000; i++) {
            fill.addLast(1);
        }
        time = sw.elapsedTime();
        times.addLast(time);

        Ns.addLast(32000);
        sw = new Stopwatch();
        for (int i = 0; i < 32000; i++) {
            fill.addLast(1);
        }
        time = sw.elapsedTime();
        times.addLast(time);

        Ns.addLast(64000);
        sw = new Stopwatch();
        for (int i = 0; i < 64000; i++) {
            fill.addLast(1);
        }
        time = sw.elapsedTime();
        times.addLast(time);

        Ns.addLast(128000);
        sw = new Stopwatch();
        for (int i = 0; i < 128000; i++) {
            fill.addLast(1);
        }
        time = sw.elapsedTime();
        times.addLast(time);

        printTimingTable(Ns, times, Ns);
    }
}
