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
import java.util.HashMap;
import java.util.List;

public class FastCollinearPoints {
    private List<LineSegment> segments;

    // finds all line segments containing 4 or more points
    public FastCollinearPoints(
            Point[] points) {

        HashMap<Point, Double> segmentsFound = new HashMap<>();

        // Corner cases. Throw an IllegalArgumentException if the argument to the constructor is null, if any point in the array is null, or if the argument to the constructor contains a repeated point.
        if (points == null) {
            throw new IllegalArgumentException("points == null");
        }

        Arrays.sort(points); // иначе если на одной прямой будут различаться на + и -

        segments = new ArrayList<>();
        for (int i1 = 0; i1 < points.length - 3; i1++) {
            Point p = points[i1];
            if (p == null) { // if any point in the array is null
                throw new IllegalArgumentException("point == null");
            }

            // набираем оставшиеся точки
            List<Point> q = new ArrayList<>();
            for (int i2 = i1 + 1; i2 < points.length; i2++) {
                if (points[i2]
                        == null) { // or if the argument to the constructor contains a repeated point.
                    throw new IllegalArgumentException("point == null");
                }
                if (p.compareTo(points[i2]) == 0) {
                    throw new IllegalArgumentException("repeated point");
                }

                q.add(points[i2]);
            }
            // сортируем
            q.sort(p.slopeOrder());

            // ищем с одинаковым наклоном
            List<Point> same = new ArrayList<>();

            double prevSlope = 0;

            for (int i = 0; i < q.size(); i++) {
                if (i == 0 || p.slopeTo(q.get(i)) != prevSlope) {
                    addSegment(same, segmentsFound, prevSlope);

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
            addSegment(same, segmentsFound, prevSlope);
        }

    }

    private void addSegment(List<Point> same, HashMap<Point, Double> segmentsFound,
                            double prevSlope) {
        if (same.size() < 4) {
            return;
        }

        Collections.sort(same);
        Point lastPoint = same.get(same.size() - 1);

        if (segmentsFound.containsKey(lastPoint) && segmentsFound.get(lastPoint) == prevSlope) {
            return;
        }

        segments.add(new LineSegment(same.get(0), lastPoint));
        segmentsFound.put(lastPoint, prevSlope);
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

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
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
