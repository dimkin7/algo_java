/* *****************************************************************************
 *  Name:              Ada Lovelace
 *  Coursera User ID:  123456
 *  Last modified:     October 16, 1842
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private Item[] array;
    private int size;

    private void changeArrayLength(int newLength) {
        Item[] newArray = (Item[]) new Object[newLength];
        for (int i = 0; i < size; i++) {
            newArray[i] = array[i];
        }
        array = newArray;
    }

    private class RandomizedQueueIterator implements Iterator<Item> {
        int[] indexArray;
        int cur;

        public RandomizedQueueIterator() {
            indexArray = new int[size];
            for (int i = 0; i < indexArray.length; i++) {
                indexArray[i] = i;
            }
            StdRandom.shuffle(indexArray, 0, indexArray.length);
            cur = 0;
        }

        public boolean hasNext() {
            return cur < indexArray.length;
        }

        public Item next() {
            if (!hasNext()) {
                throw new java.util.NoSuchElementException();
            }

            int index = indexArray[cur++];
            return array[index];
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    // --- API ---

    // construct an empty randomized queue
    public RandomizedQueue() {
        array = (Item[]) new Object[1];
        size = 0;
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return size() == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return size;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }

        // double size if not enough
        if (size == array.length) {
            changeArrayLength(array.length * 2);
        }
        array[size++] = item;
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty()) {
            throw new java.util.NoSuchElementException();
        }

        StdRandom.shuffle(array, 0, size);
        Item res = array[size - 1];
        array[size - 1] = null;
        size--;

        // not allow loitering
        if (size < array.length / 4) {
            changeArrayLength(array.length / 2);
        }

        return res;
    }


    // return a random item (but do not remove it)
    public Item sample() {
        if (isEmpty()) {
            throw new java.util.NoSuchElementException();
        }

        int index = StdRandom.uniformInt(size);
        return array[index];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }

    // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueue<String> rq = new RandomizedQueue<>();
        rq.enqueue("RPDZITTIGJ");
        rq.dequeue();

        // q.enqueue("A");
        // q.enqueue("B");
        // q.enqueue("C");
        // q.enqueue("D");
        // q.enqueue("e");
        // q.enqueue("f");

        for (String s : rq) {
            StdOut.println(s);
        }

    }
}
