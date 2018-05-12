import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
import java.util.ArrayList;
import java.util.Iterator;


public class KdTree {

    private static class Node{
        private Node lchild;
        private Node rchild;
        private boolean isVertical;
        private Point2D p;

        public Node(Point2D p,Node lchild,Node rchild,boolean isVertical){
            this.p=p;
            this.lchild=lchild;
            this.rchild=rchild;
            this.isVertical=isVertical;
        }
    }

    private Node root;
    private int num;
    private final static RectHV CONTAINER=new RectHV(0,0,1,1);

    public KdTree()                               // construct an empty set of points
    {
        this.root=null;
        this.num=0;
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
        if(p==null)throw new IllegalArgumentException("point2D is null");
        this.root=insert(this.root,p,true);
    }

    private Node insert(Node node,Point2D now,boolean isVertical)
    {
        if(node == null)//inserting operation
        {
            num++;
            return new Node(now,null,null, isVertical);
        }

        if((node.p.x()==now.x()) && (node.p.y()==now.y()))return node;

        if( ( node.isVertical && now.x() < node.p.x()) || (!node.isVertical && now.y()<node.p.y()))
            node.lchild=insert(node.lchild,now,!node.isVertical);
        else node.rchild=insert(node.rchild,now,!node.isVertical);

        return node;
    }


    public boolean contains(Point2D p)            // does the set contain point p?
    {
        if(p==null)throw new IllegalArgumentException("point2d is null");
        return contains(root,p);
    }

    private boolean contains(Node node,Point2D now){
        if(node==null) return false;// search all subtree and don't find

        if(node.p.x()==now.x() && node.p.y()==now.y())
            return true;

        if((node.isVertical && now.x()<node.p.x()) || (!node.isVertical && now.y()<node.p.y()))
            return contains(node.lchild,now);
        else return contains(node.rchild,now);
    }



    public void draw()                         // draw all points to standard draw
    {
        draw(root,CONTAINER);
    }

    private static void draw(Node node,RectHV rect)
    {
        if(node==null)return;

        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.01);
        new Point2D(node.p.x(),node.p.y()).draw();
        StdDraw.setPenRadius();

        if(node.isVertical){

            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.line(node.p.x(),rect.ymin(),node.p.x(),rect.ymax());
        }else{

            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.line(rect.xmin(),node.p.y(),rect.xmax(),node.p.y());
        }
        draw(node.lchild,leftRect(rect,node));
        draw(node.rchild,rightRect(rect,node));
    }

    private static RectHV leftRect(RectHV rect,Node node){
        if(node.isVertical)
            return new RectHV(rect.xmin(),rect.ymin(),node.p.x(),rect.ymax());
        else return new RectHV(rect.xmin(),rect.ymin(),rect.xmax(),node.p.y());
    }

    private static RectHV rightRect(RectHV rect,Node node){
        if(node.isVertical)
            return new RectHV(node.p.x(),rect.ymin(),rect.xmax(),rect.ymax());
        else return new RectHV(rect.xmin(),node.p.y(),rect.xmax(),rect.ymax());
    }


    public Iterable<Point2D> range(RectHV rect)             // all points that are inside the rectangle (or on the boundary)
    {
        ArrayList<Point2D> rangekdTree=new ArrayList<>();
        range(root, CONTAINER,rect,rangekdTree);
        return rangekdTree;
    }

    private void range(Node node,RectHV currentSubRect,RectHV rect,ArrayList<Point2D> rangelist)
    {
        if(node==null)return;

        if(rect.contains(node.p))rangelist.add(node.p);

        if(node.lchild != null && leftRect(currentSubRect,node).intersects(rect))
            range(node.lchild,leftRect(currentSubRect,node),rect,rangelist);

        if(node.rchild != null && rightRect(currentSubRect,node).intersects(rect))
            range(node.rchild,rightRect(currentSubRect,node),rect,rangelist);

    }


    public Point2D nearest(Point2D p)             // a nearest neighbor in the set to point p; null if the set is empty
    {
        if(p==null) throw new IllegalArgumentException("point is null");

        if(root==null)return null;

        return nearest(root,root.p,p);
    }

    private Point2D nearest(Node node,Point2D nearestPoint,Point2D p)
    {
        if(node==null)return nearestPoint;

        if(p.distanceSquaredTo(node.p) < p.distanceSquaredTo(nearestPoint))
            nearestPoint=node.p;

        int cmp=compare(p,node.p,node.isVertical);
        if(cmp<0){
            nearestPoint=nearest(node.lchild,nearestPoint,p);
            if(node.rchild != null){
                if(nearestPoint.distanceSquaredTo(p) > rightRect(CONTAINER,node).distanceSquaredTo(p))
                    nearestPoint=nearest(node.rchild,nearestPoint,p);
            }
        }else{
            nearestPoint=nearest(node.rchild,nearestPoint,p);
            if(node.lchild != null){
                if(nearestPoint.distanceSquaredTo(p) > leftRect(CONTAINER,node).distanceSquaredTo(p))
                    nearestPoint=nearest(node.lchild,nearestPoint,p);
            }
        }
        return nearestPoint;
    }

    private int compare(Point2D p,Point2D q,boolean isVertical){
        if(p==null || q==null)throw new IllegalArgumentException("point is null");
        if(q.equals(q)) return 0;
        if(isVertical)
            return p.x()<q.x()? -1 : 1;
        else return p.y()<q.y()? -1 : 1;
    }


    public static void main(String[] args) {
        RectHV rect = new RectHV(0.0, 0.0, 1.0, 1.0);
        StdDraw.enableDoubleBuffering();
        KdTree kdtree = new KdTree();
        while (true) {
            if (StdDraw.isMousePressed()) {
                double x = StdDraw.mouseX();
                double y = StdDraw.mouseY();
                StdOut.printf("11111%8.6f %8.6f\n", x, y);
                Point2D p = new Point2D(x, y);
                if (rect.contains(p)) {
                    StdOut.printf("222222%8.6f %8.6f\n", x, y);
                    kdtree.insert(p);
                    StdDraw.clear();
                    kdtree.draw();
                    StdDraw.show();
                }
            }
            StdDraw.pause(20);
        }

    }
}