import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;

public class Board {
    private int n;
    private int[][] block;
    private int num_hamming;
    private int num_manhattan;


    public Board(int[][] blocks)           // construct a board from an n-by-n array of blocks
    // (where blocks[i][j] = block in row i, column j)
    {
        n=blocks.length;
        block=new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++) {
                this.block[i][j]=blocks[i][j];
            }
    }

    public int dimension()                 // board dimension n
    {
        return n;
    }

    public int hamming()// number of blocks out of place
    {
        num_hamming=0;
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++) {
                if ((i * n + j + 1) == block[i][j]) continue;
                if(block[i][j]!=0)num_hamming++;
            }
        return num_hamming;
    }


    public int manhattan()                 // sum of Manhattan distances between blocks and goal
    {
        num_manhattan=0;
        int[] X=new int[n*n];
        int[] Y=new int[n*n];  //use two arrays to save the right x/y of position
        int k=-1;
        for(int m=1;m<n*n;m++) {
            if(m%n==1)k++;
            X[m]=k;
        }
        k=0;
        for(int m=1;m<n*n;m++) {
            if(k==n)k=0;
            Y[m]=k++;
        }

        for(int i=0;i<n;i++) {
            for (int j = 0; j < n; j++) {
                int now = block[i][j];
                if (now == 0) {
                    if (j != n - 1 && (j+1)<n) {
                        j++;
                        now = block[i][j];
                    }
                    else if( (i+1)<n ){
                        i++;
                        j = 0;
                        now = block[i][j];
                    }else break;
                }
                int temp = Math.abs(X[now] - i) + Math.abs(Y[now] - j);
                num_manhattan += temp;
            }
        }
            return num_manhattan;
    }

    public boolean isGoal()                // is this board the goal board?
    {
        return this.hamming()==0;
    }




    public Board twin()                    // a board that is obtained by exchanging any pair of blocks
    {
        int[][] twinBlocks=new int[n][n];//if we exchange block will change the original data
        for(int i=0;i<n;i++)
            for(int j=0;j<n;j++)
                twinBlocks[i][j]=block[i][j];

        int temp;
        if(block[0][0]!=0 && block[0][1]!=0){
            temp=twinBlocks[0][0];
            twinBlocks[0][0]=twinBlocks[0][1];
            twinBlocks[0][1]=temp;
        }else{
            temp=twinBlocks[1][0];
            twinBlocks[1][0]=twinBlocks[1][1];
            twinBlocks[1][1]=temp;
        }
        return new Board(twinBlocks);
    }

    public boolean equals(Object y)        // does this board equal y?
    {
        if(y==this)return true;
        if(y==null)return false;
        if(y.getClass()!=this.getClass())return false;
        Board yBoard=(Board)y;
        if(yBoard.n!=this.n)return false;
        int[][] yBlocks=yBoard.block;
        for(int i=0;i<n;i++)
            for(int j=0;j<n;j++)
            {
                if(this.block[i][j]!=yBlocks[i][j])
                    return false;
            }
        return true;
    }


   public Iterable<Board> neighbors(){     // all neighboring boards

        int[][] copy=new int[n][n];//inorder not to change original block
        int blank_i=0,blank_j=0;
        for(int i=0;i<n;i++)
            for(int j=0;j<n;j++){
               copy[i][j]=block[i][j];
               if(block[i][j]==0){
                   blank_i=i;
                   blank_j=j;
               }
            }
        ArrayList<Board> neighbor=new ArrayList<>();
        if(blank_i!=0){
            swap(copy,blank_i,blank_j,blank_i-1,blank_j);
            neighbor.add(new Board(copy));
            swap(copy,blank_i,blank_j,blank_i-1,blank_j);
        }
        if(blank_i!=n-1){
            swap(copy,blank_i,blank_j,blank_i+1,blank_j);
            neighbor.add(new Board(copy));
            swap(copy,blank_i,blank_j,blank_i+1,blank_j);
        }
        if(blank_j!=0){
            swap(copy,blank_i,blank_j,blank_i,blank_j-1);
            neighbor.add(new Board(copy));
            swap(copy,blank_i,blank_j,blank_i,blank_j-1);
        }
        if(blank_j!=n-1){
            swap(copy,blank_i,blank_j,blank_i,blank_j+1);
            neighbor.add(new Board(copy));
            swap(copy,blank_i,blank_j,blank_i,blank_j+1);
        }
        return neighbor;
    }


    private void swap(int[][] array,int a,int b,int c,int d){
        int temp=array[a][b];
        array[a][b]=array[c][d];
        array[c][d]=temp;
    }


    public String toString()               // string representation of this board (in the output format specified below)
    {
        StringBuffer board=new StringBuffer();
        board.append(n+"\n");
        for(int i=0;i<n;i++){
            for(int j=0;j<n;j++)
                board.append(String.format("%2d ",block[i][j]));
            board.append("\n");
        }
        return board.toString();
    }


    public static void main(String[] args) // solve a slider puzzle (given below)
    {

        // create initial board from file
       /* In in = new In("8puzzle/puzzle04.txt");
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                blocks[i][j] = in.readInt();*/
       int[][] blocks={{1,2,3},{4,5,6},{7,8,0}};
       Board initial = new Board(blocks);
        StdOut.println("board "+initial.toString());
        StdOut.println("hamming "+initial.hamming());

        StdOut.println("isgoal " + initial.isGoal());

    }
}
