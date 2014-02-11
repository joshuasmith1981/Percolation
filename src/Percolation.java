import java.awt.Point;


public class Percolation {
	
	private final int MIN_INDEX = 1;
	private final int OPEN_SITE = 1;
	private final int BLOCKED_SITE = 0;
	
	private int N;
	private int[][] grid;
	private int[] openSites;
	private WeightedQuickUnionUF lookup;

	public Percolation(int N) {
		this.N = N;
		int nSquared = (int)Math.pow(N, 2);
		
		grid = new int[N][N];
		openSites = new int[nSquared];
		lookup = new WeightedQuickUnionUF(nSquared);
	}
	
	public void open(int i, int j) {
		validateIndices(i, j);
		
		if (grid[i][j] == BLOCKED_SITE) {
			grid[i][j] = OPEN_SITE;
			
			int id = xyToID(i, j);
			openSites[id] = OPEN_SITE;
			
			System.out.println(String.format("Calculated ID %s for point (%s, %s)", id, i, j));
			System.out.println();
			
			boolean leftOpen = (i > MIN_INDEX && isOpen(i - 1, j));
			boolean rightOpen = (i < N - 1 && isOpen(i + 1, j));
			boolean aboveOpen = (j > MIN_INDEX && isOpen(i, j - 1));
			boolean belowOpen = (j < N - 1 && isOpen(i, j + 1));
			
			if (!leftOpen && !rightOpen && !aboveOpen && !belowOpen) {
				System.out.println(String.format("No open neighbors found for site with ID %s.", id));
				System.out.println();
				return;
			}
			
			if (leftOpen) {
				int idToLeft = xyToID(i - 1, j);
				connect(id, idToLeft);
				System.out.println(String.format("Connected to open site to the left (ID %s).", idToLeft));
				System.out.println();
			}
			
			if (rightOpen) {
				int idToRight = xyToID(i + 1, j);
				connect(id, idToRight);
				System.out.println(String.format("Connected to open site to the right (ID %s).", idToRight));
				System.out.println();
			}
			
			if (aboveOpen) {
				int idAbove = xyToID(i, j - 1);
				connect(id, idAbove);
				System.out.println(String.format("Connected to open site above (ID %s).", idAbove));
				System.out.println();
			}
			
			if (belowOpen) {
				int idBelow = xyToID(i, j + 1);
				connect(id, idBelow);
				System.out.println(String.format("Connected to open site below (ID %s).", idBelow));
				System.out.println();
			}
		}
	}
	
	public boolean isOpen(int i, int j) {
		validateIndices(i, j);
		
		return grid[i][j] == OPEN_SITE;
	}
	
	public boolean isFull(int i, int j) {
		validateIndices(i, j);
		
		return false;
	}
	
	public boolean percolates() {
		return false;
	}
	
	public boolean connected(int p, int q) {
		return lookup.connected(p, q);
	}
	
	private void connect(int id1, int id2) {
		lookup.union(id1, id2);
	}
		
	private void validateIndices(int i, int j) {
		if (i < MIN_INDEX || i > N) {
			throw new IndexOutOfBoundsException("Index 'i' was not within the acceptable range.");
		}
		
		if (j < MIN_INDEX || j > N) {
			throw new IndexOutOfBoundsException("Index 'j' was not within the acceptable range.");
		}
	}
	
	private int xyToID(int x, int y) {
		return ((y * N) + (x + 1)) - 6;
	}
}
