import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import java.util.ArrayList;
import java.util.Iterator;


public class PointSET {
    private SET<Point2D> pointSet;
    private int num;

    public PointSET()                               // construct an empty set of points
    {
        pointSet=new SET<Point2D>();
    }

    public boolean isEmpty()                      // is the set empty?
    {
        return num==0;
    }

    public int size()                         // number of points in the set
    {
        return num;
    }

    public void insert(Point2D p)              // add the point to the set (if it is not already in the set)
    {
        if(p==null)throw new IllegalArgumentException("Point2D is null");
        if(!pointSet.contains(p))
        {
            pointSet.add(p);
            num++;
        }
    }

    public boolean contains(Point2D p)            // does the set contain point p?
    {
        return pointSet.contains(p);
    }


    public void draw()                         // draw all points to standard draw
    {
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius();
        for(Point2D p:pointSet)
            StdDraw.point(p.x(), p.y());
    }

    public Iterable<Point2D> range(RectHV rect)             // all points that are inside the rectangle (or on the boundary)
    {
        if(rect==null)throw new IllegalArgumentException("RECTHV is null");
        ArrayList<Point2D> pointsInRect=new ArrayList<>();

        Iterator<Point2D> i=pointSet.iterator();
        while(i.hasNext()) {
            Point2D nextP=i.next();
            if(rect.contains(nextP)) pointsInRect.add(nextP);
        }
        return pointsInRect;
    }


    public Point2D nearest(Point2D p)             // a nearest neighbor in the set to point p; null if the set is empty
    {
        if(p==null)throw new IllegalArgumentException("POINT2D is null");
        Iterator<Point2D> i=pointSet.iterator();
        if(!i.hasNext()) return null;

        Point2D minPoint=i.next();
        double min=p.distanceSquaredTo(minPoint);

        while(i.hasNext()){
            Point2D nextP=i.next();
            if(min > p.distanceSquaredTo(nextP))
            {
                min=p.distanceSquaredTo(nextP);
                minPoint=nextP;
            }
        }
        return minPoint;
    }

    public static void main(String[] args)                // unit testing of the methods (optional)
    {
       /* In in=new In("./kdtree/circle4.txt");
        PointSET ps=null;
        for(int i=0;i<4;i++)
        {
            Point2D p=new Point2D(in.readDouble(),in.readDouble());
            StdOut.println("x y "+p.x()+"   "+p.y()+"  i=  "+i);
            ps=new PointSET();
            ps.insert(p);
        }
        StdOut.println(ps.size()+"  ");
        StdOut.println(ps.isEmpty());
        Iterator<Point2D> i=ps.pointSet.iterator();
        while (i.hasNext()){
            Point2D p2=i.next();
            StdOut.println("( "+p2.x()+"  "+p2.y());
        }
        StdOut.println(ps.contains(new Point2D(0.5,1.0)));
        ps.draw();
    }
*/
        String filename ="./kdtree/circle4.txt";
        In in = new In(filename);

        StdDraw.enableDoubleBuffering();

        // initialize the data structures with N points from standard input
        PointSET brute = new PointSET();
        //KdTree kdtree = new KdTree();
        while (!in.isEmpty()) {
            double x = in.readDouble();
            double y = in.readDouble();
            Point2D p = new Point2D(x, y);
            //kdtree.insert(p);
            brute.insert(p);
            StdOut.println("sizeeeee   "+brute.size());
        }

        StdOut.println(brute.size()+"  ");
        brute.nearest(new Point2D(1.0,1.0));
        StdOut.println(brute.isEmpty());
        Iterator<Point2D> i=brute.pointSet.iterator();
        while (i.hasNext()){
            Point2D p2=i.next();
            StdOut.println("( "+p2.x()+"  "+p2.y());
        }

        StdOut.println(brute.contains(new Point2D(0.3,0.5)));
        double x0 = 0.0, y0 = 0.0;      // initial endpoint of rectangle
        double x1 = 0.0, y1 = 0.0;      // current location of mouse
        boolean isDragging = false;     // is the user dragging a rectangle

        // draw the points
        StdDraw.clear();
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.01);
        brute.draw();
        StdDraw.show();

        while (true) {

            // user starts to drag a rectangle
            if (StdDraw.isMousePressed() && !isDragging) {
                x0 = StdDraw.mouseX();
                y0 = StdDraw.mouseY();
                isDragging = true;
                continue;
            }

            // user is dragging a rectangle
            else if (StdDraw.isMousePressed() && isDragging) {
                x1 = StdDraw.mouseX();
                y1 = StdDraw.mouseY();
                continue;
            }

            // mouse no longer pressed
            else if (!StdDraw.isMousePressed() && isDragging) {
                isDragging = false;
            }


            RectHV rect = new RectHV(Math.min(x0, x1), Math.min(y0, y1),
                    Math.max(x0, x1), Math.max(y0, y1));
            // draw the points
            StdDraw.clear();
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.setPenRadius(0.01);
            brute.draw();

            // draw the rectangle
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.setPenRadius();
            rect.draw();

            // draw the range search results for brute-force data structure in red
            StdDraw.setPenRadius(0.03);
            StdDraw.setPenColor(StdDraw.RED);
            for (Point2D p : brute.range(rect))
                p.draw();

            StdDraw.show();
            StdDraw.pause(40);
        }
    }
}


