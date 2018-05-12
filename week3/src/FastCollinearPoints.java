import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
import java.util.ArrayList;
import java.util.Arrays;


public class FastCollinearPoints {
    private int num=0;
    private ArrayList<LineSegment> arr_ls=new ArrayList<LineSegment>();


    public FastCollinearPoints(Point[] points)     // finds all line segments containing 4 or more points
    {
        if(points==null)throw new IllegalArgumentException("constructor is null");
        int n=points.length;

        for(int i=0;i<n;i++){
            if(points[i]==null)
            throw new IllegalArgumentException("point is null");
        }

        for(int i=0;i<n-1;i++)
            for(int j=i+1;j<n;j++){
                if(points[i].compareTo(points[j])==0)
                    throw new IllegalArgumentException("points equal");
            }


        Point[] copy=new Point[n];
        Point[] other=new Point[n];//points except origin
        for(int i=0;i<n;i++)
            copy[i]=points[i];
        Arrays.sort(copy);



        for(int l=0;l<n;l++) {
            Point origin = copy[l];
            for (int i = l + 1; i < n; i++)
                other[i] = copy[i];
            Arrays.sort(other, l + 1, n, origin.slopeOrder());
            Arrays.sort(copy, 0, l, origin.slopeOrder());
            int m = l+1;

            while (m < n) {
                Double slope1 = origin.slopeTo(other[m]);
                m++;
                boolean flag = false;

                int head = 0;
                int tail = l - 1;
                while (head <= tail) {//二分查找l与其后面点组成线的斜率是否在前面计算过，若是，则跳过
                    int mid = (head + tail) / 2;
                    if (slope1 == copy[mid].slopeTo(origin)) {
                        flag = true;
                        break;
                    } else if (slope1 < copy[mid].slopeTo(origin))
                        tail=mid-1;
                    else head=mid+1;
                }

                if (flag) continue;
                int count = 0;
                while (m<n && slope1 == origin.slopeTo(other[m])) {
                    count++;
                    m++;
                }
                if (count >= 2){
                    numberOfSegments();
                    arr_ls.add(new LineSegment(origin,other[m-1]));
                }
            }
        }
    }


    public int numberOfSegments()        // the number of line segments
    {
        return num++;
    }

    public LineSegment[] segments()                // the line segments
    {
        LineSegment[] lss=new LineSegment[num];
        for(int i=0;i<num;i++){
            lss[i]=arr_ls.get(i);
        }
        return lss;
    }

    public static void main(String[] args) {

        // read the n points from a file
        In in = new In("./collinear/input100.txt");
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