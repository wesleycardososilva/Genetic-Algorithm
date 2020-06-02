package core;

import java.io.FileWriter;
import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class FileGa {

	public FileGa() {
		
	}
	
	
	public void setFile() {
		String path = "C:\\temp\\parameters.txt";
		FileWriter writeParams = null; 
		JSONObject obJson = new JSONObject();
		obJson.put("Number of individuals", "900");
		obJson.put("Mutation rate", "0.2");
		obJson.put("Crossover rate", "0.9");
		obJson.put("Number of decision variables", "2");
		obJson.put("Number of generations", "800");
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
}
