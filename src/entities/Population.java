package entities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import com.sun.jmx.remote.util.OrderClassLoaders;

public class Population  {
	
	private  List<IndividualPop> population = new ArrayList();
	private static Random random = new Random();
	private  Double crossOverRate;
	private  Double mutationRate;
	private  int populationSize;
	private static int iterationNumber;
	private int numberOfDecisionVariables;
	public double populationFitnessSum;
	public   Chromossomes chromossome;
	public int [] []matrixIndexCouple;
	public int  coupleQuantity;
	
	

	




	public Population(Double crossOverRate, Double mutationRate, int populationSize, int numberOfDecisionVariables,
			Chromossomes chromossome) {
		super();
		this.crossOverRate = crossOverRate;
		this.mutationRate = mutationRate;
		this.populationSize = populationSize;
		this.numberOfDecisionVariables = numberOfDecisionVariables;
		this.chromossome = chromossome;
		this.matrixIndexCouple= new int [coupleQuantity][2];
	}



	public Population(Double crossOverRate, Double mutationRate, int populationSize, int numberOfDecisionVariables,
			Chromossomes chromossome, int coupleQuantity) {
		super();
		this.crossOverRate = crossOverRate;
		this.mutationRate = mutationRate;
		this.populationSize = populationSize;
		this.numberOfDecisionVariables = numberOfDecisionVariables;
		this.chromossome = chromossome;
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
	

/*public int[][] getMatrixIndexCouple() {
		return matrixIndexCouple;
	}*/



	/*public void setMatrixIndexCouple(int[][] matrixIndexCouple) {
		this.matrixIndexCouple = matrixIndexCouple;
	}*/



	/* Generate initial population using random numbers with their respective range defined by min and max constraint of each decision variables*/	
	public void generateInitialPopulation() {
		
		for(int i =0; i<populationSize;i++) {
			chromossome.setChromossomes(getRandomNumber());
			IndividualPop individual = new IndividualPop(0.0,0.0, chromossome,i);					
			individual.setFitnessValue(individual.fitness());//after calculate the fitness value we need the sum of all individuals fitness value,
			populationFitnessSum=populationFitnessSum +individual.getFitnessValue(); //so we can calculate the percentual value of every individual, this value will be used in the Selection function(Roullete)					 
			population.add(individual);	
			int index=population.indexOf(individual);
			System.out.printf("valor do cromossomo: %f %f do individuo %d  \n", individual.chromosomes.getChromossomeByposition(0),individual.chromosomes.getChromossomeByposition(1), individual.getIndex());
			//System.out.printf("valor de fitness%.2f \n",individual.getFitnessValue());
			
		}
		 
	}
	
/*Generate a random number defined by the min and max constraints of the decision variables*/	
	public double[] getRandomNumber() {
		double  decisionVariables[] = new double[numberOfDecisionVariables] ;//numberOfDecisionVariables
		for(int i =0 ; i<chromossome.minConstraints.length;i++) {
			decisionVariables[i]= (random.nextDouble()*chromossome.maxConstraints[i])+chromossome.minConstraints[i];
		}
		return (decisionVariables);		
	}
	
/*Calculate the corresponding percentual value of the fitness of every individual  */
	public void percent() {
		for(Individual individual: population ) {  
			individual.setFitnessPercent((individual.getFitnessValue()/populationFitnessSum));	
		}
		Collections.sort(population);
		//print();
		//System.out.printf("primeiro da lista %f\n", population.get(0).chromosomes.getChromossomeByposition(0));
		//System.out.printf("ultimo da lista %f\n", population.get(39).chromosomes.getChromossomeByposition(0));
	}
/*Returns the number of couples that will be on crossover, this number will be used to create a matrix with index of the individuals which will be in the crossover function*/
	/*public int prepareStructure(Double crossOverRate, int populationSize) {
		int aux;
		double result;
		result= (crossOverRate * populationSize)/2;//calculate the number of couples that will be in crossover based on crossover rate 
		aux= (int)Math.ceil(result);
		return(aux);
	}*/
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
	public void print1() {
		IndividualPop individual = new IndividualPop(0.0,0.0, chromossome,0);
		for(IndividualPop i : population) {
			
			int index=population.indexOf(individual);
			System.out.printf("valor de index do arrayList %d \n",index);
				//System.out.printf(" %f\n ",i.chromosomes.getChromossomeByposition(0) );
				//System.out.printf(" valor de index%d\n ",i.getIndex() );
				//System.out.printf("valor de percent %f\n ",i.getFitnessPercent());
			
			
		}
	}
/*Function used to make selection, generate a random number [0,1[ and check if there is an individual with a percentual value in that range */
/*Pick a random number and search for the corresponding individual*/	
	public int roulete() {
		double total=0, roulleteValue;
		int j=0, lastIndividual=0;
		roulleteValue=random.nextDouble();//spin the wheel,the wheel is divided acordding  to the percentual of the individuals, 
		for(Individual i : population) {//if there is an individual with 50% of the fitness somatorium, it will have half of the wheell as its range, so it will have 50% of chance.
		
			if(roulleteValue>=total) {//roulleteValue is compared to fitnessPercent, if its not in the range, total will acumulate the value in order to advance in the search for the winner
				total=total+ i.getFitnessPercent();// total starts with 0, if the first element is the winner, it will picked in the second loop. 
			}else {
				//System.out.printf(" valor de roulete %f:  ", roulleteValue);
				//System.out.printf(" valor de index %d ", i.getIndex());
				//System.out.printf(" valor de index %f\n ", i.getFitnessPercent());
				return i.getIndex();	
			}
			lastIndividual= i.getIndex();
		}
		return lastIndividual;//Always wil be an individual that fits in the random number, if wasn't anyone of the others, so it's the last one. 
	}
		
	public void crosover() {
		int end, begin;
		int kcross= random.nextInt(numberOfDecisionVariables);
		//System.out.printf("valor de kcross %d", kcross);
		int direction= random.nextInt(2);//there are two directions: from the begining to kcross, or from kcross to the end.
		IndividualPop child1 = new IndividualPop(0.0, 0.0, chromossome, populationSize+1);
		IndividualPop child2 = new IndividualPop(0.0, 0.0, chromossome, populationSize+1);
		IndividualPop parent1 = new IndividualPop(0.0, 0.0, chromossome, populationSize+2);
		IndividualPop parent2 = new IndividualPop(0.0, 0.0, chromossome, populationSize+3);
		
		if(direction==1) {
			end= numberOfDecisionVariables;
		    begin= kcross;
		}else {
			end= kcross;
			begin=0;
		}
		for(int i =0; i< coupleQuantity;i++) {
			for(int j=begin;j<end;j++) {
				
				for(IndividualPop indi:population) {
					System.out.printf("**valor de cromossomo no for each %f %f valor do index %d\n ", indi.chromosomes.getChromossomeByposition(0),indi.chromosomes.getChromossomeByposition(0), indi.getIndex());
				}
				
				IndividualPop ind = new IndividualPop(0.0, 0.0, chromossome, matrixIndexCouple[i][0]);
				ind=population.get(i);
				
				System.out.printf("*****valor de matrixCouple dentro do for%d",matrixIndexCouple[i][0] );
				System.out.printf("****valor do cromossomo no for de teste %f \n", ind.chromosomes.getChromossomeByposition(j));
				//System.out.printf("valor de j dentro do for %d\n", j);
				/*
				parent1.chromosomes.setChromossomeByposition(j, (population.get(matrixIndexCouple[i][0]).chromosomes.getChromossomeByposition(j)*0.5));
				parent2.chromosomes.setChromossomeByposition(j, (population.get(matrixIndexCouple[i][1]).chromosomes.getChromossomeByposition(j)*0.5));
				child1.chromosomes.setChromossomeByposition(j, (parent1.chromosomes.getChromossomeByposition(j)+parent2.chromosomes.getChromossomeByposition(j)));
				
				IndividualPop ind = new IndividualPop(0.0, 0.0, chromossome, matrixIndexCouple[0][0]);
				//ind=population.get(i);
				ind=population.get(matrixIndexCouple[0][0]);
				System.out.printf(" valor de i  %d\n ",i );
				System.out.printf(" valor de cromossomo de parent1 %f  %f\n ",parent1.chromosomes.getChromossomeByposition(0),parent1.chromosomes.getChromossomeByposition(1) );
				System.out.printf("valor do primeiro individuo: %d na lista %f\n",matrixIndexCouple[0][0], ind.chromosomes.getChromossomeByposition(j));
				//System.out.printf(" valor de cromossomo do pai1 1 %f\n ",parent1.chromosomes.getChromossomeByposition(j) );
				System.out.printf(" valor de cromossomo  %f\n ",ind.chromosomes.getChromossomeByposition(0) );
				System.out.printf(" valor de cromossomo  %f\n ",ind.chromosomes.getChromossomeByposition(1) );
				System.out.printf(" valor de index  %d\n ",ind.getIndex() );
				
				parent1.chromosomes.setChromossomeByposition(j, (population.get(matrixIndexCouple[i][0]).chromosomes.getChromossomeByposition(j)*0.9));
				parent2.chromosomes.setChromossomeByposition(j, (population.get(matrixIndexCouple[i][1]).chromosomes.getChromossomeByposition(j)*0.1));
				child2.chromosomes.setChromossomeByposition(j, (parent1.chromosomes.getChromossomeByposition(j)+parent2.chromosomes.getChromossomeByposition(j)));
				*/
			}
			if(direction==0) {
				for(int k =kcross;k< numberOfDecisionVariables; k++) {
					//System.out.printf("valor de k dentro do for %d \n", k);
					child1.chromosomes.setChromossomeByposition(k,population.get(matrixIndexCouple[i][0]).chromosomes.getChromossomeByposition(k));
					child2.chromosomes.setChromossomeByposition(k, population.get(matrixIndexCouple[i][1]).chromosomes.getChromossomeByposition(k));
				}
			}else {
				for(int m=0; m<kcross;m++) {
					//System.out.printf("valor de k dentro do for %d \n", m);
					child1.chromosomes.setChromossomeByposition(m,population.get(matrixIndexCouple[i][0]).chromosomes.getChromossomeByposition(m));
					child2.chromosomes.setChromossomeByposition(m, population.get(matrixIndexCouple[i][1]).chromosomes.getChromossomeByposition(m));
				}
			}
			child1.setIndex(this.populationSize+i);
			child2.setIndex(this.populationSize+this.coupleQuantity+i);
			
			population.add(child1);
			IndividualPop indi = new IndividualPop(0.0, 0.0, chromossome, 0);
			int index=population.indexOf(indi);
			//System.out.printf("valor de index do filho 1 na lista %d \n",index);
			population.add(child2);
		}
		
		//System.out.println(population);
		
		/*System.out.printf("valor do cromossomo 0 do pai 1 %f valor do cromossomo 1 do pai 1 %f\n", population.get(matrixIndexCouple[0][0]).chromosomes.getChromossomeByposition(0),population.get(matrixIndexCouple[0][0]).chromosomes.getChromossomeByposition(1));
		System.out.printf("valor do cromossomo 0 do filho %f valor do cromossomo 1 do filho1 %f\n", population.get(50).chromosomes.getChromossomeByposition(0),population.get(50).chromosomes.getChromossomeByposition(1));
		System.out.printf("valor do cromossomo 0 do pai 1 %f valor do cromossomo 1 do pai 1 %f\n", population.get(matrixIndexCouple[1][0]).chromosomes.getChromossomeByposition(0),population.get(matrixIndexCouple[1][0]).chromosomes.getChromossomeByposition(1));
		System.out.printf("valor do cromossomo 0 do filho %f valor do cromossomo 1 do filho1 %f\n", population.get(51).chromosomes.getChromossomeByposition(0),population.get(51).chromosomes.getChromossomeByposition(1));
		System.out.printf("valor do cromossomo 0 do individuo 0 %f valor do cromossomo 1do individuo 0 %f\n", population.get(0).chromosomes.getChromossomeByposition(0),population.get(51).chromosomes.getChromossomeByposition(1));
		System.out.printf("valor do primeiro individuo %f",population.get(0).getChromosomes().getChromossomeByposition(0) );*/
	}

	
	
	@Override
	public String toString() {
		return "Population [chromossome=" + chromossome + ", toString()=" + super.toString() + "]";
	}






	
	
}
