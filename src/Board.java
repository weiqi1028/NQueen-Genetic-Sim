import java.awt.*;
import javax.swing.*;

public class Board extends JFrame {
	public int[] solution;
	public int dimension;
	Board(int[] solution, int dimension){
		this.solution = solution;
		this.dimension = dimension;
		this.setTitle("Queen Problem");
		this.setResizable(false);
		this.setSize(500, 500);
		int w = (Toolkit.getDefaultToolkit().getScreenSize().width - 500) / 2;
		int h = (Toolkit.getDefaultToolkit().getScreenSize().height - 500) / 2;
		this.setLocation(w, h); 
		this.setVisible(false);
		
		this.setLayout(new GridLayout(dimension, dimension));
		JPanel[] rootGrid = new JPanel[dimension*dimension];
		for (int i = 0; i < dimension*dimension; i++){
			rootGrid[i] = new JPanel();
			rootGrid[i].setLayout(new GridLayout(1,1));
			this.add(rootGrid[i]);
		}
		ImagePanel[] grid = new ImagePanel[dimension*dimension];
		if (dimension%2 == 1){
			boolean reverse = true;
			for (int i = 0; i < dimension*dimension; i++){
				if (reverse){
					grid[i] = new ImagePanel(new ImageIcon("src/image/light.jpg"));
					grid[i].setSize(500/dimension, 500/dimension);
					grid[i].setLayout(new GridLayout(1,1));
					rootGrid[i].add(grid[i]);
				}
				else{
					grid[i] = new ImagePanel(new ImageIcon("src/image/dark.jpg"));
					grid[i].setSize(500/dimension, 500/dimension);
					grid[i].setLayout(new GridLayout(1,1));
					rootGrid[i].add(grid[i]);
				}
				reverse = !reverse;
			}
		}
		else{
			int counter = 0;
			for (int i = 0; i < dimension*dimension - 1; i+=2){
				if ((counter/dimension)%2 == 0){
					grid[i] = new ImagePanel(new ImageIcon("src/image/light.jpg"));
					grid[i].setSize(500/dimension, 500/dimension);
					grid[i].setLayout(new GridLayout(1,1));
					rootGrid[i].add(grid[i]);
					
					grid[i+1] = new ImagePanel(new ImageIcon("src/image/dark.jpg"));
					grid[i+1].setSize(500/dimension, 500/dimension);
					grid[i+1].setLayout(new GridLayout(1,1));
					rootGrid[i+1].add(grid[i+1]);
				}
				else{
					grid[i] = new ImagePanel(new ImageIcon("src/image/dark.jpg"));
					grid[i].setSize(500/dimension, 500/dimension);
					grid[i].setLayout(new GridLayout(1,1));
					rootGrid[i].add(grid[i]);
					
					grid[i+1] = new ImagePanel(new ImageIcon("src/image/light.jpg"));
					grid[i+1].setSize(500/dimension, 500/dimension);
					grid[i+1].setLayout(new GridLayout(1,1));
					rootGrid[i+1].add(grid[i+1]);
				}
				counter += 2;
			}
		}
		
		ImagePanel[] queen = new ImagePanel[dimension];
		for (int i = 0; i < dimension; i++){
			queen[i] = new ImagePanel(new ImageIcon("src/image/queen.png"));
			queen[i].setOpaque(false);
			grid[(solution[i]-1)*dimension+i].add(queen[i]);		// draw the queens on the board
		}
		this.setVisible(true);
	}
}
