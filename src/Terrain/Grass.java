package Terrain;

import edu.kzoo.grid.Grid;
import edu.kzoo.grid.Location;
import edu.kzoo.grid.PictureBlock;

public class Grass extends PictureBlock {
	//instance variables
	
	public Grass() {
		super("resources/Grass.png", "Simple Grass");
	}
	
	//Fills grid with grass
	public static void fillGrid(Grid grid) {
		for (int i = 0, j = 0; i <= grid.numRows()-1 && j <= grid.numCols()-1; j++) {
			grid.add(new Grass(), new Location(i, j));
			
			if (i != grid.numRows()-1 && j == grid.numCols()-1) {
				j = -1;
				i++;
			}
		}
	}
	
}
