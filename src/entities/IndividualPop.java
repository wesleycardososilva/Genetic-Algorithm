package entities;

public class IndividualPop extends Individual {


	public IndividualPop(Double fitnessValue, Double fitnessPercent, Chromossomes chromossomes, int index) {
		super(fitnessValue, fitnessPercent, chromossomes, index);
		// TODO Auto-generated constructor stub
	}

	public double fitness() {
		
		return(20+Math.pow(chromosomes.chromossomes[0], 2)+Math.pow(chromosomes.chromossomes[1], 2)-10*(Math.cos(chromosomes.chromossomes[0]*2*Math.PI+chromosomes.chromossomes[1]*2*Math.PI)));//The objective function must be placed here
							// fitnessValue will receive the result of the calculus of the objective function.
							//use each position of the chromossome array as a decision variable of your objective function 
	}

	@Override
	public String toString() {
		return "IndividualPop [chromosomes=" + chromosomes + ", fitness()=" + fitness() + ", getChromosomes()="
				+ getChromosomes() + ", getFitnessValue()=" + getFitnessValue() + ", getFitnessPercent()="
				+ getFitnessPercent() + ", getIndex()=" + getIndex() + ", toString()=" + super.toString() + "]";
	}

	

	

}
