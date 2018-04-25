import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;

public class BruteCollinearPoints {

    private int linenum=0;
    private ArrayList<LineSegment> al=new ArrayList<LineSegment>();

    public BruteCollinearPoints(Point[] points)    // finds all line segments containing 4 points
    {
        if(points==null)throw new IllegalArgumentException("constructor is null");
        int len= points.length;
        Point[] copy=new Point[len];
        for(int i=0;i<len;i++)
            copy[i]=points[i];
        Arrays.sort(copy);

        for(int i=0;i<len;i++){
            if(copy[i]==null)throw new IllegalArgumentException("point is null");

        }

        for(int i=0;i<len-1;i++)
            for(int j=i+1;j<len;j++)
            {
                if(copy[i].compareTo(copy[j])==0)throw new IllegalArgumentException("equal");
            }


        for(int i=0;i<len-3;i++)
            for(int j=i+1;j<len-2;j++)
                for (int m = j + 1; m < len-1; m++)
                    for (int n = m + 1; n < len; n++) {

                        if (copy[i].slopeOrder().compare(copy[j], copy[m]) == 0 && copy[i].slopeOrder().compare(copy[j], copy[n]) == 0) {
                            al.add( new LineSegment(copy[i], copy[n]));
                            numberOfSegments();
                        }
                    }
    }

    public int numberOfSegments()        // the number of line segments
    {

        return linenum++;
    }

    public LineSegment[] segments()                // the line segments
    {
        LineSegment[] array_ls=new LineSegment[linenum];

        for(int i=0;i<linenum;i++){
            array_ls[i]=
                    al.get(i);
        }
        return array_ls;
    }

    public static void main(String[] args) {

        // read the n points from a file
        In in = new In("./collinear/equidistant.txt");
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
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }

}