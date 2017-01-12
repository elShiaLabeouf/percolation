import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.StdRandom;

public class PercolationStats {
	private double mean;
	private double stddev;
	private double confidenceLo;
	private double confidenceHi;
	
	public PercolationStats(int n, int trials) {
		// perform trials independent experiments on an n-by-n grid
		if ((n <= 0)||(trials <= 0)) throw new java.lang.IllegalArgumentException();
		double[]openSitesArray = new double[trials];
		
		for (int i = 0; i < trials; i++) {
			Percolation perc = new Percolation(n);
			while (!perc.percolates()) {
				int siteIndex = StdRandom.uniform(0, n * n);
				perc.open(siteIndex / n + 1, siteIndex % n + 1);
			}
			openSitesArray[i] = perc.numberOfOpenSites() / Math.pow(n, 2);
		}
		
		mean = StdStats.mean(openSitesArray);
		stddev = StdStats.stddev(openSitesArray);
		confidenceLo = mean - 1.96 * stddev / Math.sqrt(trials);
		confidenceHi = mean + 1.96 * stddev / Math.sqrt(trials);
	 
	}
	
	public double mean() {
		// sample mean of percolation threshold
		return mean;
	}
	
	public double stddev() {
		// sample standard deviation of percolation threshold
		return stddev;
	}
	
	public double confidenceLo() {
		// low  endpoint of 95% confidence interval
		return confidenceLo;
	}
	
	public double confidenceHi() {
		// high endpoint of 95% confidence interval
		return confidenceHi;
	}
	
	public static void main(String[] args) {
		// test client (described below)
		PercolationStats ps = new PercolationStats(20, 10);
		System.out.println("mean                    = " + ps.mean());
		System.out.println("stddev                  = " + ps.stddev());
		System.out.println("95% confidence interval = " + ps.confidenceLo() + ", " + ps.confidenceHi());
	}
	
}