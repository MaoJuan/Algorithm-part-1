import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private final static int[] ROW = { -1, 0, 1, 0};
    private final static int[] COL = { 0, 1, 0, -1};
    private int count ;
    private int n ;
    private WeightedQuickUnionUF uf ;
    private WeightedQuickUnionUF uf_backwash ;
    private boolean[] isopen ;


    public Percolation(int n) {        //create n-by-n grid,with all sites blocked
        if(n <= 0) throw new IllegalArgumentException("n <= 0");
        this.n = n;
        uf = new WeightedQuickUnionUF(n * n + 2);
        uf_backwash = new WeightedQuickUnionUF(n * n + 1);
        isopen = new boolean[n * n];
        for(int i = 0 ; i < n ; i++) {
            uf.union(0 * n + i, n * n);
            uf_backwash.union(0 * n + i, n * n);
            uf.union((n - 1) * n + i, n * n + 1);
        }
    }

    public void open(int row,int col) {        //open site (row,col) if it is not open already
        if (row < 1  || row > n )
            throw new java.lang.IllegalArgumentException("row out of bounds of open");
        if (col < 1 || col > n )
            throw new java.lang.IllegalArgumentException("column out of bounds of open");
        int ni = row - 1;
        int nj = col - 1;
        int uni, unj;
        if(isopen[ni * n + nj]) return;
            isopen[ni * n + nj]=true;
            count++;
            for (int i = 0 ; i < 4 ; i++) {
                uni = ni + ROW[i];
                unj = nj + COL[i];
                if ( uni >= 0 && uni < n && unj >= 0 && unj < n && isopen[uni * n + unj] ) {
                    uf.union(ni * n + nj, uni * n + unj);
                    uf_backwash.union(ni * n + nj, uni * n + unj);
                }
        }
    }

    public boolean isOpen(int row,int col) {   //is site (row,col) open?
        if (row < 1  || row > n )
            throw new java.lang.IllegalArgumentException("row out of bounds of isOpen");
        if (col < 1 || col > n )
            throw new java.lang.IllegalArgumentException("column out of bounds of isOpen");        return isopen[ ( row - 1 ) * n + col - 1];
    }

    public boolean isFull(int row,int col) {    //is site (row,col) full?
        if (row < 1  || row > n )
            throw new java.lang.IllegalArgumentException("row out of bounds of isfull");
        if (col < 1 || col > n )
            throw new java.lang.IllegalArgumentException("column out of bounds of isfull");
        return isopen[ ( row - 1 ) * n + col - 1 ] && uf_backwash.connected( (( row - 1 ) * n + col - 1), n * n );
    }

    public int numberOfOpenSites() {     //number of open sites
        return count;
    }


    public boolean percolates() {  //does the system percolation?
		if(n == 1 && count==0) return false;  //after run the constructor of percolate(n),
											// uf[1] and uf[2] will connect to the uf[0] and cause the wrong answer(percolated)
        return uf.connected(n * n,n * n + 1);
    }


    public static void main(String[] args) {     // test client (optional)
        Percolation per = new Percolation(4);
        per.open(1, 1);
        per.open(1, 2);
        per.open(2, 1);
        per.open(3, 2);
        per.open(4, 3);
        per.open(4, 4);
        per.open(3, 3);
        per.open(3, 1);
        System.out.println(per.percolates());
        System.out.println("Is (4,3) full? "+per.isFull(4, 3));
    }

}