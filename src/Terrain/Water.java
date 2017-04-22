package Terrain;

import java.util.Random;

import Actors.Predator;
import Actors.Prey;
import edu.kzoo.grid.Grid;
import edu.kzoo.grid.Location;
import edu.kzoo.grid.PictureBlock;

public class Water extends PictureBlock {
	//instance variables
	Grid grid;
	Location position;
	
	public Water(Grid grid) {
		super("resources/Water.png", "Simple Water");
		this.position = new Location(0,0);
		this.grid = grid;
	}

	//Populates 3 water tiles randomly throughout the grid
	public Water[] waterPopulate(int amount) {
		Random rando = new Random();
		Location loc = new Location(0,0);
		Water[] waterArray = new Water[amount];
		
		for (int i = 0; i < 3; i++) {
			loc = new Location(rando.nextInt(grid.numRows()-1), rando.nextInt(grid.numCols()-1));
			if (grid.objectAt(loc) instanceof Predator) {
				i--;
			} else {
				waterArray[i] = new Water(this.grid);
				waterArray[i].setPosition(loc);
				grid.remove(loc);
				grid.add(waterArray[i], loc);
			}
		}
		return waterArray;
	}
	
	public Location getPosition() {
		return position;
	}
	public void setPosition(Location position) {
		this.position = position;
	}
}