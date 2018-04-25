import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import java.util.Iterator;


public class RandomizedQueue<Item> implements Iterable<Item> {
    private int cur_len;
    private Item[] queue;

    public RandomizedQueue()                 // construct an empty randomized queue
    {
        queue = (Item[]) new Object[1];
        cur_len = 0;
    }

    public boolean isEmpty()                 // is the randomized queue empty?
    {
        return cur_len == 0;
    }

    public int size()                        // return the number of items on the randomized queue
    {
        return cur_len;
    }

    private void resize(int max) {
        if (max <= 0) throw new java.lang.IllegalArgumentException("max size <= 0");
        Item[] new_queue = (Item[])new Object[max];
        for (int i = 0; i < cur_len; i++) {
            new_queue[i] = queue[i];
        }
        queue = new_queue;
    }


    public void enqueue(Item item)           // add the item
    {
        if (item == null) throw new java.lang.IllegalArgumentException("RandomizedQueue is empty");
        if (cur_len == queue.length) resize(cur_len * 2);
        queue[cur_len] = item;
        cur_len++;
    }


    public Item dequeue()                    // remove and return a random item
    {
        if (isEmpty()) throw new java.util.NoSuchElementException("RandomizedQueue is empty");
        int j = StdRandom.uniform(cur_len);
        Item tempt = queue[j];
        queue[j] = queue[cur_len - 1];
        queue[cur_len-1] = null;
        cur_len--;
        if (cur_len > 0 && cur_len == queue.length / 4)
            resize(queue.length / 2);
        return tempt;
    }

    public Item sample()                     // return a random item (but do not remove it)
    {
        if (cur_len == 0) throw new java.util.NoSuchElementException("arrary is empty");
        return queue[StdRandom.uniform(cur_len)];
    }

    public Iterator<Item> iterator()         // return an independent iterator over items in random order
    {
        return new RQIterator();
    }

    private class RQIterator implements Iterator<Item> {
        private Item[] r;
        private int index = 0;

        public RQIterator() {
            r = (Item[]) new Object[cur_len];
            for (int i = 0; i < cur_len; i++)
                r[i] = queue[i];
            StdRandom.shuffle(r);
        }

        @Override
        public boolean hasNext() {
            return index < cur_len;
        }

        @Override
        public Item next() {
            if (!hasNext()) throw new java.util.NoSuchElementException();
            Item item = r[index];
            index++;
            return item;
        }

        @Override
        public void remove() {
            throw new java.lang.UnsupportedOperationException();

        }
    }


    public static void main(String[] args)   // unit testing (optional)
    {
        RandomizedQueue<String> TestQueue = new RandomizedQueue<String>();
        TestQueue.enqueue("a SB");
        TestQueue.enqueue("hear about ");
        TestQueue.enqueue("you are");
        Iterator<String> it = TestQueue.iterator();
        while (it.hasNext()) {
            StdOut.println(it.next());
        }
    }
}

