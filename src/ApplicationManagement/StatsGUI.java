package ApplicationManagement;
import java.awt.Container;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

import Actors.Predator;

public class StatsGUI {
	//Instantiate Instance variables
	//General
	JFrame frame;
	JPanel content;
	Predator predator;
	int tickCounter;
	//Components
	JProgressBar hungerBar;
	JProgressBar thirstBar;
	JLabel hungerLabel;
	JLabel thirstLabel;
	JLabel stateLabel;
	JLabel turnLabel;
	JLabel waterLabel;
	JLabel preyLabel;
	JLabel placeholder;
	
	GridLayout statsLayout;
	
	public StatsGUI(Predator predator) {
		//Declare instance variables
		this.frame = new JFrame("Simulation Statistics");
		this.predator = predator;
		hungerBar = new JProgressBar();
		thirstBar = new JProgressBar(); 
		hungerLabel = new JLabel();
		thirstLabel = new JLabel();
		stateLabel = new JLabel();
		turnLabel = new JLabel();
		waterLabel = new JLabel();
		preyLabel = new JLabel();
		placeholder = new JLabel();
		
		content = new JPanel();
		
		frame.add(content);
		//what happens on close
		this.frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		//Size frame
		this.frame.setSize(500, 200);
		
		//Setting progress bars/labels
		hungerLabel.setText("Hunger: ");
		hungerBar.setValue((int) predator.getHunger()); hungerBar.setStringPainted(true);
		thirstLabel.setText("Thirst: ");
		thirstBar.setValue((int) predator.getThirst()); thirstBar.setStringPainted(true);
		stateLabel.setText("Currently: " + predator.getState().toString());
		turnLabel.setText("Turn: " + tickCounter);
		
		statsLayout = new GridLayout(0,2);
		content.setLayout(statsLayout);
		
		//Adding to frame
		content.add(hungerLabel);
		content.add(hungerBar);
		content.add(thirstLabel);
		content.add(thirstBar);
		content.add(stateLabel);
		content.add(placeholder);
		content.add(turnLabel);
		content.add(placeholder);
		content.add(waterLabel);
		content.add(preyLabel);
		
		//Show frame
		this.frame.setVisible(true);
		
	}
	
	public void refresh() {
		//Increment tick counter
		tickCounter++;
		
		//Setting progress bars/labels
		hungerBar.setValue((int) predator.getHunger());
		thirstBar.setValue((int) predator.getThirst());
		stateLabel.setText("Currently: " + predator.getState().toString());
		turnLabel.setText("Turn: " + tickCounter);
		if (predator.getDrinkingTarget() != null)
			waterLabel.setText("Moving to drink water at: " + predator.getDrinkingTarget().getPosition().toString());
		else
			waterLabel.setText("");
		if (predator.getHuntingTarget() != null)
			preyLabel.setText("Moving to attack prey at: " + predator.getHuntingTarget().getPosition().toString());
		else
			preyLabel.setText("");
		
		statsLayout = new GridLayout(0,2);
		content.setLayout(statsLayout);
		
		//Show frame
		this.frame.setVisible(true);
		
		//refresh frame
		frame.revalidate();
		frame.repaint();
	}
	
}
