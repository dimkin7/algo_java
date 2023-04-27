// Performance requirements.  The constructor must take time proportional to n2; all instance methods must take constant time plus a constant number
// of calls to union() and find().

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private boolean[][] grid;
    private int n;
    private int topSite;
    private int bottomSite;
    private WeightedQuickUnionUF uf;
    private int numberOfOpenSites;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        // Throw an IllegalArgumentException in the constructor if n ≤ 0.
        if (n <= 0) {
            throw new IllegalArgumentException("n <= 0");
        }
        grid = new boolean[n][n];
        this.n = n;

        // connect top row to top virtual site, bottom to bottomSite
        // The elements are named 0 through n–1.
        uf = new WeightedQuickUnionUF(n * n + 2);
        topSite = n * n; // for example if n = 4 - topSite = 16
        bottomSite = n * n + 1; // bottomSite = 17
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (row < 1 || row > n || col < 1 || col > n) {
            throw new IllegalArgumentException(
                    "row and column indices are integers between 1 and n");
        }

        if (isOpen(row, col)) {
            return;
        }

        grid[row - 1][col - 1] = true;
        numberOfOpenSites++;

        // connect to open neighbours
        if (row + 1 <= n && isOpen(row + 1, col))
            uf.union(getId(row, col), getId(row + 1, col));
        if (col + 1 <= n && isOpen(row, col + 1))
            uf.union(getId(row, col), getId(row, col + 1));
        if (row - 1 >= 1 && isOpen(row - 1, col))
            uf.union(getId(row, col), getId(row - 1, col));
        if (col - 1 >= 1 && isOpen(row, col - 1))
            uf.union(getId(row, col), getId(row, col - 1));

        // connect to virtual top and bottom
        if (row == 1)
            uf.union(getId(row, col), topSite);
        if (row == n && isFull(row, col)) // otherwise isFull will work wrong for percolated system
            uf.union(getId(row, col), bottomSite);
    }


    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (row < 1 || row > n || col < 1 || col > n) {
            throw new IllegalArgumentException(
                    "row and column indices are integers between 1 and n");
        }
        return grid[row - 1][col - 1];
    }


    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (row < 1 || row > n || col < 1 || col > n) {
            throw new IllegalArgumentException(
                    "row and column indices are integers between 1 and n");
        }
        // A full site is an open site that can be connected to an open site in the top row
        boolean isFull = false;
        if (isOpen(row, col) && uf.find(topSite) == uf.find(getId(row, col))) {
            isFull = true;
        }
        return isFull;
    }

    // get id of cell
    private int getId(int row, int col) {
        return n * (row - 1) + col - 1;
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return numberOfOpenSites;
    }


    // does the system percolate?
    public boolean percolates() {
        return uf.find(topSite) == uf.find(bottomSite);
    }

    // test client (optional)
    public static void main(String[] args) {
        Percolation per = new Percolation(4);
        per.open(1, 2);
        per.open(2, 2);
        per.open(3, 2);
        System.out.println(per.percolates());
        per.open(4, 2);
        System.out.println(per.percolates());
    }
}
