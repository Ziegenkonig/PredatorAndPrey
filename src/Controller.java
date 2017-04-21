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
    
    private int tickCounter;
    Predator predator;
    Prey[] preyArray;
    
    /** Constructs a new Controller object. **/
    public Controller(GridAppFrame gui) {
        this.gui = gui;
        tickCounter = 0;
        this.predator = new Predator();
    }
    
    //Runs once the start button is pressed
    public void ExecuteLogic() {
    	Grid grid = gui.getGrid();
    	
    	//Initializing the objects in the grid
    	Grass.fillGrid(grid);
    	predator.initialize(grid);
    	Water.waterPopulate(grid);
    	Prey prey = new Prey();
    	preyArray = prey.preyPopulate(grid);
    	
    	gui.showGrid();
    	
    	while (tickCounter <= 50) {
    		
    		//Here we are printing important info into the console for debugging
    		System.out.println("|Predator's Hunger: " + predator.hunger + " | Currently: " + predator.state + " | Turn: " + tickCounter);
    		
    		predator.stateController(preyArray);
    		
//    		for (Prey currentPrey : preyArray)
//    			currentPrey.roam(grid);
    		
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
}
