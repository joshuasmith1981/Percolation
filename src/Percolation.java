import java.awt.Point;


public class Percolation {
	
	private final int MIN_INDEX = 1;
	private final int OPEN_SITE = 1;
	private final int BLOCKED_SITE = 0;
	
	private int N;
	private int nSquared;
	private int[][] grid;
	private int[] openSites;
	private WeightedQuickUnionUF lookup;
	
	public Percolation(int N) {
		this.N = N;
		this.nSquared = (int)Math.pow(N, 2);
		
		grid = new int[N + 1][N+ 1];
		openSites = new int[nSquared + 1];
		lookup = new WeightedQuickUnionUF(nSquared + 1);
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
			boolean rightOpen = (i < N && isOpen(i + 1, j));
			boolean aboveOpen = (j > MIN_INDEX && isOpen(i, j - 1));
			boolean belowOpen = (j < N && isOpen(i, j + 1));
			
			if (!leftOpen && !rightOpen && !aboveOpen && !belowOpen) {
				System.out.println(String.format("No open neighbors found for site with ID %s.", id));
				System.out.println();
				return;
			}
			
			if (leftOpen) {
				int idToLeft = xyToID(i - 1, j);
				//connect(id, idToLeft);
				connect(idToLeft, id);
				System.out.println(String.format("Connected to open site to the left (ID %s).", idToLeft));
				System.out.println();
			}
			
			if (rightOpen) {
				int idToRight = xyToID(i + 1, j);
				//connect(id, idToRight);
				connect(idToRight, id);
				System.out.println(String.format("Connected to open site to the right (ID %s).", idToRight));
				System.out.println();
			}
			
			if (aboveOpen) {
				int idAbove = xyToID(i, j - 1);
				//connect(id, idAbove);
				connect(idAbove, id);
				System.out.println(String.format("Connected to open site above (ID %s).", idAbove));
				System.out.println();
			}
			
			if (belowOpen) {
				int idBelow = xyToID(i, j + 1);
				//connect(id, idBelow);
				connect(idBelow, id);
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
		
		int id = xyToID(i, j);
		
		int root = lookup.find(id);
		
		System.out.println(String.format("Found component root %s using ID %s (calculated from point (%s, %s))", root, id, i, j));
		
		boolean isFull = false;
		
		for (int index = 1; index <= N; index++) {
			if (openSites[index] == OPEN_SITE) {
				if (lookup.connected(id, index)) {
					isFull = true;
					break;
				}
			}
		}
		
		return isFull;
	}
	
	public boolean percolates() {
				
		int[] openTopSites = new int[N];
		
		for (int i = 1; i <= N; i++) {
			if (openSites[i] == OPEN_SITE) {
				openTopSites[i] = i;
			}
		}
		
		for (int i = nSquared - N; i <= nSquared; i++) {
			int bottomSite = lookup.find(i);
			
			if (openSites[bottomSite] == OPEN_SITE) {
				for (int j = 0; j < openTopSites.length; j++) {
					int topSite = openTopSites[j];
					
					if (lookup.connected(bottomSite, topSite)) {
						return true;
					}
				}
			}
		}
		
		return false;
	}
	
	public boolean connected(int p, int q) {
		return lookup.connected(p, q);
	}
	
	public void printGrid() {
		for (int y = 1; y <= N; y++) {
			for (int x = 1; x <= N; x++) {
				System.out.print(grid[x][y]);
			}
			System.out.println();
		}
		System.out.println();
	}
	
	public void printOpenSites() {
		for (int i = 1; i <= nSquared; i++) {
			System.out.print(openSites[i]);
		}
		
		System.out.println();
		System.out.println();
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