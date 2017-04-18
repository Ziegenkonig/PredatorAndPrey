package Terrain;

import java.util.Random;

import Actors.Predator;
import edu.kzoo.grid.Grid;
import edu.kzoo.grid.Location;
import edu.kzoo.grid.PictureBlock;

public class Water extends PictureBlock {
	//instance variables
	
	public Water() {
		super("resources/Water.png", "Simple Water");
	}
	//Populates 3 water tiles randomly throughout the grid
	public static void waterPopulate(Grid grid) {
		Random rando = new Random();
		Location loc = new Location(0,0);
		
		for (int i = 0; i < 3; i++) {
			loc = new Location(rando.nextInt(9), rando.nextInt(9));
			if (grid.objectAt(loc) instanceof Predator) {
				i--;
			} else {
				grid.remove(loc);
				grid.add(new Water(), loc);
			}
		}
		
	}
}