package entities;

public class IndividualPop implements Comparable <IndividualPop>  {
	/* comeca aqui o teste para transformar individualPop em uma classe que não implementa interface*/
	public  Chromossomes chromosomes;
	private Double fitnessValue;
	private Double fitnessPercent;
	private int index;


	public IndividualPop( Double fitnessValue, Double fitnessPercent, Chromossomes chromossomes, int index) {
		super();
		this.fitnessValue = fitnessValue;
		this.fitnessPercent = fitnessPercent;
		this.chromosomes= chromossomes;
		this.index= index;
	}	

	public IndividualPop() {
	}
		

	public Chromossomes getChromosomes() {
		return chromosomes;
	}

	public void setChromosomes(Chromossomes chromosomes) {
		this.chromosomes = chromosomes;
	}

	public Double getFitnessValue() {
		return fitnessValue;
	}

	public void setFitnessValue(Double fitnessValue) {
		this.fitnessValue = fitnessValue;
	}

	public Double getFitnessPercent() {
		return fitnessPercent;
	}

	public void setFitnessPercent(Double fitnessPercent) {
		this.fitnessPercent = fitnessPercent;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public double fitness() {
		double result;
		result=(20+Math.pow(chromosomes.chromossomes[0], 2)+Math.pow(chromosomes.chromossomes[1], 2)-10*((Math.cos(chromosomes.chromossomes[0]*2*Math.PI)+(Math.cos(chromosomes.chromossomes[1]*2*Math.PI)))));//The objective function must be placed here
							// fitnessValue will receive the result of the calculus of the objective function.
							//use each position of the chromossome array as a decision variable of your objective function 
		return (result*(-1));//if its a minimization problem you must have multiply by -1
	}

	@Override public int compareTo(IndividualPop individual) { 
		if (this.fitnessPercent < individual.getFitnessPercent()) { 
			  return -1; 
			  } if (this.fitnessValue > individual.getFitnessValue()) { //ascending order sort for minimization problem
			  return 1; 
			  } 
		return 0;
	}
	
	
	
	
	
	@Override
	public String toString() {
		return "IndividualPop [chromosomes=" + chromosomes + ", fitness()=" + fitness() + ", getChromosomes()="
				+ getChromosomes() + ", getFitnessValue()=" + getFitnessValue() + ", getFitnessPercent()="
				+ getFitnessPercent() + ", getIndex()=" + getIndex() + ", toString()=" + super.toString() + "]";
	}

}
