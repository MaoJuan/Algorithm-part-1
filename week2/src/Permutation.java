import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class Permutation {
    public static void main(String[] args) {
        RandomizedQueue<String> queue = new RandomizedQueue<String>();
        int k = Integer.parseInt(args[0]);
        int n=0;

        while (!StdIn.isEmpty()) {
            String item = StdIn.readString();
                queue.enqueue(item);
                n++;
        }
            for(int i=0;i<k;i++){
            StdOut.println(queue.dequeue());
            }
    }




}
