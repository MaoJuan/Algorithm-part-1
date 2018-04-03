import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class Permutation {
    public static void main(String[] args) {
        RandomizedQueue<String> queue = new RandomizedQueue<String>();
        int k = Integer.parseInt(args[0]);
        int n=1;

        while (!StdIn.isEmpty()) {
            String item = StdIn.readString();
            if(n<=k)
                queue.enqueue(item);
            else if (StdRandom.uniform(n) < k) {
                String s = queue.dequeue();
                queue.enqueue(item);
            }
            n++;
            }
        for (String s : queue)
            StdOut.println(s);
    }
}