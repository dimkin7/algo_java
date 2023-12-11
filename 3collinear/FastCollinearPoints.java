/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class FastCollinearPoints {
    private List<LineSegment> segments;

    // finds all line segments containing 4 or more points
    public FastCollinearPoints(
            Point[] points) {

        // Corner cases. Throw an IllegalArgumentException if the argument to the constructor is null, if any point in the array is null, or if the argument to the constructor contains a repeated point.
        if (points == null) {
            throw new IllegalArgumentException("points == null");
        }

        Point[] pointsSorted = new Point[points.length];

        for (int i = 0; i < points.length; i++) {
            if (points[i] == null) {
                throw new IllegalArgumentException("point == null");
            }
            pointsSorted[i] = points[i];
        }

        Arrays.sort(pointsSorted); // prepare data for speed

        segments = new ArrayList<>();
        for (int i1 = 0; i1 < pointsSorted.length; i1++) {
            Point p = pointsSorted[i1];
            if (p == null) { // if any point in the array is null
                throw new IllegalArgumentException("point == null");
            }

            // набираем оставшиеся точки
            List<Point> q = new ArrayList<>();
            for (int i2 = 0; i2 < pointsSorted.length; i2++) {
                if (i1 == i2) {
                    continue;
                }

                if (pointsSorted[i2]
                        == null) { // or if the argument to the constructor contains a repeated point.
                    throw new IllegalArgumentException("point == null");
                }
                if (p.compareTo(pointsSorted[i2]) == 0) {
                    throw new IllegalArgumentException("repeated point");
                }

                q.add(pointsSorted[i2]);
            }
            // сортируем
            q.sort(p.slopeOrder());

            // ищем с одинаковым наклоном
            List<Point> same = new ArrayList<>();

            double prevSlope = 0;

            for (int i = 0; i < q.size(); i++) {
                if (i == 0 || p.slopeTo(q.get(i)) != prevSlope) {
                    addSegment(same, p);

                    same.clear();
                    same.add(p);
                    same.add(q.get(i));
                    prevSlope = p.slopeTo(q.get(i));
                }
                else {
                    same.add(q.get(i));
                }
            }

            // and last loop
            addSegment(same, p);
        }

    }

    private void addSegment(List<Point> same, Point p) {
        if (same.size() < 4) {
            return;
        }

        Collections.sort(same);
        // добавляем только сегменты начинающиеся из начальной точки
        if (p != same.get(0)) {
            return;
        }

        Point lastPoint = same.get(same.size() - 1);
        segments.add(new LineSegment(same.get(0), lastPoint));
    }

    // the number of line segments
    public int numberOfSegments() {
        return segments.size();
    }

    // the line segments
    public LineSegment[] segments() {
        return segments.toArray(new LineSegment[0]);
    }

    public static void main(String[] args) {
        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // Point[] points = new Point[2];
        // points[0] = null;
        // points[1] = new Point(1, 2);

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            if (p != null) {
                p.draw();
            }
        }
        StdDraw.show();

        // print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
