
public class Main {
	private static final int N = 20;
	
	private static final Percolation perk = new Percolation(N);
	
	public static void main(String[] args) {
		
		int x1 = 3;
		int y1 = 1;
		
		int x2 = 3;
		int y2 = 2;
		
		int x3 = 3;
		int y3 = 3;
		
		int x4 = 4;
		int y4 = 5;
				
		perk.open(x1, y1);
		perk.open(x2, y2);
		perk.open(x3, y3);
		perk.open(3, 4);
		perk.open(4, 4);
		perk.open(x4, y4);
		System.out.println();
		
		perk.printGrid();
		perk.printOpenSites();
		
		writeFullStateToConsole(x2, y2);
		writeFullStateToConsole(x4, y4);
		
		boolean percolates = perk.percolates();
		
		System.out.println();
		System.out.println(String.format("System %s percolate!", percolates ? "does" : "does not"));
	}
	
	private static int xyToID(int x, int y) {
		return ((y * N) + (x + 1)) - 6;
	}
	
	private static void writeConnectedStateToConsole(int x1, int y1, int x2, int y2) {
		int id1 = xyToID(x1, y1);
		int id2 = xyToID(x2, y2);
		boolean connected = perk.connected(id1, id2);
		
		System.out.println(String.format("Points (%s, %s) and (%s, %s) %s connected.", x1, y1, x2, y2, connected ? "are" : "are not"));
		System.out.println();
	}
	
	private static void writeFullStateToConsole(int x, int y) {
		boolean siteFull = perk.isFull(x, y);
		
		System.out.println(String.format("Site at point (%s, %s) is %s.", x, y, siteFull ? "full" : "not full"));
		System.out.println();
	}
}
