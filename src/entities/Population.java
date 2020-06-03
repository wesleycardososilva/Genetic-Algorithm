package entities;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import com.sun.jmx.remote.util.OrderClassLoaders;

import core.Parameters;

public class Population  {
	
	private  List<Individual> population = new ArrayList();
	private static Random random = new Random();
	private  Double crossOverRate;
	private  Double mutationRate;
	private  int populationSize;
	private static int iterationNumber;
	private int numberOfDecisionVariables;
	public double populationFitnessSum;
	public int [] []matrixIndexCouple;
	public int  coupleQuantity;


	public Population(Double crossOverRate, Double mutationRate, int populationSize, int numberOfDecisionVariables,
			 int coupleQuantity) {
		super();
		this.crossOverRate = crossOverRate;
		this.mutationRate = mutationRate;
		this.populationSize = populationSize;
		this.numberOfDecisionVariables = numberOfDecisionVariables;
		this.coupleQuantity = coupleQuantity;
		this.matrixIndexCouple= new int [coupleQuantity][2];
		this.population= population;
	}

	public List<Individual> getPopulation() {
		return population;
	}


	public void setPopulation(List<Individual> population) {
		this.population = population;
	}

	public Double getCrossOverRate() {
		return crossOverRate;
	}


	public void setCrossOverRate(Double crossOverRate) {
		this.crossOverRate = crossOverRate;
	}


	public Double getMutationRate() {
		return mutationRate;
	}


	public void setMutationRate(Double mutationRate) {
		this.mutationRate = mutationRate;
	}


	public int getPopulationSize() {
		return populationSize;
	}


	public void setPopulationSize(int populationSize) {
		this.populationSize = populationSize;
	}


	public int getCoupleQuantity() {
		return coupleQuantity;
	}


	public void setCoupleQuantity(int coupleQuantity) {
		this.coupleQuantity = coupleQuantity;
	}


	public int getNumberOfDecisionVariables() {
		return numberOfDecisionVariables;
	}


	public double getPopulationFitnessSum() {
		return populationFitnessSum;
	}


	public void setPopulationFitnessSum(double populationFitnessSum) {
		this.populationFitnessSum = populationFitnessSum;
	}
	
	/* Generate initial population using random numbers with their respective range defined by min and max constraint of each decision variables*/	
	public List<Individual> generateInitialPopulation(Parameters parameters) {
		
		for(int i =0; i<populationSize;i++) {
			Chromossomes c = new Chromossomes(numberOfDecisionVariables);
			c.setChromossomes(getRandomNumber(parameters));
			c.setMaxConstraints(parameters.chromossome.maxConstraints);
			c.setMinConstraints(parameters.chromossome.getMinConstraints());
			Individual individual = new Individual(0.0,0.0, c,i);					
			individual.setFitnessValue(individual.fitness());//after calculate the fitness value we need the sum of all individuals fitness value,
			populationFitnessSum=populationFitnessSum +individual.getFitnessValue(); //so we can calculate the percentual value of every individual, this value will be used in the Selection function(Roullete)					 
			population.add(individual);	
			int index=population.indexOf(individual);	
		}
		return this.population;
		 
	}
	
/*Generate a random number defined by the min and max constraints of the current decision variable*/	
	public double[] getRandomNumber(Parameters parameters) {
		double  decisionVariables[] = new double[numberOfDecisionVariables] ;
		Individual ind = new Individual();
		
		for(int i =0 ; i<numberOfDecisionVariables;i++) {	
			decisionVariables[i]=ThreadLocalRandom.current().nextDouble(parameters.chromossome.minConstraints[i],parameters.chromossome.maxConstraints[i]);
		}
		return (decisionVariables);		
	}
		
	
}
