/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */


import edu.princeton.cs.algs4.In;

import java.util.ArrayList;
import java.util.List;

public class BruteCollinearPoints {
    private List<LineSegment> segments;

    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {
        // Throw an IllegalArgumentException if the argument to the constructor is null
        if (points == null) {
            throw new IllegalArgumentException("points == null");
        }

        segments = new ArrayList<>();

        for (int i1 = 0; i1 < points.length - 3; i1++) {
            if (points[i1] == null) { // if any point in the array is null
                throw new IllegalArgumentException("point == null");
            }

            for (int i2 = i1 + 1; i2 < points.length - 2; i2++) {
                if (points[i2]
                        == null) { // or if the argument to the constructor contains a repeated point.
                    throw new IllegalArgumentException("point == null");
                }
                if (points[i1].compareTo(points[i2]) == 0) {
                    throw new IllegalArgumentException("repeated point");
                }


                double slope12 = points[i1].slopeTo(points[i2]);

                for (int i3 = i2 + 1; i3 < points.length - 1; i3++) {
                    if (points[i3] == null) {
                        throw new IllegalArgumentException("point == null");
                    }
                    if (points[i1].compareTo(points[i3]) == 0
                            || points[i2].compareTo(points[i3]) == 0) {
                        throw new IllegalArgumentException("repeated point");
                    }


                    double slope23 = points[i2].slopeTo(points[i3]);

                    // compare first three points for collinear
                    if (slope12 != slope23) {
                        continue;
                    }

                    for (int i4 = i3 + 1; i4 < points.length; i4++) {
                        if (points[i4] == null) {
                            throw new IllegalArgumentException("point == null");
                        }
                        if (points[i1].compareTo(points[i4]) == 0
                                || points[i2].compareTo(points[i4]) == 0
                                || points[i3].compareTo(points[i4]) == 0) {
                            throw new IllegalArgumentException("repeated point");
                        }

                        double slope34 = points[i3].slopeTo(points[i4]);
                        if (slope23 != slope34) {
                            continue;
                        }

                        // System.out.println(
                        //         "Found slope:" + points[i1] + " " + points[i2] + " " + points[i3]
                        //                 + " " + points[i4]);

                        // find min and max - edge points
                        Point pointMin = points[i1], pointMax = points[i1];
                        if (points[i2].compareTo(pointMin) < 0) {
                            pointMin = points[i2];
                        }
                        if (points[i2].compareTo(pointMax) > 0) {
                            pointMax = points[i2];
                        }
                        if (points[i3].compareTo(pointMin) < 0) {
                            pointMin = points[i3];
                        }
                        if (points[i3].compareTo(pointMax) > 0) {
                            pointMax = points[i3];
                        }
                        if (points[i4].compareTo(pointMin) < 0) {
                            pointMin = points[i4];
                        }
                        if (points[i4].compareTo(pointMax) > 0) {
                            pointMax = points[i4];
                        }

                        segments.add(new LineSegment(pointMin, pointMax));

                    }
                }
            }
        }


    }

    // the number of line segments
    public int numberOfSegments() {
        return segments.size();
    }

    /* The method segments() should include each line segment containing 4 points exactly once.
    If 4 points appear on a line segment in the order p→q→r→s, then you should include either the line segment p→s or s→p (but not both) and you should not
    include subsegments such as p→r or q→r. For simplicity, we will not supply any input to BruteCollinearPoints that has 5 or more collinear points.


    Performance requirement. The order of growth of the running time of your program should be n4 in the worst case and it should use space proportional to n plus
    the number of line segments returned.
    */
    // the line segments
    public LineSegment[] segments() {
        return segments.toArray(new LineSegment[0]);
    }


    public static void main(String[] args) {
        In in = new In(args[0]);

        List<Point> points = new ArrayList<Point>();

        int lines = in.readInt();
        for (int i = 0; i < lines; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points.add(new Point(x, y));
        }
        in.close();

        BruteCollinearPoints brute = new BruteCollinearPoints(points.toArray(new Point[0]));

        System.out.println("Segments found: " + brute.numberOfSegments());

        LineSegment[] segments = brute.segments();

        for (int i = 0; i < segments.length; i++) {
            System.out.println(segments[i]);
        }


    }
}
