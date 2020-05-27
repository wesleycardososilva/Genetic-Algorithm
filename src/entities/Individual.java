package entities;

public abstract class Individual implements Comparable <Individual>  {
	public  Chromossomes chromosomes;
	private Double fitnessValue;
	private Double fitnessPercent;
	private int index;
	
	
	
	public Individual() {
	}


	public Individual( Double fitnessValue, Double fitnessPercent, Chromossomes chromossomes, int index) {
		super();
		this.fitnessValue = fitnessValue;
		this.fitnessPercent = fitnessPercent;
		this.chromosomes= chromossomes;
		this.index= index;
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

	@Override public int compareTo(Individual individual) { 
		if (this.fitnessPercent < individual.getFitnessPercent()) { 
			  return -1; 
			  } if (this.fitnessValue > individual.getFitnessValue()) { //ascending order sort for minimization problem
			  return 1; 
			  } 
		return 0;
	}
	
	public double fitness() {
		double result;
		result=(20+Math.pow(chromosomes.chromossomes[0], 2)+Math.pow(chromosomes.chromossomes[1], 2)-10*((Math.cos(chromosomes.chromossomes[0]*2*Math.PI)+(Math.cos(chromosomes.chromossomes[1]*2*Math.PI)))));//The objective function must be placed here
		return (result*(-1));//if its a minimization problema you must have multiply by -1
	}	
	

}
