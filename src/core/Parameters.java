package core;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import entities.Chromossomes;

public class Parameters {
	
	private String path;
	public   Integer numberOfIndividuals;
	public   Double mutationRate;
	public   Double crossOverRate;
	public   Integer numberOfDecisionVariables;
	public   Integer numberOfGenerations;
	public   Chromossomes chromossome;
	

	
	public Parameters(String path) {
		super();
		this.path = path;
	}
	
	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public int getNumberOfIndividuals() {
		return numberOfIndividuals;
	}

	public void setNumberOfIndividuals(int numberOfIndividuals) {
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

	public int getNumberOfDecisionVariables() {
		return numberOfDecisionVariables;
	}

	public void setNumberOfDecisionVariables(int numberOfDecisionVariables) {
		this.numberOfDecisionVariables = numberOfDecisionVariables;
	}

	public int getNumberOfGenerations() {
		return numberOfGenerations;
	}

	public void setNumberOfGenerations(int numberOfGenerations) {
		this.numberOfGenerations = numberOfGenerations;
	}
	
	
public  void readFile() {
		
		
		try (BufferedReader br = new BufferedReader(new FileReader(path))) {
		String line = br.readLine();
			this.numberOfIndividuals= Integer.parseInt(line);
			line = br.readLine();
			this.mutationRate= Double.parseDouble(line);
			line = br.readLine();
			this.crossOverRate= Double.parseDouble(line);
			line = br.readLine();
			this.numberOfDecisionVariables= Integer.parseInt(line);
			line = br.readLine();
			this.numberOfGenerations= Integer.parseInt(line);
			line = br.readLine();
			this.chromossome= new Chromossomes(numberOfDecisionVariables);
			
			
			
			for(int i =0 ; i<numberOfDecisionVariables;i++)	{				
				this.chromossome.minConstraints[i]=Double.parseDouble(line);
				line = br.readLine();
				this.chromossome.maxConstraints[i]= Double.parseDouble(line);
				line = br.readLine();
			}
			
		} catch (IOException e) {
		System.out.println("Error: " + e.getMessage());
		}
		
	}	
}
