
public class PercolationStats {
	
	Percolation perc;
		
	public PercolationStats(int N, int T) {
		 
		if (N <= 0 || T <= 0) {
			throw new IllegalArgumentException("Invalid input.  Inputs must be greater than zero.");
		}		 
		
		perc = new Percolation(N);
	}
	 
	public double mean() {
		return 0.0;
	}
	 
	public double stddev() {
		return 0.0;
	}
	
	public double confidenceLo() {     
		return 0.0;
	}
	 
	public double confidenceHi() {
		return 0.0;
	}
	 
	public static void main(String[] args) {
		
		int N = Integer.parseInt(args[0]);	        
	    int T = Integer.parseInt(args[1]);
	    
	    PercolationStats stats = new PercolationStats(N, T);
	    
	    
	}
}
