package Actors;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import Terrain.Grass;
import Terrain.Water;
import edu.kzoo.grid.Direction;
import edu.kzoo.grid.Grid;
import edu.kzoo.grid.GridObject;
import edu.kzoo.grid.Location;
import edu.kzoo.grid.PictureBlock;

public class Prey extends PictureBlock {
	//instance variables
	double hunger; //refilled by eating from grass terrain
	double thirst; //refilled by drinking from water terrain
	Location position;
	Grid grid;
	
	public Prey(Grid grid) {
		super("resources/Prey.png", "Prey");
		this.hunger = 100.0;
		this.thirst = 100.0;
		this.position = new Location(0,0);
		this.grid = grid;
	}
	
	//Add single prey randomly into grid
	public Prey spawn() {
		Random rando = new Random();
		Location loc = new Location(0,0);
		
		Prey prey = new Prey(this.grid);
		
		loc = new Location(rando.nextInt(this.grid.numRows()-1), rando.nextInt(this.grid.numCols()-1));
		
		boolean placed = false;
		while (!placed) {
			if ( !(grid.objectAt(loc) instanceof Predator || grid.objectAt(loc) instanceof Water) ) {
				prey.setPosition(loc);
				grid.remove(loc);
				grid.add(prey, loc);
				placed = true;
			}
		}
		return prey;
	}
	
	//Randomly populates the grid with 3 prey
	public Prey[] preyPopulate(int amount) {
		Random rando = new Random();
		Location loc = new Location(0,0);
		Prey[] preyArray = new Prey[amount];
		
		boolean placed = false;
		for (int i = 0; i < 3; i++) {
			loc = new Location(rando.nextInt(this.grid.numRows()-1), rando.nextInt(this.grid.numCols()-1));
			
			while(!placed)
				if (grid.objectAt(loc) instanceof Predator || grid.objectAt(loc) instanceof Water) {
					i--;
				} else {
					preyArray[i] = new Prey(this.grid);
					preyArray[i].setPosition(loc);
					grid.remove(loc);
					grid.add(preyArray[i], loc);
					placed = true;
				}
			placed = false;
		}
		return preyArray;
	}
	
	//Decides randomly which direction to move and checks those directions for invalid moves
	public void roam(Grid grid) {
		Random rando = new Random();
		boolean validDirection = false;
		List<GridObject> neighbors = this.allNeighbors();
		
		GridObject neighbor;
		while (!validDirection) {
			
			neighbor = neighbors.get(rando.nextInt(neighbors.size()));
			if ( neighbor instanceof Grass && grid.isValid(neighbor.location())) {
				this.move(neighbor.location());
				validDirection = true;
			}
			
		}
			
	}
	
	//Moves to the specified location
	public void move(Location loc) {
		grid.remove(loc);
		this.changeLocation(loc);
		grid.add(new Grass(), this.position);
		this.position = loc;
	}
	
	//Returns all neighbors to the prey object
	public ArrayList<GridObject> allNeighbors() {
		ArrayList<GridObject> neighbors = new ArrayList<GridObject>();
		List<Location> locations = new ArrayList<Location>();
		locations = grid.neighborsOf(this.position);
		
		for (Location loc : locations)
			neighbors.add(grid.objectAt(loc));
			
		return neighbors;
	}
	
	public Location getPosition() {
		return this.position;
	}
	
	public void setPosition(Location position) {
		this.position = position;
	}
	
}