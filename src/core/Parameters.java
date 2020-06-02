package core;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

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
/*Function to set the parameters of the genetic algorthm*/	
public void setFile() {
	//String path = "C:\\temp\\parameters.json";
	FileWriter writeParams = null; 
	JSONObject obJson = new JSONObject();
	obJson.put("NumberOfIndividuals", "800");
	obJson.put("MutationRate", "0.2");
	obJson.put("CrossoverRate", "0.9");
	obJson.put("NumberOfDecisionVariables", "2");
	obJson.put("NumberOfGenerations", "1000");
	JSONArray minConstraints = new JSONArray();
	JSONArray maxConstraints = new JSONArray();
	minConstraints.add("-5.12");
	minConstraints.add("-5.12");
	maxConstraints.add("5.12");
	maxConstraints.add("5.12");
	obJson.put("minConstraints", minConstraints);
	obJson.put("maxConstraints", maxConstraints);
	
	try {
		writeParams= new FileWriter("C:\\temp\\params.json");
		writeParams.write(obJson.toJSONString());
		writeParams.close();
	}catch(IOException msg) {
		System.out.println("Error: " + msg.getMessage());
	}
}
public void writeResult(double bestfitness,double []variables) {
	FileWriter writeResult = null;
	JSONObject obJson = new JSONObject();
	JSONArray decisionVariables = new JSONArray();
	obJson.put("Best fitness value", bestfitness);
	for(int i=0;i<numberOfDecisionVariables;i++) {
		decisionVariables.add(variables[i]);
	}
	obJson.put("Decision Variables", decisionVariables);

	try {
		writeResult= new FileWriter("C:\\temp\\result.json", true);
		writeResult.append(obJson.toJSONString());
		//writeParams.write(obJson.toJSONString());
		writeResult.close();
		
	}catch(IOException msg) {
		System.out.println("Error: " + msg.getMessage());
	}
}
/*Another way to read the parameters of the genetic algorithm whithout using json*/	
public  void readFile2() {
		
		
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
public  void readFile() {	
	JSONParser jsonParser = new JSONParser();	
	
	try {
		
		JSONObject jSonObject=(JSONObject) jsonParser.parse(new FileReader("C:\\temp\\params.json"));
		JSONArray minConstraints =(JSONArray)jSonObject.get(jSonObject);
		JSONArray maxConstraints =(JSONArray)jSonObject.get(jSonObject);
		this.numberOfIndividuals= Integer.parseInt((String) jSonObject.get("NumberOfIndividuals"));
		this.mutationRate= Double.parseDouble((String) jSonObject.get("MutationRate"));
		this.crossOverRate= Double.parseDouble((String) jSonObject.get("CrossoverRate"));
		this.numberOfDecisionVariables=Integer.parseInt((String) jSonObject.get("NumberOfDecisionVariables"));		
		this.numberOfGenerations= Integer.parseInt((String) jSonObject.get("NumberOfGenerations"));
		Chromossomes c= new Chromossomes(numberOfDecisionVariables);
		this.chromossome=c;		
		JSONArray minJsonArray = (JSONArray) jSonObject.get("minConstraints");
		JSONArray maxJsonArray = (JSONArray) jSonObject.get("maxConstraints");
		for(int i =0;i<numberOfDecisionVariables;i++) {
			this.chromossome.minConstraints[i]= Double.parseDouble((String) minJsonArray.get(i));
			this.chromossome.maxConstraints[i]= Double.parseDouble((String) maxJsonArray.get(i));
		}		
	}catch(FileNotFoundException e) {
		e.printStackTrace();
	}catch(IOException e1) {
		e1.printStackTrace();
	} catch (ParseException e) {
		e.printStackTrace();
	}
	
}

}
