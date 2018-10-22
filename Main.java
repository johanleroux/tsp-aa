import tsp.Report;
import tsp.TSP;
import tsp.TspGui;

public class Main {
	public static void main(String[] args) {
		Report report = new Report();
		report.write();
		// Initialise GUI
		TspGui tspGui = new TspGui();
		
		// Initialise TSP
		new TSP(tspGui);
	}
}
