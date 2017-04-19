package Algorithms;

import java.util.ArrayList;
import java.util.List;

import edu.kzoo.grid.Direction;
import edu.kzoo.grid.Grid;
import edu.kzoo.grid.Location;

public class SimulatedAnnealing {
	//Returns distance between two points
	public static double distanceFormula(int x1, int x2, int y1, int y2) {
		
		double test = Math.sqrt( (double)(x1-x2)*(double)(x1-x2) + (double)(y1-y2)*(double)(y2-y1) );
		return test;
		
	}
	
	//
	public static double acceptanceProbability(double currentEnergy, double newEnergy, double temperature) {
		//If new solution is better, accept
		if (newEnergy < currentEnergy)
			return 1.0;

		//If new solution is worse, calculate an acceptance probability
		return Math.exp((currentEnergy - newEnergy) / temperature);
	}
	
	//Returns the best single move that will get you closer to the desired target
	public static Location simulatedAnnealingMove(Grid grid, Location initialLocation, Location finalLocation) {
		//bestMove keeps track of the best move found by simulated annealing
		Location bestMove = new Location(0,0);
		
		//set initial temp - this will determine how long the algorithm runs
		double temp = 1000;
		
		//set cooling rate - this will determine how quickly temp decreases
		double coolingRate = .003;
		
		double newEnergy;
		//Loop until system has cooled
		while (temp > 1) {
			//Creat new location
			Location newLoc = new Location(0, 0);
			
			//Get some random direction to move in
			Direction randomDirection = Direction.randomDirection();
			
			if (grid.getNeighbor(initialLocation, randomDirection).col() >= 0 &&
				grid.getNeighbor(initialLocation, randomDirection).row() >= 0) {
				//set newLoc to new location one space away from initialLocation in random direction
				newLoc = grid.getNeighbor(initialLocation, randomDirection);
				
				//Check new distance formula (newEnergy) and current distance formula (currentEnergy)
				//We pass this into the accepted probability function
				newEnergy = distanceFormula(newLoc.row(), finalLocation.row(), 
												   newLoc.col(), finalLocation.col());
				double currentEnergy = distanceFormula(initialLocation.row(), finalLocation.row(),
						 					  		   initialLocation.col(), finalLocation.col());
				double bestEnergy = distanceFormula(bestMove.row(), finalLocation.row(),
						  							bestMove.col(), finalLocation.col());
				
				//Decide if we accept the new location
				if ( acceptanceProbability(currentEnergy, newEnergy, temp) > Math.random())
					initialLocation = newLoc;
				
				//Setting best to newLoc if newLoc is better
				if ( currentEnergy < bestEnergy)
					bestMove = newLoc;
				
				//cool system
				temp *= 1-coolingRate;
			}
		}
		
		return bestMove;
	}
	
	//Returns the best path that will deliver you to the specified target
	public static List<Location> simulatedAnnealingPath(Grid grid, Location initialLocation, Location finalLocation) {
		//bestMove keeps track of the best move found by simulated annealing
		ArrayList<Location> bestPath = new ArrayList<Location>();
		
		//set initial temp - this will determine how long the algorithm runs
		double temp = 1000;
		
		//set cooling rate - this will determine how quickly temp decreases
		double coolingRate = .003;
		
		//keep track of current index
		int currentIndex = 0;
		
		//Loop until system has cooled
		while (temp > 1) {
			//Creat new location
			Location newLoc = new Location(0, 0);
			
			//Get some random direction to move in
			Direction randomDirection = Direction.randomDirection();
			
			if (grid.getNeighbor(initialLocation, randomDirection).col() >= 0 &&
				grid.getNeighbor(initialLocation, randomDirection).row() >= 0) {
				//set newLoc to new location one space away from initialLocation in random direction
				newLoc = grid.getNeighbor(initialLocation, randomDirection);
			
				//Check new distance formula (newEnergy) and current distance formula (currentEnergy)
				//We pass this into the accepted probability function
				double newEnergy = distanceFormula(newLoc.row(), finalLocation.row(), 
												   newLoc.col(), finalLocation.col());
				double currentEnergy = distanceFormula(initialLocation.row(), finalLocation.row(),
						 					  		   initialLocation.col(), finalLocation.col());
				double bestEnergy = distanceFormula(bestPath.get(currentIndex).row(), finalLocation.row(),
						  							bestPath.get(currentIndex).col(), finalLocation.col());
				
				//Decide if we accept the new location
				if ( acceptanceProbability(currentEnergy, newEnergy, temp) > Math.random())
					initialLocation = newLoc;
				
				//Setting best to newLoc if newLoc is better
				if ( currentEnergy < bestEnergy) {
					bestPath.add(newLoc);
					currentIndex ++;
				}
				
				//cool system
				temp *= 1-coolingRate;
			}
		}
		
		return bestPath;
	}
	
}
