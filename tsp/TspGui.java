package tsp;
import java.awt.*;

import javax.swing.*;

import graph.*;

@SuppressWarnings("serial")
public class TspGui extends JFrame {
    private static final int WIDTH = 1280;
    private static final int HEIGHT = 720;
    
    private Panel panel;
    private Statistics stats;
    
    public TspGui() {
    	panel = createPanel();
    	setWindowProperties();
    }
    
    private Panel createPanel() {
        Panel panel = new Panel();
        Container cp = getContentPane();
        cp.add(panel);
        panel.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        return panel;
    }
    
    private void setWindowProperties () {
        setResizable(false);
        pack();
        setTitle("TSP - Ant Algorithms");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

	public void draw(Statistics stats) {
		this.stats = stats;
		
		panel.repaint();
	}
	
	private class Panel extends JPanel {
        @Override
        protected void paintComponent(Graphics graphics) {
            super.paintComponent(graphics);
            paint((Graphics2D)graphics);
        }
        
        private void paint (Graphics2D graphics) {
            graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            if (stats == null) return;;
            
            paintIterationAnt(graphics);
            paintFittestAnt(graphics);
            paintLocations(graphics);
            paintStatistics(graphics);
        }
        
        private void paintLocations (Graphics2D graphics) {       	
            graphics.setColor(Color.DARK_GRAY);
            if (stats.overallBest == null) return;
            
            for (Node node : stats.overallBest.getTour()) {
                graphics.fillOval(node.getX(), node.getY(), 5, 5);
            }
        }
        
        private void paintIterationAnt (Graphics2D graphics) {
            graphics.setColor(Color.LIGHT_GRAY);
            if (stats.iterationBest == null) return;
            
            Node[] array = stats.iterationBest.getTour();

            for (int i = 1; i < array.length; i++) {
                int fromX = (int)(array[i-1].getX() + 2);
                int fromY = (int)(array[i-1].getY() + 2);
                int toX = (int)(array[i].getX() + 2);
                int toY = (int)(array[i].getY() + 2);
                graphics.drawLine(fromX, fromY, toX, toY);
            }
        }
        
        private void paintFittestAnt (Graphics2D graphics) {       	
            graphics.setColor(Color.MAGENTA);
            if (stats.overallBest == null) return;
            
            Node[] array = stats.overallBest.getTour();

            for (int i = 1; i < array.length; i++) {
                int fromX = (int)(array[i-1].getX() + 2);
                int fromY = (int)(array[i-1].getY() + 2);
                int toX = (int)(array[i].getX() + 2);
                int toY = (int)(array[i].getY() + 2);
                graphics.drawLine(fromX, fromY, toX, toY);
            }
        }
        
        private void paintStatistics (Graphics2D graphics) {
        	graphics.setColor(Color.DARK_GRAY);
            if (stats.overallBest == null) return;
            if (stats.iterationBest == null) return;
        	
        	String[] printStats = {
        			"Stuck Iterations: " + stats.stuckIterations, 
        			"Iterations: " + stats.iterations, 
        			"Overall Best: " + stats.overallBest.fitness(),
        			"Iteration Best: " + stats.iterationBest.fitness(),
        			"",
        			"Ant Count: " + Configuration.antCount,
        			"Location Count: " + Configuration.locationCount,
        			"",
        			"Alpha: " + Configuration.alpha,
        			"Beta: " + Configuration.beta,
                    "Evaporation Rate: " + Configuration.evaporation,
                    "",
        			"Testing: " + Configuration.testRun,
        			"Testing Runs: " + stats.testRuns + "/" + Configuration.testRuns,
        	};
        	
        	int i = 10;
        	for(String print : printStats) {
        		graphics.drawString(print, 0, i);
        		i += 15;
        	}
        }
	}
}
