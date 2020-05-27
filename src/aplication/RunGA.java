package aplication;

import core.Parameters;
import entities.GA;
import entities.Population;

public class RunGA {
	private   Integer numberOfIndividuals;
	private   Double mutationRate;
	private   Double crossOverRate;
	private   Integer numberOfDecisionVariables;
	private   Integer numberOfGenerations;

	public RunGA(Integer numberOfIndividuals, Double mutationRate, Double crossOverRate,
			Integer numberOfDecisionVariables, Integer numberOfGenerations) {
		this.numberOfIndividuals =numberOfIndividuals;
		this.mutationRate = mutationRate;
		this.crossOverRate = crossOverRate;
		this.numberOfDecisionVariables = numberOfDecisionVariables;
		this.numberOfGenerations = numberOfGenerations;
		
	}

	public RunGA() {
		
	}

	public Integer getNumberOfIndividuals() {
		return numberOfIndividuals;
	}

	public void setNumberOfIndividuals(Integer numberOfIndividuals) {
		this.numberOfIndividuals = numberOfIndividuals;
	}

	public Double getMutationRate() {
		return mutationRate;
	}

	public void setMutationRate(Double mutationRate) {
		this.mutationRate = mutationRate;
	}

	public Double getCrossOverRate() {
		return crossOverRate;
	}

	public void setCrossOverRate(Double crossOverRate) {
		this.crossOverRate = crossOverRate;
	}

	public Integer getNumberOfDecisionVariables() {
		return numberOfDecisionVariables;
	}

	public void setNumberOfDecisionVariables(Integer numberOfDecisionVariables) {
		this.numberOfDecisionVariables = numberOfDecisionVariables;
	}

	public Integer getNumberOfGenerations() {
		return numberOfGenerations;
	}

	public void setNumberOfGenerations(Integer numberOfGenerations) {
		this.numberOfGenerations = numberOfGenerations;
	}
	
/* GA starts here with a initial population created randomly based on its parameters */
	
	public void start(Parameters parameters) {
		int coupleQuantity = getCoupleQuantity(crossOverRate, numberOfIndividuals);
		Population pop = new Population(crossOverRate, mutationRate, numberOfIndividuals, numberOfDecisionVariables,
				coupleQuantity);
		GA ga=new GA(parameters, coupleQuantity, pop);
		ga.setPopulation();
		ga.percent();
		for (int i = 0; i <1100; i++) {
			ga.Selection();
			ga.crosover(parameters);
			ga.mutation();
			ga.nextGeneration();
			ga.elitism();

		}
	}
	
	
	/*Returns the number of couples that will be on crossover, this number will be used to create a matrix with index of the individuals which will be in the crossover function*/
	public int getCoupleQuantity(Double crossOverRate, int populationSize) {
		int aux;
		double result;
		result= (crossOverRate * numberOfIndividuals)/2;//calculate the number of couples that will be in crossover based on crossover rate, this is a given parameter of the GA in the Parameters file 
		aux= (int)Math.ceil(result);
		return(aux);		
	}		
}
