package tsp;

public class Statistics {
	public Ant overallBest, iterationBest;
	
	public int iterations, stuckIterations = 0;
	
	public int testRuns = 1;
	
	public Statistics() {
		this.iterations = 0;
	}
	
	public void reset() {
		overallBest = null;
		iterationBest = null;
		iterations = 0;
		stuckIterations = 0;
	}
}
