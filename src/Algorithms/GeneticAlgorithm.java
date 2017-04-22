package Algorithms;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.Random;

import edu.kzoo.grid.Grid;
import edu.kzoo.grid.Location;

public class GeneticAlgorithm {
	Random random = new Random();
	List<Chromosome> allChromosomes;
	ArrayList<Location> bestRoute;
	Chromosome best;
	Grid grid;
	Location initial;
	Location goal;
	
	public GeneticAlgorithm(Location initial, Location goal, Grid grid) {
		this.grid = grid;
		this.initial = initial;
		this.goal = goal;
		this.allChromosomes = new ArrayList<Chromosome>();
		
		//Populating allChromosomes with randomly generated chromosomes
		for (int i = 0; i < 100; i++)
			allChromosomes.add(new Chromosome(initial, goal, grid));
		
		this.best = allChromosomes.get(0);
		
		//Run the genetic algorithm 1000 times
//		for (int i = 0; i < 1000; i++) {
//			
//			this.crossover();
//			this.massMutation();
//			
//		}
		
		ArrayList<Location> neighbors = grid.neighborsOf(best.simulate());
		while ( !neighbors.contains(best.getGoal()) ) {
			
			this.crossover();
			this.massMutation();
			this.fittestOfTheFit();
			
			neighbors = grid.neighborsOf(best.simulate());
		}

		
//		for (Chromosome chromosome : allChromosomes) {
//			System.out.println(chromosome.moves);
//			chromosome.decode();
//			System.out.println(chromosome.getLocations().toString());
//		}
		
		//set best route
		fittestOfTheFit();
		this.best.decode();
		this.bestRoute = best.getLocations();
		
		System.out.println("Initial: " + best.getInitial().toString());
		System.out.println("Goal: " + best.getGoal().toString());
		System.out.println("Final: " + best.simulate().toString());
		
	}

	//Pulls the fittest individual from the list of chromosomes
	public void fittestOfTheFit() {

		ArrayList<Location> neighbors;
		Chromosome fittest = this.allChromosomes.get(0);
		for (Chromosome chromosome : this.allChromosomes) {
			//need to grab all neighbors of the chromosomes final location
			neighbors = grid.neighborsOf(chromosome.simulate());
			//Check if goal location is neighbor to final location
			if ( neighbors.contains(chromosome.getGoal()) && chromosome.fitness() > fittest.fitness())
				this.best = chromosome;
		}
	}
	
	//Creates two new offspring chromosomes using one-point crossover between two parent chromosomes
	public void crossover() {
		
		//return list of chromosomes
		List<Chromosome> newGeneration = new ArrayList<Chromosome>();

		int crossoverPoint;
		List<Chromosome> parents;
		Chromosome parent1;
		Chromosome parent2;
		Chromosome child1;
		Chromosome child2;
		int counter = allChromosomes.size()/2;
		
		for (int i = 0; i < counter; i++) {
			parents = survivalOfTheRandom();
			parent1 = parents.get(0);
			parent2 = parents.get(1);
			
			crossoverPoint = random.nextInt( (parent1.getMaxDistance()-1) + 1 ) + 1;
			
			child1 = new Chromosome(parent1.getInitial(), parent1.getGoal(), parent1.getGrid());
			child1.setMoves( parent1.getMoves().substring(0, crossoverPoint) + parent2.getMoves().substring(crossoverPoint) );
			
			child2 = new Chromosome(parent2.getInitial(), parent2.getGoal(), parent2.getGrid());
			child1.setMoves( parent2.getMoves().substring(0, crossoverPoint) + parent1.getMoves().substring(crossoverPoint) );
			
			newGeneration.add(child1);
			newGeneration.add(child2);
		}

		this.allChromosomes = newGeneration;

	}
	
	//Mutates all of the chromosomes in the list
	public void massMutation() {
		
		for (Chromosome chromosome : this.allChromosomes)
			chromosome.mutate();
		
	}
	
	//Selects the two fittest individuals from the chromosome list, a form of selection
	public List<Chromosome> survivalOfTheRandom() {
		
		//List of the 2 fittest parent chromosomes in the list
		List<Chromosome> fittest = new ArrayList<Chromosome>();
		int currentIndex = 0;
		Chromosome fitParent1 = allChromosomes.get(new Random().nextInt(allChromosomes.size()-1));
		Chromosome fitParent2 = allChromosomes.get(new Random().nextInt(allChromosomes.size()-1));
		
		fittest.add(0, fitParent1);
		fittest.add(1, fitParent2);
		
		allChromosomes.remove(fitParent1);
		allChromosomes.remove(fitParent2);

		return fittest;
		
	}
	
	//Getters and Setters
	
	public ArrayList<Location> getBestRoute() {
		return bestRoute;
	}
	
}
