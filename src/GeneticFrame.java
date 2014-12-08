import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GeneticFrame extends JFrame implements ActionListener {
	int dimension;
	int population;
	JButton bok = new JButton("OK");
	JButton bexit = new JButton("Exit");
	JButton bback = new JButton("Back");
	JLabel label = new JLabel("Please enter the population");
	JTextField text = new JTextField();
	Query query;
	
	GeneticFrame(Query q, int dimension){
		this.query = q;
		this.dimension = dimension;
		this.setTitle("Genetic Algorithm");
		this.setResizable(false);
		this.setSize(300, 200);
		int w = (Toolkit.getDefaultToolkit().getScreenSize().width - 300) / 2;
		int h = (Toolkit.getDefaultToolkit().getScreenSize().height - 200) / 2;
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
		
		bok.setSize(70, 30);
		bok.setLocation(25, 100);
		bok.addActionListener(this);
		this.add(bok);
		
		bback.setSize(70, 30);
		bback.setLocation(115, 100);
		bback.addActionListener(this);
		this.add(bback);
		
		bexit.setSize(70, 30);
		bexit.setLocation(205, 100);
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
		else if (obj.equals(bback)){
			query.setVisible(true);
			this.dispose();
		}
		else if (obj.equals(bok)){
			if (text.getText() == null){
				JOptionPane.showMessageDialog(null, "Please enter the population!");
			}
			else if (text.getText().equals("0")){
				JOptionPane.showMessageDialog(null, "The population can not be 0!");
			}
			else{
				this.population = Integer.parseInt(text.getText());
				Genetic problem = new Genetic(dimension, population);
				this.text.setText(null);
				problem.process();
			}
		}
	}

}
