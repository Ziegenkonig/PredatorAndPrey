package Algorithms;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Random;
import java.util.Stack;

import Terrain.Grass;
import edu.kzoo.grid.Grid;
import edu.kzoo.grid.GridObject;
import edu.kzoo.grid.Location;

public class Chromosome {
	Random random = new Random();
	String moves;
	ArrayList<Location> locations;
	Location initial;
	Location goal;
	Grid grid;
	int maxDistance;
	
	//Creates a new chromosome with random directions
	public Chromosome(Location initial, Location goal, Grid grid) {
		//Declaring intial and goal and grid
		this.initial = initial;
		this.goal = goal;
		this.grid = grid;
		
		//Getting max distance between two points
		this.maxDistance = (int)(SimulatedAnnealing.distanceFormula(initial, goal));
		this.maxDistance = (int)(this.maxDistance + (this.maxDistance * .35));
		
		//declaring moves to be empty
		this.moves = "";
		
		//array that we will be randomly pulling from
		String[] directions = new String[4];
		directions[0] = "00"; //down
		directions[1] = "10"; //left
		directions[2] = "01"; //up
		directions[3] = "11"; //right
		
		//populating the string moves witb random directions
		for (int i = 0; i <= maxDistance; i++)
			this.moves = this.moves + directions[random.nextInt(4)];
		
		//Declaring locations array
		this.locations = new ArrayList<Location>();

	}
	
	//decodes the path from the moves string
	public void decode() {
		
		Location lastLoc = new Location(0,0);
		Location currentLoc = this.initial;
		String allMoves = this.moves;
		String currentMove = "";
		for (int i = 0; i < moves.length()/2; i++) {
			currentMove = allMoves.substring(0, 2);
			allMoves = allMoves.substring(2);
			currentLoc = simulateMove(currentMove, currentLoc);
			//Checking to make sure a duplicate move is not being made
			//Checking to see if the current index is 0, and if it is that we arent including a move where we are standing
			if (currentLoc != lastLoc && !(i == 0 && currentLoc == initial))
				this.locations.add(currentLoc);
			lastLoc = currentLoc;
		}
	}
	
	//the fitness function for a given population
	public double fitness() {
		Location finalLoc = this.simulate();
		return -SimulatedAnnealing.distanceFormula(finalLoc, this.goal) - (this.maxDistance/100);
	}
	
	//Mutates the current chromosome randomly
	public void mutate() {
		
		int mutationPoint = random.nextInt( ((moves.length()/2)-1) + 1 ) + 1;
		
		this.moves = this.moves.substring(0, mutationPoint) + random.nextInt(2) + this.moves.substring(mutationPoint+1);
		
	}
	
	//Runs through the moves of the chromosome and returns a final location
	public Location simulate() {
		
		Location finalLoc = this.initial;
		String allMoves = this.moves;
		String currentMove = "";
		for (int i = 0; i < this.moves.length()/2; i++) {
			
			currentMove = allMoves.substring(0, 2);
			allMoves = allMoves.substring(2);
			finalLoc = simulateMove(currentMove, finalLoc);
			
		}
		
		return finalLoc;
	}

	//Simulates a move given the binary representation of direction moved; returns location
	public Location simulateMove(String direction, Location newLoc) {
		
		switch (direction) {
			case "00":
				if ( (grid.objectAt(new Location(newLoc.row()+1, newLoc.col())) instanceof Grass) && grid.isValid(newLoc) ) {
					newLoc = new Location(newLoc.row()+1, newLoc.col());
					return newLoc;
				}
				
				break;
			case "10":
				if ( (grid.objectAt(new Location(newLoc.row(), newLoc.col()-1)) instanceof Grass) && grid.isValid(newLoc) ) {
					newLoc = new Location(newLoc.row(), newLoc.col()-1);
					return newLoc;
				}
				
				break;
			case "01":
				if ( (grid.objectAt(new Location(newLoc.row()-1, newLoc.col())) instanceof Grass) && grid.isValid(newLoc) ) {
					newLoc = new Location(newLoc.row()-1, newLoc.col());
					return newLoc;
				}
				
				break;
			case "11":
				if ( (grid.objectAt(new Location(newLoc.row(), newLoc.col()+1)) instanceof Grass) && grid.isValid(newLoc) ) {
					newLoc = new Location(newLoc.row(), newLoc.col()+1);
					return newLoc;
				}
				
				break;
			default:
				break;
		}
		
		return newLoc;
	}
	
	//Getters and Setters
	
	public String getMoves() {
		return moves;
	}
	
	public void setMoves(String moves) {
		this.moves = moves;
	}
	
	public ArrayList<Location> getLocations() {
		return locations;
	}

	public Location getInitial() {
		return initial;
	}

	public Location getGoal() {
		return goal;
	}

	public Grid getGrid() {
		return grid;
	}

	public int getMaxDistance() {
		return maxDistance;
	}
}
