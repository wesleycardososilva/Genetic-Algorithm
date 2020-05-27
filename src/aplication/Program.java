package aplication;

import core.Parameters;
import entities.Chromossomes;

public class Program {

	public static void main(String[] args) {
		String path = "C:\\temp\\parameters.txt";
		Parameters parameters= new Parameters(path);
		
		parameters.readFile();
		RunGA run = new RunGA(parameters.getNumberOfIndividuals(),parameters.getMutationRate() ,parameters.getCrossOverRate(), parameters.getNumberOfDecisionVariables(),parameters.getNumberOfGenerations());
		//run.start(parameters);
		run.start(parameters);
		
	}

}
