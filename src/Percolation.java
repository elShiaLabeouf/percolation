import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
	
	private WeightedQuickUnionUF id;
	private WeightedQuickUnionUF idNoWashback;
	private int size;
	private boolean[]siteStatus;
	
	public Percolation(int n) {
		if (n <= 0) throw new java.lang.IllegalArgumentException();
		id = new WeightedQuickUnionUF(n * n + n * 2);
		idNoWashback = new WeightedQuickUnionUF(n * n + n * 2);
		siteStatus = new boolean[n * n + n * 2];
		size = n;
		siteStatus[0] = true;						//virtual top
		siteStatus[siteStatus.length - 1] = true;	//virtual bottom
	}
	
	
	public void open(int row, int col) {
		// open site (row, col) if it is not open already
		if (isOpen(row, col)) return;
		siteStatus[xyTo1D(row, col)] = true;
		if (row == 1) {
			id.union(0, xyTo1D(row, col));
			idNoWashback.union(0, xyTo1D(row, col));
		}
		if (row == size) {
			id.union(siteStatus.length - 1, xyTo1D(row, col));
		}
		try {										// upper neighbor
			if (isOpen(row - 1, col)) {
				
				id.union(xyTo1D(row, col), xyTo1D(row - 1, col));
				idNoWashback.union(xyTo1D(row, col), xyTo1D(row - 1, col));
				
			}
		} catch(IndexOutOfBoundsException e) { /* no neighbor element */ }
		
		try {										// lower neighbor
			if (isOpen(row + 1, col)) {
				
				id.union(xyTo1D(row, col), xyTo1D(row + 1, col));
				idNoWashback.union(xyTo1D(row, col), xyTo1D(row + 1, col));
				
			}
		} catch(IndexOutOfBoundsException e) { /* no neighbor element */ }
		
		try {										// left neighbor
			if (isOpen(row, col - 1)) {
				
				id.union(xyTo1D(row, col), xyTo1D(row, col - 1));
				idNoWashback.union(xyTo1D(row, col), xyTo1D(row, col - 1));
				
			}
		} catch(IndexOutOfBoundsException e) { /* no neighbor element */ }
		
		try {										// right neighbor
			if (isOpen(row, col + 1)) {
				
				id.union(xyTo1D(row, col), xyTo1D(row, col + 1));
				idNoWashback.union(xyTo1D(row, col), xyTo1D(row, col + 1));
				
			}
		} catch(IndexOutOfBoundsException e) { /* no neighbor element */ }
				
	}
	
	public boolean isOpen(int row, int col) {
		// is site (row, col) open?
		if ((row < 1) || (col < 1) || (row > size) || (col > size)) throw new java.lang.IndexOutOfBoundsException();
		else return siteStatus[xyTo1D(row, col)];
	}
	
	public boolean isFull(int row, int col) {
		if ((row < 1) || (col < 1) || (row > size) || (col > size)) throw new java.lang.IndexOutOfBoundsException();
		if (!isOpen(row, col)) return false;
		return idNoWashback.connected(0, xyTo1D(row, col));
	}
	
	
	public int numberOfOpenSites() {
		int number = 0;
		for (int i = 0; i < siteStatus.length; i++) {
			if (siteStatus[i]) number++;
		}
		return number - 2;
	}
	
	public boolean percolates() { 
		// does the system percolate?
		return id.connected(0, siteStatus.length - 1);
	}
	
	
	
	private int xyTo1D(int row, int col) {
		return row * size + col - 1;
	}
}