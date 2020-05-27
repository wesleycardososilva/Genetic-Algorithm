package entities;

import java.util.Arrays;

public class Chromossomes {
	
	
	public double[] chromossomes;
	public double[] minConstraints;
	public double[] maxConstraints;
	
	public Chromossomes(int size) {
		chromossomes = new double[size];
		minConstraints = new double[size];
		maxConstraints = new double[size];
		
	}
	public Chromossomes() {
		
	}
	public double[] getChromossomes() {
		return chromossomes;
	}

	public void setChromossomes(double[] chromossomes) {
		this.chromossomes = chromossomes;
	}
	public double[] getMinConstraints() {
		return minConstraints;
	}
	public void setMinConstraints(double[] minConstraints) {
		this.minConstraints = minConstraints;
	}
	public double[] getMaxConstraints() {
		return maxConstraints;
	}
	public void setMaxConstraints(double[] maxConstraints) {
		this.maxConstraints = maxConstraints;
	}

	
	public double getChromossomeByposition(int index) {
		return this.chromossomes[index];
	}
	public void  setChromossomeByposition(int index, double chromossomes) {
		this.chromossomes[index]=chromossomes;
	}
	
}
