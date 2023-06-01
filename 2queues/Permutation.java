/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Permutation {
    public static void main(String[] args) {
        int k = Integer.parseInt(args[0]);

        RandomizedQueue<String> q = new RandomizedQueue<>();

        for (int i = 1; i <= k; i++) {
            String input = StdIn.readString();
            q.enqueue(input);
        }

        for (String s : q) {
            StdOut.println(s);
        }
    }
}
