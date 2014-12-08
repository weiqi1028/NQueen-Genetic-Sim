import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class Query extends JFrame implements ActionListener{
	public int dimension;
	JLabel label = new JLabel("Please enter the number of the queens:");
	JTextField text = new JTextField();
	JButton bok = new JButton("OK");
	JButton bexit = new JButton("Exit");
	JLabel choose = new JLabel("Please choose the search algorithm:");
	JRadioButton genetic = new JRadioButton("Genetic algorithm");
	JRadioButton annealing = new JRadioButton("Simulated annealing algorithm");
	String command = null;
	
	Query(){
		this.setTitle("N Queen Problem");
		this.setResizable(false);
		this.setSize(300, 300);
		int w = (Toolkit.getDefaultToolkit().getScreenSize().width - 300) / 2;
		int h = (Toolkit.getDefaultToolkit().getScreenSize().height - 300) / 2;
		this.setLocation(w, h); 
		this.setVisible(false);
		this.setLayout(null);
		
		label.setSize(260, 30);
		label.setLocation(20, 20);
		label.setHorizontalAlignment(JTextField.CENTER);
		this.add(label);
		
		text.setSize(60, 30);
		text.setLocation(120, 50);
		text.setHorizontalAlignment(JTextField.CENTER);
		this.add(text);
		
		choose.setSize(260, 30);
		choose.setLocation(20, 100);
		choose.setHorizontalAlignment(JTextField.CENTER);
		this.add(choose);
		
		genetic.setSize(260, 30);
		genetic.setLocation(40, 130);
		genetic.setSelected(false);
		genetic.addActionListener(this);
		this.add(genetic);
		
		annealing.setSize(260, 30);
		annealing.setLocation(40, 160);
		annealing.setSelected(false);
		annealing.addActionListener(this);
		this.add(annealing);
		
		ButtonGroup group = new ButtonGroup();
		group.add(genetic);
		group.add(annealing);
		
		bok.setSize(80, 30);
		bok.setLocation(45, 200);
		bok.addActionListener(this);
		this.add(bok);
		
		bexit.setSize(80, 30);
		bexit.setLocation(185, 200);
		bexit.addActionListener(this);
		this.add(bexit);
		
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public void actionPerformed(ActionEvent event){
		Object obj = new Object();
		obj = event.getSource();
		if (obj.equals(bexit)){
			System.exit(0);
		}
		else if (obj.equals(bok)){
			if (genetic.isSelected() == false && annealing.isSelected() == false){
				JOptionPane.showMessageDialog(null, "Please choose the searh algorithm!");
			}
			else if (text.getText() == null){
				JOptionPane.showMessageDialog(null, "Please enter the number of the queens!");
			}
			else if (text.getText().equals("0")){
				JOptionPane.showMessageDialog(null, "Number of the queens can not be 0!");
			}
			else{
				if (genetic.isSelected() == true){
					this.dimension = Integer.parseInt(text.getText());
					this.setVisible(false);
					new GeneticFrame(this, dimension);
				}
				else if (annealing.isSelected() == true){
					this.dimension = Integer.parseInt(text.getText());
					SimulatedAnnealing problem = new SimulatedAnnealing(dimension);
					problem.process();
				}
			}
		}
	}
}
