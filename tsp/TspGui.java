package tsp;
import java.awt.*;
import java.util.Random;

import javax.swing.*;

import graph.*;

public class TspGui extends JFrame {
    private static final int WIDTH = 1280;
    private static final int HEIGHT = 720;
    
    private Panel panel;
    private Ant overall, iteration;
    
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
        int sWidth = (int)Toolkit.getDefaultToolkit().getScreenSize().getWidth()/2;
        int sHeight = (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight()/2;
        int x = sWidth - (WIDTH / 2);
        int y = sHeight - (HEIGHT / 2);
        setLocation(x, y);
        setResizable(false);
        pack();
        setTitle("Traveling Salesman Problem - Ant Algorithms");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

	public void draw(Ant overall, Ant iteration) {
		this.overall = overall;
		this.iteration = iteration;
		
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
            graphics.setColor(Color.darkGray);
            Random r = new Random();
            graphics.drawString("Hello World " + r.nextInt(), 100, 100);
            
            paintIterationAnt(graphics);
            paintFittestAnt(graphics);
            paintLocations(graphics);
            
            // Paint Stats
        }
        
        private void paintLocations (Graphics2D graphics) {
            graphics.setColor(Color.DARK_GRAY);
            for (Node node : overall.getTour()) {
                graphics.fillOval(node.getX(), node.getY(), 5, 5);
            }
        }
        
        private void paintIterationAnt (Graphics2D graphics) {
            graphics.setColor(Color.LIGHT_GRAY);
            Node[] array = iteration.getTour();

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
            Node[] array = overall.getTour();

            for (int i = 1; i < array.length; i++) {
                int fromX = (int)(array[i-1].getX() + 2);
                int fromY = (int)(array[i-1].getY() + 2);
                int toX = (int)(array[i].getX() + 2);
                int toY = (int)(array[i].getY() + 2);
                graphics.drawLine(fromX, fromY, toX, toY);
            }
        }
	}
}
