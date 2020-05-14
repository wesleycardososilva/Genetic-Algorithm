package entities;

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
	
	private  List<IndividualPop> population = new ArrayList();
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
		//this.chromossome = chromossome;
		this.coupleQuantity = coupleQuantity;
		this.matrixIndexCouple= new int [coupleQuantity][2];
		this.population= population;
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
	public void generateInitialPopulation(Parameters parameters) {
		
		for(int i =0; i<populationSize;i++) {
			Chromossomes c = new Chromossomes(numberOfDecisionVariables);
			c.setChromossomes(getRandomNumber(parameters));
			c.setMaxConstraints(parameters.chromossome.maxConstraints);
			c.setMinConstraints(parameters.chromossome.getMinConstraints());
			IndividualPop individual = new IndividualPop(0.0,0.0, c,i);					
			individual.setFitnessValue(individual.fitness());//after calculate the fitness value we need the sum of all individuals fitness value,
			populationFitnessSum=populationFitnessSum +individual.getFitnessValue(); //so we can calculate the percentual value of every individual, this value will be used in the Selection function(Roullete)					 
			population.add(individual);	
			int index=population.indexOf(individual);		
		}
		 
	}
	
/*Generate a random number defined by the min and max constraints of the current decision variable*/	
	public double[] getRandomNumber(Parameters parameters) {
		double  decisionVariables[] = new double[numberOfDecisionVariables] ;//numberOfDecisionVariables
		IndividualPop ind = new IndividualPop();
		for(int i =0 ; i<numberOfDecisionVariables;i++) {	
			decisionVariables[i]=ThreadLocalRandom.current().nextDouble(parameters.chromossome.minConstraints[i],parameters.chromossome.maxConstraints[i]);
			//System.out.printf("valor de aleatorio %f \n",decisionVariables[i] );
		}
		return (decisionVariables);		
	}
	
/*Calculate the corresponding percentual value of the fitness of every individual  */
	public void percent() {
		for(Individual individual: population ) {  
			individual.setFitnessPercent((individual.getFitnessValue()/populationFitnessSum));
		}
		//Collections.sort(population);
	}

	/*Call the funciton roullete to select individuals, based on its fitness, in order to Fulfill one matrix with their index */
	public void Selection() {
		for(int i=0;i<coupleQuantity;i++) {
			for(int j=0;j<2;j++) {
				matrixIndexCouple[i][j]=roulete();//roulete is calledd twice to form a couple in each row of the matrix. We need couples to crossover function.
			}
		}
		
	}
	
	public void print() {
		//IndividualPop individual = new IndividualPop(0.0,0.0, chromossome,0);
		for(int i=0;i<coupleQuantity;i++) {
			for(int j=0;j<matrixIndexCouple[0].length;j++) {
				
				System.out.printf(" %d", matrixIndexCouple[i][j]);
				
			}
			System.out.println(" \n");
		}
	}

/*Function used to make selection, generate a random number [0,1[ and check if there is an individual with a percentual value in that range */
/*Pick a random number and search for the corresponding individual*/	
	public int roulete() {
		double total=0, roulleteValue;
		int k=0;
		roulleteValue=random.nextDouble();//spin the wheel,the wheel is divided acordding  to the percentual of the individuals, 
		
		for(Individual i : population) {//if there is an individual with 50% of the fitness somatorium, it will have half of the wheell as its range, so it will have 50% of chance.
			if(roulleteValue>=total) {//roulleteValue is compared to fitnessPercent, if its not in the range, total will acumulate the value in order to advance in the search for the winner
				total=total+ i.getFitnessPercent();// total starts with 0, if the first element is the winner, it will picked in the second loop.	
			}else {	
				return k-1;	
			}
			k++;
		}
		return k-1;//Always wil be an individual that fits in the random number, if wasn't anyone of the others, so it's the last one.
		//return lastIndividual; 
	}
		
	public void crosover(Parameters parameters) {
		int end, begin;
		int kcross= random.nextInt(numberOfDecisionVariables);//random number used to defined which position of the array chromossomes, from the two parents, will be mixed. 
		//System.out.printf("valor do kcross %d\n", kcross);
		//System.out.printf("valor de kcross %d", kcross);
		int direction= random.nextInt(2);//there are two directions: from the begining to kcross, or from kcross to the end.
		//System.out.printf("valor de direction %d\n", direction);		
		if(direction==1) {//this direction is from the position pointed by kcros to the number of decision values, which is the last position of the array.
			end= numberOfDecisionVariables;//2
		    begin= kcross;
		}else {
			end= kcross;
			begin=0;
		}
		
		for(int i =0; i< coupleQuantity;i++) {//generate two childs per couple
			makeChild(begin, end, direction, kcross, i,parameters);
			makeChild2(begin, end, direction, kcross, i,parameters);
		}
			
		//for(IndividualPop i : population) {
			//System.out.printf("*** %f %f\n", i.chromosomes.getChromossomeByposition(0),i.chromosomes.getChromossomeByposition(1));
		//}
		
	}
/*makechild1 generate and add, to the population, a child with 50% of the first parent and 50% of the second parent from the matrixCouple  */	
	public  void makeChild(int begin, int end,int direction, int kcross, int couple, Parameters parameters) {
		double child[]=new double[numberOfDecisionVariables];
		for(int j=begin;j<end;j++) {//access the selected positions of the chromossome array in order to get their values to mix with a chromossome at the same position of the another parent.
			
			child[j]=(population.get(matrixIndexCouple[couple][0]).chromosomes.getChromossomeByposition(j)*0.5)+(population.get(matrixIndexCouple[couple][1]).chromosomes.getChromossomeByposition(j)*0.5);				
		}
		if(direction==0) {// parts of the two parents are mixed, but the remain part of the child, which is empty yet, must have fulfilled with the chromossome of the parent from the first column of the matrixColumn.   
			for(int k =kcross;k< numberOfDecisionVariables; k++) {
				child[k]=population.get(matrixIndexCouple[couple][0]).chromosomes.getChromossomeByposition(k);
			}
		}else {
			for(int m=0; m<kcross;m++) {
					child[m]=population.get(matrixIndexCouple[couple][0]).chromosomes.getChromossomeByposition(m);
				}
			}
		Chromossomes c= new Chromossomes();
		c.setMaxConstraints(parameters.chromossome.getMaxConstraints());
		c.setMinConstraints(parameters.chromossome.getMinConstraints());
		IndividualPop i=new IndividualPop(0.0, 0.0, c, populationSize+couple);
		i.chromosomes.setChromossomes(child);
		//System.out.printf("+++ valor de child %f %f\n", i.chromosomes.getChromossomeByposition(0),i.chromosomes.getChromossomeByposition(1));
		population.add(i);
		
	}
/*makeChild2 is alike to makeChild1, but generate childs with 90% of the first parent and 10% of the second */	
	public void makeChild2(int begin, int end,int direction, int kcross, int couple,Parameters parameters) {
		double child[]=new double[numberOfDecisionVariables];
		for(int j=begin;j<end;j++) {
			child[j]=(population.get(matrixIndexCouple[couple][0]).chromosomes.getChromossomeByposition(j)*0.9)+(population.get(matrixIndexCouple[couple][1]).chromosomes.getChromossomeByposition(j)*0.1);				
		}
		if(direction==0) {
			for(int k =kcross;k< numberOfDecisionVariables; k++) {
				child[k]=population.get(matrixIndexCouple[couple][1]).chromosomes.getChromossomeByposition(k);
			}
		}else {
			for(int m=0; m<kcross;m++) {
					child[m]=population.get(matrixIndexCouple[couple][1]).chromosomes.getChromossomeByposition(m);
				}
			}
		Chromossomes c= new Chromossomes();
		c.setMaxConstraints(parameters.chromossome.getMaxConstraints());
		c.setMinConstraints(parameters.chromossome.getMinConstraints());
		IndividualPop i=new IndividualPop(0.0, 0.0, c, populationSize+couple+coupleQuantity);
		i.chromosomes.setChromossomes(child);
		population.add(i);
		
	}
	/*There is a probability of mutation, if an individual has the chance to mutate ramdomly its chromossome will be choose and part of it will be changed, this change will be a sum of a random negative or positive number*/
	public void mutation() {
		double randomNumber;
		for(int i=populationSize;i<population.size();i++) {
			randomNumber=ThreadLocalRandom.current().nextDouble();
			if(mutationRate>=randomNumber) {
				getDirection(i);//this function define which chromossome will be mutaded and call the fuction mutateIndividual to do so
			}
		}
	}
	public void getDirection(int individualIndex) {
		double beta ;
		int signal,kmut, j,k, begin,end,direction;
		kmut=ThreadLocalRandom.current().nextInt(numberOfDecisionVariables);		
		direction=ThreadLocalRandom.current().nextInt(2);
		if(direction==1) {
			end = numberOfDecisionVariables;
			begin = kmut;
		}else {
			end= kmut;
			begin = 0;
		}
		signal=ThreadLocalRandom.current().nextInt(2);
		if(signal==0) {//if signal is zero we have to sum a negative number to the chosen chromossome
			signal=(-1);
		}
		beta=ThreadLocalRandom.current().nextDouble();
		beta=beta*signal;
		mutateIndividual(begin,end,beta, individualIndex);
	}
	public void mutateIndividual(int begin, int end,double beta, int individualIndex) {
		double range=0, mutatedValue=0;
		for(int i=begin; i<end;i++) {
			range=population.get(individualIndex).chromosomes.maxConstraints[i]-population.get(individualIndex).chromosomes.minConstraints[i];
			mutatedValue= range*0.05*beta;
			population.get(individualIndex).chromosomes.setChromossomeByposition(i, (mutatedValue+population.get(individualIndex).chromosomes.getChromossomeByposition(i)));
			if(population.get(individualIndex).chromosomes.getChromossomeByposition(i)>population.get(individualIndex).chromosomes.getMaxConstraints()[i]) {
				population.get(individualIndex).chromosomes.setChromossomeByposition(i, population.get(individualIndex).chromosomes.maxConstraints[i]);
			}
			if(population.get(individualIndex).chromosomes.getChromossomeByposition(i)<population.get(individualIndex).chromosomes.getMinConstraints()[i]) {
				population.get(individualIndex).chromosomes.setChromossomeByposition(i, population.get(individualIndex).chromosomes.getMinConstraints()[i]);
			}
			//System.out.printf("valor do cromossomo no individuo %f\n", population.get(individualIndex).chromosomes.getChromossomeByposition(i));		
		}
		//nextGeneration();
	}
	public void elitism() {
		int index;
		double bestfitness;
		List<IndividualPop> newPop = new ArrayList();
		//newPop=population;
		for(IndividualPop i: population) {
			newPop.add(i);			
		}
		Collections.sort(newPop);
		population.clear();
		
		for(int i=0;i<populationSize;i++) {
			if(i==0) {
				//System.out.println("entrou no if do primeiro elemento");
				population.add(i, newPop.get(i));
			}else {
				index=ThreadLocalRandom.current().nextInt(populationSize);
				//index=(index*i)%population.size();
				population.add(i, newPop.get(index));
			}
		}
		System.out.printf("*+*+*valor de tamanho da populacao %d\n", population.size());
		for(IndividualPop i : population) {
			//System.out.printf("*** %f %f\n", i.chromosomes.getChromossomeByposition(0),i.chromosomes.getChromossomeByposition(1));
			//System.out.printf("valor de fitness %f\n", i.getFitnessValue());
		}
		population.get(0).setFitnessValue(population.get(0).getFitnessValue()*-1);
		System.out.printf("valor de fitness do primeiro elemento %f\n", population.get(0).getFitnessValue());
		System.out.printf("valor de cromossomo do primeiro elemento %f %f\n", population.get(0).chromosomes.getChromossomeByposition(0),population.get(0).chromosomes.getChromossomeByposition(1));
		//System.out.printf("valor de index do primeiro elemento %d", population.get(0).);
		//return (bestfitness=population.get(0).getFitnessValue());
	}
	/*Preparing population to the next generation by calculating the fitness of the new individuals and seting their values of percent*/
	public void nextGeneration() {
		populationFitnessSum=0;
		for(Individual i: population) {
			i.setFitnessValue(i.fitness());
			populationFitnessSum=populationFitnessSum+i.getFitnessValue();
		}
		percent();
		//elitism();
	}
		
	
}
