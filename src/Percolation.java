public class Percolation {
	
	private final int MIN_INDEX = 1;
	private final int OPEN_SITE = 1;
	private final int BLOCKED_SITE = 0;
	
	private int N;
	private int nSquared;
	private int[][] grid;
	private boolean[] openSites;
	private WeightedQuickUnionUF lookup;
	
	public Percolation(int N) {
		this.N = N;
		this.nSquared = (int)Math.pow(N, 2);
		
		grid = new int[N + 1][N+ 1];		
		openSites = new boolean[nSquared + 1];
		lookup = new WeightedQuickUnionUF(nSquared + 1);
	}
	
	public void open(int i, int j) {
		validateIndices(i, j);
		
		if (grid[i][j] == BLOCKED_SITE) {
			grid[i][j] = OPEN_SITE;
			
			int id = xyToID(i, j);
			openSites[id] = true;			

			boolean leftOpen = (id > 1 && openSites[id - 1] == true);
			boolean rightOpen = (id < nSquared && openSites[id + 1] == true);
			boolean aboveOpen = (id > N && openSites[id - N] == true);
			boolean belowOpen = (id < nSquared - N && openSites[id + N] == true);
			
			if (!leftOpen && !rightOpen && !aboveOpen && !belowOpen) {				
				return;
			}
			
			if (leftOpen) {				
				int idToLeft = id - 1;
				System.out.println(String.format("Connecting ID %s to the left (ID %s)", id, idToLeft));
				connect(id, idToLeft);				
			}
			
			if (rightOpen) {				
				int idToRight = id + 1;
				System.out.println(String.format("Connecting ID %s to the right (ID %s)", id, idToRight));
				connect(id, idToRight);				
			}
			
			if (aboveOpen) {				
				int idAbove = id - N;
				System.out.println(String.format("Connecting ID %s to above (ID %s)", id, idAbove));
				connect(id, idAbove);				
			}
			
			if (belowOpen) {
				int idBelow = id + N;
				System.out.println(String.format("Connecting ID %s to below (ID %s)", id, idBelow));
				connect(id, idBelow);
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
		
		boolean isFull = false;
		
		for (int index = 1; index <= N; index++) {
			if (openSites[index] == true) {
				if (lookup.connected(id, index)) {
					isFull = true;
					break;
				}
			}
		}		
				
		return isFull;
	}
	
	public boolean percolates() {
						
		int[] openTopSites = new int[N + 1];
		
		for (int i = 1; i <= N; i++) {
			if (openSites[i] == true) {
				openTopSites[i] = i;
			}
		}
		
		for (int i = nSquared - N; i <= nSquared; i++) {
			int bottomSite = lookup.find(i);
			
			if (openSites[bottomSite] == true) {
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
		//return ((y * N) + (x + 1)) - 6;
		return (x * N) - (N - y);
	}
}