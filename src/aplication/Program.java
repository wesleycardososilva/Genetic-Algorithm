package aplication;

import java.io.FileWriter;

import org.json.simple.JSONObject;

import core.Parameters;

public class Program {

	public static void main(String[] args) {
		String path = "C:\\temp\\params.json";
		Parameters parameters= new Parameters(path);
		
		parameters.setFile();
		parameters.readFile();
		RunGA run = new RunGA(parameters.getNumberOfIndividuals(),parameters.getMutationRate() ,parameters.getCrossOverRate(), parameters.getNumberOfDecisionVariables(),parameters.getNumberOfGenerations());
		for(int i=0;i<3;i++) {
			run.start(parameters);
		}
		parameters.setFile();
	}

}
