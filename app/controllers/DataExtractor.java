package controllers;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.regex.Pattern;

import models.Medication;

public class DataExtractor {
	
	private BufferedReader reader;
	
	/**
	 * Constructor
	 * @param fileName
	 */
	public DataExtractor(String fileName){
		try {
			
			this.reader = new BufferedReader(new FileReader(fileName));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

	public List<Medication> getMedication(){
		//Pattern medicationPatter = Pattern.compile("(-+)(\\s*)MEDICATIONS AND SUPPLEMENTS", Pattern.CASE_INSENSITIVE);
		
		String line = new String();
		try {
			while((line = reader.readLine()) != null){
				if(line.matches("^-+\\s*MEDICATIONS.*")){
					System.out.println("Got it!");
					System.out.println(line);
				}
					
				
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
}
