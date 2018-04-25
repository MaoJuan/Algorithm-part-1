import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public class Solver {
    private SearchNode currentSearchNode;
    private Stack<Board> solution;

    private class SearchNode implements Comparable{
        private Board snBoard;
        private int moves;
        private int priority;   //priority=distance+moves
        private SearchNode preSearchNode;
        private boolean isInitialNode;

        public SearchNode (Board board,boolean isInitial){//for initial node and it's twin
            this.snBoard=board;
            this.moves=0;
            this.priority=this.moves+board.manhattan();
            this.isInitialNode=isInitial;
        }

        public SearchNode(Board board, SearchNode pre){//for later nodes
            this.snBoard=board;
            this.preSearchNode=pre;
            this.moves=pre.moves+1;
            this.priority=this.moves+board.manhattan();
            this.isInitialNode=pre.isInitialNode;
        }

        @Override
        public int compareTo(Object o) {
            SearchNode oo=(SearchNode)o;
            if(this.priority==oo.priority)
                return 0;
            else return this.priority-oo.priority;
        }
    }


    public Solver(Board initial)// find a solution to the initial board (using the A* algorithm)
    {
        if(initial==null)throw new java.lang.NullPointerException("initial board is null");
        MinPQ<SearchNode> minPQ=new MinPQ<SearchNode>();

        minPQ.insert(new SearchNode(initial,true));
        minPQ.insert(new SearchNode(initial.twin(),false));
        int k=1;
        while(!minPQ.isEmpty()){

            currentSearchNode=minPQ.delMin();
            if(currentSearchNode.snBoard.isGoal())break;
            Iterable<Board> neighbor=currentSearchNode.snBoard.neighbors();
            for(Board i:neighbor){
                if(currentSearchNode.preSearchNode==null || !i.equals(currentSearchNode.preSearchNode.snBoard)) {//no pre or pre not equal can be inserted
                    minPQ.insert(new SearchNode(i, currentSearchNode));
                }
            }
        }
    }

   /* public void putInNeighbors(MinPQ pq,SearchNode sn){//if use this function,we need make minPQ member variables
                                                        //which will blow up the memory
        Iterable<Board> neighbor=sn.snBoard.neighbors();
        for(Board i:neighbor){
            if(sn.preSearchNode!=null && !i.equals(sn.preSearchNode.snBoard))
                pq.insert(new SearchNode(i,sn));
        }
    }*/


    public boolean isSolvable()            // is the initial board solvable?
    {
        return currentSearchNode.isInitialNode && currentSearchNode.snBoard.isGoal();
    }

    public int moves()                     // min number of moves to solve initial board; -1 if unsolvable
    {
        if(isSolvable())return currentSearchNode.moves;
        else return -1;
    }

    public Iterable<Board> solution()      // sequence of boards in a shortest solution; null if unsolvable
    {
        if(!currentSearchNode.snBoard.isGoal())return null;
        solution=new Stack<Board>();
        SearchNode backtrack=currentSearchNode;
        while (backtrack!=null) {
            solution.push(backtrack.snBoard);
            backtrack=backtrack.preSearchNode;
        }
        return solution;
    }


    public static void main(String[] args) // solve a slider puzzle (given below)
    {

        // create initial board from file
        In in = new In("./8puzzle/puzzle04.txt");
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);
      //  StdOut.println(initial.toString());

        // solve the puzzle
       Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}

