package ApplicationManagement;
import java.awt.Color;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import Actors.Predator;
import Actors.Prey;
import Terrain.Grass;
import Terrain.Water;
import edu.kzoo.grid.ColorBlock;
import edu.kzoo.grid.Grid;
import edu.kzoo.grid.GridObject;
import edu.kzoo.grid.Location;
import edu.kzoo.grid.gui.GridAppFrame;

public class Controller {
	 // Instance variable(s) for each Controller instance.
    private GridAppFrame gui;
    private Grid grid;
    
    private int tickCounter;
    Predator predator;
    Prey[] preyArray;

	Water[] waterArray;
    StatsGUI statsWindow;
    
    /** Constructs a new Controller object. **/
    public Controller(GridAppFrame gui) {
        this.gui = gui;
        tickCounter = 0;
        this.predator = new Predator();
        grid = gui.getGrid();
    }
    
    //Runs once the start button is pressed
    public void ExecuteLogic() {
       	grid = gui.getGrid();
    	grid.removeAll();
    	tickCounter = 0;
    	statsWindow = new StatsGUI(predator);
    	
    	//Initializing the objects in the grid
    	Grass.fillGrid(grid);
    	
    	Water water = new Water(grid);
    	waterArray = water.waterPopulate(3);
    	Prey prey = new Prey(grid);
    	preyArray = prey.preyPopulate(3);
    	predator.initialize(grid, this);
    	
    	gui.showGrid();
    	
    	while (tickCounter <= 150) {
    		statsWindow.refresh();
    		//Here we are printing important info into the console for debugging
    		//System.out.println("|Predator's Hunger: " + predator.hunger + "|Predator's Thirst: " + predator.thirst + " | Currently: " + predator.state + " | Turn: " + tickCounter);
    		
    		int index = 0;
    		for (Prey currentPrey : preyArray) {
    			
    			if (currentPrey == null)
    				preyArray[index] = prey.spawn();
    			else
    				currentPrey.roam(grid);
    			
    			index++;
    		}
    		
    		predator.stateController();
    		
    		gui.showGrid();
    		tick();
    	}

    }

	//Simulated a period of 1 second wherein nothing is moving on the grid
    public void tick(){
    	try {
			Thread.sleep(100);
			tickCounter++;
			predator.decay();
			
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
    }
    
    public Water[] getWaterArray() {
		return waterArray;
	}

	public void setWaterArray(Water[] waterArray) {
		this.waterArray = waterArray;
	}
    
    public Prey[] getPreyArray() {
		return preyArray;
	}

	public void setPreyArray(Prey[] preyArray) {
		this.preyArray = preyArray;
	}
}
