package core;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import entities.Chromossomes;

public class Parameters {
	
	private String path;
	private   Integer numberOfIndividuals;
	private   Double mutationRate;
	private   Double crossOverRate;
	private   Integer numberOfDecisionVariables;
	private   Integer numberOfGenerations;
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
		//while (line != null) {
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
			
			
			//while (line != null) {
			for(int i =0 ; i<numberOfDecisionVariables;i++)	{
				//System.out.printf("valor de i no comeco %d \n",i);
				this.chromossome.minConstraints[i]=Double.parseDouble(line);
				//chromossome.minConstraints[i]= Double.parseDouble(line);
				line = br.readLine();
				//System.out.printf("valor da restricao %f \n",chromossome.minConstraints[i]);
				this.chromossome.maxConstraints[i]= Double.parseDouble(line);
				line = br.readLine();
				//System.out.printf("valor da restricao %f \n",chromossome.maxConstraints[i]);
				//System.out.printf("valor de i  %d \n",i);
				
			}
			//this.minConstraint= Double.parseDouble(line);
			//line = br.readLine();
			//this.maxConstraint= Double.parseDouble(line);
			//line = br.readLine();
			
		//}
		} catch (IOException e) {
		System.out.println("Error: " + e.getMessage());
		}
		
	}


	
	
	
	
}
