import java.util.Random;

import tsp.TSP;
import tsp.TspGui;

public class Main {

	public static void main(String[] args) {
		// Initialise Random Seed
		Random r = new Random(523515415);

		// Initialise GUI
		TspGui tspGui = new TspGui();
		
		// Initialise TSP
		new TSP(tspGui, r);
	}
}
