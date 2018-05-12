import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;


public class Deque<Item> implements Iterable<Item> {
    private int len;
    private Node first;
    private Node last;

    private class Node {
        private Item node;
        private Node pre;
        private Node next;
    }

    public Deque()                           // construct an empty deque
    {
        first = null;
        last = null;
        len = 0;
    }

    public boolean isEmpty()                 // is the deque empty?
    {
        return len==0;
    }

    public int size()                        // return the number of items on the deque
    {
        return len;
    }


    public void addFirst(Item item)          // add the item to the front
    {
        if (item == null) throw new java.lang.IllegalArgumentException("item==null");
        Node s = new Node();
        s.node = item;
        if (isEmpty()) {
            first = s;
            last = s;
        } else {
            s.next = first;
            first.pre = s;
            first = s;
        }
        len++;
    }

    public void addLast(Item item)           // add the item to the end
    {
        if (item == null) throw new java.lang.IllegalArgumentException("item==null");
        Node s = new Node();
        s.node = item;
        if (isEmpty()) {
            first = s;
            last = s;
        } else {
            last.next = s;
            s.pre = last;
            last = s;
        }
        len++;
    }

    public Item removeFirst()                // remove and return the item from the front
    {
        if (isEmpty()) throw new java.util.NoSuchElementException("deque is empty");
        Item tempt = first.node;
        if(len==1)
        {
            first=null;
            last=null;
        }else
        {
            first = first.next;
            first.pre=null;
        }
        len--;
        return tempt;
    }

    public Item removeLast()                // remove and return the item from the end
    {
        if (isEmpty()) throw new java.util.NoSuchElementException("deque is empty");
        Item tempt = last.node;
        if(len==1){
            first=null;
            last=null;
        } else {
            last = last.pre;
            last.next=null;
        }
        len--;
        return tempt;
    }

    public Iterator<Item> iterator()         // return an iterator over items in order from front to end
    {
        return new DequeIterator();
    }

    private class DequeIterator implements Iterator<Item> {
        private Node current = first;

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public Item next() {
            if (!hasNext()) throw new java.util.NoSuchElementException("current is last");
            Item i = current.node;
            current = current.next;
            return i;
        }

        @Override
        public void remove() {
            throw new java.lang.UnsupportedOperationException();
        }
    }

    public static void main(String[] args)   // unit testing (optional)
    {
        Deque d=new Deque();
        StdOut.println(d.isEmpty());
        d.addFirst(1);
        StdOut.println(d.isEmpty());
        d.addFirst(2);
        d.addLast(3);
        d.addLast(9);
        StdOut.println(d.len);
      //  for(int i=0;i<d.size();i++)
       //StdOut.print(d.removeLast());
        Iterator<String> ii=d.iterator();
        while (ii.hasNext())StdOut.print(ii.next());
    }

}