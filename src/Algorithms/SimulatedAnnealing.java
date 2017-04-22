package Algorithms;

import java.util.ArrayList;
import java.util.List;

import Terrain.Grass;
import edu.kzoo.grid.Direction;
import edu.kzoo.grid.Grid;
import edu.kzoo.grid.GridObject;
import edu.kzoo.grid.Location;

public class SimulatedAnnealing {
	//Returns distance between two points
	public static double distanceFormula(Location location1, Location location2) {
		double test;
		
		if ( (location1.row() + location1.col()) >= (location2.row() + location2.col()) )
			test = Math.sqrt( (Math.pow((location1.row() - location2.row()), 2)) + (Math.pow((location1.col() - location2.col()), 2)) );
		else 
			test = Math.sqrt( (Math.pow((location2.row() - location1.row()), 2)) + (Math.pow((location2.col() - location1.col()), 2)) );
		
		return test;
		
	}
	
	//
	public static double acceptanceProbability(double currentEnergy, double newEnergy, double temperature) {
		//If new solution is better, accept
		if (newEnergy < currentEnergy)
			return 1.0;
		
		double prob = (double)Math.exp((currentEnergy - newEnergy) / temperature);
		//If new solution is worse, calculate an acceptance probability
		return Math.exp( (currentEnergy - newEnergy) / temperature );
	}
	
	//Returns the best single move that will get you closer to the desired target
	public static Location simulatedAnnealingMove(Grid grid, GridObject object1, GridObject object2) {
		
		//Setting the locations of object1 and object2
		Location initialLoc = object1.location();
		Location finalLoc = object2.location();
		
		//set initial temp - this will determine how long the algorithm runs
		double temp = 1000000.0;
		
		//set cooling rate - this will determine how quickly temp decreases
		double coolingRate = .003;
		
		//Loop until system has cooled
		while (temp > 1) {
			//Creat new location
			Location newLoc = new Location(0, 0);
			
			//Get some random direction to move in
			Direction randomDirection = Direction.randomDirection();
			
			//Check to make sure new location will be acceptable
			if ( grid.isValid(grid.getNeighbor(initialLoc, randomDirection)) &&
			   ( grid.objectAt(grid.getNeighbor(initialLoc, randomDirection)) ) instanceof Grass) {
				//set newLoc to new location one space away from initialLocation in random direction
				newLoc = grid.getNeighbor(initialLoc, randomDirection);
				
				//Check new distance formula (newEnergy) and current distance formula (currentEnergy)
				//We pass this into the accepted probability function
				double newEnergy = distanceFormula(newLoc, finalLoc);
				double currentEnergy = distanceFormula(initialLoc, finalLoc);
				
				//Decide if we accept the new location
				if ( acceptanceProbability(currentEnergy, newEnergy, (coolingRate/temp)) > Math.random() )
					return newLoc;
				
				//cool system
				temp *= 1-coolingRate;
			}
		}
		return null;
	}
	
}
