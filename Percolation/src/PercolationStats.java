import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private double mean = 0.0;
    private double std = 0.0;
    private double conlo =0.0;
    private double conhi = 0.0;
    private static final double CONFIDENCE_95 = 1.96;

    public PercolationStats(int n , int trials)    // perform trials independent experiments on an n-by-n grid
    {
        if(n <= 0) throw new IllegalArgumentException("n <= 0");
        if(trials <= 0) throw new IllegalArgumentException("trials <=0 ");
    	int count;
        double[] res = new double[trials];
        for(int i = 0;i < trials;i++) {
            count=0;
            Percolation p=new Percolation(n);
            while(!p.percolates()) {
                int ni = StdRandom.uniform(n) + 1;
                int nj = StdRandom.uniform(n) + 1;
               if(!p.isOpen(ni , nj)){
                   p.open(ni , nj) ;
                   count++;
               }
            }
            res[i] = (double) count / (n * n);  // wrong: count/n*n
        }

        mean = StdStats.mean(res);
        std = StdStats.stddev(res);
        conlo = mean - CONFIDENCE_95 * std / Math.sqrt(trials);
        conhi = mean + CONFIDENCE_95 * std / Math.sqrt(trials);

    }

    public double mean()      // sample mean of percolation threshold
    {
        return mean;
    }

    public double stddev()         // sample standard deviation of percolation threshold
    {
        return std;
    }

    public double confidenceLo()                  // low  endpoint of 95% confidence interval
    {
        return conlo;
    }

    public double confidenceHi()                  // high endpoint of 95% confidence interval
    {
        return conhi;
    }

    public static void main(String[] args) {
        int N = 2;
        int T = 1000;
        PercolationStats percolationStats = new PercolationStats(N, T);
        System.out.println("mean = " + percolationStats.mean());
        System.out.println("stddev = " + percolationStats.stddev());
        System.out.println("95% confidence interval " + percolationStats.confidenceLo() + ", " + percolationStats.confidenceHi());
    }
}