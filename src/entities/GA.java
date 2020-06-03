package entities;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import entities.Population;

import core.Parameters;

public class GA {
	private static Random random = new Random();
	private Parameters parameters;
	public int coupleQuantity;
	public int [] []matrixIndexCouple;
	public Population population;
	public Double bestfitness;
	
	
	public GA(Parameters parameters, int coupleQuantity, Population population) {
		this.parameters = parameters;
		this.coupleQuantity = coupleQuantity;
		this.population= population;
		this.matrixIndexCouple= new int [coupleQuantity][2];
	}
	
	public Double getBestfitness() {
		return bestfitness;
	}

	public void setBestfitness(Double bestfitness) {
		this.bestfitness = bestfitness;
	}


	public int getCoupleQuantity() {
		return coupleQuantity;
	}


	public void setCoupleQuantity(int coupleQuantity) {
		this.coupleQuantity = coupleQuantity;
	}


	public int[][] getMatrixIndexCouple() {
		return matrixIndexCouple;
	}


	public void setMatrixIndexCouple(int[][] matrixIndexCouple) {
		this.matrixIndexCouple = matrixIndexCouple;
	}


	public Population getPopulation() {
		return population;
	}


	public void setPopulation() {
		
		this.population.setPopulation(population.generateInitialPopulation(parameters)) ;
	}
	
	
	/*Calculate the corresponding percentual value of the fitness of every individual  */
	public void percent() {
		for(Individual individual: this.population.getPopulation() ) {  
			individual.setFitnessPercent((individual.getFitnessValue()/population.populationFitnessSum));
		}
	}
	

/*Call the funciton roullete to select individuals, based on its fitness, in order to Fulfill one matrix with their index */
/*roulete is calledd twice to form a couple in each row of the matrix. We need couples to crossover function.*/
	public void Selection() {
		for(int i=0;i<coupleQuantity;i++) {
			for(int j=0;j<2;j++) {
				matrixIndexCouple[i][j]=roulete();
			}
		}
		
	}
	
	/*Function used to make selection, generate a random number [0,1[ and check if there is an individual with a percentual value in that range */
	/*Pick a random number and search for the corresponding individual*/	
	public int roulete() {
		double total = 0, roulleteValue;
		int k = 0;
		roulleteValue = random.nextDouble();// spin the wheel,the wheel is divided acordding to the percentual of the
											// individuals,
		for (Individual i : this.population.getPopulation()) {
																	
			if (roulleteValue >= total) {
				total = total + i.getFitnessPercent();
			} else {
				return k - 1;
			}
			
			k++;
		}
		return k - 1;
	}
		
	public void crosover(Parameters parameters) {
		int end, begin;
		int kcross = random.nextInt(parameters.numberOfDecisionVariables);// random number used to define which
																			// position of the array chromossomes, from
																			// the two parents, will be mixed.
		int direction = random.nextInt(2);// there are two directions: from the begining to kcross, or from kcross to
											// the end.
		if (direction == 1) {// this direction is from the position pointed by kcros to the number of
								// decision values, which is the last position of the array.
			end = parameters.numberOfDecisionVariables;// 2
			begin = kcross;
		} else {
			end = kcross;
			begin = 0;
		}

		for (int i = 0; i < coupleQuantity; i++) {
			makeChild(begin, end, direction, kcross, i, parameters);
			makeChild2(begin, end, direction, kcross, i, parameters);
		}
	}
/* access the selected positions of the chromossome array in order to get their values to mix with a chromossome at the same position of the
 another parent.*/	
	public void makeChild(int begin, int end, int direction, int kcross, int couple, Parameters parameters) {
		double child[] = new double[parameters.numberOfDecisionVariables];
		for (int j = begin; j < end; j++) {

			child[j] = (population.getPopulation().get(matrixIndexCouple[couple][0]).chromosomes
					.getChromossomeByposition(j) * 0.5)
					+ (population.getPopulation().get(matrixIndexCouple[couple][1]).chromosomes
							.getChromossomeByposition(j) * 0.5);
		}
		if (direction == 0) {// parts of the two parents are mixed, but the remain part of the child, which
								// is empty yet, must have fulfilled with the chromossome of the parent from the
								// first column of the matrixColumn.
			for (int k = kcross; k < parameters.numberOfDecisionVariables; k++) {
				child[k] = population.getPopulation().get(matrixIndexCouple[couple][0]).chromosomes
						.getChromossomeByposition(k);
			}
		} else {
			for (int m = 0; m < kcross; m++) {
				child[m] = population.getPopulation().get(matrixIndexCouple[couple][0]).chromosomes
						.getChromossomeByposition(m);
			}
		}

		Chromossomes c = new Chromossomes();
		c.setMaxConstraints(parameters.chromossome.getMaxConstraints());
		c.setMinConstraints(parameters.chromossome.getMinConstraints());
		Individual i = new Individual(0.0, 0.0, c, parameters.getNumberOfIndividuals() + couple);
		i.chromosomes.setChromossomes(child);
		population.getPopulation().add(i);

	}
	/*makeChild2 is alike to makeChild1, but generate childs with 90% of the first parent and 10% of the second */	
	public void makeChild2(int begin, int end, int direction, int kcross, int couple, Parameters parameters) {
		double child[] = new double[parameters.numberOfDecisionVariables];

		for (int j = begin; j < end; j++) {
			child[j] = (population.getPopulation().get(matrixIndexCouple[couple][0]).chromosomes
					.getChromossomeByposition(j) * 0.9)
					+ (population.getPopulation().get(matrixIndexCouple[couple][1]).chromosomes
							.getChromossomeByposition(j) * 0.1);
		}
		if (direction == 0) {
			for (int k = kcross; k < parameters.numberOfDecisionVariables; k++) {
				child[k] = population.getPopulation().get(matrixIndexCouple[couple][1]).chromosomes
						.getChromossomeByposition(k);
			}
		} else {
			for (int m = 0; m < kcross; m++) {
				child[m] = population.getPopulation().get(matrixIndexCouple[couple][1]).chromosomes
						.getChromossomeByposition(m);
			}
		}

		Chromossomes c = new Chromossomes();
		c.setMaxConstraints(parameters.chromossome.getMaxConstraints());
		c.setMinConstraints(parameters.chromossome.getMinConstraints());
		Individual i = new Individual(0.0, 0.0, c, parameters.getNumberOfIndividuals() + couple + coupleQuantity);
		i.chromosomes.setChromossomes(child);
		population.getPopulation().add(i);

	}
		
		/*There is a probability of mutation, if an individual has the chance to mutate ramdomly its chromossome will be choose and part of it will be changed, this change will be a sum of a random negative or positive number*/
	public void mutation() {
		double randomNumber;
		for (int i = parameters.getNumberOfIndividuals(); i < population.getPopulation().size(); i++) {
			randomNumber = ThreadLocalRandom.current().nextDouble();
			if (parameters.getMutationRate() >= randomNumber) {
				getDirection(i);// this function define which chromossome will be mutaded and call the fuction
								// mutateIndividual to do so
			}
		}
	}

	public void getDirection(int individualIndex) {
		double beta;
		int signal, kmut, j, k, begin, end, direction;
		kmut = ThreadLocalRandom.current().nextInt(parameters.getNumberOfDecisionVariables());
		direction = ThreadLocalRandom.current().nextInt(2);
		if (direction == 1) {
			end = parameters.getNumberOfDecisionVariables();
			begin = kmut;
		} else {
			end = kmut;
			begin = 0;
		}
		signal = ThreadLocalRandom.current().nextInt(2);
		if (signal == 0) {
			signal = (-1);
		}
		beta = ThreadLocalRandom.current().nextDouble();
		beta = beta * signal;
		mutateIndividual(begin, end, beta, individualIndex);
	}

	public void mutateIndividual(int begin, int end, double beta, int individualIndex) {
		double range = 0, mutatedValue = 0;

		for (int i = begin; i < end; i++) {
			range = population.getPopulation().get(individualIndex).chromosomes.maxConstraints[i]
					- population.getPopulation().get(individualIndex).chromosomes.minConstraints[i];
			mutatedValue = range * 0.05 * beta;
			population.getPopulation().get(individualIndex).chromosomes.setChromossomeByposition(i, (mutatedValue
					+ population.getPopulation().get(individualIndex).chromosomes.getChromossomeByposition(i)));
			if (population.getPopulation().get(individualIndex).chromosomes.getChromossomeByposition(
					i) > population.getPopulation().get(individualIndex).chromosomes.getMaxConstraints()[i]) {
				population.getPopulation().get(individualIndex).chromosomes.setChromossomeByposition(i,
						population.getPopulation().get(individualIndex).chromosomes.maxConstraints[i]);
			}else
				if (population.getPopulation().get(individualIndex).chromosomes.getChromossomeByposition(
						i) < population.getPopulation().get(individualIndex).chromosomes.getMinConstraints()[i]) {
					population.getPopulation().get(individualIndex).chromosomes.setChromossomeByposition(i,
							population.getPopulation().get(individualIndex).chromosomes.getMinConstraints()[i]);
				}
		}
	}
/* Elitism means pick the individual with the best fitness value and make sure it will be in the next generation*/
/*The others individuals have to be picked randomly to ensure diversity in the next generation, this will make the algorithm not be stucked in local minimuns or maximus*/
	public void elitism() {
		int index;
		//Double bestfitness;
		List<Individual> newPop = new ArrayList();
		for (Individual i : population.getPopulation()) {
			newPop.add(i);
		}
		Collections.sort(newPop);
		population.getPopulation().clear();

		for (int i = 0; i < parameters.getNumberOfIndividuals(); i++) {
			if (i == 0) {
				population.getPopulation().add(i, newPop.get(0));
			} else {
				index = ThreadLocalRandom.current().nextInt(parameters.getNumberOfIndividuals());
				population.getPopulation().add(i, newPop.get(index));
			}
		}
		population.getPopulation().get(0).setFitnessValue(population.getPopulation().get(0).getFitnessValue() * -1);
		this.bestfitness = population.getPopulation().get(0).getFitnessValue();
		returnVariables();
	}
		
	public void nextGeneration() {
		population.setPopulationFitnessSum(0);
		for (Individual i : population.getPopulation()) {
			i.setFitnessValue(i.fitness());
			population.setPopulationFitnessSum(population.getPopulationFitnessSum() + i.getFitnessValue());
		}
		percent();
	}
	public double[] returnVariables(){
		return population.getPopulation().get(0).getChromosomes().chromossomes ;
	}

}
