
public class Main {
	private static final int N = 5;
	
	private static final Percolation perk = new Percolation(N);
	
	public static void main(String[] args) {
		
		//Percolation perk = new Percolation(N);
		
		int x1 = 2;
		int y1 = 2;
		
		int x2 = 2;
		int y2 = 3;
		
		int x3 = 4;
		int y3 = 4;
		
		int x4 = 5;
		int y4 = 4;
		
		perk.open(x1, y1);
		perk.open(x2, y2);
		perk.open(x3, y3);
		perk.open(x4, y4);
				
		writeConnectedStateToConsole(x1, y1, x2, y2);
		writeConnectedStateToConsole(x1, y1, x3, y3);
		writeConnectedStateToConsole(x3, y3, x4, y4);
		
	}
	
	private static int xyToID(int x, int y) {
		return ((y * N) + (x + 1)) - 6;
	}
	
	private static void writeConnectedStateToConsole(int x1, int y1, int x2, int y2) {
		int id1 = xyToID(x1, y1);
		int id2 = xyToID(x2, y2);
		boolean connected = perk.connected(id1, id2);
		
		System.out.println(String.format("Points (%s, %s) and (%s, %s) %s connected.", x1, y1, x2, y2, connected ? "are" : "are not"));
	}
}
