// specification: https://coursera.cs.princeton.edu/algs4/assignments/percolation/specification.php
// tests: https://github.com/DanielOchoa/percolation-problem

// Performance requirements.  The constructor must take time proportional to n2; all instance methods must take constant time plus a constant number
// of calls to union() and find().
// hints: https://stackoverflow.com/questions/61396690/how-to-handle-the-backwash-problem-in-percolation-without-creating-an-extra-wuf/73572415#73572415

// FAQ with test cases - https://coursera.cs.princeton.edu/algs4/assignments/percolation/faq.php

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    // 001 = 0x1 - open,   010 = 0x2 - connected to top,   100 = 0x4 - connected to bottom
    private static final byte OPENED = 0x1;
    private static final byte TO_TOP = 0x2;
    private static final byte TO_BOTTOM = 0x4;

    private byte[][] grid;

    private int n;
    // private int topVirtualSite;
    // private int bottomVirtualSite;
    private WeightedQuickUnionUF uf;
    private int numberOfOpenSites;
    private boolean isPercolate;

    private class RowCol {
        public int row;
        public int col;

        RowCol(int row, int col) {
            this.row = row;
            this.col = col;
        }

        public String toString() {
            return row + ", " + col;
        }
    }

    private RowCol getRowColById(int id) {
        int row = id / n + 1;
        int col = id - ((row - 1) * n) + 1;
        return new RowCol(row, col);
    }

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        // Throw an IllegalArgumentException in the constructor if n ≤ 0.
        if (n <= 0) {
            throw new IllegalArgumentException("n <= 0");
        }
        grid = new byte[n][n];
        this.n = n;

        // connect top row to top virtual site, bottom to bottomSite
        // The elements are named 0 through n–1.
        uf = new WeightedQuickUnionUF(n * n + 2);
        // topVirtualSite = n * n; // for example if n = 4 - topVirtualSite = 16
        // bottomVirtualSite = n * n + 1; // 17
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

        int neighbour;
        int root;
        RowCol rootRowCol;
        RowCol neighbourRowCol;
        byte status = OPENED;

        numberOfOpenSites++;

        // connect to open neighbours
        for (int i = 1; i <= 4; i++) {
            if (1 == i) neighbourRowCol = new RowCol(row + 1, col);
            else if (2 == i) neighbourRowCol = new RowCol(row, col + 1);
            else if (3 == i) neighbourRowCol = new RowCol(row - 1, col);
            else neighbourRowCol = new RowCol(row, col - 1);

            // check out of bounds
            if (neighbourRowCol.row < 1 || neighbourRowCol.row > n || neighbourRowCol.col < 1
                    || neighbourRowCol.col > n) {
                continue;
            }

            if (!isOpen(neighbourRowCol.row, neighbourRowCol.col)) {
                continue;
            }

            neighbour = getId(neighbourRowCol.row, neighbourRowCol.col);
            root = uf.find(neighbour);
            rootRowCol = getRowColById(root);
            // join status with root status
            status |= grid[rootRowCol.row - 1][rootRowCol.col - 1];
            uf.union(getId(row, col), neighbour);
        }

        // connect to virtual top
        if (row == 1) {
            // uf.union(getId(row, col), topVirtualSite);
            status |= TO_TOP;
        }
        if (row == n) {
            // uf.union(getId(row, col), topVirtualSite);
            status |= TO_BOTTOM;
        }

        grid[row - 1][col - 1] = status;

        // update root status
        root = uf.find(getId(row, col));
        rootRowCol = getRowColById(root);
        grid[rootRowCol.row - 1][rootRowCol.col - 1] = status;

        if ((status & TO_TOP) != 0 && (status & TO_BOTTOM) != 0) {
            isPercolate = true;
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (row < 1 || row > n || col < 1 || col > n) {
            throw new IllegalArgumentException(
                    "row and column indices are integers between 1 and n");
        }
        return (grid[row - 1][col - 1] & OPENED) != 0;
    }


    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (row < 1 || row > n || col < 1 || col > n) {
            throw new IllegalArgumentException(
                    "row and column indices are integers between 1 and n");
        }

        // A full site is an open site that can be connected to an open site in the top row
        if (!isOpen(row, col)) {
            return false;
        }

        int root = uf.find(getId(row, col));
        RowCol rootRowCol = getRowColById(root);

        return (grid[rootRowCol.row - 1][rootRowCol.col - 1] & TO_TOP) != 0;
    }

    // get id of cell
    private int getId(int row, int col) {
        return n * (row - 1) + col - 1;
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return numberOfOpenSites;
    }


    // does the system isPercolate?
    public boolean percolates() {
        return isPercolate;
    }

    // test client (optional)
    public static void main(String[] args) {
        int n = 6;
        Percolation per = new Percolation(n);
        // for (int i = 0; i < n * n; i++) {
        //     System.out.println(per.getRowColById(i).toString());
        // }

        per.open(1, 6);
        per.open(2, 6);
        per.open(3, 6);
        per.open(4, 6);
        per.open(5, 6);
        per.open(5, 5);
        per.open(4, 4);
        System.out.println(per.isFull(4, 4));
        // per.open(4, 2);
        System.out.println(per.percolates());
    }
}
