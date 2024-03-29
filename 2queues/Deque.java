/* *****************************************************************************
 *  Name:              Ada Lovelace
 *  Coursera User ID:  123456
 *  Last modified:     October 16, 1842
 **************************************************************************** */

import java.util.Iterator;

public class Deque<Item> implements Iterable<Item> {


    private class Node {
        private Item item;
        private Node next;
        private Node prev;

        public Node(Item item, Node next, Node prev) {
            this.item = item;
            this.next = next;
            this.prev = prev;
        }

    }

    private Node first;
    private Node last;
    private int size;

    private class DequeIterator implements Iterator<Item> {
        private Node cur;

        public DequeIterator() {
            cur = first;
        }

        public boolean hasNext() {
            return cur != null;
        }

        public Item next() {
            if (!hasNext()) {
                throw new java.util.NoSuchElementException();
            }

            Item res = cur.item;
            cur = cur.next;
            return res;
        }

        public void remove() {
            throw new UnsupportedOperationException("remove");
        }
    }

    // API -------------------------
    // construct an empty deque
    public Deque() {
        first = null;
        last = null;
        size = 0;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return first == null;
    }

    // return the number of items on the deque
    public int size() {
        return size;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }

        Node newNode = new Node(item, first, null);
        if (first != null) {
            first.prev = newNode;
        }
        first = newNode;
        if (last == null) {
            last = first;
        }
        size++;
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }

        Node newNode = new Node(item, null, last);
        if (last != null) {
            last.next = newNode;
        }
        last = newNode;
        if (first == null) {
            first = last;
        }
        size++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (first == null) {
            throw new java.util.NoSuchElementException();
        }

        Item res = first.item;
        first = first.next;
        if (first == null) {
            last = null;
        }
        else {
            first.prev = null;
        }

        size--;
        return res;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (last == null) {
            throw new java.util.NoSuchElementException();
        }

        Item res = last.item;
        last = last.prev;
        if (last == null) {
            first = null;
        }
        else {
            last.next = null;
        }
        size--;
        return res;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    // unit testing (required)
    public static void main(String[] args) {
        Deque<Integer> deque = new Deque<>();

        // deque.addFirst("a");
        // deque.addFirst("b");
        // deque.addFirst("c");
        // deque.addLast("d");
        // deque.addLast("e");
        // deque.addLast("f");

        deque.addFirst(1);
        deque.print();
        deque.addFirst(2);
        deque.print();
        deque.addLast(3);
        deque.print();
        deque.addFirst(4);
        deque.print();
        deque.removeLast();
        deque.print();

        // System.out.println(deque.size());

    }

    private void print() {
        Iterator<Item> iter = iterator();
        System.out.print("[");
        while (iter.hasNext()) {
            System.out.print(iter.next());
            if (iter.hasNext()) {
                System.out.print(", ");
            }
        }
        System.out.println("]");

    }

}
