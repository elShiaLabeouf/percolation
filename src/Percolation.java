import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
	
	private WeightedQuickUnionUF id;
	private int size;
	private boolean[]siteStatus;
	private Integer[]virtualTop;
	private Integer[]virtualBot;
	
	public Percolation(int n) {
		if (n <= 0) throw new java.lang.IllegalArgumentException();
		id = new WeightedQuickUnionUF(n * n);
		siteStatus = new boolean[n * n];
		size = n;
		virtualTop = new Integer[size];
		virtualBot = new Integer[size];
	}
	
	
	public void open(int row, int col) {
		// open site (row, col) if it is not open already
		if (isOpen(row, col)) return;
		siteStatus[xyTo1D(row, col)] = true;
		if (row==1) virtualTop[xyTo1D(row, col)] = xyTo1D(row, col);
		if (row==size) virtualBot[xyTo1D(row, col) % size] = xyTo1D(row, col);
		boolean structHasChanged = false;
		try {										// upper neighbor
			if (isOpen(row - 1, col)) {
 				id.union(xyTo1D(row, col), xyTo1D(row - 1, col));
 				structHasChanged = true;
			}
		} catch(IndexOutOfBoundsException e) { /* no neighbor element */ }
		
		try {										// lower neighbor
			if (isOpen(row + 1, col)) {
				id.union(xyTo1D(row, col), xyTo1D(row + 1, col));
				structHasChanged = true;
			}
		} catch(IndexOutOfBoundsException e) { /* no neighbor element */ }
		
		try {										// left neighbor
			if (isOpen(row, col - 1)) {
				id.union(xyTo1D(row, col), xyTo1D(row, col - 1));
				structHasChanged = true;
			}
		} catch(IndexOutOfBoundsException e) { /* no neighbor element */ }
		
		try {										// right neighbor
			if (isOpen(row, col + 1)) {
				id.union(xyTo1D(row, col), xyTo1D(row, col + 1));
				structHasChanged = true;
			}
		} catch(IndexOutOfBoundsException e) { /* no neighbor element */ }
		
		if (structHasChanged)
			for (int i = 0; i < virtualTop.length; i++) {
				if (siteStatus[i]) virtualTop[i] = id.find(i);
				if (siteStatus[(size - 1) * size + i]) virtualBot[i] = id.find((size - 1) * size + i);
			}
		
	}
	
	public boolean isOpen(int row, int col) {
		// is site (row, col) open?
		if ((row < 1) || (col < 1) || (row > size) || (col > size)) throw new java.lang.IndexOutOfBoundsException();
		else return siteStatus[xyTo1D(row, col)];
	}
	
	public boolean isFull(int row, int col) {
		if ((row < 1) || (col < 1) || (row > size) || (col > size)) throw new java.lang.IndexOutOfBoundsException();
		if (!isOpen(row, col)) return false;
		int parent = id.find(xyTo1D(row, col));
		for (int i = 0; i < size; i++) {
			if (virtualTop[i] == parent) return true;
		}
		return false;
	}
	
	
	public int numberOfOpenSites() {
		int number = 0;
		for (int i = 0; i < siteStatus.length; i++) {
			if (siteStatus[i]) number++;
		}
		return number;
	}
	
	public boolean percolates() { 
		// does the system percolate?
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				if ((virtualTop[i] != null) && (virtualTop[i].equals(virtualBot[j]))) return true;
				
			}
		}
		return false;
	}
	
	
	
	private int xyTo1D(int row, int col) {
		return (row - 1) * size + col - 1;
	}
}