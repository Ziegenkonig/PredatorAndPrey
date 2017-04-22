package Actors;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.Random;

import Algorithms.GeneticAlgorithm;
import Algorithms.SimulatedAnnealing;
import Terrain.Grass;
import Terrain.Water;
import edu.kzoo.grid.Grid;
import edu.kzoo.grid.GridObject;
import edu.kzoo.grid.Location;
import edu.kzoo.grid.PictureBlock;

//Predator is the main actor for this program; it's job is to survive for
//100 ticks, using various algorithms to determine the best possible way
//to do so.

//Notes: Predator's goal is to SURVIVE for 100 ticks, which means that it needs to find the most
//efficient actions to refill it's hunger and thirst bars in terms of how many ticks it takes.
//Cost could be measured by calculating ticksSpent/hungerGained and ticksSpent/thirstGained per action(?)
//Therefore, an efficient route would have a low cost

public class Predator extends PictureBlock {
	//instance variables
	public double hunger; //refilled by eating prey
	public double thirst; //refilled by drinking from water terrain
	int actionCounter; //keeps track of number of ticks a given action has taken so far
	Prey huntingTarget; //used to keep track of the current prey target of the predator
	Water drinkingTarget; //used to keep track of the current water target of the predator
	ArrayList<Location> currentPath;
	Location position;
	Grid grid;
	public CurrentState state;
	
	public Predator() {
		super("resources/Predator.png", "Predator");
		this.hunger = 100.0;
		this.thirst = 100.0;
		this.position = new Location(0,0);
		this.state = CurrentState.IDLE;
	}
	
	public void initialize(Grid grid) {
		this.grid = grid;
		this.grid.remove(this.position);
    	this.grid.add(this, this.position);
	}
	
	//Called within tick(), decays the stats of the predator to simulate time passing
	public void decay() {
		
		this.hunger = this.hunger - 10;
		this.thirst = this.thirst - 10;
		
	}
	
	//Decides randomly which direction to move and checks those directions for invalid moves
	public void roam() {
		Random rando = new Random();
		boolean validDirection = false;
		List<GridObject> neighbors = this.allNeighbors();
		
		GridObject neighbor;
		while (!validDirection) {
			
			neighbor = neighbors.get(rando.nextInt(neighbors.size()));
			if ( neighbor instanceof Grass ) {
				this.move(neighbor.location());
				validDirection = true;
			}
			
		}
			
	}
		
	//Moves to the specified location (takes one tick)
	public void move(Location loc) {
		grid.remove(loc);
		this.changeLocation(loc);
		grid.add(new Grass(), this.position);
		this.position = loc;
	}
	
	//Decides which action to take
	public void stateController(Prey[] prey, Water[] water) {
		
		switch (this.state) {
			case IDLE: //Decide which action needs to be taken here depending on hunger/thirst
//				if (hunger <= 70.0)
//					initiateHunt(prey[0]);
				if (thirst <= 70.0)
					initiateMoveToWater(water[0]);
				else
					roam();
				break;
			
			case EATING: //eating takes 2 ticks
				if (actionCounter == 2) {
					actionCounter = 0;
					this.state = CurrentState.IDLE;
				}
				actionCounter ++;
				break;
				
			case MOVINGTOWATER: //while movingtowater we need to call moveToWater() every tick
				moveToWater();
				break;
				
			case DRINKING: //drinking takes 2 ticks
				this.state = CurrentState.IDLE;
				break;
			case HUNTING: //while hunting we need to call hunt() every tick
				hunt();
				break;
				
			case SLEEPING: //sleeping is not yet implemented
				break;
				
			case SNEAKING: //sneaking is not yet implemented
				break;
				
			case MOVING: //moving is done when on the way to water for now, further uses will be implemented later
				break;
				
			default:
				break;
		}
	}
	
	//Initiates the drinking cycle
	public void initiateMoveToWater(Water target) {
		
		if (this.allNeighbors().contains(drinkingTarget)) {
			this.state = CurrentState.DRINKING;
		} else {
			this.state = CurrentState.MOVINGTOWATER;
			this.drinkingTarget = target;
			GeneticAlgorithm algorithm = new GeneticAlgorithm(this.position, drinkingTarget.getPosition(), this.grid);
			this.currentPath = algorithm.getBestRoute();
			System.out.println(currentPath.toString());
		}
		
	}
	
	public void moveToWater() {
		
		if (this.allNeighbors().contains(drinkingTarget)) {
			this.thirst = 100.0;
			System.out.println("Drinking from: " + this.position);
			this.state = CurrentState.DRINKING;
		} else if (!this.currentPath.isEmpty()) {
			this.move(currentPath.get(0));
			this.currentPath.remove(0);
		}
		
	}
	
	//Initiates the hunting cycle
	public void initiateHunt(Prey target) {
		
		this.state = CurrentState.HUNTING;
		this.huntingTarget = target;
		
	}
	
	//the hunt cycle
	public void hunt() {
		
		//Getting current distance between predator and prey
		double distance = SimulatedAnnealing.distanceFormula(this.getPosition(), huntingTarget.getPosition());
		
		//If our current location is adjacent to the prey, then we need to attack and end the hunt
		if (this.allNeighbors().contains(huntingTarget))
			this.attack(huntingTarget);
		else {
			//Setting next move using simulated annealing algorithm
			Location nextMove = SimulatedAnnealing.simulatedAnnealingMove( grid, this, this.huntingTarget);
			
			//Making the actual move
			this.move(nextMove);
		}
	}
	
	//Attacks the specified target
	public void attack(Prey target) {
		System.out.println("Hunting Successful!");
		this.state = CurrentState.IDLE;
		this.hunger = 100.0;
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
	
	public void setLocation(Location position) {
		this.position = position;
	}
	
}
