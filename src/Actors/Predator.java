package Actors;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import Terrain.Grass;
import edu.kzoo.grid.Grid;
import edu.kzoo.grid.GridObject;
import edu.kzoo.grid.Location;
import edu.kzoo.grid.PictureBlock;

//Predator is the main actor for this program; it's job is to survive for
//100 ticks, using various algorithms to determine the best possible way
//to do so.

public class Predator extends PictureBlock {
	//instance variables
	double hunger; //refilled by eating prey
	double thirst; //refilled by drinking from water terrain
	Location position;
	
	public Predator() {
		super("resources/Predator.png", "Predator");
		this.hunger = 100.0;
		this.thirst = 100.0;
		this.position = new Location(0,0);
	}
	
	public void initialize(Grid grid) {
		grid.remove(this.position);
    	grid.add(this, this.position);
	}
	
	//Decides randomly which direction to move and checks those directions for invalid moves
	public void roam(Grid grid) {
		Random rando = new Random();
		boolean validDirection = false;
		List<GridObject> neighbors = this.allNeighbors(grid);
		
		GridObject neighbor;
		while (!validDirection) {
			
			neighbor = neighbors.get(rando.nextInt(neighbors.size()));
			if ( neighbor instanceof Grass) {
				this.move(grid, neighbor.location());
				validDirection = true;
			}
			
		}
			
	}
		
	//Moves to the specified location
	public void move(Grid grid, Location loc) {
		grid.remove(loc);
		this.changeLocation(loc);
		grid.add(new Grass(), this.position);
		this.position = loc;
	}
	
	//Returns all neighbors to the prey object
	public ArrayList<GridObject> allNeighbors(Grid grid) {
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
	
	public void setLocation(Location position) {
		this.position = position;
	}
	
}
